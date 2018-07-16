package com.android.hmvertical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.hmvertical.R;
import com.android.hmvertical.bean.CheckOrderBean;
import com.android.hmvertical.interfaces.OnCusItemClickListener;
import com.android.hmvertical.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckOrderListAdapter extends RecyclerView.Adapter<CheckOrderListAdapter.CheckOrderListViewHolder> {


    private OnCusItemClickListener onCusItemClickListener;

    public void setOnCusItemClickListener(OnCusItemClickListener onCusItemClickListener) {
        this.onCusItemClickListener = onCusItemClickListener;
    }

    private List<CheckOrderBean.ListBean> list;
    private Context mContext;


    public CheckOrderListAdapter(List<CheckOrderBean.ListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public CheckOrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_check_order_layout, parent, false);
        CheckOrderListViewHolder holder = new CheckOrderListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CheckOrderListViewHolder holder, final int position) {

        holder.itemCheckOrderNumberberingTv.setText(""+list.get(position).getId());
        holder.itemCheckOrderNumberTv.setText(""+list.get(position).getNumber());
        holder.itemCompanyTv.setText(""+list.get(position).getCompany());
        holder.itemCheckOrderCountTv.setText(""+list.get(position).getCount());
        holder.itemCheckOrderCreateTimeTv.setText(""+Utils.longToDate(list.get(position).getCreatetime()));
        /**
         * WAIT_AUDITING(1,"待接收"),
         WAIT_INTO(2,"待入库"),
         CHECKING(3,"检测中"),
         WAIT_CONFIRM(4,"检测完成"),
         WAIT_OUT(5,"待出库"),
         CONFIRM(6,"已确认"),
         REJECT_AUDITING(7,"拒绝接收"),
         REJECT_INTO(8,"拒绝入库")
         */
        int checkState = list.get(position).getCheckstatus();
        if(checkState == 1){
            holder.itemCheckOrderCheckStateTv.setText("待接收");
        }else if(checkState == 2){
            holder.itemCheckOrderCheckStateTv.setText("待入库");
        }else if(checkState == 3){
            holder.itemCheckOrderCheckStateTv.setText("检测中");
        }else if(checkState == 4){
            holder.itemCheckOrderCheckStateTv.setText("检测完成");
        }else if(checkState == 5){
            holder.itemCheckOrderCheckStateTv.setText("待出库");
        }else if(checkState == 6){
            holder.itemCheckOrderCheckStateTv.setText("已确认");
        }else if(checkState == 7){
            holder.itemCheckOrderCheckStateTv.setText("拒绝接收");
        }else if(checkState == 8){
            holder.itemCheckOrderCheckStateTv.setText("拒绝入库");
        }
        //审核状态
        int upsetState = list.get(position).getUpset();
        if(upsetState == 0){
            holder.itemCheckOrderReviewStateTv.setText("待审核");
        }else if(upsetState == 1){
            holder.itemCheckOrderReviewStateTv.setText("通过");
        }else if(upsetState == 2){
            holder.itemCheckOrderReviewStateTv.setText("不通过");
        }
        //备注
        holder.itemCheckOrderRemarkTv.setText(""+list.get(position).getRemark());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCusItemClickListener != null){
                    int positon = holder.getLayoutPosition();
                    onCusItemClickListener.doOnItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CheckOrderListViewHolder extends RecyclerView.ViewHolder {

        TextView itemCheckOrderNumberberingTv;
        TextView itemCheckOrderNumberTv;
        TextView itemCheckOrderCountTv;
        TextView itemCheckOrderCreateTimeTv;
        TextView itemCheckOrderCheckStateTv;
        TextView itemCheckOrderReviewStateTv;
        TextView itemCheckOrderRemarkTv;

        TextView itemCompanyTv;

        public CheckOrderListViewHolder(View itemView) {
            super(itemView);
            itemCheckOrderNumberberingTv = (TextView) itemView.findViewById(R.id.item_checkOrderNumberberingTv);
            itemCheckOrderNumberTv = (TextView) itemView.findViewById(R.id.item_checkOrderNumberTv);
            itemCheckOrderCountTv = (TextView) itemView.findViewById(R.id.item_checkOrderCountTv);
            itemCheckOrderCreateTimeTv = (TextView) itemView.findViewById(R.id.item_checkOrderCreateTimeTv);
            itemCheckOrderCheckStateTv = (TextView) itemView.findViewById(R.id.item_checkOrderCheckStateTv);
            itemCheckOrderReviewStateTv = (TextView) itemView.findViewById(R.id.item_checkOrderReviewStateTv);
            itemCheckOrderRemarkTv = (TextView) itemView.findViewById(R.id.item_checkOrderRemarkTv);

            itemCompanyTv = (TextView) itemView.findViewById(R.id.item_checkOrderCompanyTv);
        }
    }

}
