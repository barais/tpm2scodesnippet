package org.gwtrpc4j.http;

import org.gwtrpc4j.JRpcServiceProxy.SynchroCallback;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;

public class RequestCallbackAdapter<T> implements RequestCallback {

	/**
	 * {@link AsyncCallback} to notify or success or failure.
	 */
	private final AsyncCallback<T> callback;

	/**
	 * Used for stats recording.
	 */
	private final String methodName;

	/**
	 * Used for stats recording.
	 */
	private final int requestId;

	/**
	 * Instance which will read the expected return type out of the
	 * {@link SerializationStreamReader}.
	 */
	private final ResponseReader responseReader;

	/**
	 * {@link SerializationStreamFactory} for creating
	 * {@link SerializationStreamReader}s.
	 */
	private final SerializationStreamFactory streamFactory;

	boolean synchro = false;

	public RequestCallbackAdapter(SerializationStreamFactory streamFactory,
			String methodName, int requestId, AsyncCallback<T> callback,
			ResponseReader responseReader) {
		assert streamFactory != null;
		assert callback != null;
		assert responseReader != null;

		this.streamFactory = streamFactory;
		this.callback = callback;
		this.methodName = methodName;
		this.requestId = requestId;
		this.responseReader = responseReader;
		this.synchro = callback instanceof SynchroCallback;
	}

	public boolean isSynchro() {
		return this.synchro;
	}

	public void onError(Request request, Throwable exception) {
		callback.onFailure(exception);
	}

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

	@SuppressWarnings(value = { "unchecked" })
	public void onResponseReceived(Request request, Response response) {
		T result = null;
		Throwable caught = null;
		try {
			String encodedResponse = response.getText();
			int statusCode = response.getStatusCode();

			// response.getHeader("Set-Cookie");

			if (statusCode != Response.SC_OK) {
				caught = new StatusCodeException(statusCode, encodedResponse);
			} else if (encodedResponse == null) {
				// This can happen if the XHR is interrupted by the server dying
				caught = new InvocationException("No response payload");
			} else if (isReturnValue(encodedResponse)) {
				result = (T) responseReader.read(streamFactory
						.createStreamReader(encodedResponse));
			} else if (isThrownException(encodedResponse)) {
				caught = (Throwable) streamFactory.createStreamReader(
						encodedResponse).readObject();
			} else {
				caught = new InvocationException(encodedResponse);
			}
		} catch (com.google.gwt.user.client.rpc.SerializationException e) {
			caught = new IncompatibleRemoteServiceException(
					"The response could not be deserialized", e);
		} catch (Throwable e) {
			caught = e;
		} finally {
			// boolean toss = RemoteServiceProxy.isStatsAvailable()
			// && RemoteServiceProxy.stats(RemoteServiceProxy.timeStat(
			// methodName, requestId, "responseDeserialized"));
		}

		try {
			if (caught == null) {
				callback.onSuccess(result);
			} else {
				callback.onFailure(caught);
			}
		} finally {
			// boolean toss = RemoteServiceProxy.isStatsAvailable()
			// && RemoteServiceProxy.stats(RemoteServiceProxy.timeStat(
			// methodName, requestId, "end"));
		}
	}
}
