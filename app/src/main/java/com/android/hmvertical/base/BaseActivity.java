package com.android.hmvertical.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.hmvertical.bean.UserInfoBean;
import com.android.hmvertical.utils.SharedPreferenceUtils;
import com.android.hmvertical.utils.Utils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/9/21.
 */

public class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    private ProgressDialog dialog;
    private MyApplication myApplication;
    private BaseActivity baseActivity;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(myApplication == null){
            myApplication = (MyApplication) getApplication();
        }
        baseActivity = this;
        addActivity();
    }

    //通用的跳转
    public void startActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    /**
     * 传递int类型参数
     * @param cls
     * @param keys
     * @param val
     */
    public void startIntActivity(Class<?> cls,String keys,Integer val){
        Intent intent = new Intent(this,cls);
        intent.putExtra(keys,val);
        startActivity(intent);
    }

    //带参数跳转
    public void startActivity(Class<?> cls ,String[] keys,String[] values){
        Intent intent = new Intent(this,cls);
        int size = keys.length;
        for(int i = 0;i<size;i++){
            intent.putExtra(keys[i],values[i]);
        }
        startActivity(intent);
    }

    //将所有Activity都添加至集合中
    public void addActivity() {
        myApplication.addActivity(baseActivity);
    }

    public void showToast(String msg){
        if(mToast == null){
            mToast = Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    //保存用户信息
    public void setUserInfoData(Object userInfoData){
        SharedPreferenceUtils.put(this,"userInfo",new Gson().toJson(userInfoData,Object.class));
    }

    //获取用户信息
    public UserInfoBean getUserInfoData(){
        UserInfoBean userInfoBean = null;
        String userD = (String) SharedPreferenceUtils.get(this,"userInfo","");
        Log.e("Base","----userd="+userD);
        if(!Utils.isEmpty(userD)){
            userInfoBean = new Gson().fromJson(userD,UserInfoBean.class);
        }
        return userInfoBean;
    }




    /**
     * 显示进度条
     * @param msg
     */
    public void showDialog(String msg){
        if(dialog == null){
            dialog = new ProgressDialog(BaseActivity.this);
            dialog.setMessage(msg);
            dialog.setCancelable(true);
            dialog.show();
        }else{
            dialog.setMessage(msg);
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    //关闭进度条
    public void closeDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    /**
     * 输入框获取焦点时自动弹出软键盘，
     * 点击屏幕的其它任何位置，软件盘消失
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText )) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
