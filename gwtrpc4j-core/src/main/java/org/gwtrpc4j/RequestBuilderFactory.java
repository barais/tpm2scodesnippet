package org.gwtrpc4j;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;

public interface RequestBuilderFactory {
	public RequestBuilder newInstance(Method httpMethod,
			String serviceEntryPoint);
}
