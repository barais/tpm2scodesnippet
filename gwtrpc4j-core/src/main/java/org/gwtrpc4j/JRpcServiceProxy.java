package org.gwtrpc4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.gwtrpc4j.http.RpcRequestBuilder;
import org.gwtrpc4j.stream.JClientSerializationStreamWriter;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.rpc.client.impl.RemoteException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.impl.SerializabilityUtil;

public class JRpcServiceProxy<T> extends RpcServiceProxy implements
		InvocationHandler {

	private final Class<T> serviceInterface;
	private final boolean isSyncroCall;
	private final RequestBuilderFactory rpcRequestBuilderFactory;

	private final Map<Class<?>, ResponseReader> JPRIMITIVETYPE_TO_RESPONSEREADER = new HashMap<Class<?>, ResponseReader>();

	{
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(boolean.class,
				ResponseReader.BOOLEAN);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(byte.class, ResponseReader.BYTE);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(char.class, ResponseReader.CHAR);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(double.class,
				ResponseReader.DOUBLE);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(float.class, ResponseReader.FLOAT);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(int.class, ResponseReader.INT);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(long.class, ResponseReader.LONG);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(short.class, ResponseReader.SHORT);
		JPRIMITIVETYPE_TO_RESPONSEREADER.put(void.class, ResponseReader.VOID);
	}

	public JRpcServiceProxy(Class<T> serviceInterface,
			SerializationPolicyProvider serializationPolicyProvider,
			String moduleBaseURL, String remoteServiceRelativePath,
			String serializationPolicyStrongName,
			RequestBuilderFactory rpcRequestBuilderFactory) {
		super(serializationPolicyProvider, moduleBaseURL,
				remoteServiceRelativePath, serializationPolicyStrongName);
		this.serviceInterface = serviceInterface;
		this.isSyncroCall = RemoteService.class
				.isAssignableFrom(serviceInterface);
		this.rpcRequestBuilderFactory = rpcRequestBuilderFactory;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try {
			return doInvoke(method, args);
		} catch (Throwable ex) {
			if (!(ex instanceof RuntimeException)) {
				throw new RemoteException(ex);
			} else {
				throw ex;
			}
		}
	}

	private <R> Object doInvoke(Method method, Object[] args)
			throws SerializationException, Throwable {

		JClientSerializationStreamWriter writer = buildWriter(method, args,
				isSyncroCall);

		Class<R> returnType = getMethodReturnType(method, args, isSyncroCall);
		ResponseReader responseReader = getResponseReaderFor(returnType);

		if (isSyncroCall) {
			SynchroCallback<R> callback = new SynchroCallback<R>();
			doInvoke(responseReader, method.getName(), new RpcStatsContext(1), writer.toString(),
					callback);

			if (callback.caught == null) {
				return callback.result;
			} else {
				throw callback.caught;
			}

		} else {
			AsyncCallback<R> callback = (AsyncCallback<R>) args[args.length - 1];
			doInvoke(responseReader, method.getName(), new RpcStatsContext(1), writer.toString(),
					callback);

			return null;

		}
	}

	private <R> Class<R> getMethodReturnType(Method method, Object[] args,
			boolean isSyncroCall) {
		Class<R> returnType = null;
		if (isSyncroCall) {
			returnType = (Class<R>) method.getReturnType();
		} else {
			AsyncCallback obj = (AsyncCallback) args[args.length - 1];
			Method[] am = obj.getClass().getMethods();
			for (Method m : am) {
				if ("onSuccess".equals(m.getName())
						&& m.getParameterTypes().length == 1
						&& !m.getParameterTypes()[0].equals(Object.class)) {
					returnType = (Class<R>) m.getParameterTypes()[0];
					break;
				}
			}
		}
		return returnType;
	}

	private ResponseReader getResponseReaderFor(Class<?> returnType) {
		if (returnType.isPrimitive()) {
			return JPRIMITIVETYPE_TO_RESPONSEREADER.get(returnType);
		}

		if (returnType.equals(String.class)) {
			return ResponseReader.STRING;
		}

		return ResponseReader.OBJECT;
	}

	private JClientSerializationStreamWriter buildWriter(Method method,
			Object[] args, boolean isSyncroCall) throws SerializationException {
		int nbParams = isSyncroCall ? method.getParameterTypes().length
				: method.getParameterTypes().length - 1;
		String methodName = method.getName();
		String serviceName = serviceInterface.getName();
		if (!isSyncroCall) {
			serviceName = serviceName.substring(0, serviceName.length()
					- "Async".length());
		}

		JClientSerializationStreamWriter writer = this.createStreamWriter();
		writer.prepareToWrite();
		writer.writeString(serviceName);
		writer.writeString(methodName);
		writer.writeInt(nbParams);

		SerializationPolicy policy = this.serializationPolicyProvider
				.getSerializationPolicy(moduleBaseURL,
						serializationPolicyStrongName);
		for (int i = 0; i < nbParams; i++) {
			Class<?> clazz = method.getParameterTypes()[i];
			writer.writeString(SerializabilityUtil
					.encodeSerializedInstanceReference(clazz, policy));
		}
		for (int i = 0; i < nbParams; i++) {
			Class<?> clazz = method.getParameterTypes()[i];
			writer.serializeValue(args[i], clazz);
		}
		return writer;
	}

	@Override
	protected <R> RequestCallback doCreateRequestCallback(
			ResponseReader responseReader, String methodName,
			RpcStatsContext invocationCount, AsyncCallback<R> callback) {
		return new org.gwtrpc4j.http.RequestCallbackAdapter<R>(this,
				methodName, invocationCount.getRequestId(), callback, responseReader);
	}

	public static class SynchroCallback<C> implements AsyncCallback<C> {
		Throwable caught;
		C result;

		public void onFailure(Throwable caught) {
			this.caught = caught;
		}

		public void onSuccess(C result) {
			this.result = result;
		}

	}

	@Override
	protected RpcRequestBuilder ensureRpcRequestBuilder() {
		return new RpcRequestBuilder(this.rpcRequestBuilderFactory,
				this.moduleBaseURL, this.serializationPolicyStrongName);
	}

	public String getSerializationPolicyName() {
		// TODO Auto-generated method stub
		return null;
	}

}
