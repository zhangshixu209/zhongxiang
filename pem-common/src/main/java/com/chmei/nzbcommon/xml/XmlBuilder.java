package com.chmei.nzbcommon.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据确定的值构造Xml
 * @author lixinjie
 * @since 2018-05-24
 */
public class XmlBuilder {

	private char declQuote;
	private char doctQuote;
	private char attrQuote;
	
	private IDocument document;
	private ICNode currentElement;
	
	public XmlBuilder() {
		this('"', '"', '"');
	}
	
	public XmlBuilder(char declQuote, char doctQuote, char attrQuote) {
		this.declQuote = declQuote;
		this.doctQuote = doctQuote;
		this.attrQuote = attrQuote;
		document = new Document(null, "");
	}
	
	public XmlBuilder declaration() {
		return declaration("1.0", "UTF-8");
	}
	
	public XmlBuilder declaration(String version, String encoding) {
		document.setDeclaration(new Declaration(document, version, encoding, declQuote));
		return this;
	}
	
	public XmlBuilder docType(String name, String publicID, String systemID, String... externalDTDs) {
		document.setDocType(new DocType(document, name, doctQuote, publicID, systemID, externalDTDs));
		return this;
	}
	
	public XmlBuilder root(String name) {
		return root(null, name);
	}
	
	public XmlBuilder root(String namespace, String name) {
		return root(namespace, name, false);
	}
	
	public XmlBuilder root(String namespace, String name, boolean selfClosing) {
		Element root = new Element(document, namespace, name, selfClosing);
		document.setRoot(root);
		currentElement = root;
		return this;
	}
	
	public XmlBuilder element(String name) {
		return element(null, name);
	}
	
	public XmlBuilder element(String namespace, String name) {
		return element(namespace, name, false);
	}
	
	public XmlBuilder element(String namespace, String name, boolean selfClosing) {
		Element element = new Element(currentElement, namespace, name, selfClosing);
		currentElement.addChild(element);
		currentElement = element;
		return this;
	}
	
	public XmlBuilder namespace(String prefix, String uri) {
		((IElement)currentElement).addNamespace(new Namespace(currentElement, prefix, uri, attrQuote));
		return this;
	}
	
	public XmlBuilder attribute(String name, String value) {
		return attribute(null, name, value);
	}
	
	public XmlBuilder attribute(String namespace, String name, String value) {
		((IElement)currentElement).addAttribute(new Attribute(currentElement, namespace, name, value, attrQuote));
		return this;
	}
	
	public XmlBuilder text(Object text) {
		currentElement.addChild(new Text(currentElement, text));
		return this;
	}
	
	public XmlBuilder text(String name, Object text) {
		return text(null, name, text);
	}
	
	public XmlBuilder text(String namespace, String name, Object text) {
		Element element = new Element(currentElement, namespace, name, false);
		element.addChild(new Text(element, text));
		currentElement.addChild(element);
		return this;
	}
	
	public XmlBuilder texts(String name, Object[] texts) {
		return texts(null, name, texts);
	}
	
	public XmlBuilder texts(String namespace, String name, Object[] texts) {
		currentElement.addChild(new Texts(currentElement, namespace, name, texts));
		return this;
	}
	
	public XmlBuilder cdata(Object cdata) {
		currentElement.addChild(new CData(currentElement, cdata));
		return this;
	}
	
	public XmlBuilder cdata(String name, Object cdata) {
		return cdata(null, name, cdata);
	}
	
	public XmlBuilder cdata(String namespace, String name, Object cdata) {
		Element element = new Element(currentElement, namespace, name, false);
		element.addChild(new CData(element, cdata));
		currentElement.addChild(element);
		return this;
	}
	
	public XmlBuilder cdatas(String name, Object[] cdatas) {
		return cdatas(null, name, cdatas);
	}
	
	public XmlBuilder cdatas(String namespace, String name, Object[] cdatas) {
		currentElement.addChild(new CDatas(currentElement, namespace, name, cdatas));
		return this;
	}
	
	public XmlBuilder comment(Object comment) {
		currentElement.addChild(new Comment(currentElement, comment));
		return this;
	}
	
	public XmlBuilder end() {
		currentElement = (ICNode)currentElement.getParent();
		return this;
	}
	
	public String toXml() {
		return toXml(false);
	}
	
	public String toXml(boolean format) {
		StringBuilder sb = new StringBuilder(1024);
		if (format) {
			document.toXml(sb, 0);
		} else {
			document.toXml(sb);
		}
		return sb.toString();
	}
	
	public interface IXNode {
		
		String getName();
		
		IXNode getParent();
		
		void toXml(StringBuilder sb);
		
		void toXml(StringBuilder sb, int indent);
	}
	
	public interface ICNode extends IXNode {

