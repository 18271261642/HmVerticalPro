package com.android.hmvertical.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.android.hmvertical.R;

/**
 * Created by Administrator on 2017/9/21.
 */

public class ConfirmDialogView extends Dialog implements View.OnClickListener{

    private Button btn;  //确认按钮

    private TextView titleTv;  //title

    private TextView contentTv;  //内容

    private ClickListener listener;


    public ConfirmDialogView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirm_view);

        initViews();



    }

    private void initViews() {
        titleTv = (TextView) findViewById(R.id.dialog_show_title);
        contentTv = (TextView) findViewById(R.id.dialog_show_content);
        btn = (Button) findViewById(R.id.dialog_show_btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_show_btn:
                if(listener!= null){
                    listener.doDismiss();
                }
                cancel();
                break;
        }
    }

    /**
     * 设置标题文字
     * @param str
     */
    public void setTitle(String str) {
        titleTv.setText(str);
    }

    /**
     * 设置内容文字
     * @param msg
     */
    public void setContent(String msg){
        contentTv.setText(msg);
    }

    public interface ClickListener{
        public void doDismiss();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }
}
