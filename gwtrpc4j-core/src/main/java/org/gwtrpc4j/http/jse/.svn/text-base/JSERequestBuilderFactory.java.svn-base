package org.gwtrpc4j.http.jse;

import java.net.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.gwtrpc4j.RequestBuilderFactory;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;

public class JSERequestBuilderFactory implements RequestBuilderFactory {

	private ExecutorService asynchroExecutor;
	private Proxy proxy;
	private int timeoutMillis;

	// static {
	// CookieHandler cookieManager = new CookieManager(
	// new CookieStoreThreadLocal(), CookiePolicy.ACCEPT_ALL);
	// CookieHandler.setDefault(cookieManager);
	// }

	public RequestBuilder newInstance(Method httpMethod,
			String serviceEntryPoint) {
		JSERequestBuilder builder = new JSERequestBuilder(httpMethod,
				serviceEntryPoint);
		if (asynchroExecutor == null) {
			builder.setAsynchroExecutor(Executors.newCachedThreadPool());
		} else {
			builder.setAsynchroExecutor(asynchroExecutor);
		}
		builder.setProxy(proxy);
		builder.setTimeoutMillis(timeoutMillis);
		builder.setCookieManager(CookieManager.getThreadInstance());
		return builder;
	}

	public ExecutorService getAsynchroExecutor() {
		return asynchroExecutor;
	}

	public void setAsynchroExecutor(ExecutorService asynchroExecutor) {
		this.asynchroExecutor = asynchroExecutor;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public int getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

}