		void addChild(IXNode child);
		
		List<IXNode> getChildren();
	}

	public interface IElement extends ICNode {

		void addNamespace(Namespace namespace);
		
		List<Namespace> getNamespaces();
		
		void addAttribute(Attribute attribute);
		
		List<Attribute> getAttributes();
		
	}
	
	public interface IDocument extends IXNode {

		Declaration getDeclaration();
		
		void setDeclaration(Declaration declaration);
		
		DocType getDocType();
		
		void setDocType(DocType docType);
		
		IElement getRoot();
		
		void setRoot(IElement root);
	}
	
	public static abstract class XNode implements IXNode {
		
		protected IXNode parent;
		protected String name;
		protected char quote;
		
		protected XNode(IXNode parent, String name) {
			this(parent, name, '\0');
		}
		
		protected XNode(IXNode parent, String name, char quote) {
			this.parent = parent;
			this.name = name;
			this.quote = quote;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public IXNode getParent() {
			return parent;
		}
		
		char getQuote() {
			return quote;
		}
		
		void setQuote(char quote) {
			this.quote = quote;
		}
		
		@Override
		public abstract void toXml(StringBuilder sb);
		
		@Override
		public abstract void toXml(StringBuilder sb, int indent);
		
		void xmlIndent(StringBuilder sb, int indent) {
			for (int i = 0; i < indent; i++) {
				sb.append("    ");
			}
		}
		
		void xmlCRLF(StringBuilder sb) {
			sb.append('\r').append('\n');
		}
	}
	
	public static class Declaration extends XNode {

		private String version;
		private String encoding;
		
		public Declaration(IXNode parent, String version, String encoding, char quote) {
			super(parent, "xml", quote);
			this.version = version;
			this.encoding = encoding;
		}
		
		String getVersion() {
			return version;
		}
		
		String getEncoding() {
			return encoding;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append("<?");
			sb.append(getName());
			sb.append(" version=").append(getQuote()).append(version).append(getQuote());
			sb.append(" encoding=").append(getQuote()).append(encoding).append(getQuote());
			sb.append("?>");
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			sb.append("<?");
			sb.append(getName());
			sb.append(" version=").append(getQuote()).append(version).append(getQuote());
			sb.append(" encoding=").append(getQuote()).append(encoding).append(getQuote());
			sb.append("?>");
			xmlCRLF(sb);
		}
	}

	public static class QName extends XNode {

		private String prefix;
		
		public QName(IXNode parent, String prefix, String name) {
			super(parent, name);
			this.prefix = prefix;
		}
		
		public String getPrefix() {
			return prefix;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			if (prefix != null && !prefix.isEmpty()) {
				sb.append(prefix);
				sb.append(':');
			}
			sb.append(getName());
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			if (prefix != null && !prefix.isEmpty()) {
				sb.append(prefix);
				sb.append(':');
			}
			sb.append(getName());
		}
	}
	
	public static class Attribute extends XNode {

		private QName qname;
		private String value;
		
		public Attribute(IXNode parent, String prefix, String name, String value, char quote) {
			super(parent, name, quote);
			this.value = value;
			this.qname = new QName(this, prefix, name);
		}
		
		QName getQName() {
			return qname;
		}
		
		String getValue() {
			return value;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			qname.toXml(sb);
			sb.append('=');
			sb.append(getQuote()).append(value).append(getQuote());
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			qname.toXml(sb, indent);
			sb.append('=');
			sb.append(getQuote()).append(value).append(getQuote());
		}
	}

	public static class DocType extends XNode {

		private String publicID;
		private String systemID;
		private String[] externalDTDs;
		
		public DocType(IXNode parent, String name, char quote, String publicID, String systemID, String... externalDTDs) {
			super(parent, name, quote);
			this.publicID = publicID;
			this.systemID = systemID;
			this.externalDTDs = externalDTDs;
		}
		
		String getPublicID() {
			return publicID;
		}
		
		String getSystemID() {
			return systemID;
		}
		
		String[] getExternalDTDs() {
			return externalDTDs;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append("<!DOCTYPE ");
			sb.append(getName());
			if (publicID != null && !publicID.isEmpty()) {
				 sb.append(" PUBLIC ");
		         sb.append(getQuote()).append(publicID).append(getQuote());
			} else if (systemID != null && !systemID.isEmpty()) {
				sb.append(" SYSTEM ");
				sb.append(getQuote()).append(systemID).append(getQuote());
			}
			if (externalDTDs != null && externalDTDs.length > 0) {
				for (String dtd : externalDTDs) {
					sb.append(' ').append(getQuote());
					sb.append(dtd);
					sb.append(getQuote());
				}
			}
			sb.append('>');
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			sb.append("<!DOCTYPE ");
			sb.append(getName());
			if (publicID != null && !publicID.isEmpty()) {
				 sb.append(" PUBLIC ");
		         sb.append(getQuote()).append(publicID).append(getQuote());
			} else if (systemID != null && !systemID.isEmpty()) {
				sb.append(" SYSTEM ");
				sb.append(getQuote()).append(systemID).append(getQuote());
			}
			if (externalDTDs != null && externalDTDs.length > 0) {
				for (String dtd : externalDTDs) {
					xmlCRLF(sb);
					sb.append(' ').append(getQuote());
					sb.append(dtd);
					sb.append(getQuote());
				}
			}
			sb.append('>');
			xmlCRLF(sb);
		}
	}

