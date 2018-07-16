package com.android.hmvertical;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.jb.barcode.BarcodeManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hmvertical.base.MyApplication;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.utils.Utils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mexxen.barcode.BarcodeEvent;
import com.mexxen.barcode.BarcodeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_edt_show)
    TextView tvEdtShow;
    @BindView(R.id.btn_resest)
    Button btnResest;

    private Toast mToast;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        judgePhoneModels();

    }

    @OnClick(R.id.btn_resest)
    public void onViewClicked() {
        tvEdtShow.setText("");
    }

    /**
     * 扫描模块
     */
    public final static String MX5050_MANUFACTURER = "scx35_sp7730eccuccspecBplus_UUI";// mx5050手持机生产厂商
    public final static String HT380K_MANUFACTURER = "M9PLUS";// ht380k手持机生产厂商
    private final static String HT380K_ScanAction = "com.jb.action.F4key";
    private long nowTime = 0;
    private long lastTime = 0;
    // ht380k
    private BarcodeManager mHt380kCodeManager;
    // mx5050
    private com.mexxen.barcode.BarcodeManager mMx5050CodeManager;
    /**
     * 捷宝手持机callback
     */
    BarcodeManager.Callback dataReceived = new BarcodeManager.Callback() {

        @Override
        public void Barcode_Read(byte[] buffer, String codeId, int errorCode) {
            if (buffer != null) {
                String mxCode = new String(buffer);
//                VerificationCode(mxCode, present);// 验证扫描得到的二维码是否正确
                postData(mxCode);
            }

        }
    };

    /**
     * HT380K捕获扫描物理按键广播
     */
    private BroadcastReceiver scanDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("F4key")) {
                if (intent.getStringExtra("F4key").equals("down")) {
                    Log.e("trig", "key down");
                    // isContines = true;
                    if (null != mHt380kCodeManager) {
                        nowTime = System.currentTimeMillis();

                        if (nowTime - lastTime > 200) {
                            mHt380kCodeManager.Barcode_Stop();
                            lastTime = nowTime;
                            if (null != mHt380kCodeManager) {
                                mHt380kCodeManager.Barcode_Start();
                            }
                        }
                    }
                } else if (intent.getStringExtra("F4key").equals("up")) {
                    Log.e("trig", "key up");
                }
            }
        }
    };

    /**
     * 判断手机型号
     */
    private void judgePhoneModels() {

        if (Build.PRODUCT.equals(MX5050_MANUFACTURER)) {// MX5050

            mMx5050CodeManager = new com.mexxen.barcode.BarcodeManager(this);
            mMx5050CodeManager.addListener(new BarcodeListener() {
                @Override
                public void barcodeEvent(BarcodeEvent arg0) {
                    // 调用 getBarcode()方法读取二维码信息
                    String mxCode = mMx5050CodeManager.getBarcode();
                    if (!TextUtils.isEmpty(mxCode)) {
                        System.out.println(mxCode);
                        //VerificationCode(mxCode, present);// 验证扫描得到的二维码是否正确

                        postData(mxCode);
                    }
                }
            });
        } else if (Utils.isContanis(Constants.ht380kBuild, Build.PRODUCT)) {// HT380K
            IntentFilter scanDataFilter = new IntentFilter();
            scanDataFilter.addAction(HT380K_ScanAction);
            registerReceiver(scanDataReceiver, scanDataFilter);// HT380K注册广播
            if (mHt380kCodeManager == null) {
                mHt380kCodeManager = BarcodeManager.getInstance();
            }
            mHt380kCodeManager
                    .Barcode_Open(MainActivity.this, dataReceived);
        }
    }

    private void postData(String bocode) {
        try {
            bocode = URLEncoder.encode(bocode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://106.75.137.43/checkQRCodeMobile/checkRepeatQRCode?bottleCode="+bocode;
        showDialogs("检测中...");
        StringRequest strre = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String request) {
                if(dialog != null){
                    dialog.dismiss();
                }
                if(request != null){
                    tvEdtShow.setText(request);
                    try {
                        JSONObject jsonObject = new JSONObject(request);
                        int code = jsonObject.getInt("code");
                        if(code == 200){
                            voicInfo(MainActivity.this,R.raw.warning);  //错误提示
                        }else {
                            voicInfo(MainActivity.this,R.raw.beep);  //错误提示
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(dialog != null){
                    dialog.dismiss();
                }
                voicInfo(MainActivity.this,R.raw.warning);  //错误提示
                showToast("错误信息:"+volleyError.getMessage());
                tvEdtShow.setText(volleyError.getMessage());
            }
        });
        strre.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,1,1.0f));
        MyApplication.getRequestQueue().add(strre);
    }

    private void showDialogs(String s) {
        if(dialog == null){
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(true);
            dialog.setMessage(s);
            dialog.show();
        }else{
            dialog.setCancelable(true);
            dialog.setMessage(s);
            dialog.show();
        }
    }

    /**
     * 开启提示声音
     *
     */
    public void voicInfo(Context context, int voiceId) {

        MediaPlayer mp = MediaPlayer.create(context, voiceId);
        if (mp!=null) {
            mp.start();
        }else{
            showToast("提示音加载错误.");
        }
    }

    private void showToast(String msg){
        if(mToast == null){
            mToast = Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }
}
