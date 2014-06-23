package org.mech.xmlbind;

/**
 * {@link EnityDefinitionNotFoundException} is thrown when unknow entity
 * definition (XSD schemas, DTD) is asked to be loaded.
 * 
 * @author martin.krajc
 * 
 */
public class EnityDefinitionNotFoundException extends XmlException {

	private static final long serialVersionUID = 5335650003319040450L;

	public EnityDefinitionNotFoundException(final String message) {
		super(message);
	}

}
