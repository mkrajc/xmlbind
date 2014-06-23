package org.mech.xmlbind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Abstract {@link DocumentBinder} implementation. It enables register
 * {@link ElementBinder} under xml names to bind different part of Document with
 * different binders
 * 
 * @author martin.krajc
 * 
 * @param <T>
 */
public abstract class AbstractDocumentBinder<T> extends AbstractElementBinder<T, Document> implements DocumentBinder<T> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final T bind(final Document doc) throws XmlBindingException {
		T data = null;
		
		try {
			LOGGER.debug("Binding of document start");
			data = bind(doc.getDocumentElement(), doc);
			LOGGER.debug("Binding of document end");
		} catch (XmlBindingException e) {
			throw e;
		} catch (Exception e) {
			throw new XmlBindingException("Exception during binding of document", e);
		}

		return data;
	}

}
