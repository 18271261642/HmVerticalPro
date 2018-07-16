package com.android.hmvertical.utils;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/3.
 */

public class Utils {

    //字符串非空
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组中是否包含某个元素
     * @param arr
     * @param value
     * @return
     */
    public static boolean isContanis(String[] arr,String value){

        return Arrays.asList(arr).contains(value);

    }


    /**
     * long转时间
     */
    public static String longToDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }


    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * String  类型转Date
     * @param str
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * 解析json数组中的code
     */
    public static int getJSONCode(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 解析json数组中的data
     */
    public static String getJSONData(String jsonData){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
            return  jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取data中的value
    public static String getJSONDataFromData(String data,String keys){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            return jsonObject.getString(keys);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取json中的MSG
     */
    public static String getJSONMSG(String jsonData){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
            return jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
