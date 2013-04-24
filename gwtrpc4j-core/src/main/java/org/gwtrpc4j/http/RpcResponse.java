package org.gwtrpc4j.http;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;

public class RpcResponse extends Response {

	private final int statutCode;
	private final Header[] headers;
	private final String data;

	public RpcResponse(int statutCode, Header[] headers, String data) {
		super();
		this.statutCode = statutCode;
		this.headers = headers;
		this.data = data;
	}

	@Override
	public String getHeader(String header) {
		for (Header h : headers) {
			if (h.getName().equals(header)) {
				return h.getValue();
			}
		}
		return null;
	}

	@Override
	public Header[] getHeaders() {
		return headers;
	}

	@Override
	public String getHeadersAsString() {
		return null;
	}

	@Override
	public int getStatusCode() {
		return statutCode;
	}

	@Override
	public String getStatusText() {
		return String.valueOf(statutCode);
	}

	@Override
	public String getText() {
		return data;
	}

}
