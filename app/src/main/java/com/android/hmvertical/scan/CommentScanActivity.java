package com.android.hmvertical.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.jb.Preference;
import android.jb.barcode.BarcodeManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.android.hmvertical.base.BaseActivity;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.utils.Utils;
import com.mexxen.barcode.BarcodeEvent;
import com.mexxen.barcode.BarcodeListener;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;


/**
 * Created by Administrator on 2017/9/21.
 */

/**
 * 共用的扫描方法
 */
public class CommentScanActivity extends BaseActivity {

    private static final String TAG = "CommentScanActivity";
    
    
    
    //T50防爆机扫描
    private ScanInterface scanDecode;

    /**
     * 手持机380k mx5050
     */
    public final static String MX5050_MANUFACTURER = "scx35_sp7730eccuccspecBplus_UUI";// mx5050手持机生产厂商
    public final static String HT380K_MANUFACTURER = "M9PLUS";// ht380k手持机生产厂商
    private final static String HT380K_ScanAction = "com.jb.action.F4key";
    private long nowTime = 0;
    private long lastTime = 0;
    // ht380k
    android.jb.barcode.BarcodeManager mHt380kCodeManager;
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
                getScanResultData(mxCode.trim());
            }

        }
    };

    //扫描返回内容
    public void getScanResultData(String trim) {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.PRODUCT.equals(Constants.MX505_BUILD)) {
            judgePhoneModels();
        }

        //T50初始化
        if(Build.PRODUCT.equals("T50")){
            scanDecode = new ScanDecode(this);
            scanDecode.initService("true");//初始化扫描服务
            scanDecode.getBarCode(new ScanInterface.OnScanListener() {
                @Override
                public void getBarcode(String s) {
                    if(!Utils.isEmpty(s)){
                        getScanResultData(s);
                    }
                }
            });
        }
    }

    /**
     * 判断手机型号
     */
    private void judgePhoneModels() {

        if (Build.PRODUCT.equals(Constants.MX505_BUILD)) {// MX5050
            mMx5050CodeManager = new com.mexxen.barcode.BarcodeManager(this);
            mMx5050CodeManager.addListener(new BarcodeListener() {
                @Override
                public void barcodeEvent(BarcodeEvent arg0) {
                    // 调用 getBarcode()方法读取二维码信息
                    String mxCode = mMx5050CodeManager.getBarcode();
                    if (!TextUtils.isEmpty(mxCode)) {
                        getScanResultData(mxCode.trim());
                    }else{
                        //Utils.setVibrator(ScanActivity.this, 1000);  //没有扫描到手机震动
                    }
                }
            });
        } else if (Utils.isContanis(Constants.ht380kBuild, Build.PRODUCT)) {// HT380K
//            IntentFilter scanDataFilter = new IntentFilter();
//            scanDataFilter.addAction(HT380K_ScanAction);
//            registerReceiver(scanDataReceiver, scanDataFilter);// HT380K注册广播
//            if (mHt380kCodeManager == null) {
//                mHt380kCodeManager = android.jb.barcode.BarcodeManager
//                        .getInstance();
//            }
//            mHt380kCodeManager.Barcode_Open(CommentScanActivity.this, dataReceived);
//            if(Build.PRODUCT.equals("HT380D")){
//                Preference.setScanOutMode(this, 3);
//                mHt380kCodeManager.setScannerModel(4);
//            }

            IntentFilter scanDataFilter = new IntentFilter();
            scanDataFilter.addAction(HT380K_ScanAction);
            registerReceiver(scanDataReceiver, scanDataFilter);
//			if (mHt380kCodeManager == null) {
//				mHt380kCodeManager = android.jb.barcode.BarcodeManager
//						.getInstance();
//			}
//
//			mHt380kCodeManager.Barcode_Open(CommentScanActivity.this,
//					dataReceived);
//
//			if(Build.PRODUCT.equals("HT380D")){
//				Preference.setScanOutMode(this, 3);
//				mHt380kCodeManager.setScannerModel(4);
//			}
//			Logger.e("---22===="+Preference.getScannerModel(CommentScanActivity.this)+"----="+Preference.getScanOutMode(CommentScanActivity.this));

            if (mHt380kCodeManager == null) {
                mHt380kCodeManager = BarcodeManager.getInstance();
            }
      
            mHt380kCodeManager.Barcode_Open(CommentScanActivity.this, dataReceived);
            System.out.println("Service onStart()");
            Preference.setScanOutMode(CommentScanActivity.this,3);
            mHt380kCodeManager.setScannerModel(4);
            Log.e(TAG,"-=-----22----"+mHt380kCodeManager.getScannerModel());
            if (mHt380kCodeManager == null) {
                mHt380kCodeManager = BarcodeManager.getInstance();
            } else {
                // if (scanManager.isSerialPort_isOpen()) {
                mHt380kCodeManager.Barcode_Close();
            }
            Log.e(TAG, "onStart()");
            mHt380kCodeManager.Barcode_Open(CommentScanActivity.this, dataReceived);
            
            
            
        }
        
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        // 判断是否是非防爆机，非防爆机需要在onResume中判断
        if (Utils.isContanis(Constants.ht380kBuild, Build.PRODUCT)) {
            judgePhoneModels();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Utils.isContanis(Constants.ht380kBuild, Build.PRODUCT)) {
            unregisterReceiver(scanDataReceiver);
        } else if (Build.PRODUCT.equals(Constants.MX505_BUILD)) {
            mMx5050CodeManager.dismiss();
        }
        if(scanDecode != null){
            scanDecode.onDestroy();
        }
    }


}
