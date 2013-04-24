package org.gwtrpc4j;

import java.lang.reflect.Proxy;

import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.impl.LegacySerializationPolicy;

public class RpcServiceProxyFactory {

	public static <T> T create(Class<T> rcpInterface, String moduleBaseURL,
			String remoteServiceRelativePath,
			String serializationPolicyStrongName, RequestBuilderFactory factory) {
		return create(rcpInterface, defaultSerializationPolicyProvider,
				moduleBaseURL, remoteServiceRelativePath,
				serializationPolicyStrongName, factory);
	}

	public static <T> T create(Class<T> rcpInterface,
			SerializationPolicyProvider serializationPolicyProvider,
			String moduleBaseURL, String remoteServiceRelativePath,
			String serializationPolicyStrongName, RequestBuilderFactory factory) {

		JRpcServiceProxy<T> proxyHandler = new JRpcServiceProxy<T>(
				rcpInterface, serializationPolicyProvider, moduleBaseURL,
				remoteServiceRelativePath, serializationPolicyStrongName,
				factory);
		T proxy = (T) Proxy.newProxyInstance(Thread.currentThread()
				.getContextClassLoader(), new Class<?>[] { rcpInterface },
				proxyHandler);
		return proxy;

	}

	static SerializationPolicyProvider defaultSerializationPolicyProvider = new SerializationPolicyProvider() {
		public SerializationPolicy getSerializationPolicy(String moduleBaseURL,
				String serializationPolicyStrongName) {
			return LegacySerializationPolicy.getInstance();
		}
	};

}
