package org.gwtrpc4j;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

public class RCPProxyImpl extends RpcServiceProxy {

	protected RCPProxyImpl(
			SerializationPolicyProvider serializationPolicyProvider,
			String moduleBaseURL, String remoteServiceRelativePath,
			String serializationPolicyStrongName) {
		super(serializationPolicyProvider, moduleBaseURL,
				remoteServiceRelativePath, serializationPolicyStrongName);
	}

	public <T extends Object> void invoke() {
		RpcStatsContext invocationCount = new RpcStatsContext(0);
		String methodName = "";
		String requestData = "gwt msg";
		RequestCallbackAdapter.ResponseReader typeReturn = RequestCallbackAdapter.ResponseReader.OBJECT;

		AsyncCallback<T> callback = new AsyncCallback<T>() {

			public void onSuccess(Object result) {

			}

			public void onFailure(Throwable caught) {

			}
		};
		RequestCallbackAdapter<T> rca = new RequestCallbackAdapter<T>(this,
				methodName, new RpcStatsContext(1), callback,
				RequestCallbackAdapter.ResponseReader.OBJECT);

		doInvoke(typeReturn, methodName, invocationCount, requestData, callback);

	}

	@Override
	protected RpcRequestBuilder ensureRpcRequestBuilder() {
		return null;
	}

	public String getSerializationPolicyName() {
		// TODO Auto-generated method stub
		return null;
	}

}
