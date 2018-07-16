package com.android.hmvertical;

/**
 * Created by Administrator on 2017/9/21.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.hmvertical.scan.CommentScanActivity;
import com.android.hmvertical.view.ConfirmDialogView;
import com.android.hmvertical.view.ProDialogView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 初始化页面
 */
public class InitBotActivity extends CommentScanActivity {


    @BindView(R.id.commentbackImg)
    ImageView commentbackImg;
    @BindView(R.id.commentTitleTv)
    TextView commentTitleTv;
    @BindView(R.id.initScanBotEdit)
    EditText initScanBotEdit;
    @BindView(R.id.initSubBtn)
    Button initSubBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initbot);
        ButterKnife.bind(this);

        initViews();

    }

    private void initViews() {
        commentTitleTv.setText("钢瓶初始化");
    }

    @Override
    public void getScanResultData(String scancontent) {
        super.getScanResultData(scancontent);
        if(!TextUtils.isEmpty(scancontent)){
            initScanBotEdit.setText(scancontent);
        }
    }

    @OnClick({R.id.commentbackImg, R.id.initSubBtn,R.id.initScanBotEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commentbackImg:
                finish();
                break;
            case R.id.initScanBotEdit:
                final ConfirmDialogView condV = new ConfirmDialogView(InitBotActivity.this);
                condV.show();
                condV.setTitle("详情");
                condV.setContent(initScanBotEdit.getText().toString().trim());
                condV.setListener(new ConfirmDialogView.ClickListener() {
                    @Override
                    public void doDismiss() {
                        condV.dismiss();
                    }
                });
                break;
            case R.id.initSubBtn:
                final ProDialogView pdv = new ProDialogView(InitBotActivity.this);
                pdv.show();
                pdv.setTitle("提示");
                pdv.setContent("是否提交?");
                pdv.setrightText("是");
                pdv.setleftText("否");
                pdv.setListener(new ProDialogView.OnPromptDialogListener() {
                    @Override
                    public void leftClick(int code) {
                        pdv.dismiss();
                    }

                    @Override
                    public void rightClick(int code) {
                        pdv.dismiss();
                    }
                });
                break;
        }
    }
}
