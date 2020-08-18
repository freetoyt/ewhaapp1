package com.gms.app.barcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.gms.app.barcode.R;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private ArrayList<ReportData> arrayList;
    private String shared = "file";

    public ReportAdapter(ArrayList<ReportData> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list,parent,false);

        ReportViewHolder holder = new ReportViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportAdapter.ReportViewHolder holder, int position) {

        holder.tv_reportCustomerNm.setText(arrayList.get(position).getTv_reportCustomerNm());
        holder.tv_reportProductNm.setText(arrayList.get(position).getTv_reportProductNm());
        holder.tv_reportProductCapa.setText(arrayList.get(position).getTv_reportProductCapa());
        holder.tv_reportBottleWork.setText(arrayList.get(position).getTv_reportBottleWork());
        holder.tv_reportProductCount.setText(Integer.toString( arrayList.get(position).getTv_reportProductCount()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curNmae = holder.tv_reportCustomerNm.getText().toString();

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_reportCustomerNm;
        protected TextView tv_reportProductNm;
        protected TextView tv_reportProductCapa;
        protected TextView tv_reportBottleWork;
        protected TextView tv_reportProductCount;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_reportCustomerNm = (TextView) itemView.findViewById(R.id.tv_reportCustomerNm);
            this.tv_reportProductNm = (TextView) itemView.findViewById(R.id.tv_reportProductNm);
            this.tv_reportProductCapa = (TextView) itemView.findViewById(R.id.tv_reportProductCapa);
            this.tv_reportBottleWork = (TextView) itemView.findViewById(R.id.tv_reportBottleWork);
            this.tv_reportProductCount = (TextView)itemView.findViewById(R.id.tv_reportProductCount);
        }
    }



}
