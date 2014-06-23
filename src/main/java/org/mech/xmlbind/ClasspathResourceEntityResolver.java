package org.mech.xmlbind;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Implementation of {@link EntityResolver} that maps system id to classpath
 * resource.
 * 
 * @author martin.krajc
 * 
 */
public class ClasspathResourceEntityResolver implements EntityResolver {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClasspathResourceEntityResolver.class);

	/**
	 * {@inheritDoc}
	 */
	public InputSource resolveEntity(final String publicId, final String systemId) {
		final String classpathPath = getClasspathPath(systemId);
		final InputStream schemaStream = getClass().getClassLoader().getResourceAsStream(classpathPath);

		if (schemaStream == null) {
			LOGGER.warn("Entity definition not found [" + classpathPath + "]");
			throw new EnityDefinitionNotFoundException("Cannot find enitity definition as resource in classpath [ "
					+ classpathPath + " ]");
		}

		LOGGER.debug("Entity definition loaded [" + classpathPath + "]");

		final InputSource inputSource = new InputSource(schemaStream);

		inputSource.setPublicId(publicId);
		inputSource.setSystemId(systemId);

		return inputSource;

	}

	/**
	 * Gets classpath path of resource from system id
	 * 
	 * @param systemId
	 * @return path of resource
	 */
	protected String getClasspathPath(final String systemId) {
		int i = systemId.lastIndexOf('/');
		String substring = systemId.substring(i + 1);
		return substring;
	}

}
