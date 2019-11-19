package com.chmei.nzbcommon.http.param;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.chmei.nzbcommon.http.RequestBodyType;

/**
 * @author lixinjie
 * @since 2018-05-16
 */
public class FileParam extends FormParam {

	public FileParam() {
		super();
		setRequestBodyType(RequestBodyType.FILE);
	}
	
	public void addFile(String name, String file) throws IOException {
		addFile(name, FileUtils.getFile(file));
	}
	
	public void addFile(String name, File file) throws IOException {
		addFile(name, FileUtils.openInputStream(file));
	}
	
	public void addFile(String name, InputStream inputStream) {
		addFile(name, new InputStreamResource(inputStream));
	}
	
	public void addFile(String name, Resource resource) {
		addField(name, resource);
	}
	
	public void addField(String name, Object value) {
		addFieldInternal(name, value);
	}
}
