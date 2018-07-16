package com.android.hmvertical.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.adapter.OperatorAdapter;
import com.android.hmvertical.adapter.SpinnerAdapter;
import com.android.hmvertical.base.BaseActivity;
import com.android.hmvertical.bean.CheckDetailBean;
import com.android.hmvertical.bean.CheckResultTmpBean;
import com.android.hmvertical.bean.OperatorBean;
import com.android.hmvertical.constants.Constants;
import com.android.hmvertical.utils.Utils;
import com.android.hmvertical.utils.VoiceUtils;
import com.android.hmvertical.utils.http.RequestPresent;
import com.android.hmvertical.utils.http.RequestView;
import com.android.hmvertical.view.ProDialogView;
import com.android.hmvertical.view.TimeDialogView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/2.
 */

public class OperatorCheckOrderActivity extends BaseActivity implements RequestView<JSONObject>,
        OperatorAdapter.OnSpinnerSelectListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.commentTitleBackImg)
    ImageView commentTitleBackImg;
    @BindView(R.id.commentTitleTv)
    TextView commentTitleTv;
    @BindView(R.id.commentTitleScanImg)
    ImageView commentTitleScanImg;
    @BindView(R.id.item_detailTv1)
    TextView itemDetailTv1;
    @BindView(R.id.item_detailTv2)
    TextView itemDetailTv2;
    @BindView(R.id.item_detailTv3)
    TextView itemDetailTv3;
    @BindView(R.id.item_detailTv4)
    TextView itemDetailTv4;
    @BindView(R.id.item_detailTv5)
    TextView itemDetailTv5;
    @BindView(R.id.item_detailTv6)
    TextView itemDetailTv6;
    @BindView(R.id.item_detailTv7)
    TextView itemDetailTv7;
    @BindView(R.id.item_detailTv8)
    TextView itemDetailTv8;
    @BindView(R.id.item_detailTv9)
    TextView itemDetailTv9;
    @BindView(R.id.item_detailTv10)
    TextView itemDetailTv10;
    @BindView(R.id.item_detailTv11)
    TextView itemDetailTv11;
    @BindView(R.id.item_detailTv12)
    TextView itemDetailTv12;
    @BindView(R.id.item_detailTv13)
    TextView itemDetailTv13;
    @BindView(R.id.item_detailTv18)
    TextView itemDetailTv18;
    @BindView(R.id.item_detailTv14)
    TextView itemDetailTv14;
    @BindView(R.id.item_detailTv15)
    TextView itemDetailTv15;
    @BindView(R.id.item_detailTv16)
    TextView itemDetailTv16;
    @BindView(R.id.item_detailTv17)
    TextView itemDetailTv17;
    @BindView(R.id.operatorProjectLv)
    RecyclerView operatorProjectLv;
    @BindView(R.id.operateSubBtn)
    Button operateSubBtn;
    @BindView(R.id.operateCheckBox)
    CheckBox operateCheckBox;
    //气瓶类型下拉
    @BindView(R.id.operatorSpinner)
    Spinner operatorSpinner;
    @BindView(R.id.nextCheckTimeTv)
    TextView nextCheckTimeTv;
    @BindView(R.id.operateRaditGroup)
    RadioGroup operateRaditGroup;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.lastUseTimeTv)
    TextView lastUseTimeTv;
    @BindView(R.id.endUseTimeTv)
    TextView endUseTimeTv;
    @BindView(R.id.otherEdit)
    EditText otherEdit;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.rb7)
    RadioButton rb7;
    @BindView(R.id.scrappedLin)
    LinearLayout scrappedLin;

    @BindView(R.id.operatorDateSpinner)
    Spinner operatorDateSpinner;
    @BindView(R.id.showSpinnerDateTv)
    TextView showSpinnerDateTv;
    private RequestQueue requestQueue;
    private RequestPresent requestPresent;

    //列表展示
    private List<OperatorBean> list;
    private OperatorAdapter operatorAdapter;

    //下拉框数据整合用
    private List<Map<String, Object>> listMap;
    private Map<String, Object> maps;

    //查询检测单详情结果
    private List<CheckResultTmpBean.CheckInfoResultsBean> checkResultTmpBeans;
    //检测结果中气瓶类型下拉列表 即回填的bean
    private List<CheckResultTmpBean.CheckItemResultModelsBean> checkItemResultModelsBeanList;
    //检测结果中气瓶类型列表adapter
    private SpinnerAdapter spinnerAdapter;

    //modeId
    private int modeId;

    private String number = null;

    //传递过来的检测单详情对象
    CheckDetailBean.ListBean checkDetailBean;

    //勾选，是否报废
    private int checkIsscrap;

    //提示框
    ProDialogView proDialogView;
    //日期选择
    TimeDialogView timeDialogView;
    //下次检修日期
    private String nextCheckDate = "";
    //截止使用日期
    private String lastUseDate = "";
    //终止使用日期
    private String endUseDate = "";
    //报废原因
    private String scrapText = null;

    private Calendar calendar;

    private TimePickerDialog timePickerDialog;
    long tenYears = 30L * 365 * 1000 * 60 * 60 * 24L;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    private String[] dateStr = new String[]{"下次检验日期","截止使用日期","终止使用日期"};
    private ArrayAdapter<String> arrayAdapter;


    private int switchCode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_check_order_layout);
        ButterKnife.bind(this);

        initViews();
        initData();
        number = getIntent().getStringExtra("number");
        checkDetailBean = (CheckDetailBean.ListBean) getIntent().getSerializableExtra("checkbean");
        if (checkDetailBean != null) {
            Map<String, Object> mp = new HashMap<>();
            mp.put("key", getUserInfoData().getCheckStationCode() + "");
            mp.put("number", number);
            mp.put("checkNumber", checkDetailBean.getCheckNumber());
            Logger.e("---------map-=" + Constants.GET_CHECKORDER_DETAIL_RESULT + mp.toString());
            requestPresent.getPresentRequestJSONObject(requestQueue, 2, Constants.GET_CHECKORDER_DETAIL_RESULT, mp, "22");
            showCheckData(checkDetailBean);
        }
        Logger.e("------chekc-=" + checkDetailBean.getCheckNumber());

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initData() {
        requestQueue = NoHttp.newRequestQueue(1);
        requestPresent = new RequestPresent();
        requestPresent.attach(this);
        maps = new HashMap<>();
        listMap = new ArrayList<>();
        list = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(OperatorCheckOrderActivity.this,android.R.layout.simple_list_item_1,dateStr);
        operatorDateSpinner.setAdapter(arrayAdapter);
        operatorDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0x00:  //下次检修
//                        switchCode = 0;
//                        //chooseSwitchDate(0x00);
//                        break;
//                    case 0x01:  //截止使用日期
//                        switchCode = 1;
//                        break;
//                    case 0x02:  //终止检测日期
//                        switchCode = 2;
//                        break;
//                }

                chooseDateTest(position,showSpinnerDateTv.getText().toString().trim());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chooseDateTest(0,showSpinnerDateTv.getText().toString().trim());
            }
        });

        //检测结果
        checkResultTmpBeans = new ArrayList<>();

        //气瓶类型展示
        checkItemResultModelsBeanList = new ArrayList<>();
        spinnerAdapter = new SpinnerAdapter(checkItemResultModelsBeanList, OperatorCheckOrderActivity.this);
        operatorSpinner.setAdapter(spinnerAdapter);
        operatorSpinner.setOnItemSelectedListener(this);

        //列表展示
        operatorAdapter = new OperatorAdapter(list, OperatorCheckOrderActivity.this);
        operatorProjectLv.setAdapter(operatorAdapter);
        operatorAdapter.setOnSpinnerSelectListener(this);

    }

    //获取检测项目的数据
    private void getProjectData(int modId) {
        if (requestPresent != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", getUserInfoData().getCheckStationCode());
            map.put("modelId", modId + "");
            requestPresent.getPresentRequestJSONObject(requestQueue, 1, Constants.GET_CHECKITEM_AND_RESULT_URL, map, "nn");
        }
    }

    //展示具体的详情
    private void showCheckData(CheckDetailBean.ListBean checkDetailBean) {
        //编号
        itemDetailTv1.setText("" + checkDetailBean.getId());
        itemDetailTv2.setText("" + checkDetailBean.getCheckNumber());
        itemDetailTv3.setText("" + checkDetailBean.getCompany());
        itemDetailTv4.setText("" + checkDetailBean.getCylinderType());
        itemDetailTv5.setText("" + checkDetailBean.getFillingMedium());
        itemDetailTv6.setText("" + checkDetailBean.getCheckOrder());
        itemDetailTv7.setText("" + checkDetailBean.getGasNumber());
        itemDetailTv8.setText("" + checkDetailBean.getNewGsNumber());
        itemDetailTv9.setText("" + checkDetailBean.getCreatecode());
        itemDetailTv10.setText("" + checkDetailBean.getProducer());

        //产权性质
        String createType = checkDetailBean.getCreatetype();
        if (!Utils.isEmpty(createType)) {
            if (createType.equals("1")) {
                itemDetailTv11.setText("自有");
            } else if (createType.equals("2")) {
                itemDetailTv11.setText("公共");
            }
        }

        itemDetailTv12.setText("" + checkDetailBean.getCreatedate());
        String checkD = checkDetailBean.getCheckdate() + "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if (!Utils.isEmpty(checkD) && !checkD.equals("0")) {
            itemDetailTv13.setText("" + sf.format(new Date(checkDetailBean.getCheckdate())));
        }

        itemDetailTv14.setText("" + checkDetailBean.getTwobarcode());
        itemDetailTv15.setText("" + checkDetailBean.getResult());

        //是否报废
        int isscrapState = checkDetailBean.getIsscrap();
        if (isscrapState == 0) {
            itemDetailTv16.setText("否");
            operateCheckBox.setChecked(false);
        } else if (isscrapState == 1) {
            operateCheckBox.setChecked(true);
            itemDetailTv16.setText("是");
        }
        itemDetailTv17.setText("" + checkDetailBean.getRemark());

        //下次检测日期
        String nexD = checkDetailBean.getNextCheckDate() + "";
        if (!Utils.isEmpty(nexD) && !nexD.equals("0")) {
            showSpinnerDateTv.setText("" + sdf.format(new Date(checkDetailBean.getNextCheckDate())));
            nextCheckDate = sdf.format(new Date(checkDetailBean.getNextCheckDate()));
            operatorDateSpinner.setSelection(0,true);
            if(timePickerDialog != null){
                timePickerDialog.dismiss();
            }
        }
        //截止使用日期
        String lastD = checkDetailBean.getLastUseDate() + "";
        if (!Utils.isEmpty(lastD) && !lastD.equals("0")) {
            showSpinnerDateTv.setText("" + sdf.format(new Date(checkDetailBean.getLastUseDate())));
            lastUseDate = sdf.format(new Date(checkDetailBean.getLastUseDate()));
            operatorDateSpinner.setSelection(1,true);
        } else {
            lastUseTimeTv.setText("");
        }
        //终止使用日期
        String endD = checkDetailBean.getEndUseDate() + "";
        if (!Utils.isEmpty(endD) && !endD.equals("0")) {
            operatorDateSpinner.setSelection(2,true);
            showSpinnerDateTv.setText("" + sdf.format(new Date(checkDetailBean.getEndUseDate())));
            endUseDate = sdf.format(new Date(checkDetailBean.getEndUseDate()));
        }
        //报废原因回填
        otherEdit.setText("" + checkDetailBean.getScrapText());

    }

    private void initViews() {
        commentTitleTv.setText("检测单详情");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        operatorProjectLv.setLayoutManager(layoutManager);
        operateCheckBox.setOnCheckedChangeListener(this);
        operateRaditGroup.setOnCheckedChangeListener(new CusCheckChanagedListener());
        calendar = Calendar.getInstance();

    }


    @Override
    public void showLoadDialog(int what) {
        showDialog("加载中..");
    }

    @Override
    public void closeLoadDialog(int what) {

    }

    @Override
    public void requestSuccessData(int what, Response<JSONObject> response, String bot) {
        Logger.e("----response-=" + what + response.get().toString());
        closeDialog();
        try {
            if (response.get().getInt("code") == 200) {
                if (what == 1) {  //详情 即下拉列表展示数据
                    String data = response.get().getString("data");
                    if (!Utils.isEmpty(data) && !data.equals("[]")) {
                        List<OperatorBean> tmpLt = new Gson().fromJson(data, new TypeToken<List<OperatorBean>>() {
                        }.getType());
                        list.clear();
                        list.addAll(tmpLt);
                        operatorAdapter.notifyDataSetChanged();

                    } else {
                        list.clear();
                        operatorAdapter.notifyDataSetChanged();
                        VoiceUtils.showToastVoice(OperatorCheckOrderActivity.this, R.raw.warning, "无数据!");
                    }
                } else if (what == 2) {     //查询检测单详情 回填数据
                    if (response.get().getInt("code") == 200) {
                        String tmpData = response.get().getString("data");
                        if (!Utils.isEmpty(tmpData)) {
                            CheckResultTmpBean checkResultTmpBean = new Gson().fromJson(tmpData, CheckResultTmpBean.class);
                            List<CheckResultTmpBean.CheckItemResultModelsBean> tmpItemModelLt = checkResultTmpBean.getCheckItemResultModels();
                            if (tmpItemModelLt.size() > 0) {  //Spinner的数据集合
                                checkItemResultModelsBeanList.addAll(tmpItemModelLt);
                                spinnerAdapter.notifyDataSetChanged();

                                modeId = checkItemResultModelsBeanList.get(0).getModelId();
                                getProjectData(modeId);

                            }
                            //回填需要的数据
                            List<CheckResultTmpBean.CheckInfoResultsBean> tmpInfoResultLt = checkResultTmpBean.getCheckInfoResults();
                            if (tmpInfoResultLt.size() > 0) {
                                checkResultTmpBeans.addAll(tmpInfoResultLt);
                            } else {
                                checkResultTmpBeans.clear();
                            }
                            operatorAdapter.setCheckResultTmpBeans(checkResultTmpBeans);
                        }

                    }

                } else if (what == 3) {    //提交
                    if (response.get().getInt("code") == 200) {
                        VoiceUtils.showToastVoice(OperatorCheckOrderActivity.this, R.raw.beep, "提交成功!");
                        finish();
                    }
                }

            } else {
                VoiceUtils.showToastVoice(OperatorCheckOrderActivity.this, R.raw.warning, "非200返回:" + response.get().getString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailedData(int what, Response<JSONObject> response) {
        Logger.e("----err=" + what + "-=res-=" + response.getException().toString());
        closeDialog();

    }

    @OnClick({R.id.operateSubBtn, R.id.nextCheckTimeTv,
            R.id.lastUseTimeTv, R.id.endUseTimeTv,R.id.showSpinnerDateTv,R.id.clearDateTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.operateSubBtn:    //提交
                subData();
                break;
            case R.id.nextCheckTimeTv:  //选择日期
               // chooseSwitchDate(0x00);
                break;
            case R.id.lastUseTimeTv:    //截止使用日期
               // chooseSwitchDate(0x01);
                break;
            case R.id.endUseTimeTv: //终止使用日期
               // chooseSwitchDate(0x02);
                break;
            case R.id.showSpinnerDateTv:
                chooseSwitchDate(switchCode);
                break;
            case R.id.clearDateTv:  //清楚数据
                showSpinnerDateTv.setText("");
                clearDate();
                break;
        }

    }

    private void chooseSwitchDate(final int datePosition) {

        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String chooseDate = Utils.getDateToString(millseconds);
                        showSpinnerDateTv.setText(chooseDate);
                        chooseDateTest(datePosition,chooseDate);
                        Logger.e("------时间选择=" + chooseDate);
                    }
                }).setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("日期选择")
                .setYearText("年")
                .setMonthText("月")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.theme_color))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.black))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.black))
                .setWheelItemTextSize(12)
                .build();
        timePickerDialog.show(getSupportFragmentManager(), "date");

    }

    private void chooseDateTest(int index,String chooseDate) {
        switch (index) {
            case 0x00:
                clearDate();
                nextCheckDate = chooseDate;
                break;
            case 0x01:
                clearDate();
                lastUseDate = chooseDate;
                break;
            case 0x02:
                clearDate();
                endUseDate = chooseDate;
                break;
        }
    }

    private void clearDate(){
        nextCheckDate = "";
        lastUseDate = "";
        endUseDate = "";
    }

    //提交
    private void subData() {
        Logger.e("-----提交=" + maps.toString());
        Map<String, Object> tmpMap;//= new HashMap<>();
        listMap.clear();
        //遍历map
        for (Map.Entry<String, Object> mp : maps.entrySet()) {
            tmpMap = new HashMap<>();
            tmpMap.put("item", mp.getKey());
            tmpMap.put("result", mp.getValue());
            listMap.add(tmpMap);
        }
        Logger.e("----转换=" + listMap.toString());
        proDialogView = new ProDialogView(this);
        proDialogView.show();
        proDialogView.setTitle("提醒");
        proDialogView.setContent("是否提交?");
        proDialogView.setrightText("是");
        proDialogView.setleftText("否");
        proDialogView.setListener(new ProDialogView.OnPromptDialogListener() {
            @Override
            public void leftClick(int code) {
                proDialogView.dismiss();

            }

            @Override
            public void rightClick(int code) {
                proDialogView.dismiss();
                if (requestPresent != null) {
                    Map<String, Object> parMap = new HashMap<>();
                    parMap.put("key", "CTSTATION");
                    parMap.put("number", number + "");
                    parMap.put("checkNumber", checkDetailBean.getCheckNumber());
                    parMap.put("isscrap", checkIsscrap + "");
                    parMap.put("modelId", modeId + "");
                    parMap.put("checkResults", listMap.toString());
                    if (checkIsscrap == 1) {
                        parMap.put("scrapText", otherEdit.getText().toString());
                    } else {
                        parMap.put("scrapText", "");
                    }
                    parMap.put("nextCheckDate", nextCheckDate);
                    parMap.put("lastUseDate", lastUseDate);
                    parMap.put("endUseDate", endUseDate);
                    requestPresent.getPresentRequestJSONObject(requestQueue, 3, Constants.SUB_CHECK_RESULT_URL, parMap, "23");
                }
            }
        });
    }

    //spinner选择
    @Override
    public void onSelectListener(int parentPosition, int position) {
        maps.put(list.get(parentPosition).getItem().getId() + "", list.get(parentPosition).getResults().get(position).getId() + "");
    }

    //spinner未选择
    @Override
    public void onNoSelectListener() {
        maps.put(list.get(0).getItem().getId() + "", list.get(0).getResults().get(0).getId() + "");
    }

    //checkBox
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checkIsscrap = 1;
            scrappedLin.setVisibility(View.VISIBLE);
        } else {
            checkIsscrap = 0;
            scrappedLin.setVisibility(View.GONE);
        }
    }


    //spinner item选择监听回调
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        modeId = checkItemResultModelsBeanList.get(position).getModelId();
        getProjectData(modeId);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        modeId = checkItemResultModelsBeanList.get(0).getModelId();
        getProjectData(modeId);

    }

    class CusCheckChanagedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb1:  //已达终止使用年限
                    scrapText = rb1.getText().toString();
                    break;
                case R.id.rb2:  //瓶体腐蚀
                    scrapText = rb2.getText().toString();
                    break;
                case R.id.rb3:  //线腐蚀
                    scrapText = rb3.getText().toString();
                    break;
                case R.id.rb4:  //瓶体凹陷
                    scrapText = rb4.getText().toString();
                    break;
                case R.id.rb5:  //气密性不合格
                    scrapText = rb5.getText().toString();
                    break;
                case R.id.rb6:  //其它
                    scrapText = "请输入报废原因";
                    break;
                case R.id.rb7:  //护罩被更换
                    scrapText = rb7.getText().toString();
                    break;
                default:
                    scrapText = "未勾选报废";
                    break;
            }
            otherEdit.setText(scrapText);
        }
    }
}
