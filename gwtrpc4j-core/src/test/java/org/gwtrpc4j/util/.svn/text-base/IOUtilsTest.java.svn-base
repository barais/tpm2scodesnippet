package org.gwtrpc4j.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class IOUtilsTest {

	@Test
	public void testRead() throws IOException {

		String msg = new String("Hello World \u0023!");
		ByteArrayInputStream input = new ByteArrayInputStream(msg.getBytes());
		String result = IOUtils.readAsString(input);
		assertEquals(msg, result);
	}

}
