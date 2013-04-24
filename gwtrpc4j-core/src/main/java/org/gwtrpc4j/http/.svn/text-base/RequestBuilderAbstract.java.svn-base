package org.gwtrpc4j.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class RequestBuilderAbstract extends RequestBuilder {

	protected List<RpcHeader> headers;

	// protected List<String> cookies;

	public RequestBuilderAbstract(Method httpMethod, String url) {
		super(httpMethod, url);
	}

	protected Header[] toRcpHeader(Map<String, List<String>> headers) {
		Header[] result = new Header[headers.size()];
		int i = 0;
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			RpcHeader h = new RpcHeader();
			h.setName(entry.getKey());
			if (entry.getValue().size() >= 1) {
				h.setValue(entry.getValue().get(0));
			}
			result[i++] = h;
		}
		return result;
	}

	@Override
	public Request sendRequest(String requestData, RequestCallback callback)
			throws RequestException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets a request header with the given name and value. If a header with the
	 * specified name has already been set then the new value overwrites the
	 * current value.
	 * 
	 * @param header
	 *            the name of the header
	 * @param value
	 *            the value of the header
	 * 
	 * @throws NullPointerException
	 *             if header or value are null
	 * @throws IllegalArgumentException
	 *             if header or value are the empty string
	 */
	@Override
	public void setHeader(String header, String value) {

		if (headers == null) {
			headers = new ArrayList<RpcHeader>();
		}

		headers.add(new RpcHeader(header, value));
	}

}
