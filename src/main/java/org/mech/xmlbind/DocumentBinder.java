package org.mech.xmlbind;

import org.w3c.dom.Document;

/**
 * DocumentBinder is interface to bind xml documents to data object. Specific
 * instance of binder must know inner structure of document
 * 
 * @author martin.krajc
 * 
 * @param <T>
 *            data to bind
 */
public interface DocumentBinder<T> {

	/**
	 * Binds document to data object
	 * 
	 * @param doc
	 *            - loaded xml document
	 * @return bound data
	 * @throws XmlBindingException
	 *             when binding failed
	 */
	T bind(Document doc) throws XmlBindingException;
}
