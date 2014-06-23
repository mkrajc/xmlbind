package org.mech.xmlbind;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mech.xmlbind.AbstractElementBinder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;



@SuppressWarnings({ "rawtypes", "unchecked" })
public class AbstractElementBinderTest {

	private AbstractElementBinder binder;

	@Before
	public void setup() {
		binder = new TestElementBinder();
	}

	private Document prepareDocument(String xml) throws UnsupportedEncodingException, SAXException, IOException,
			ParserConfigurationException {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	}

	@Test
	public void testElementBinding() throws UnsupportedEncodingException, SAXException, IOException,
			ParserConfigurationException {
		Document doc = prepareDocument("<a><b><c></c></b></a>");

		Set<String> set = new HashSet<String>();
		binder.bind(doc.getDocumentElement(), set);
		assertEquals(1, set.size());
	}

	@Test
	public void testElementChildBinding() throws UnsupportedEncodingException, SAXException, IOException,
			ParserConfigurationException {
		Document doc = prepareDocument("<a><b><c></c></b></a>");

		Set<String> set = new HashSet<String>();

		AbstractElementBinder cBinder = new TestElementBinder();
		AbstractElementBinder bBinder = new TestElementBinder();
		bBinder.registerBinder("c", cBinder);
		binder.registerBinder("b", bBinder);

		binder.bind(doc.getDocumentElement(), set);
		assertEquals(3, set.size());
	}

	@Test
	public void testElementChildBindingSkipElement() throws UnsupportedEncodingException, SAXException, IOException,
			ParserConfigurationException {
		Document doc = prepareDocument("<a><b><c></c></b></a>");

		Set<String> set = new HashSet<String>();

		AbstractElementBinder cBinder = new TestElementBinder();
		binder.registerBinder("c", cBinder);

		binder.bind(doc.getDocumentElement(), set);
		assertEquals(2, set.size());
	}

	static class TestElementBinder extends AbstractElementBinder<Collection, Collection> {

		@Override
		protected Collection doBindElement(Collection rootObject, Element element) {
			rootObject.add(element.getNodeName());
			return rootObject;
		}
	};

}
