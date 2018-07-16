package com.android.hmvertical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.bean.WaitInfoCheckBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.scan.CommentScanActivity;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.VoiceUtils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.android.hmvertical.view.ConfirmDialogView;
import com.google.gson.Gson;
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
 * Created by Administrator on 2018/7/4.
 */

public class WaitCheckDetailActivity extends CommentScanActivity implements RequestView<JSONObject> {


    @BindView(R.id.commentTitleBackImg)
    ImageView commentTitleBackImg;
    @BindView(R.id.commentTitleTv)
    TextView commentTitleTv;
    @BindView(R.id.commentTitleScanImg)
    ImageView commentTitleScanImg;
    @BindView(R.id.waitCheckDetailNumberTv)
    TextView waitCheckDetailNumberTv;
    @BindView(R.id.waitCheckDetailCompanyTv)
    TextView waitCheckDetailCompanyTv;
    @BindView(R.id.waitCheckDetailCountTv)
    TextView waitCheckDetailCountTv;
    @BindView(R.id.waitCheckDetailTimeTv)
    TextView waitCheckDetailTimeTv;
    @BindView(R.id.waitCheckDetailStateTv)
    TextView waitCheckDetailStateTv;
    @BindView(R.id.waitCheckDetailRemarkTv)
    TextView waitCheckDetailRemarkTv;
    @BindView(R.id.waitCheckDetailNumTv)
    TextView waitCheckDetailNumTv;
    @BindView(R.id.waitCheckDetailScanTv)
    TextView waitCheckDetailScanTv;

