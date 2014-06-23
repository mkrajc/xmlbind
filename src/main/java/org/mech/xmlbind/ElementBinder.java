package org.mech.xmlbind;

import org.w3c.dom.Element;

/**
 * ElementBinder binds xml element node to some data.
 * 
 * @author martin.krajc
 * 
 * @param <DATA>
 *            data to return from element as result of binding
 * @param <ROOT_CONTEXT>
 *            root object expected to get from parent binders
 */
public interface ElementBinder<DATA, ROOT_CONTEXT> {

	/**
	 * Bind element to some data.
	 * 
	 * @param element
	 *            to bind
	 * @param root
	 *            object expected from parent object
	 * @return bound data
	 * @exception XmlBindingException
	 *                when binding failed
	 */
	DATA bind(Element element, ROOT_CONTEXT root) throws XmlBindingException;
}
