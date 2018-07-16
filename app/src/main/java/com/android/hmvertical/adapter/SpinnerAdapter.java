package com.android.hmvertical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.bean.CheckResultTmpBean;

import java.util.List;

/**
 * Created by Administrator
 */

/**
 * Spinner下拉框adapter
 */
public class SpinnerAdapter extends BaseAdapter {

    private List<CheckResultTmpBean.CheckItemResultModelsBean> list;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(List<CheckResultTmpBean.CheckItemResultModelsBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_spinner_layout,viewGroup,false);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.item_spinner_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(list.get(i).getName());
        return view;
    }

    class  ViewHolder{
        TextView tv;
    }
}
