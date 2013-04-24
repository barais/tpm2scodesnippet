package org.gwtrpc4j.http;

import com.google.gwt.http.client.Header;

public class RpcHeader extends Header {

	private String name;
	private String value;

	public RpcHeader() {
	}

	public RpcHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name == null ? value : name + ": " + value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
