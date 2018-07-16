package com.android.hmvertical.constants;


import com.android.hmvertical.base.MyApplication;

/**
 * Created by Administrator on 2018/1/24.
 */

public class Constants {

    //捷宝手持机型号
    public static final String HT380K_BUILD1 = "M9PLUS";
    public static final String HT380K_BUILD2 = "HT380K";
    public static final String HT380K_BUILD3 = "IT380";
    public static final String HT380K_BUILD4 = "HT380D";

    public static final String[] ht380kBuild = new String[]{HT380K_BUILD1,HT380K_BUILD2,HT380K_BUILD3,HT380K_BUILD4};

    //MX505防爆机型号
    public static final String MX505_BUILD = "scx35_sp7730eccuccspecBplus_UUI";


    /**
     * 地址
     */
     //正式服
    public static final String FORMAL_BASURL = "http://ctgpjc.cpsyq.cn/";
    //测试服
    public static final String TEST_BASURL = "http://ctcheck.suchkj.com/";
    //本地服
    public static final String LOCAL_BASURL = "http://192.168.3.180:8083/GasDetection/";


    public static String getAbsoluteUrl(){
        String barUrl = null;
        if(MyApplication.urlFlagCode == 0){ //正式服
            barUrl = FORMAL_BASURL;
        }else if(MyApplication.urlFlagCode == 1){   //测试服
            barUrl = TEST_BASURL;
        }else if(MyApplication.urlFlagCode == 2){   //本地
            barUrl = LOCAL_BASURL;
        }
        else{
            return  null;
        }
        return barUrl;
    }

    //登录
    public static final String USER_LOGIN_URL = "userApi/login";

    //异常日志上传
    public static final String UP_TO_SERVER = "appExceptionApi/submitException";

    //一、查询需要检测的检测单列表
    public static final String CHECK_ORDER_LIST_URL = "checkOrderApi/getList";

    //查询检测单详情
    public static final String GET_CHECKORDER_DETAIL_URL = "checkOrderApi/getCheckOrderDetailByNum";

    //查询检测单详情结果
    public static final String GET_CHECKORDER_DETAIL_RESULT = "checkOrderApi/getDetailResult";

    //查询检测项目和检测结果
    public static final String GET_CHECKITEM_AND_RESULT_URL = "checkOrderApi/getCheckItemAndResult";

    //提交检测结果
    public static final String SUB_CHECK_RESULT_URL = "checkOrderApi/submitCheckResult";

    //完成检测
    public static final String CONFIRM_CHECK_ORDER_URL = "checkOrderApi/confirmCheckOrder";

    //获取类型和model
    public static final String GETTYPE_AND_MODELS_URL = "checkOrderApi/getTypeAndModels";

    //app版本更新
    public static final String APP_VERSION_URL = "appVersionApi/getNewAppVersion";

    //获取需要扫码入库的订单列表
    public static final String GET_WAIT_CHECK_LIST = "checkOrderApi/getWaitIntoList";

    //查询扫码入库检测单详情
    public static final String GET_WAIT_CHECK_DETAIL = "checkOrderApi/getWaitIntoCheckOrderDetail";

    //扫码入库
    public static final String SUBMIT_BOT_INFO_HOUSE = "checkOrderApi/submitInto";

    //获取扫码出库的订单列表
    public static final String GET_OUT_WEARHOUSE_ORDER_LIST = "checkOrderApi/getWaitOutList";

    //查询需要扫码出库的订单的详情
    public static final String GET_OUT_WEARHOUSE_BOT_DETAIL = "checkOrderApi/getWaitOutCheckOrderDetail";

    //扫码出库
    public static final String SUBMIT_OUT = "checkOrderApi/submitOut";

}
