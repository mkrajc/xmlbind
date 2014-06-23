package org.mech.xmlbind;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Default error handler for {@link Document} building. It just log exceptions
 * to logger.
 * 
 * @author martin.krajc
 * 
 */
public class DefaultErrorHandler implements ErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandler.class);

	// unique token per instance to easily associate logs in parallel processing
	private final String token = UUID.randomUUID().toString();

	/**
	 * {@inheritDoc}
	 */
	public void warning(SAXParseException exception) throws SAXException {
		LOGGER.warn("Parsing warning [t=" + token + "] : " + exception.getMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(SAXParseException exception) throws SAXException {
		throw exception;
	}

	/**
	 * {@inheritDoc}
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
		throw exception;
	}

}
