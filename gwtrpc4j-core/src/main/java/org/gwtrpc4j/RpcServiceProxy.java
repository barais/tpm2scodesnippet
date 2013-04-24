package org.gwtrpc4j;

import org.gwtrpc4j.http.jse.JSERequestBuilder;
import org.gwtrpc4j.stream.JClientSerializationStreamReader;
import org.gwtrpc4j.stream.JClientSerializationStreamWriter;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

/**
 * Replace the javascript proxy genere by GWT
 * 
 * Superclass for client-side
 * {@link com.google.gwt.user.client.rpc.RemoteService RemoteService} proxies.
 * 
 * 
 */
public abstract class RpcServiceProxy implements SerializationStreamFactory,
		ServiceDefTarget {

	// function $GreetingService_Proxy(this$static){
	// $clinit_306();
	// $RemoteServiceProxy(this$static, $moduleBase, 'greet',
	// '6EB1B2E2A5D102C4E06AE6D3CA779A55');
	// return this$static;
	// }
	//
	// function $greetServer(this$static, req1, req2, callback){
	// var $e0, payload, requestId, streamWriter,
	// clientSerializationStreamWriter;
	// requestId = requestId_0++;
	// !!$stats && $stats({moduleName:$moduleName, sessionId:$sessionId,
	// subSystem:'rpc', evtGroup:requestId,
	// method:'GreetingService_Proxy.greetServer', millis:(new Date).getTime(),
	// type:'begin'});
	// streamWriter = (clientSerializationStreamWriter =
	// $ClientSerializationStreamWriter(new ClientSerializationStreamWriter,
	// this$static.moduleBaseURL, this$static.serializationPolicyName) ,
	// clientSerializationStreamWriter.objectCount = 0 ,
	// $clearImpl(clientSerializationStreamWriter.objectMap) ,
	// $clearImpl(clientSerializationStreamWriter.stringMap) ,
	// $clear(clientSerializationStreamWriter.stringTable) ,
	// clientSerializationStreamWriter.encodeBuffer = $StringBuffer(new
	// StringBuffer) , $writeString(clientSerializationStreamWriter,
	// clientSerializationStreamWriter.moduleBaseURL) ,
	// $writeString(clientSerializationStreamWriter,
	// clientSerializationStreamWriter.serializationPolicyStrongName) ,
	// clientSerializationStreamWriter);
	// try {
	// append(streamWriter.encodeBuffer, '' + $addString(streamWriter,
	// 'org.nico.remotegwt.client.GreetingService'));
	// append(streamWriter.encodeBuffer, '' + $addString(streamWriter,
	// 'greetServer'));
	// append(streamWriter.encodeBuffer, '2');
	// append(streamWriter.encodeBuffer, '' + $addString(streamWriter,
	// 'org.nico.remotegwt.client.RequestGTO/2178853857'));
	// append(streamWriter.encodeBuffer, '' + $addString(streamWriter,
	// 'org.nico.remotegwt.client.RequestGTO/2178853857'));
	// $writeObject(streamWriter, req1);
	// $writeObject(streamWriter, req2);
	// payload = $toString_2(streamWriter);
	// !!$stats && $stats({moduleName:$moduleName, sessionId:$sessionId,
	// subSystem:'rpc', evtGroup:requestId,
	// method:'GreetingService_Proxy.greetServer', millis:(new Date).getTime(),
	// type:'requestSerialized'});
	// $doInvoke(this$static, ($clinit_165() ,
	// 'GreetingService_Proxy.greetServer'), requestId, payload, callback);
	// }
	// catch ($e0) {
	// $e0 = caught_0($e0);
	// if (instanceOf($e0, 35)) {
	// $onFailure(callback);
	// }
	// else
	// throw $e0;
	// }
	// }

	/**
	 * The content type to be used in HTTP requests.
	 */
	private static final String RPC_CONTENT_TYPE = "text/x-gwt-rpc; charset=utf-8";

	/**
	 * Return <code>true</code> if the encoded response contains a value
	 * returned by the method invocation.
	 * 
	 * @param encodedResponse
	 * @return <code>true</code> if the encoded response contains a value
	 *         returned by the method invocation
	 */
	static boolean isReturnValue(String encodedResponse) {
		return encodedResponse.startsWith("//OK");
	}

	/**
	 * Return <code>true</code> if the encoded response contains a checked
	 * exception that was thrown by the method invocation.
	 * 
	 * @param encodedResponse
	 * @return <code>true</code> if the encoded response contains a checked
	 *         exception that was thrown by the method invocation
	 */
	static boolean isThrownException(String encodedResponse) {
		return encodedResponse.startsWith("//EX");
	}

	/**
	 * Returns a string that encodes the result of a method invocation.
	 * Effectively, this just removes any headers from the encoded response.
	 * 
	 * @param encodedResponse
	 * @return string that encodes the result of a method invocation
	 */
	private static String getEncodedInstance(String encodedResponse) {
		if (isReturnValue(encodedResponse)
				|| isThrownException(encodedResponse)) {
			return encodedResponse.substring(4);
		}

		return encodedResponse;
	}

	/**
	 * The module base URL as specified during construction.
	 */
	protected final String moduleBaseURL;

	/**
	 * URL of the {@link com.google.gwt.user.client.rpc.RemoteService
	 * RemoteService}.
	 */
	private String remoteServiceURL;

	protected SerializationPolicyProvider serializationPolicyProvider;

	/**
	 * The name of the serialization policy file specified during construction.
	 */
	protected String serializationPolicyStrongName;

	protected RpcServiceProxy(
			SerializationPolicyProvider serializationPolicyProvider,
			String moduleBaseURL, String remoteServiceRelativePath,
			String serializationPolicyStrongName) {

		this.serializationPolicyProvider = serializationPolicyProvider;
		this.moduleBaseURL = moduleBaseURL;
		if (remoteServiceRelativePath != null) {
			/*
			 * If the module relative URL is not null we set the remote service
			 * URL to be the module base URL plus the module relative remote
			 * service URL. Otherwise an explicit call to
			 * ServiceDefTarget.setServiceEntryPoint(String) is required.
			 */
			this.remoteServiceURL = moduleBaseURL + remoteServiceRelativePath;
		}
		this.serializationPolicyStrongName = serializationPolicyStrongName;
	}

	/**
	 * Returns a
	 * {@link com.google.gwt.user.client.rpc.SerializationStreamReader
	 * SerializationStreamReader} that is ready for reading.
	 * 
	 * @param encoded
	 *            string that encodes the response of an RPC request
	 * @return {@link com.google.gwt.user.client.rpc.SerializationStreamReader
	 *         SerializationStreamReader} that is ready for reading
	 * @throws SerializationException
	 */
	public SerializationStreamReader createStreamReader(String encoded)
			throws SerializationException {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		JClientSerializationStreamReader clientSerializationStreamReader = new JClientSerializationStreamReader(
				cl, this.serializationPolicyProvider, this.moduleBaseURL,
				this.serializationPolicyStrongName);
		clientSerializationStreamReader
				.prepareToRead(getEncodedInstance(encoded));
		return clientSerializationStreamReader;
	}

	/**
	 * Returns a
	 * {@link com.google.gwt.user.client.rpc.SerializationStreamWriter
	 * SerializationStreamWriter} that has had
	 * {@link ClientSerializationStreamWriter#prepareToWrite()} called on it and
	 * it has already had had the name of the remote service interface written
	 * as well.
	 * 
	 * @return {@link com.google.gwt.user.client.rpc.SerializationStreamWriter
	 *         SerializationStreamWriter} that has had
	 *         {@link ClientSerializationStreamWriter#prepareToWrite()} called
	 *         on it and it has already had had the name of the remote service
	 *         interface written as well
	 */
	public JClientSerializationStreamWriter createStreamWriter() {
		SerializationPolicy policy = this.serializationPolicyProvider
				.getSerializationPolicy(moduleBaseURL,
						serializationPolicyStrongName);
		JClientSerializationStreamWriter clientSerializationStreamWriter = new JClientSerializationStreamWriter(
				policy, moduleBaseURL, serializationPolicyStrongName);
		clientSerializationStreamWriter.prepareToWrite();
		return clientSerializationStreamWriter;
	}

	/**
	 * @see ServiceDefTarget#getServiceEntryPoint()
	 */
	public String getServiceEntryPoint() {
		return remoteServiceURL;
	}

	public void setRpcRequestBuilder(RpcRequestBuilder builder) {
	}

	/**
	 * @see ServiceDefTarget#setServiceEntryPoint(String)
	 */
	public void setServiceEntryPoint(String url) {
		this.remoteServiceURL = url;
	}

	protected <T> RequestCallback doCreateRequestCallback(
			ResponseReader responseReader, String methodName,
			RpcStatsContext invocationCount, AsyncCallback<T> callback) {
		return new RequestCallbackAdapter<T>(this, methodName, invocationCount,
				callback, responseReader);
	}

	/**
	 * Performs a remote service method invocation. This method is called by
	 * generated proxy classes.
	 * 
	 * @param <T>
	 *            return type for the AsyncCallback
	 * @param responseReader
	 *            instance used to read the return value of the invocation
	 * @param requestData
	 *            payload that encodes the addressing and arguments of the RPC
	 *            call
	 * @param callback
	 *            callback handler
	 * 
	 * @return a {@link Request} object that can be used to track the request
	 */
	protected <T> Request doInvoke(ResponseReader responseReader,
			String methodName, RpcStatsContext invocationCount, String requestData,
			AsyncCallback<T> callback) {

		RequestBuilder rb = doPrepareRequestBuilderImpl(responseReader,
				methodName, invocationCount, requestData, callback);

		try {
			return rb.send();
		} catch (RequestException ex) {
			InvocationException iex = new InvocationException(
					"Unable to initiate the asynchronous service invocation -- check the network connection",
					ex);
			callback.onFailure(iex);
		} finally {
			// if (RemoteServiceProxy.isStatsAvailable()
			// && RemoteServiceProxy.stats(RemoteServiceProxy.bytesStat(
			// methodName, invocationCount, requestData.length(),
			// "requestSent"))) {
			// }
		}
		return null;
	}

	/**
	 * Configures a RequestBuilder to send an RPC request when the
	 * RequestBuilder is intended to be returned through the asynchronous proxy
	 * interface.
	 * 
	 * @param <T>
	 *            return type for the AsyncCallback
	 * @param responseReader
	 *            instance used to read the return value of the invocation
	 * @param requestData
	 *            payload that encodes the addressing and arguments of the RPC
	 *            call
	 * @param callback
	 *            callback handler
	 * 
	 * @return a RequestBuilder object that is ready to have its
	 *         {@link JSERequestBuilder#send()} method invoked.
	 */
	protected <T> RequestBuilder doPrepareRequestBuilder(
			ResponseReader responseReader, String methodName,
			RpcStatsContext invocationCount, String requestData, AsyncCallback<T> callback) {

		RequestBuilder rb = doPrepareRequestBuilderImpl(responseReader,
				methodName, invocationCount, requestData, callback);

		return rb;
	}

	/**
	 * Configures a RequestBuilder to send an RPC request.
	 * 
	 * @param <T>
	 *            return type for the AsyncCallback
	 * @param responseReader
	 *            instance used to read the return value of the invocation
	 * @param requestData
	 *            payload that encodes the addressing and arguments of the RPC
	 *            call
	 * @param callback
	 *            callback handler
	 * 
	 * @return a RequestBuilder object that is ready to have its
	 *         {@link JSERequestBuilder#send()} method invoked.
	 */
	private <T> RequestBuilder doPrepareRequestBuilderImpl(
			ResponseReader responseReader, String methodName,
			RpcStatsContext invocationCount, String requestData, AsyncCallback<T> callback) {

		if (getServiceEntryPoint() == null) {
			throw new NoServiceEntryPointSpecifiedException();
		}

		RequestCallback responseHandler = doCreateRequestCallback(
				responseReader, methodName, invocationCount, callback);
		RpcRequestBuilder rpcRequestBuilder = ensureRpcRequestBuilder();

		rpcRequestBuilder.create(getServiceEntryPoint());
		rpcRequestBuilder.setCallback(responseHandler);
		rpcRequestBuilder.setContentType(RPC_CONTENT_TYPE);
		rpcRequestBuilder.setRequestData(requestData);
		rpcRequestBuilder.setRequestId(invocationCount.getRequestId());
		return rpcRequestBuilder.finish();
	}

	protected abstract RpcRequestBuilder ensureRpcRequestBuilder();

}
