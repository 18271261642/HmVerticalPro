package com.android.hmvertical.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.adapter.OperatorAdapter;
import com.android.hmvertical.bean.GetTypeModelBean;
import com.android.hmvertical.bean.OperatorBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/13.
 */

public class GetTypeModelView extends Dialog implements RequestView<JSONObject>,View.OnClickListener{

    public GetTypeModelListener getTypeModelListener;

    public void setGetTypeModelListener(GetTypeModelListener getTypeModelListener) {
        this.getTypeModelListener = getTypeModelListener;
    }

    private Context mContext;
    private List<GetTypeModelBean> getTypeModelBeanList;
    //adapter
    private GetTypeModelAdapter getTypeModelAdapter;


    private RequestPresent requestPresent;
    private RequestQueue requestQueue;


    private RecyclerView recyclerView;
    private Button button;

    private Map<Integer,Object> typeMap;
    private List<Map<String,Object>> listTypeMap;


    public GetTypeModelView(@NonNull Context context) {
        super(context);
        this.mContext = context;

    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.getTypeModelRecyView);
        button = (Button) findViewById(R.id.getTypeBtn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        button.setOnClickListener(this);
    }

    private void initData() {
        requestQueue = NoHttp.newRequestQueue();
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
        getTypeModelBeanList = new ArrayList<>();
        getTypeModelAdapter = new GetTypeModelAdapter(getTypeModelBeanList);
        recyclerView.setAdapter(getTypeModelAdapter);
        typeMap = new HashMap<>();
        listTypeMap = new ArrayList<>();

    }

    private void getData() {
        String url = Constants.GETTYPE_AND_MODELS_URL;
        if(requestPresent != null){
            Map<String,Object> masp = new HashMap<>();
            masp.put("key","CTSTATION");
            requestPresent.getPresentRequestJSONObject(requestQueue,1,url,masp,"11");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_type_model_view);

        initViews();

        initData();

        getData();
    }

    @Override
    public void showLoadDialog(int what) {

    }

    @Override
    public void closeLoadDialog(int what) {

    }

    @Override
    public void requestSuccessData(int what, Response<JSONObject> response, String bot) {
        Logger.e("------view-="+response.get().toString());
        try {
            if(response.get().getInt("code") == 200){
                String data = response.get().getString("data");
                if(!Utils.isEmpty(data) && !data.equals("[]")){
                     List<GetTypeModelBean> tmpLt = new Gson().fromJson(data,new TypeToken<List<GetTypeModelBean>>(){}.getType());
                    getTypeModelBeanList.addAll(tmpLt);
                    getTypeModelAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailedData(int what, Response<JSONObject> response) {

    }

    //按钮点击
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getTypeBtn:
                //遍历map组装数据
                Map<String,Object> dataMap;
                listTypeMap.clear();
                for(Map.Entry<Integer,Object> mp : typeMap.entrySet()){
                    dataMap = new HashMap<>();
                    dataMap.put("typeId",mp.getKey());
                    dataMap.put("modelId",mp.getValue());
                    listTypeMap.add(dataMap);
                }
                Logger.e("-----getyteview="+listTypeMap.toString());
                if(getTypeModelListener != null){
                    getTypeModelListener.getTypeModelData(listTypeMap.toString());
                }

                break;
        }
    }

    //adapter
    private class GetTypeModelAdapter extends RecyclerView.Adapter<GetTypeModelAdapter.GetTypeModelViewHolder>{

        private List<GetTypeModelBean> list;

        public GetTypeModelAdapter(List<GetTypeModelBean> list) {
            this.list = list;
        }

        @Override
        public GetTypeModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_gettype_layout,parent,false);
            return new GetTypeModelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GetTypeModelViewHolder holder, final int position) {
            holder.tv_modelName.setText(list.get(position).getTypeName());

            //spinner列表
            Spinner spinner = holder.typeSpinner;
            CusSpinnerAdapter cusSpinnerAdapter = new CusSpinnerAdapter(list.get(position).getModels());
            spinner.setAdapter(cusSpinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int positions, long id) {
                    typeMap.put(list.get(position).getTypeId(),list.get(position).getModels().get(positions).getModelId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    typeMap.put(list.get(0).getTypeId(),list.get(0).getModels().get(0).getModelId());
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class GetTypeModelViewHolder extends RecyclerView.ViewHolder{

            TextView tv_modelName;
            Spinner typeSpinner;

            public GetTypeModelViewHolder(View itemView) {
                super(itemView);
                tv_modelName = (TextView) itemView.findViewById(R.id.item_getTypeTv);
                typeSpinner = (Spinner) itemView.findViewById(R.id.item_getTypeSpinner);

            }
        }
    }

    //列表的adapter
    class CusSpinnerAdapter extends BaseAdapter {

        private List<GetTypeModelBean.ModelsBean> resultsBeans;
        private LayoutInflater layoutInflater;


        public CusSpinnerAdapter(List<GetTypeModelBean.ModelsBean> resultsBeans) {
            this.resultsBeans = resultsBeans;
            layoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return resultsBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return resultsBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CusSpinnerAdapter.CusSpinnerViewHolder holder = null;

            if(convertView == null){
                holder = new CusSpinnerAdapter.CusSpinnerViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_spinner_layout,parent,false);
                holder.tv = (TextView) convertView.findViewById(R.id.item_spinner_tv);
                convertView.setTag(holder);
            }else{
                holder = (CusSpinnerViewHolder) convertView.getTag();
            }
            holder.tv.setText(resultsBeans.get(position).getModelName());
            return convertView;
        }

        class CusSpinnerViewHolder {

            TextView tv;

        }
    }

    public interface GetTypeModelListener{
        void getTypeModelData(String jsonMap);
    }
}
