package org.gwtrpc4j.stream;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.rpc.SerializationException;

/**
 * 
 * @author npeters
 * 
 */
public class JClientSerializationStreamReaderTest {
	@Test
	public void testReadPrimitifObj() throws SerializationException {
		try {
			JClientSerializationStreamReader reader = new JClientSerializationStreamReader(
					null, null, null, null);

			reader
					.prepareToRead("[1,[\"Hello, Nicolas!<br><br>I am running\"],0,5]");
			reader.deserializeStringTable();
			Assert.assertEquals(4, reader.results.size());
			Assert.assertEquals(1, reader.stringTable.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testReadComplexObj() throws SerializationException {
		JClientSerializationStreamReader reader = new JClientSerializationStreamReader(
				null, null, null, null);

		reader
				.prepareToRead("[3,1.271310319616E12,2.987459821E9,2,1,"
						+ "[\"org.gwtrpc4j.test.app.client.ResponseGTO/916896323\",\"java.util.Date/1659716317\",\"titre\"],0,5]");
		reader.deserializeStringTable();

		Assert.assertEquals(8, reader.results.size());
		Assert
				.assertEquals(
						"\"org.gwtrpc4j.test.app.client.ResponseGTO/916896323\",\"java.util.Date/1659716317\",\"titre\"",
						reader.results.get(5));

		Assert.assertEquals(3, reader.stringTable.size());

	}

}
