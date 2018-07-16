package com.android.hmvertical.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.hmvertical.R;
import com.android.hmvertical.bean.CheckResultTmpBean;
import com.android.hmvertical.bean.OperatorBean;
import com.yanzhenjie.nohttp.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2018/6/2.
 */

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.OperatorViewHolder>{

    public OnSpinnerSelectListener onSpinnerSelectListener;

    public void setOnSpinnerSelectListener(OnSpinnerSelectListener onSpinnerSelectListener) {
        this.onSpinnerSelectListener = onSpinnerSelectListener;
    }

    private List<OperatorBean> list;
    private Context mContext;
    public List<CheckResultTmpBean.CheckInfoResultsBean> checkResultTmpBeans;

    public List<CheckResultTmpBean.CheckInfoResultsBean> getCheckResultTmpBeans() {
        return checkResultTmpBeans;
    }

    public void setCheckResultTmpBeans(List<CheckResultTmpBean.CheckInfoResultsBean> checkResultTmpBeans) {
        this.checkResultTmpBeans = checkResultTmpBeans;
    }

    public OperatorAdapter(List<OperatorBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;

    }

    @Override
    public OperatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_operator_layout,parent,false);
        return new OperatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OperatorViewHolder holder, final int position) {
        holder.nameTv.setText(list.get(position).getItem().getName()+"");
      //  Logger.e("----adaper="+list.get(position).getItem().getName());
        Spinner spinner = holder.spinner;
        final CusSpinnerAdapter cusSpinnerAdapter = new CusSpinnerAdapter(list.get(position).getResults());
        spinner.setAdapter(cusSpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positions, long id) {
                if(onSpinnerSelectListener != null){
                    onSpinnerSelectListener.onSelectListener(position,positions);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(onSpinnerSelectListener != null){
                    onSpinnerSelectListener.onNoSelectListener();
                }
            }
        });
        //回填的checkResultTmpBeans
        for(int i = 0;i<checkResultTmpBeans.size();i++){
            //获取checkItem
            CheckResultTmpBean.CheckInfoResultsBean.CheckItemBean checkItemBean = checkResultTmpBeans.get(i).getCheckItem();
            if(checkItemBean.getId() == list.get(position).getItem().getId()){
                //获取checkResult 的id
               int checkResultId =  checkResultTmpBeans.get(i).getCheckResult().getId();
               Logger.e("----checkId=="+checkResultId);
               for(int k = 0;k<list.get(position).getResults().size();k++){
                   if(checkResultId == list.get(position).getResults().get(k).getId()){
                       spinner.setSelection(k,true);
                   }
               }

            }
        }


//
//        if(checkResultTmpBeans != null && checkResultTmpBeans.size()>0){
//            //回填元素位置
//            int checkPosition =checkResultTmpBeans.get(position).getCheckResult().getId();
//            Logger.e("----checkPo="+position+checkPosition+"--name="+checkResultTmpBeans.get(position).getCheckItem().getName());
//            for(int i = 0;i<list.get(position).getResults().size();i++){
//                Logger.e("----id="+list.get(position).getResults().get(i).getId()+"--name="+list.get(position).getResults().get(i).getResult());
//                if(list.get(position).getResults().get(i).getId() == checkPosition){
//                    Logger.e("----下标="+i);
//                    spinner.setSelection(i,true);
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OperatorViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        Spinner spinner;

        public OperatorViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_operatorTv);
            spinner = (Spinner) itemView.findViewById(R.id.item_operatorSpinner);

        }
    }

    class CusSpinnerAdapter extends BaseAdapter{

        private List<OperatorBean.ResultsBean> resultsBeans;
        private LayoutInflater layoutInflater;


        public CusSpinnerAdapter(List<OperatorBean.ResultsBean> resultsBeans) {
            this.resultsBeans = resultsBeans;
            layoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return resultsBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return resultsBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CusSpinnerViewHolder holder = null;

            if(convertView == null){
                holder = new CusSpinnerViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_spinner_layout,parent,false);
                holder.tv = (TextView) convertView.findViewById(R.id.item_spinner_tv);
                convertView.setTag(holder);
            }else{
                holder = (CusSpinnerViewHolder) convertView.getTag();
            }
            holder.tv.setText(resultsBeans.get(position).getResult());
            return convertView;
        }

        class CusSpinnerViewHolder {

            TextView tv;

        }
    }

    public interface OnSpinnerSelectListener{
        void onSelectListener(int parentPosition,int position);
        void onNoSelectListener();
    }
}
