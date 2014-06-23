package org.mech.xmlbind;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementation of {@link ElementBinder} bind xml element node to some data.
 * It get already bound object from parent binder or root Document binder and
 * can return different or same data within the context.
 * 
 * <p>
 * This implementation after binding traverse recursivly through elements until
 * some child mapped binder is found and then pass binding to child binder. When
 * binder is found traversing on that level ends.
 * </p>
 * 
 * @author martin.krajc
 * 
 * @param <T>
 *            data to bind
 * @param <ROOT>
 *            already bound object passed from parent binder
 */
public abstract class AbstractElementBinder<T, ROOT> implements ElementBinder<T, ROOT> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * child binders
	 */
	private Map<String, ElementBinder<?, T>> childMap;

	/**
	 * {@inheritDoc}
	 */
	public T bind(final Element element, final ROOT root) throws XmlBindingException {

		LOGGER.debug("Binding element [" + getElementBoundName(element) + "]");

		final T data = doBindElement(root, element);

		if (childMap != null && !childMap.isEmpty()) {
			tryPassBindingToChildBinder(element, data);
		}

		return data;
	}

	// try find next binder if element has child elements
	private void tryPassBindingToChildBinder(final Element element, final T data) {
		final NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			final Node node = nl.item(i);
			if (node instanceof Element) {
				final Element child = (Element) node;
				final ElementBinder<?, T> childBinder = childMap.get(getElementBoundName(child));

				if (childBinder != null) {
					childBinder.bind(child, data);
				} else {
					LOGGER.debug("Binder not found [" + child.getNodeName() + "]");

					// recursion until some binder is found
					tryPassBindingToChildBinder(child, data);
				}
			}
		}
	}

	/**
	 * Actual binding of element.
	 * 
	 * @param rootObject
	 *            passed from parent, serves as a context information
	 * @param element
	 *            to bind
	 * @return bound data
	 */
	protected abstract T doBindElement(final ROOT rootObject, final Element element);

	/**
	 * Register binder as child under element name
	 * 
	 * @param name
	 *            of element under which the binder will sit
	 * @param binder
	 *            mapped binder
	 */
	protected void registerBinder(final String name, final ElementBinder<?, T> binder) {
		if (childMap == null) {
			childMap = new HashMap<String, ElementBinder<?, T>>();
		}

		childMap.put(name, binder);
		LOGGER.info("Binder registered [name=" + name + ", binder= " + binder + "]");
	}

	/**
	 * Get name of element to used as key when looking for child binders.
	 * 
	 * @param element
	 *            from document
	 * @return element bound name
	 */
	protected String getElementBoundName(final Element element) {
		return element.getNodeName();
	}

}
