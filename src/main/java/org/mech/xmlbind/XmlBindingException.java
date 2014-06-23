package org.mech.xmlbind;

/**
 * {@link XmlBindingException} is thrown when binding failed
 * 
 * @author martin.krajc
 * 
 */
public class XmlBindingException extends XmlException {

	public XmlBindingException(final String message, Exception e) {
		super(message, e);
	}

	public XmlBindingException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -1355646117100364274L;

}
