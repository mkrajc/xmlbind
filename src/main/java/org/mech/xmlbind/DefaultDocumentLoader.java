package org.mech.xmlbind;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Default implementation of Document loader using SAX. It is set to XSD
 * validation
 * 
 * @author martin.krajc
 * 
 */
public class DefaultDocumentLoader implements DocumentLoader {

	private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

	private static boolean IS_NAMESPACE_AWARE = true;
	private static boolean IS_VALIDATING = true;

	private ErrorHandler errorHandler = new DefaultErrorHandler();
	private EntityResolver entityResolver = new ClasspathResourceEntityResolver();

	/**
	 * {@inheritDoc}
	 */
	public final Document loadDocument(final InputStream xmlSourceStream) throws XmlException {
		Document parsedDoc = null;
		try {
			parsedDoc = createDocumentBuilder().parse(xmlSourceStream);
		} catch (XmlException e) {
			throw e;
		} catch (SAXParseException e) {
			throw new XmlParsingException("Exception during parsing of xml stream", e);
		} catch (Exception e) {
			throw new XmlException("Excepion caught", e);
		}
		return parsedDoc;
	}

	/**
	 * Create setup instance of {@link DocumentBuilderFactory}
	 * 
	 * @return {@link DocumentBuilderFactory}
	 */
	protected DocumentBuilderFactory createDocumentBuilderFactory() {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(IS_NAMESPACE_AWARE);
		factory.setValidating(IS_VALIDATING);
		factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

		return factory;
	}

	/**
	 * Create setup instance of {@link DocumentBuilder} from factory
	 * 
	 * @return {@link DocumentBuilder}
	 */
	protected DocumentBuilder createDocumentBuilder() {
		final DocumentBuilderFactory factory = createDocumentBuilderFactory();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XmlException("Cannot obtain document builder factory", e);
		}

		if (getErrorHandler() != null) {
			builder.setErrorHandler(getErrorHandler());
		}

		if (getEntityResolver() != null) {
			builder.setEntityResolver(getEntityResolver());
		}

		return builder;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(final ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public EntityResolver getEntityResolver() {
		return entityResolver;
	}

	public void setEntityResolver(final EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

}