	public static class Comment extends XNode {

		private Object comment;
		
		public Comment(IXNode parent, Object comment) {
			super(parent, "");
			this.comment = comment;
		}
		
		Object getComment() {
			return comment;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append("<!--");
			sb.append(comment);
			sb.append("-->");
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			xmlIndent(sb, indent);
			sb.append("<!--");
			sb.append(comment);
			sb.append("-->");
			xmlCRLF(sb);
		}
	}

	public static class Namespace extends XNode {

		private String prefix;
		private String uri;
		
		public Namespace(IXNode parent, String prefix, String uri, char quote) {
			super(parent, "", quote);
			this.prefix = prefix;
			this.uri = uri;
		}
		
		String getPrefix() {
			return prefix;
		}
		
		String getUri() {
			return uri;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append("xmlns");
			if (prefix != null && !prefix.isEmpty()) {
				sb.append(':').append(prefix);
			}
			sb.append('=').append(getQuote()).append(uri).append(getQuote());
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			sb.append("xmlns");
			if (prefix != null && !prefix.isEmpty()) {
				sb.append(':').append(prefix);
			}
			sb.append('=').append(getQuote()).append(uri).append(getQuote());
		}
	}

	public static class Text extends XNode {

		private Object text;
		
		public Text(IXNode parent, Object text) {
			super(parent, null);
			this.text = text;
		}
		
		Object getText() {
			return text;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append(text);
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			sb.append(text);
		}
	}
	
	public static class CData extends XNode {

		private Object cdata;
		
		public CData(IXNode parent, Object cdata) {
			super(parent, null);
			this.cdata = cdata;
		}
		
		Object getCData() {
			return cdata;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append("<![CDATA[");
			sb.append(cdata);
			sb.append("]]>");
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			xmlIndent(sb, indent);
			sb.append("<![CDATA[");
			sb.append(cdata);
			sb.append("]]>");
			xmlCRLF(sb);
		}
	}

	public static abstract class CNode extends XNode implements ICNode {

		protected List<IXNode> children = new ArrayList<>();
		
		protected CNode(IXNode parent, String name) {
			super(parent, name);
		}

		@Override
		public void addChild(IXNode child) {
			children.add(child);
		}

		@Override
		public List<IXNode> getChildren() {
			return children;
		}
	}

	public static class Texts extends CNode {

		private Object[] texts;
		private String namespace;
		private boolean prepared;
		
		public Texts(IXNode parent, String name, Object[] texts) {
			this(parent, null, name, texts);
		}
		
		public Texts(IXNode parent, String namespace, String name, Object[] texts) {
			super(parent, name);
			this.texts = texts;
			this.namespace = namespace;
			this.prepared = false;
		}
		
		Object[] getTexts() {
			return texts;
		}
		
		String getNamespace() {
			return namespace;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			prepare();
			for (IXNode child : getChildren()) {
				child.toXml(sb);
			}
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			prepare();
			for (IXNode child : getChildren()) {
				child.toXml(sb, indent);
			}
		}

		void prepare() {
			if (prepared) {
				return;
			}
			for (Object text : texts) {
				Element element = new Element(this, namespace, getName(), false);
				element.addChild(new Text(element, text));
				addChild(element);
			}
			prepared = true;
		}
	}
	
	public static class CDatas extends CNode {

		private Object[] cdatas;
		private String namespace;
		private boolean prepared;
		
		public CDatas(IXNode parent, String name, Object[] cdatas) {
			this(parent, null, name, cdatas);
		}
		
		public CDatas(IXNode parent, String namespace, String name, Object[] cdatas) {
			super(parent, name);
			this.cdatas = cdatas;
			this.namespace = namespace;
			this.prepared = false;
		}

		Object[] getCDatas() {
			return cdatas;
		}
		
		String getNamespace() {
			return namespace;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			prepare();
			for (IXNode child : getChildren()) {
				child.toXml(sb);
			}
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			prepare();
			for (IXNode child : getChildren()) {
				child.toXml(sb, indent);
			}
		}

