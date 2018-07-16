package com.android.hmvertical.utils.http;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import org.json.JSONObject;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RequestPresent {

    RequestModel requestModel;
    RequestView requestView;

    public RequestPresent() {
        requestModel = new RequestModel();
    }

    /**
     * JSONObject 返回
     * @param requestQueue
     * @param what
     * @param url
     * @param params
     * @param dataCode
     */
    public void getPresentRequestJSONObject(final RequestQueue requestQueue, int what, String url, Map<String,Object> params, final String dataCode){
        if(requestView != null){
            requestModel.getModelJSONObjectRequest(requestQueue, what, url, params, new OnResponseListener<JSONObject>() {
                @Override
                public void onStart(int what) {
                    if(requestView != null){
                        requestView.showLoadDialog(what);
                    }
                }

                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    Logger.e("----pres="+response.get().toString());
                    if(requestView != null && response != null){
                        requestView.closeLoadDialog(what);
                        requestView.requestSuccessData(what,response,dataCode);
                    }
                }

                @Override
                public void onFailed(int what, Response<JSONObject> response) {
                    Logger.e("--err--pres="+response.getException());
                    if(requestView != null){
                        requestView.closeLoadDialog(what);
                        requestView.requestFailedData(what,response);
                    }
                }

                @Override
                public void onFinish(int what) {
                    if(requestView != null){
                        requestView.closeLoadDialog(what);
                    }
                }
            });
        }
    }


    /**
     * String请求返回
     * @param requestQueue
     * @param what
     * @param url
     * @param params
     * @param dataCode
     */
    public void getPresentRequestString(RequestQueue requestQueue, int what, String url, Map<String,Object> params, final String dataCode){
        if(requestView != null){
            requestModel.getModelRequestString(requestQueue, what, url, params, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {
                    if(requestView != null){
                        requestView.showLoadDialog(what);
                    }
                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    if(requestView != null && response != null){
                        requestView.requestSuccessData(what,response,dataCode);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    if(requestView != null){
                        requestView.requestFailedData(what,response);
                    }
                }

                @Override
                public void onFinish(int what) {
                    if(requestView != null){
                        requestView.closeLoadDialog(what);
                    }
                }
            });
        }
    }

    //绑定
    public void attach(RequestView requestView) {
        this.requestView = requestView;
    }

    //解除绑定
    public void detach() {
        if (requestView != null) {
            requestView = null;
        }
    }

    //取消网络请求
    public void interruptHttp(int flag,RequestQueue requestQueue) {
        requestModel.cancleHttpPost(flag,requestQueue);
    }
}
