package org.mech.xmlbind;

/**
 * {@link XmlParsingException} is thrown when parsing of xml failed
 * 
 * @author martin.krajc
 * 
 */
public class XmlParsingException extends XmlException {

	public XmlParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = -8697820504278535579L;

}
