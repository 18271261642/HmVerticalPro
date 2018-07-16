package com.android.hmvertical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hmvertical.R;
import com.android.hmvertical.bean.CheckDetailBean;
import com.android.hmvertical.interfaces.OnCusItemClickListener;
import com.android.hmvertical.utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckOrderDetailAdapter extends RecyclerView.Adapter<CheckOrderDetailAdapter.DetailViewHolder> {

    public OnCusItemClickListener onCusItemClickListener;

    public void setOnCusItemClickListener(OnCusItemClickListener onCusItemClickListener) {
        this.onCusItemClickListener = onCusItemClickListener;
    }

    private List<CheckDetailBean.ListBean> list;
    private Context mContext;

    public CheckOrderDetailAdapter(List<CheckDetailBean.ListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_check_detail_layout, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailViewHolder holder, int position) {

        //检测详情单号
        holder.tv1.setText(""+list.get(position).getCheckNumber());
        holder.tv2.setText(""+list.get(position).getResult());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCusItemClickListener != null){
                    int position = holder.getLayoutPosition();
                    onCusItemClickListener.doOnItemClick(position);
                }
            }
        });

//        //编号
//        holder.tv1.setText(""+list.get(position).getId());
//        //检测详情单号
//        holder.tv2.setText(""+list.get(position).getCheckNumber());
//        //燃气公司
//        holder.tv3.setText(""+list.get(position).getCompany());
//        //气瓶类型
//        holder.tv4.setText(""+list.get(position).getCylinderType());
//        //填充介质
//        holder.tv5.setText(""+list.get(position).getFillingMedium());
//        //检测单号
//        holder.tv6.setText(""+list.get(position).getCheckOrder());
//        //气瓶编码
//        holder.tv7.setText(""+list.get(position).getGasNumber());
//        //新气瓶编码
//        holder.tv8.setText(""+list.get(position).getNewGsNumber());
//        //出厂编号
//        holder.tv9.setText(""+list.get(position).getCreatecode());
//        //生成厂商
//        holder.tv10.setText(""+list.get(position).getProducer());
//        //产权性质
//        String createType = list.get(position).getCreatetype();
//        if(!Utils.isEmpty(createType)){
//            if(createType.equals("1")){
//                holder.tv11.setText("自有");
//            }else if(createType.equals("2")){
//                holder.tv11.setText("公共");
//            }else{
//                holder.tv11.setText(""+createType);
//            }
//        }
//
//        //创建日期
//        holder.tv12.setText(""+list.get(position).getCreatedate());
//        //检测日期
//        holder.tv13.setText(""+ list.get(position).getRemark());
//        //二维码密文
//        holder.tv18.setText(""+list.get(position).getTwobarcodecipher());
//        //二维码明文
//        holder.tv14.setText(""+list.get(position).getTwobarcode());
//        //检测结果
//        holder.tv15.setText(""+list.get(position).getResult());
//        //是否报废
//        int isscrapState = list.get(position).getIsscrap();
//        if(isscrapState == 0){
//            holder.tv16.setText("否");
//        }else if(isscrapState== 1){
//            holder.tv16.setText("是");
//        }
//        //备注
//        holder.tv17.setText(""+list.get(position).getRemark());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        // TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18;

        TextView tv1, tv2;


        public DetailViewHolder(View itemView) {
            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.item_detailTv1);
            tv2 = (TextView) itemView.findViewById(R.id.item_detailTv2);

//            tv1 = (TextView) itemView.findViewById(R.id.item_detailTv1);
//            tv2 = (TextView) itemView.findViewById(R.id.item_detailTv2);
//            tv3 = (TextView) itemView.findViewById(R.id.item_detailTv3);
//            tv4 = (TextView) itemView.findViewById(R.id.item_detailTv4);
//            tv5 = (TextView) itemView.findViewById(R.id.item_detailTv5);
//            tv6 = (TextView) itemView.findViewById(R.id.item_detailTv6);
//            tv7 = (TextView) itemView.findViewById(R.id.item_detailTv7);
//            tv8 = (TextView) itemView.findViewById(R.id.item_detailTv8);
//            tv9 = (TextView) itemView.findViewById(R.id.item_detailTv9);
//            tv10 = (TextView) itemView.findViewById(R.id.item_detailTv10);
//            tv11 = (TextView) itemView.findViewById(R.id.item_detailTv11);
//            tv12 = (TextView) itemView.findViewById(R.id.item_detailTv12);
//            tv13 = (TextView) itemView.findViewById(R.id.item_detailTv13);
//            tv14 = (TextView) itemView.findViewById(R.id.item_detailTv14);
//            tv15 = (TextView) itemView.findViewById(R.id.item_detailTv15);
//            tv16 = (TextView) itemView.findViewById(R.id.item_detailTv16);
//            tv17 = (TextView) itemView.findViewById(R.id.item_detailTv17);
//            tv18 = (TextView) itemView.findViewById(R.id.item_detailTv18);
        }
    }
}
