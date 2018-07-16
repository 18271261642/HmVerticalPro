package com.android.hmvertical.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.hmvertical.R;
import com.android.hmvertical.adapter.CheckOrderListAdapter;
import com.android.hmvertical.base.BaseActivity;
import com.android.hmvertical.bean.CheckDetailBean;
import com.android.hmvertical.bean.CheckOrderBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.interfaces.OnCusItemClickListener;
import com.android.hmvertical.scan.CommentScanActivity;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.VoiceUtils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.android.hmvertical.view.refresh.PullLoadMoreRecyclerView;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckOrderListActivit extends CommentScanActivity implements RequestView<JSONObject>,
      OnCusItemClickListener,PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.commentTitleBackImg)
    ImageView commentTitleBackImg;
    @BindView(R.id.commentTitleTv)
    TextView commentTitleTv;
    @BindView(R.id.commentTitleScanImg)
    ImageView commentTitleScanImg;
    @BindView(R.id.getCheckOrderListRecy)
    PullLoadMoreRecyclerView getCheckOrderListRecy;
    RecyclerView mRecyclerView;

    RequestQueue requestQueue;
    RequestPresent requestPresent;
    private List<CheckOrderBean.ListBean> checkOrderBeanList;
    private CheckOrderListAdapter adapter;
    private int currPage = 1;
    int toatl ; //总页数

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.e("---had="+msg.what);
            if(getCheckOrderListRecy != null ){
                getCheckOrderListRecy.setPullRefreshEnable(true);
                getCheckOrderListRecy.setPushRefreshEnable(true);
                getCheckOrderListRecy.setPullLoadMoreCompleted();
            }
        }
    };

    //标识
    int flagCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orderlist_layout);
        ButterKnife.bind(this);

        initViews();
        initData();

    }



    @Override
    protected void onResume() {
        super.onResume();
        getData(currPage);
    }

    private void getData(int currPage){
        if(checkOrderBeanList != null)
            checkOrderBeanList.clear();
        if(requestPresent != null){
            String url = null;
            if(flagCode == 1001){
                url = Constants.CHECK_ORDER_LIST_URL;
            }else if(flagCode == 1002){
                url = Constants.GET_WAIT_CHECK_LIST;
            }else if(flagCode == 1003){
                url = Constants.GET_OUT_WEARHOUSE_ORDER_LIST;
            }
            Map<String,Object> maps = new HashMap<>();
            maps.put("key",getUserInfoData().getCheckStationCode());
            maps.put("page",currPage);
            maps.put("rows","10");
            requestPresent.getPresentRequestJSONObject(requestQueue,1,url ,maps,"11");
        }
    }

    private void initData() {

        requestQueue = NoHttp.newRequestQueue(2);
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
    }

    private void initViews() {
        flagCode = getIntent().getIntExtra("flagCode",0);
        commentTitleScanImg.setVisibility(View.INVISIBLE);
        if(flagCode == 1001){
            commentTitleTv.setText("检测单列表");
        }else if(flagCode == 1002){
            commentTitleTv.setText("扫码入库列表");
        }else if(flagCode == 1003){
            commentTitleTv.setText("扫码出库列表");
        }

        mRecyclerView = getCheckOrderListRecy.getRecyclerView();
        //显示下拉刷新
        getCheckOrderListRecy.setRefreshing(true);
        //设置上拉刷新文字
        getCheckOrderListRecy.setFooterViewText("加载中...");
        //设置为垂直布局
        getCheckOrderListRecy.setLinearLayout();
        getCheckOrderListRecy.setPushRefreshEnable(true);
        getCheckOrderListRecy.setPullRefreshEnable(true);
        //设置监听
        getCheckOrderListRecy.setOnPullLoadMoreListener(this);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
        checkOrderBeanList = new ArrayList<>();
        adapter = new CheckOrderListAdapter(checkOrderBeanList,CheckOrderListActivit.this);
        getCheckOrderListRecy.setAdapter(adapter);
        adapter.setOnCusItemClickListener(this);
    }

    @OnClick(R.id.commentTitleBackImg)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestPresent.detach();
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
        closeDialog();
        Logger.e("----response-="+response.get().toString());
        try {
            if(response.get().getInt("code") == 200){
                String data = response.get().getString("data");
                if(!Utils.isEmpty(data) ){
                    CheckOrderBean checkOrderBean = new Gson().fromJson(data, CheckOrderBean.class);
                    //总页数
                    toatl = checkOrderBean.getTotal();
                    if(toatl == 1){
                        handler.sendEmptyMessage(1001);
                    }
                    if(checkOrderBean.getList() != null && checkOrderBean.getList().size() > 0){
                        getCheckOrderListRecy.setRefreshing(false);
                        getCheckOrderListRecy.setPullLoadMoreCompleted();
                        //集合
                        List<CheckOrderBean.ListBean> tmLt = checkOrderBean.getList();
                        checkOrderBeanList.addAll(tmLt);
                        adapter.notifyDataSetChanged();
                    }else{
                        checkOrderBeanList.clear();
                        adapter.notifyDataSetChanged();
                        VoiceUtils.showToastVoice(CheckOrderListActivit.this,R.raw.warning,"无数据!");
                        if(getCheckOrderListRecy != null){
                            getCheckOrderListRecy.setRefreshing(false);
                        }
                    }

                }else{
                    if(getCheckOrderListRecy != null ){
                        getCheckOrderListRecy.setRefreshing(false);
                    }
                }

//                else{
//                    checkOrderBeanList.clear();
//                    adapter.notifyDataSetChanged();
//                    VoiceUtils.showToastVoice(CheckOrderListActivit.this,R.raw.warning,"无数据!");
//                }

            }else{

                VoiceUtils.showToastVoice(CheckOrderListActivit.this,R.raw.warning,"非200返回:"+response.get().getString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void requestFailedData(int what, Response<JSONObject> response) {
        closeDialog();
        if(getCheckOrderListRecy != null){
            getCheckOrderListRecy.setRefreshing(false);
        }
        Logger.e("-----error="+response.getException().toString());
        VoiceUtils.showToastVoice(CheckOrderListActivit.this,R.raw.warning,"错误信息:"+response.getException().toString());
    }

    //item点击
    @Override
    public void doOnItemClick(int position) {
        Class cls = null;
        String outFlag = null;
        if(flagCode == 1001){
            cls = TmpCheckOrderDetailActivity.class;
        }else if(flagCode == 1002){ //入库
            outFlag = "in";
            cls = WaitCheckDetailActivity.class;
        }else if(flagCode == 1003){ //出库
            outFlag = "out";
            cls = WaitCheckDetailActivity.class;
        }
        startActivity(cls,new String[]{"checkNum","outFlag"},new String[]{checkOrderBeanList.get(position).getNumber()+"",outFlag});
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        checkOrderBeanList.clear();
        adapter.notifyDataSetChanged();
        currPage = 1;
        getData(currPage);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if(currPage == toatl){
            handler.sendEmptyMessage(1001);
            //showToast("加载完了");
        }else{
            currPage = currPage + 1;
            getData(currPage);
        }
    }
}
