package com.chmei.nzbcommon.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
public class RequestResponseLoggingFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String method = httpRequest.getMethod();
		String contentType = httpRequest.getContentType();
		String queryString = httpRequest.getQueryString() == null ? "" : "?" + httpRequest.getQueryString();
		log.info("{} {} {}", method, httpRequest.getRequestURL().append(queryString), contentType);
		if (!isFileUpload(method, contentType)) {
			RepeatableHttpServletRequest repeatableRequest = new RepeatableHttpServletRequest(httpRequest);
			log.info("{}", repeatableRequest.getBody());
			httpRequest = repeatableRequest;
		}
		RepeatableHttpServletResponse repeatableResponse = new RepeatableHttpServletResponse((HttpServletResponse)response);
		chain.doFilter(httpRequest, repeatableResponse);
		String contentDisposition = repeatableResponse.getHeader("Content-Disposition");
		contentType = repeatableResponse.getContentType();
		log.info("{} {}", contentDisposition, contentType);
		if (!isFileDownload(contentDisposition, contentType) && !isJs(contentType) && !isCss(contentType) && !isImage(contentType)) {
			log.info("{}", repeatableResponse.getBody());
		}
	}

	@Override
	public void destroy() {
		
	}

	private boolean isFileUpload(String method, String contentType) {
		if (!"post".equalsIgnoreCase(method)) {
			return false;
		}
		return (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
	}
	
	private boolean isFileDownload(String contentDisposition, String contentType) {
		return (contentDisposition != null && contentDisposition.toLowerCase().contains("attachment"));
	}
	
	private boolean isJs(String contentType) {
		return contentType == null || contentType.contains("javascript");
	}
	
	private boolean isCss(String contentType) {
		return contentType == null || contentType.contains("css");
	}
	
	private boolean isImage(String contentType) {
		return contentType == null || contentType.contains("image");
	}
	
	private static class RepeatableHttpServletRequest extends HttpServletRequestWrapper {

		private RepeatableServletInputStream inputStream;
		private BufferedReader reader;
		private Map<String, List<String>> params;
		
		public RepeatableHttpServletRequest(HttpServletRequest request) throws IOException {
			super(request);
			inputStream = new RepeatableServletInputStream(request.getInputStream());
		}
		
		@Override
		public String getParameter(String name) {
			if (!isForm()) {
				return super.getParameter(name);
			}
			if (params == null) {
				params = resolveParameters();
			}
			List<String> values = params.get(name);
			if (values != null && !values.isEmpty()) {
				return values.get(0);
			}
			return null;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			if (!isForm()) {
				return super.getParameterMap();
			}
			if (params == null) {
				params = resolveParameters();
			}
			Map<String, String[]> ps = new HashMap<>();
			for (Map.Entry<String, List<String>> entry : params.entrySet()) {
				ps.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
			}
			return ps;
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			if (!isForm()) {
				return super.getParameterNames();
			}
			if (params == null) {
				params = resolveParameters();
			}
			final Iterator<String> iterator = params.keySet().iterator();
			return new Enumeration<String>() {
				
				private Iterator<String> iter = iterator;
				
				@Override
				public boolean hasMoreElements() {
					return iter.hasNext();
				}

				@Override
				public String nextElement() {
					return iter.next();
				}};
		}
		
		@Override
		public String[] getParameterValues(String name) {
			if (!isForm()) {
				return super.getParameterValues(name);
			}
			if (params == null) {
				params = resolveParameters();
			}
			List<String> values = params.get(name);
			if (values != null && !values.isEmpty()) {
				return values.toArray(new String[values.size()]);
			}
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return inputStream;
		}
		
		@Override
		public BufferedReader getReader() throws IOException {
			if (reader == null) {
				reader = getNewReader();
			}
			return reader;
		}
		
		public String getBody() throws IOException {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = getNewReader();
			char[] buf = new char[1024];
			int count;
			while ((count = reader.read(buf, 0, buf.length)) > 0) {
				sb.append(buf, 0, count);
			}
			reader.close();
			return sb.toString();
		}
		
		private BufferedReader getNewReader() throws UnsupportedEncodingException {
			String charset = getCharacterEncoding();
			return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputStream.getDatas()), charset != null ? charset : "UTF-8"));
		}
		
		private boolean isForm() {
			String contentType = getContentType();
			return contentType != null && contentType.contains("application/x-www-form-urlencoded");
		}
		
		private Map<String, List<String>> resolveParameters() {
			String charset = getCharacterEncoding();
			Map<String, List<String>> params = new HashMap<>();
			try {
				String body = getBody();
				String queryString = getQueryString();
				if (queryString != null && !queryString.isEmpty()) {
					body = (!body.isEmpty() ? body + "&" : "")  + queryString;
				}
				String[] paramArray = body.split("[&]");
				for (String param : paramArray) {
					String[] nv = param.split("=");
					List<String> values = params.get(nv[0]);
					if (values == null) {
						values = new ArrayList<>();
						params.put(nv[0], values);
					}
					values.add(nv.length > 1 ? URLDecoder.decode(nv[1], charset != null ? charset : "UTF-8") : "");
				}
			} catch (IOException e) {
				
			}
			return params;
		}
	}
	
	private static class RepeatableHttpServletResponse extends HttpServletResponseWrapper {

		private RepeatableServletOutputStream outputStream;
		private PrintWriter writer;
		
		public RepeatableHttpServletResponse(HttpServletResponse response) throws IOException {
			super(response);
			outputStream = new RepeatableServletOutputStream(response.getOutputStream());
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return outputStream;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			if (writer == null) {
				writer = new PrintWriter(outputStream);
			}
			return writer;
		}
		
		public String getBody() throws IOException {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = getNewReader();
			char[] buf = new char[1024];
			int count;
			while ((count = reader.read(buf, 0, buf.length)) > 0) {
				sb.append(buf, 0, count);
			}
			reader.close();
			return sb.toString();
		}
		
		private BufferedReader getNewReader() throws UnsupportedEncodingException {
			String charset = getCharacterEncoding();
			return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.getDatas()), charset != null ? charset : "UTF-8"));
		}
	}
	
	private static class RepeatableServletInputStream extends ServletInputStream {

		private ByteArrayInputStream in;
		private byte[] datas;
		
		public RepeatableServletInputStream(ServletInputStream inputStream) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 10);
			byte[] buf = new byte[1024];
			int count;
			while ((count = inputStream.read(buf, 0, buf.length)) > 0) {
				out.write(buf, 0, count);
			}
			datas = out.toByteArray();
			in = new ByteArrayInputStream(datas);
			inputStream.close();
			out.close();
		}
		
		@Override
		public int read() throws IOException {
			return in.read();
		}

		@Override
		public long skip(long n) throws IOException {
			return in.skip(n);
		}
		
		@Override
		public int available() throws IOException {
			return in.available();
		}
		
		@Override
		public void close() throws IOException {
			in.close();
		}
		
		@Override
		public synchronized void mark(int readlimit) {
			in.mark(readlimit);
		}
		
		@Override
		public synchronized void reset() throws IOException {
			in.reset();
		}
		
		@Override
		public boolean markSupported() {
			return in.markSupported();
		}
		
		public byte[] getDatas() {
			return datas;
		}
	}

	private static class RepeatableServletOutputStream extends ServletOutputStream {

		private ServletOutputStream outputStream;
		private ByteArrayOutputStream output;
		
		public RepeatableServletOutputStream(ServletOutputStream outputStream) {
			this.outputStream = outputStream;
			this.output = new ByteArrayOutputStream(1024 * 10);
		}
		
		@Override
		public void write(int b) throws IOException {
			outputStream.write(b);
			output.write(b);
		}
		
		@Override
		public void flush() throws IOException {
			outputStream.flush();
			output.flush();
		}

		@Override
		public void close() throws IOException {
			outputStream.close();
			output.close();
		}
		
		public byte[] getDatas() {
			return output.toByteArray();
		}
	}

}
