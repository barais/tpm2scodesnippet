package org.gwtrpc4j.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class IOUtils {

	private static final int BUFFER_SIZE = 1024;

	public static void write(OutputStream outputStream, String data)
			throws IOException {

		OutputStreamWriter writer = new OutputStreamWriter(outputStream,
				Charset.forName("UTF-8"));
		try {
			writer.write(data);
			writer.flush();
			outputStream.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static String readAsString(InputStream input) throws IOException {
		InputStreamReader reader = null;
		BufferedReader bufReader = null;
		try {
			reader = new InputStreamReader(input, Charset.forName("UTF-8"));
			bufReader = new BufferedReader(reader);
			final StringBuilder builder = new StringBuilder();
			CharBuffer charBuffer = CharBuffer.allocate(BUFFER_SIZE);
			while (bufReader.read(charBuffer) != -1) {
				charBuffer.flip();
				builder.append(charBuffer);
			}
			return builder.toString();
		} finally {
			if (bufReader != null) {
				bufReader.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (input != null) {
				input.close();
			}
		}
	}

}
