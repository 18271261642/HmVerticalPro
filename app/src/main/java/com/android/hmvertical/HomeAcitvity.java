package com.android.hmvertical;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.android.hmvertical.base.BaseActivity;
import com.android.hmvertical.base.MyApplication;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.ui.CheckOrderListActivit;
import com.android.hmvertical.utils.AppUtils;
import com.android.hmvertical.utils.UpdateChecker;
import com.android.hmvertical.view.ConfirmDialogView;
import com.zbar.lib.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HomeAcitvity extends BaseActivity {

    @BindView(R.id.showVerTv)
    TextView showVerTv;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.userCharacterTv)
    TextView userCharacterTv;
    private UpdateChecker updateChecker;


    private ConfirmDialogView confirmDialogView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //申请权限
        if (ContextCompat.checkSelfPermission(HomeAcitvity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(HomeAcitvity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeAcitvity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE}, 1001);
        }

        initViews();

        showUserData();
        checkAppUpdate();
    }

    private void showUserData() {
        userNameTv.setText(getUserInfoData().getUserName());
        userCharacterTv.setText(getUserInfoData().getFullName());
    }

    private void initViews() {
        showVerTv.setText("当前版本:" + AppUtils.getVersionName(this));

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    //检测更新
    private void checkAppUpdate() {
        updateChecker = new UpdateChecker(HomeAcitvity.this);
        updateChecker.setCheckUrl(Constants.getAbsoluteUrl() + Constants.APP_VERSION_URL + "?key=" + getUserInfoData().getCheckStationCode() + "&type=1");
        updateChecker.checkForUpdates();
    }

    @OnClick({R.id.home_initCardView, R.id.home_botCheckView,
            R.id.home_getCheckOrderView, R.id.homeTitleTv,
            R.id.homeTitleScanImg, R.id.home_updateCardView,
            R.id.home_inHouseCardView,R.id.home_outHouseCardView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_initCardView:
                startActivity(new Intent(HomeAcitvity.this, InitBotActivity.class));
                break;
            case R.id.home_botCheckView:
                //startActivity(new Intent(HomeAcitvity.this,BotVerticalActivity.class));
                break;
            case R.id.home_getCheckOrderView:   //检测单列表
                startIntActivity(CheckOrderListActivit.class,"flagCode",1001);
                //startActivity(CheckOrderListActivit.class);
                break;
            case R.id.homeTitleScanImg: //扫描
                Intent intent = new Intent(HomeAcitvity.this, CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.home_updateCardView:  //检查更新
                checkAppUpdate();
                break;
            case R.id.home_inHouseCardView:  //扫码入库检测单列表
                startIntActivity(CheckOrderListActivit.class,"flagCode",1002);
                break;
            case R.id.home_outHouseCardView:    //扫码出库列表
                startIntActivity(CheckOrderListActivit.class,"flagCode",1003);
                break;

        }
    }


    public long exitTime; // 储存点击退出时间

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {

                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    showToast("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                    return false;
                } else {
                    MyApplication.getMyApplication().removeAllActivity();
                    return true;
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                String scanResult = data.getStringExtra("scanResult");
                showScanResultData(scanResult);
            }
        }
    }

    private void showScanResultData(String scanResult) {
        confirmDialogView = new ConfirmDialogView(this);
        confirmDialogView.show();
        confirmDialogView.setTitle("提醒");
        confirmDialogView.setContent(scanResult);
        confirmDialogView.setListener(new ConfirmDialogView.ClickListener() {
            @Override
            public void doDismiss() {
                confirmDialogView.dismiss();
            }
        });

    }
}
