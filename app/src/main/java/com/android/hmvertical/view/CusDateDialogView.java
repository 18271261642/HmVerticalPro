package com.android.hmvertical.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.android.hmvertical.ui.OperatorCheckOrderActivity;

/**
 * Created by Administrator on 2018/7/12.
 */

public class CusDateDialogView extends DatePickerDialog {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CusDateDialogView(@NonNull Context context, OnCusDateListener onCusDateListener, int year, int month , int dayOfMonth) {
        super(context);
       // this.onCusDateListener = onCusDateListener;
        this.setTitle(year+"年"+(month+1)+"月");
        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
                .getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @Override
    public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        this.setTitle(year+"年"+(month+1)+"月");
    }

    public interface OnCusDateListener{
        void getYearMonthDate(String yearData);
    }

    @Override
    public void show() {
        super.show();
//        DatePicker datePicker = this.getDatePicker();
//        NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
//        NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
//        NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度
//
//
//        //年的最大值为2100
//        //月的最大值为11
//        //日的最大值为28,29,30,31
//        int value0 = view0.getMaxValue();//获取第一个View的最大值
//        int value1 = view1.getMaxValue();//获取第二个View的最大值
//        int value2 = view2.getMaxValue();//获取第三个View的最大值
//        if(value0 >= 28 && value0 <= 31){
//            view0.setVisibility(View.GONE);
//        }else if(value1 >= 28 && value1 <= 31){
//            view1.setVisibility(View.GONE);
//        }else if(value2 >= 28 && value2 <= 31){
//            view2.setVisibility(View.GONE);
//        }

    }
}
