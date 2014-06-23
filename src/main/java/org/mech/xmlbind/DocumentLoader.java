package org.mech.xmlbind;

import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * DocumentLoader is abstraction for building Document object from xml source.
 * 
 * @author martin.krajc
 * 
 */
public interface DocumentLoader {

	/**
	 * Load document from xml input source.
	 * 
	 * @param xmlSourceStream
	 *            - stream from xml source
	 * @return build document
	 * @throws XmlException
	 *             when loading of document failed
	 */
	Document loadDocument(InputStream xmlSourceStream) throws XmlException;

}
