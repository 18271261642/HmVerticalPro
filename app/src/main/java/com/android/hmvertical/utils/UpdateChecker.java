package com.android.hmvertical.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.android.hmvertical.base.MyApplication;
import com.android.hmvertical.bean.AppVersion;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;


public class UpdateChecker {

    public static final String TAG = "UpdateChecker";

    private Context mContext;
    //地址
    private String mCheckUrl;
    private AppVersion mAppVersion;
    //下载提示框
    private ProgressDialog mProgressDialog;

    private File apkFile;

    public void setCheckUrl(String url) {
        mCheckUrl = url;

        Log.e(TAG, "---url===" + mCheckUrl);
    }

    public UpdateChecker(Context context) {
        mContext = context;
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("下载中...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
        mProgressDialog
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                    }
                });
    }

    @SuppressLint("HandlerLeak")
    public void checkForUpdates() {
        if (mCheckUrl == null) {
            // throw new Exception("checkUrl can not be null");
            Log.e(TAG, "-------===");
            return;
        }
        //第一步，先获取服务器版本号
        StringRequest StringRequest = new StringRequest(mCheckUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG,"----response="+response);
                if (response != null) {
                    analysisResponse(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e(TAG,"----error="+error.getMessage().toString());

            }
        });
        MyApplication.getRequestQueue().add(StringRequest);

    }

    /**
     * 解析
     *
     * @param response
     */
    protected void analysisResponse(String response) {
        // TODO Auto-generated method stub
        try {
            JSONObject jsonObject = new JSONObject(response);
            String data = jsonObject.getString("data");
            if (!Utils.isEmpty(data)) {
                Gson gson = new Gson();
                mAppVersion = gson.fromJson(data, AppVersion.class);
                Log.e(TAG,"----serverCode="+mAppVersion.getVersionCode());
                //获取当前APP的版本号
                int versionCode = mContext.getPackageManager().getPackageInfo(
                        mContext.getPackageName(), 0).versionCode;
                //是否强制更新
                int isForce = mAppVersion.getIsForce();
                //比对
                if (Integer.valueOf(mAppVersion.getVersionCode().trim()) > versionCode) {
                    showUpdateDialog(isForce);
                }else{
                    VoiceUtils.showToast(mContext, "当前已是最新版本");
                }
            } else {
                VoiceUtils.showToast(mContext, "当前已是最新版本");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *0-否
     * 1-强制更新
     */
    public void showUpdateDialog(int isForce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // builder.setIcon(R.drawable.icon);
        builder.setTitle("提醒");
        builder.setMessage("" + mAppVersion.getContent());
        if(isForce == 1){   //强制更新
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    downLoadApk();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    downLoadApk();
                }
            });
        }else{
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    downLoadApk();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();

    }

    /**
     * 下载apk
     */
    public void downLoadApk() {
        String apkUrl = mAppVersion.getDownloadUrl();
        Log.e(TAG,"----apkUrl="+apkUrl);
        String dir = mContext.getExternalFilesDir("apk").toString();
        Log.e(TAG,"----dir-="+dir);
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        if (folder.exists() && folder.isDirectory()) {
            //删除
            FileUtils.deleteFile(folder);
        } else {
            folder.mkdirs();
        }
//		String filename = apkUrl.substring(apkUrl.lastIndexOf("/"),
//				apkUrl.length());
        String filename = "checkBot"+System.currentTimeMillis()/100+".apk";
        String destinationFilePath = dir + "/" + filename;

        System.out.println("---destinationFilePath---" + destinationFilePath);
        apkFile = new File(destinationFilePath);
        mProgressDialog.show();
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("url", apkUrl);
        intent.putExtra("dest", destinationFilePath);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        mContext.startService(intent);

    }

    private class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                mProgressDialog.setProgress(progress);
                mProgressDialog.setCancelable(false);
                if (progress == 100) {
                    mProgressDialog.dismiss();
                    installAPK(apkFile);


//                    String[] command = {"chmod", "777", apkFile.toString()};
//                    try {
//                        ProcessBuilder builder = new ProcessBuilder(command);
//                        builder.start();
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(apkFile),
//                                "application/vnd.android.package-archive");
//                        mContext.startActivity(intent);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }

    /**
     * 7.0兼容
     */
    private void installAPK(File file) {
//        File apkFile =
//                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RaceFitPro.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