    private ConfirmDialogView confirmDialogView;
    String checkNum;
    String outFlag;
    //统计扫描验证的数量
    private List<String> scanList;
    private RequestPresent requestPresent;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_check_detail);
        ButterKnife.bind(this);


        initViews();
        initData();

        getWaitDetailData(checkNum);
    }

    private void initViews() {
        waitCheckDetailScanTv.setText("0瓶");
        commentTitleScanImg.setVisibility(View.VISIBLE);
        commentTitleBackImg.setVisibility(View.VISIBLE);
    }

    //扫描返回
    @Override
    public void getScanResultData(String botCode) {
        super.getScanResultData(botCode);
        verticalScanData(botCode);
    }

    //验证扫描
    private void verticalScanData(String botCode) {
        if (requestPresent != null) {
            String url = null;
            if (!Utils.isEmpty(outFlag)) {
                if (outFlag.equals("out")) {  //出库
                    url = Constants.SUBMIT_OUT;
                } else if (outFlag.equals("in")) { //入库
                    url = Constants.SUBMIT_BOT_INFO_HOUSE;
                }
            }
            Map<String, Object> mp = new HashMap<>();
            mp.put("key", getUserInfoData().getCheckStationCode());
            mp.put("number", checkNum + "");
            mp.put("QRCode", botCode);
            requestPresent.getPresentRequestJSONObject(requestQueue, 2, url, mp, botCode);
        }
    }

    //获取详情
    private void getWaitDetailData(String checkNum) {
        if (requestPresent != null) {
            String url = null;
            if (!Utils.isEmpty(outFlag)) {
                if (outFlag.equals("out")) {  //出库
                    url = Constants.GET_OUT_WEARHOUSE_BOT_DETAIL;
                } else if (outFlag.equals("in")) { //入库
                    url = Constants.GET_WAIT_CHECK_DETAIL;
                }
            }
            Map<String, Object> maps = new HashMap<>();
            maps.put("key", getUserInfoData().getCheckStationCode());
            maps.put("number", checkNum + "");
            requestPresent.getPresentRequestJSONObject(requestQueue, 1, url, maps, "1");
        }
    }

    private void initData() {
        checkNum = getIntent().getStringExtra("checkNum");
        outFlag = getIntent().getStringExtra("outFlag");
        Logger.e("----out-=" + outFlag);
        if (!Utils.isEmpty(outFlag)) {
            if (outFlag.equals("out")) {  //出库
                commentTitleTv.setText("出库操作");
            } else if (outFlag.equals("in")) { //入库
                commentTitleTv.setText("入库操作");
            }
        }
        scanList = new ArrayList<>();
        requestQueue = NoHttp.newRequestQueue(1);
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
    }

    @Override
    public void showLoadDialog(int what) {
        showDialog("加载中...");
    }

    @Override
    public void closeLoadDialog(int what) {
        closeDialog();
    }

    @Override
    public void requestSuccessData(int what, Response<JSONObject> response, String bot) {
        closeDialog();
        try {
            if (response.get().getInt("code") == 200) {
                if (what == 1) {  //获取详情返回
                    String data = response.get().getString("data");
                    if (!Utils.isEmpty(data)) {
                        WaitInfoCheckBean waitInfoCheckBean = new Gson().fromJson(data, WaitInfoCheckBean.class);
                        showWatiInData(waitInfoCheckBean);
                        if (waitInfoCheckBean.getWaitCount() == 0) {  //已完成入库
                            VoiceUtils.showVoice(this, R.raw.beep);
                            showAlertSubData();
                        }
                    }
                } else if (what == 2) {    //扫描返回

                    if (!scanList.contains(bot)) {
                        VoiceUtils.showVoice(this, R.raw.beep);
                        scanList.add(bot);
                        waitCheckDetailScanTv.setText("已扫描:" + scanList.size() + "瓶");
                        getWaitDetailData(checkNum);
                    } else {
                        VoiceUtils.showToastVoice(this, R.raw.warning, "该条码已扫描!");
                    }

                }

            } else {
                VoiceUtils.showToastVoice(this, R.raw.warning, response.get().getString("msg") + "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailedData(int what, Response<JSONObject> response) {
        closeDialog();
        VoiceUtils.showToastVoice(this,R.raw.warning,"错误信息:"+response.responseCode()+response.getException().toString());
    }

    //展示数据
    private void showWatiInData(WaitInfoCheckBean waitInfoCheckBean) {
        waitCheckDetailNumberTv.setText(waitInfoCheckBean.getCheckOrder().getNumber());
        waitCheckDetailCompanyTv.setText(waitInfoCheckBean.getCheckOrder().getCompany());
        waitCheckDetailCountTv.setText(waitInfoCheckBean.getCheckOrder().getCount() + "");
        waitCheckDetailTimeTv.setText(Utils.longToDate(waitInfoCheckBean.getCheckOrder().getCreatetime()));
        /**
         * WAIT_AUDITING(1,"待接收"),
         WAIT_INTO(2,"待入库"),
         CHECKING(3,"检测中"),
         WAIT_CONFIRM(4,"检测完成"),
         WAIT_OUT(5,"待出库"),
         CONFIRM(6,"已确认"),
         REJECT_AUDITING(7,"拒绝接收"),
         REJECT_INTO(8,"拒绝入库")
         */
        int state = waitInfoCheckBean.getCheckOrder().getCheckstatus();
        if (state == 1) {
            waitCheckDetailStateTv.setText("待接收");
        } else if (state == 2) {
            waitCheckDetailStateTv.setText("待入库");
        } else if (state == 3) {
            waitCheckDetailStateTv.setText("检测中");
        } else if (state == 4) {
            waitCheckDetailStateTv.setText("检测完成");
        } else if (state == 5) {
            waitCheckDetailStateTv.setText("待出库");
        } else if (state == 6) {
            waitCheckDetailStateTv.setText("已确认");
        } else if (state == 7) {
            waitCheckDetailStateTv.setText("拒绝接收");
        } else if (state == 8) {
            waitCheckDetailStateTv.setText("拒绝入库");
        }
        waitCheckDetailRemarkTv.setText(waitInfoCheckBean.getCheckOrder().getRemark() + "");

        waitCheckDetailNumTv.setText(waitInfoCheckBean.getWaitCount() + "");
    }


    //完成入库提示
    private void showAlertSubData() {
        confirmDialogView = new ConfirmDialogView(this);
        confirmDialogView.show();
        confirmDialogView.setCancelable(false);
        confirmDialogView.setCanceledOnTouchOutside(false);
        confirmDialogView.setTitle("提醒");
        confirmDialogView.setContent("该单已完成!");
        confirmDialogView.setListener(new ConfirmDialogView.ClickListener() {
            @Override
            public void doDismiss() {
                confirmDialogView.dismiss();
                finish();
            }
        });

    }

    //摄像头扫描返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            if(data != null){
                String scanResult = data.getStringExtra("scanResult");
                if(!Utils.isEmpty(scanResult)){
                    verticalScanData(scanResult);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestPresent != null)
            requestPresent.detach();
    }

    @OnClick({R.id.commentTitleBackImg, R.id.commentTitleScanImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commentTitleBackImg:  //返回
                finish();
                break;
            case R.id.commentTitleScanImg:  //摄像头扫描
                Intent intent = new Intent(WaitCheckDetailActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1001);
                break;
        }
    }
}
