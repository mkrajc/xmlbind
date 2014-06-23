package org.mech.xmlbind;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mech.xmlbind.DefaultDocumentLoader;
import org.mech.xmlbind.EnityDefinitionNotFoundException;
import org.mech.xmlbind.XmlException;
import org.mech.xmlbind.XmlParsingException;
import org.w3c.dom.Document;



public class DefaultDocumentLoaderTest {

	private DefaultDocumentLoader loader;

	@Before
	public void setup() {
		loader = new DefaultDocumentLoader();
	}

	@Test
	public void testLoadSimpleDocument() {
		final InputStream s = getClass().getClassLoader().getResourceAsStream("xml/test.xml");
		final Document doc = loader.loadDocument(s);
		assertNotNull(doc);
		assertNotNull(doc.getElementsByTagName("root"));
		assertNotNull(doc.getElementsByTagName("element"));
	}

	@Test(expected = EnityDefinitionNotFoundException.class)
	public void testBadSchema() {
		final InputStream s = getClass().getClassLoader().getResourceAsStream("xml/test-bad-schema.xml");
		loader.loadDocument(s);
	}

	@Test(expected = XmlParsingException.class)
	public void testBadDocument() {
		final InputStream s = getClass().getClassLoader().getResourceAsStream("xml/test-bad-document.xml");
		loader.loadDocument(s);
	}

	@Test(expected = XmlParsingException.class)
	public void testBadFormed() {
		final InputStream s = getClass().getClassLoader().getResourceAsStream("xml/test-bad-formed.xml");
		loader.loadDocument(s);
	}

	@Test
	public void testDefaultErrorHandler() {
		assertNotNull(loader.getErrorHandler());
		loader.setErrorHandler(null);
		assertNull(loader.getErrorHandler());
	}

	@Test
	public void testDefaultEntityResolver() {
		assertNotNull(loader.getEntityResolver());
		loader.setEntityResolver(null);
		assertNull(loader.getEntityResolver());
	}

	@Test
	public void testBuilderSetup() {
		loader.setEntityResolver(null);
		loader.setErrorHandler(null);
		// cannot check if builder used implementation defaults
		loader.createDocumentBuilder();

	}

	@Test(expected = XmlException.class)
	public void testBuilderFactorySetup() {

		loader = new DefaultDocumentLoader() {
			@Override
			protected DocumentBuilderFactory createDocumentBuilderFactory() {
				return new DocumentBuilderFactory() {

					@Override
					public void setFeature(String name, boolean value) throws ParserConfigurationException {

					}

					@Override
					public void setAttribute(String name, Object value) throws IllegalArgumentException {

					}

					@Override
					public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
						throw new ParserConfigurationException("testing exception");
					}

					@Override
					public boolean getFeature(String name) throws ParserConfigurationException {
						return false;
					}

					@Override
					public Object getAttribute(String name) throws IllegalArgumentException {
						return null;
					}
				};
			}
		};

		final InputStream s = getClass().getClassLoader().getResourceAsStream("xml/test.xml");
		loader.loadDocument(s);

	}

}
