package com.android.hmvertical.utils.http;

import android.util.Log;

import com.android.hmvertical.constants.Constants;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import org.json.JSONObject;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RequestModel {

    public void getModelJSONObjectRequest(RequestQueue requestQueue, int what, String url, Map<String,Object> params, OnResponseListener<JSONObject> onResponseListener){
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(Constants.getAbsoluteUrl()+url, RequestMethod.POST);
        //遍历map，将参数添加至requst中
        if(!params.isEmpty()){
            for(Map.Entry<String,Object> map : params.entrySet()){
                jsonObjectRequest.add(map.getKey(),map.getValue()+"");
            }
        }
        Log.e("rrrr","-----params==="+url+"---"+params.toString());
        requestQueue.add(what,jsonObjectRequest,onResponseListener);

    }

    /**
     * 请求StringRequest
     * @param what
     * @param url
     * @param params
     * @param onResponseListener
     */
    public void getModelRequestString(RequestQueue requestQueue,int what, String url, Map<String,Object> params, OnResponseListener<String> onResponseListener){
        Request<String> stringRequest = NoHttp.createStringRequest(Constants.getAbsoluteUrl()+url,RequestMethod.POST);
        //遍历map，将参数添加至requst中
        if(!params.isEmpty()){
            for(Map.Entry<String,Object> map : params.entrySet()){
                stringRequest.add(map.getKey(),map.getValue()+"");
            }
        }
        Log.e("rrrr","-----params==="+params.toString());
        requestQueue.add(what,stringRequest,onResponseListener);
    }

    /**
     * 取消网络请求
     * @param what
     */
    public void cancleHttpPost(int what,RequestQueue requestQueue){
        requestQueue.cancelBySign(what);
    }

}
