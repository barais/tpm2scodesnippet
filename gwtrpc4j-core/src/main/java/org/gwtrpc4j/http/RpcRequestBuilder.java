package org.gwtrpc4j.http;

import org.gwtrpc4j.RequestBuilderFactory;

import com.google.gwt.http.client.RequestBuilder;

public class RpcRequestBuilder extends
		com.google.gwt.user.client.rpc.RpcRequestBuilder {

	private String moduleBase;
	private String strongName;
	private RequestBuilderFactory factory;

	public RpcRequestBuilder(RequestBuilderFactory factory, String moduleBase,
			String strongName) {
		this.factory = factory;
		this.moduleBase = moduleBase;
		this.strongName = strongName;
	}

	@Override
	protected void doFinish(RequestBuilder rb) {
		rb.setHeader(STRONG_NAME_HEADER, strongName);
		rb.setHeader(MODULE_BASE_HEADER, moduleBase);
	}

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		return factory.newInstance(RequestBuilder.POST, serviceEntryPoint);
	}

}
