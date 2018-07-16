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

public class ProDialogView extends Dialog implements View.OnClickListener{

    private OnPromptDialogListener listener;

    private TextView content;

    private TextView contentMsg;

    private Button btnok;

    private Button btnno;

    private int code;


    public ProDialogView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_view);

        initView();

    }

    private void initView() {
        content = (TextView) findViewById(R.id.dialog_prompt_content);
        contentMsg = (TextView) findViewById(R.id.dialog_prompt_content1);
        btnok = (Button) findViewById(R.id.dialog_ok);
        btnno = (Button) findViewById(R.id.dialog_no);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        btnok.setOnClickListener(this);
        btnno.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_ok:
                if (listener != null) {
                    listener.leftClick(code);
                }
                cancel();
                break;
            case R.id.dialog_no:
                if (listener != null) {
                    listener.rightClick(code);
                }
                cancel();
                break;
            default:
                break;
        }
    }
    public interface OnPromptDialogListener {

        public void leftClick(int code);

        public void rightClick(int code);
    }

    public void setListener(OnPromptDialogListener listener) {
        this.listener = listener;
    }

    public void setTitle(String str) {
        content.setText(str);
    }

    public void setContent(String msg){
        contentMsg.setText(msg);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setNoButtom(boolean bool) {
        if (bool) {
            btnno.setVisibility(View.VISIBLE);
        } else {
            btnno.setVisibility(View.GONE);
        }
    }


    public void setleftText(String text) {
        btnok.setText(text);
    }


    public void setrightText(String text) {
        btnno.setText(text);
    }
}
