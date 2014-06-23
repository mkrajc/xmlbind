package org.mech.xmlbind;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mech.xmlbind.AbstractDocumentBinder;
import org.mech.xmlbind.XmlBindingException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;



public class AbstractDocumentBinderTest {

	private static final String CONTENT = "content";
	private static final String EXCEPTION = "exc";
	

	private AbstractDocumentBinder<?> binder;

	@Before
	public void setup() {
		binder = new TestDocumentBinder();
	}

	private Document prepareDocument(String xml) throws UnsupportedEncodingException, SAXException, IOException,
			ParserConfigurationException {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	}

	@Test
	public void testSampleBinding() throws ParserConfigurationException, UnsupportedEncodingException, SAXException,
			IOException {
		final TestData data = (TestData) binder.bind(prepareDocument("<test>" + CONTENT + "</test>"));
		assertNotNull(data);
		assertEquals(CONTENT, data.content);
	}

	@Test(expected = XmlBindingException.class)
	public void testGetBindingException() throws ParserConfigurationException, UnsupportedEncodingException,
			SAXException, IOException {
		binder.bind(prepareDocument("<test></test>"));
	}
	
	@Test(expected = XmlBindingException.class)
	public void testGetException() throws ParserConfigurationException, UnsupportedEncodingException,
			SAXException, IOException {
		binder.bind(prepareDocument("<test>"+EXCEPTION+"</test>"));
	}

	public static class TestData {
		String content;
	}

	public static class TestDocumentBinder extends AbstractDocumentBinder<TestData> {

		@Override
		protected TestData doBindElement(Document rootObject, Element element) {
			TestData t = new TestData();
			t.content = element.getTextContent();

			if (t.content == null || t.content.isEmpty()) {
				throw new XmlBindingException("expecting non empty content");
			}

			if (t.content.equals(EXCEPTION)) {
				throw new RuntimeException();
			}
			return t;
		}

	}

}