		void prepare() {
			if (prepared) {
				return;
			}
			for (Object cdata : cdatas) {
				Element element = new Element(this, namespace, getName(), false);
				element.addChild(new CData(element, cdata));
				addChild(element);
			}
			prepared = true;
		}
	}
	
	public static class Element extends CNode implements IElement {
		
		private boolean selfClosing;
		private QName qname;
		private List<Namespace> namespaces;
		private List<Attribute> attributes;
		
		public Element(IXNode parent, String namespace, String name, boolean selfClosing) {
			super(parent, name);
			this.selfClosing = selfClosing;
			this.qname = new QName(this, namespace, name);
		}
		
		@Override
		public void addNamespace(Namespace namespace) {
			if (namespaces == null) {
				namespaces = new ArrayList<>();
			}
			namespaces.add(namespace);
		}
		
		@Override
		public List<Namespace> getNamespaces() {
			return namespaces;
		}
		
		@Override
		public void addAttribute(Attribute attribute) {
			if (attributes == null) {
				attributes = new ArrayList<>();
			}
			attributes.add(attribute);
		}
		
		@Override
		public List<Attribute> getAttributes() {
			return attributes;
		}
		
		public QName getQName() {
			return qname;
		}
		
		public boolean getSelfClosing() {
			return selfClosing;
		}
		
		@Override
		public void toXml(StringBuilder sb) {
			sb.append('<');
			qname.toXml(sb);
			
			if (namespaces != null && !namespaces.isEmpty()) {
				for (Namespace namespace : namespaces) {
					sb.append(' ');
					namespace.toXml(sb);
				}
			}
			
			if (attributes != null && !attributes.isEmpty()) {
				for (Attribute attribute : attributes) {
					sb.append(' ');
					attribute.toXml(sb);
				}
			}
			
			boolean hasChild = (getChildren() != null) && (!getChildren().isEmpty());
			if (hasChild || !selfClosing) {
				sb.append('>');
			} else {
				sb.append("/>");
			}
			
			if (hasChild) {
				for (IXNode child : getChildren()) {
					child.toXml(sb);
				}
			}
			
			if (hasChild || !selfClosing) {
				sb.append("</");
				qname.toXml(sb);
				sb.append('>');
			}
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			xmlIndent(sb, indent);
			sb.append('<');
			qname.toXml(sb, indent);
			
			if (namespaces != null && !namespaces.isEmpty()) {
				for (Namespace namespace : namespaces) {
					sb.append(' ');
					namespace.toXml(sb, indent);
				}
			}
			
			if (attributes != null && !attributes.isEmpty()) {
				for (Attribute attribute : attributes) {
					sb.append(' ');
					attribute.toXml(sb, indent);
				}
			}
			
			boolean hasChild = (getChildren() != null) && (!getChildren().isEmpty());
			if (hasChild || !selfClosing) {
				sb.append('>');
			} else {
				sb.append("/>");
			}
			
			if (!hasChild && selfClosing) {
				xmlCRLF(sb);
			} else if (hasChild && !(getChildren().get(0) instanceof Text)) {
				xmlCRLF(sb);
			}
			
			if (hasChild) {
				for (IXNode child : getChildren()) {
					child.toXml(sb, indent + 1);
				}
			}
			
			if (hasChild && !(getChildren().get(0) instanceof Text)) {
				xmlIndent(sb, indent);
			}
			
			if (hasChild || !selfClosing) {
				sb.append("</");
				qname.toXml(sb);
				sb.append('>');
				xmlCRLF(sb);
			}
		}
	}
	
	public static class Document extends XNode implements IDocument {

		private Declaration declaration;
		private DocType docType;
		private IElement root;
		
		public Document(IXNode parent, String name) {
			super(parent, name);
		}
		
		@Override
		public Declaration getDeclaration() {
			return declaration;
		}
		
		@Override
		public void setDeclaration(Declaration declaration) {
			this.declaration = declaration;
		}
		
		@Override
		public DocType getDocType() {
			return docType;
		}
		
		@Override
		public void setDocType(DocType docType) {
			this.docType = docType;
		}
		
		@Override
		public IElement getRoot() {
			return root;
		}
		
		@Override
		public void setRoot(IElement root) {
			this.root = root;
		}

		@Override
		public void toXml(StringBuilder sb) {
			if (declaration != null) {
				declaration.toXml(sb);
			}
			
			if (docType != null) {
				docType.toXml(sb);
			}
			if (root != null) {
				root.toXml(sb);
			}
		}

		@Override
		public void toXml(StringBuilder sb, int indent) {
			if (declaration != null) {
				declaration.toXml(sb, indent);
			}
			
			if (docType != null) {
				docType.toXml(sb, indent);
			}
			if (root != null) {
				root.toXml(sb, indent);
			}
		}
	}

}
