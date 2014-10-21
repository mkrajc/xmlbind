package org.mech.xmlbind;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mech.xmlbind.DefaultErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultErrorHandlerTest {

	private DefaultErrorHandler handler;
	
	@Before
	public void setup(){
		handler = new DefaultErrorHandler();
	}

	@Test
	public void testWarning() throws SAXException {
		handler.warning(new SAXParseException("warning", "pId", "sId", 0, 0));
	}

	@Test
	public void testError() {
		final SAXParseException exception = new SAXParseException("error", "pId", "sId", 0, 0);
		try {
			handler.error(exception);
		} catch (SAXException e) {
			// check if rethrowed
			assertSame(exception, e);
			return;
		}
		fail();
	}

	@Test
	public void testFatalError() {
		final SAXParseException exception = new SAXParseException("fatalError", "pId", "sId", 0, 0);
		try {
			handler.fatalError(exception);
		} catch (SAXException e) {
			// check if rethrowed
			assertSame(exception, e);
			return;
		}
		fail();
	}
}
