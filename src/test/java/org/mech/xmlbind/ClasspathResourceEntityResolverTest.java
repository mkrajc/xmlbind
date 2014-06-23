package org.mech.xmlbind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.mech.xmlbind.ClasspathResourceEntityResolver;
import org.mech.xmlbind.EnityDefinitionNotFoundException;
import org.xml.sax.InputSource;



public class ClasspathResourceEntityResolverTest {

	private ClasspathResourceEntityResolver resolverUnderTest = new ClasspathResourceEntityResolver();

	@Test
	public void testResolveClasspathPath() {
		assertEquals("gearbox.xsd", resolverUnderTest.getClasspathPath("http://www.test.com/schema/gearbox.xsd"));
		assertEquals("gearbox.xsd", resolverUnderTest.getClasspathPath("gearbox.xsd"));
	}

	@Test
	public void testExistingFile() {
		final String name = getClass().getSimpleName();
		final InputSource src = resolverUnderTest.resolveEntity(null, "http://www.test.com/schema/" + name);
		assertNotNull(src.getByteStream());

		try {
			src.getByteStream().close();
		} catch (IOException e) {
		}
	}

	@Test(expected = EnityDefinitionNotFoundException.class)
	public void testNotExistingFile() {
		resolverUnderTest.resolveEntity(null, "http://www.test.com/schema/uknown");
	}

}
