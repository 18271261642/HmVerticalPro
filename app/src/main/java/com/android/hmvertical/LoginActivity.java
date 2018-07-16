package com.android.hmvertical;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.hmvertical.base.BaseActivity;
import com.android.hmvertical.base.MyApplication;
import com.android.hmvertical.bean.UserInfoBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.utils.SharedPreferenceUtils;
import com.android.hmvertical.utils.UpdateChecker;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.VoiceUtils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/21.
 */

public class LoginActivity extends BaseActivity implements RequestView<JSONObject>{

    @BindView(R.id.login_accountEdit)
    EditText loginAccountEdit;
    @BindView(R.id.login_pwdEdit)
    EditText loginPwdEdit;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    View botView;
    private BottomSheetDialog bottomSheetDialog;
    private RequestPresent requestPresent;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        String uName = (String) SharedPreferenceUtils.get(this,"username","");
        String uPwd = (String) SharedPreferenceUtils.get(this,"userpwd","");
        if(!Utils.isEmpty(uName) && !Utils.isEmpty(uName)){
            loginAccountEdit.setText(uName);
            loginPwdEdit.setText(uPwd);
        }
        requestQueue = NoHttp.newRequestQueue(1);
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
    }

    @OnClick({R.id.loginBtn,R.id.loginServerTv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.loginBtn: //登录
                String userAcc = loginAccountEdit.getText().toString().trim();
                String userPwd = loginPwdEdit.getText().toString().trim();
                if(!Utils.isEmpty(userAcc) && !Utils.isEmpty(userPwd)){
                    loginToServer(userAcc,userPwd);
                }
                break;
            case R.id.loginServerTv:    //选择服务器
                chooseNetServer();
                break;
        }

    }

    private void loginToServer(String userAcc, String userPwd) {
        if(requestPresent != null){
            Map<String,Object> mps = new HashMap<>();
            mps.put("userName",userAcc);
            mps.put("password",userPwd);
            requestPresent.getPresentRequestJSONObject(requestQueue,1, Constants.USER_LOGIN_URL,mps,"11");
        }
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
            if(response.get().getInt("code") == 200){
                String data = response.get().getString("data");
                if(data != null && !data.equals("[]")){
                    VoiceUtils.showVoice(this,R.raw.beep);
                    SharedPreferenceUtils.put(this,"username",loginAccountEdit.getText().toString().trim());
                    SharedPreferenceUtils.put(this,"userpwd",loginPwdEdit.getText().toString().trim());
                    UserInfoBean userInfoBean = new Gson().fromJson(data,UserInfoBean.class);
                    MyApplication.setUserKey(userInfoBean.getCheckStationCode());
                    setUserInfoData(userInfoBean);
                    startActivity(HomeAcitvity.class);

                }
            }else{
                VoiceUtils.showToastVoice(this,R.raw.warning,""+response.get().getString("msg"));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(requestPresent != null)
            requestPresent.detach();
    }

    private void chooseNetServer() {
        botView = LayoutInflater.from(this).inflate(R.layout.item_bot_view_layout, null);
        TextView formalTv = (TextView) botView.findViewById(R.id.formalServerTv);
        TextView testTv = (TextView) botView.findViewById(R.id.testServerTv);
        TextView localTv = (TextView) botView.findViewById(R.id.localServerTv);
        TextView cancleTv = (TextView) botView.findViewById(R.id.calcleTv);
        formalTv.setOnClickListener(new BotMenuClickListener());
        testTv.setOnClickListener(new BotMenuClickListener());
        localTv.setOnClickListener(new BotMenuClickListener());
        cancleTv.setOnClickListener(new BotMenuClickListener());
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(botView);
        bottomSheetDialog.show();

    }

    class BotMenuClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.formalServerTv:   //正式服
                    MyApplication.getUrlFlagCode(0);
                    break;
                case R.id.testServerTv: //测试服
                    MyApplication.getUrlFlagCode(1);
                    break;
                case R.id.localServerTv:    //本地服
                    MyApplication.getUrlFlagCode(2);
                    break;
                case R.id.calcleTv: //取消

                    break;
            }
            if (bottomSheetDialog != null) {
                bottomSheetDialog.dismiss();
            }
        }
    }
}
