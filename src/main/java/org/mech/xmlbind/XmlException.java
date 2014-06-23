package org.mech.xmlbind;

/**
 * Root excetion for xml framework. All exceptions should derived from this class.
 * 
 * @author martin.krajc
 * 
 */
public class XmlException extends RuntimeException {

	private static final long serialVersionUID = -7382608243791872043L;

	public XmlException(final String message) {
		super(message);
	}

	public XmlException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
