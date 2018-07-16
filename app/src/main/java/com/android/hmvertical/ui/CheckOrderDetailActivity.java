package com.android.hmvertical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.adapter.CheckOrderDetailAdapter;
import com.android.hmvertical.bean.CheckDetailBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.interfaces.OnCusItemClickListener;
import com.android.hmvertical.scan.CommentScanActivity;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.VoiceUtils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.android.hmvertical.view.ProDialogView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zbar.lib.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckOrderDetailActivity extends CommentScanActivity implements RequestView<JSONObject> ,OnCusItemClickListener{

    @BindView(R.id.checkDetailRecy)
    RecyclerView checkDetailRecy;
    @BindView(R.id.commentTitleBackImg)
    ImageView commentTitleBackImg;
    @BindView(R.id.commentTitleTv)
    TextView commentTitleTv;
    @BindView(R.id.commentTitleScanImg)
    ImageView commentTitleScanImg;
    @BindView(R.id.inputOrderDetailEdit)
    EditText inputOrderDetailEdit;

    // RecyclerView mRecyclerView;
    private RequestQueue requestQueue;
    RequestPresent requestPresent;

    private List<CheckDetailBean.ListBean> checkDetailBeanList;
    private CheckOrderDetailAdapter checkOrderDetailAdapter;

    //当前页
    int currIndexPager = 1;

    private String number = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_detail_layout);
        ButterKnife.bind(this);

        initViews();

        initData();

        number = getIntent().getStringExtra("checkNum");

//        if (!Utils.isEmpty(number) && requestPresent != null) {
//            getServerData(number, "", "");
//        }
        Logger.e("----checkNum=" + number);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.isEmpty(number) && requestPresent != null) {
            getServerData(number, "", "");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //获取数据
    private void getServerData(String number, String qrcode, String checkNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "CTSTATION");
        map.put("number", number);
        map.put("checkNumber", checkNumber + "");
        map.put("QRCode", qrcode + "");
        map.put("page", currIndexPager);
        map.put("rows", "10");
        requestPresent.getPresentRequestJSONObject(requestQueue, 1, Constants.GET_CHECKORDER_DETAIL_URL, map, "22");
    }

    //扫描返回
    @Override
    public void getScanResultData(String trim) {
        super.getScanResultData(trim);
        Logger.e("----scan="+trim);
        if (!Utils.isEmpty(number) && requestPresent != null) {
            getServerData(number, trim, "");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("---requ="+requestCode+"-=result="+resultCode);
        if(requestCode == 1001){
            if(data != null){
                String scanResult = data.getStringExtra("scanResult");
                Logger.e("----scanResult="+scanResult);
                getServerData(number, scanResult, "");
            }
        }

    }

    private void initData() {
        requestQueue = NoHttp.newRequestQueue(2);
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
        checkDetailBeanList = new ArrayList<>();
        checkOrderDetailAdapter = new CheckOrderDetailAdapter(checkDetailBeanList, CheckOrderDetailActivity.this);
        checkDetailRecy.setAdapter(checkOrderDetailAdapter);
        checkOrderDetailAdapter.setOnCusItemClickListener(this);
    }

    private void initViews() {
        commentTitleTv.setText("检测单详情");
        commentTitleScanImg.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        checkDetailRecy.setLayoutManager(layoutManager);
    }


    @Override
    public void showLoadDialog(int what) {
        showDialog("加载中...");
    }

    @Override
    public void closeLoadDialog(int what) {

    }

    @Override
    public void requestSuccessData(int what, Response<JSONObject> response, String bot) {
        Logger.e("------response-=" + response.get().toString());
        closeDialog();
        try {
            if(what == 1){  //获取详情
                if (response.get().getInt("code") == 200) {
                    String data = response.get().getString("data");
                    if (!Utils.isEmpty(data) && !"[]".equals(data)) {
                        VoiceUtils.showVoice(CheckOrderDetailActivity.this,R.raw.beep);
                        List<CheckDetailBean> tmpLit = new Gson().fromJson(data, new TypeToken<List<CheckDetailBean>>() {
                        }.getType());
                        checkDetailBeanList.clear();
//                        checkDetailBeanList.addAll(tmpLit);
//                        checkOrderDetailAdapter.notifyDataSetChanged();
                    }else{
                        checkDetailBeanList.clear();
                        checkOrderDetailAdapter.notifyDataSetChanged();
                        VoiceUtils.showToastVoice(CheckOrderDetailActivity.this,R.raw.warning,"无数据!");
                    }
                }
            }else if(what == 2){    //提交
                 if(response.get().getInt("code") == 200){
                     VoiceUtils.showToastVoice(CheckOrderDetailActivity.this,R.raw.beep,"提交成功!");
                     finish();
                 }  else{
                     VoiceUtils.showToastVoice(CheckOrderDetailActivity.this,R.raw.warning,""+response.get().getInt("code")+response.get().getString("msg"));
                 }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailedData(int what, Response<JSONObject> response) {
        closeDialog();
        if(response.getException() != null){
            VoiceUtils.showToastVoice(CheckOrderDetailActivity.this,R.raw.warning,"错误信息:"+response.getException().toString());
        }

    }

    @OnClick({R.id.searchBtn,R.id.confirmSubBtn,R.id.commentTitleScanImg})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.commentTitleScanImg:  //摄像头搜索
                Intent intent = new Intent(CheckOrderDetailActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1001);
                break;
            case R.id.searchBtn:    //搜索
                String editData =inputOrderDetailEdit.getText().toString().trim();
                if(!Utils.isEmpty(editData) && !Utils.isEmpty(number)){
                    getServerData(number,"",editData);
                }else{
                    getServerData(number,"","");
                }
                break;
            case R.id.confirmSubBtn:    //提交
                subConfitData();
                break;
        }

    }

    private void subConfitData() {
        final ProDialogView proDialogView = new ProDialogView(CheckOrderDetailActivity.this);
        proDialogView.show();
        proDialogView.setTitle("提醒");
        proDialogView.setContent("是否提交？");
        proDialogView.setleftText("否");
        proDialogView.setrightText("是");
        proDialogView.setListener(new ProDialogView.OnPromptDialogListener() {
            @Override
            public void leftClick(int code) {
                proDialogView.dismiss();

            }

            @Override
            public void rightClick(int code) {
                proDialogView.dismiss();
                if(requestPresent != null){
                    Map<String,Object> mp = new HashMap<>();
                    mp.put("key","CTSTATION");
                    mp.put("number",number+"");
                    requestPresent.getPresentRequestJSONObject(requestQueue,2,Constants.CONFIRM_CHECK_ORDER_URL,mp,"33");
                }
            }
        });
    }


    @Override
    public void doOnItemClick(int position) {
        Intent intent = new Intent(CheckOrderDetailActivity.this,OperatorCheckOrderActivity.class);
        intent.putExtra("checkbean",checkDetailBeanList.get(position));
        intent.putExtra("number",number);
        startActivity(intent);


    }
}
