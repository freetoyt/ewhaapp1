package com.gms.app.ewhabarcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.domain.BottleHistoryData;

import java.util.ArrayList;

public class BottleHistoryAdapter extends RecyclerView.Adapter<BottleHistoryAdapter.BottleViewHolder> {

    private ArrayList<BottleHistoryData> arrayList;
    private String shared = "file";

    public BottleHistoryAdapter(ArrayList<BottleHistoryData> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public BottleHistoryAdapter.BottleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bitem_list,parent,false);

        BottleViewHolder holder = new BottleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BottleHistoryAdapter.BottleViewHolder holder, int position) {

        holder.tv_customerNm.setText(arrayList.get(position).getTv_customerNm());
        holder.tv_bottleWorkNm.setText(arrayList.get(position).getTv_bottleWorkNm());
        holder.tv_updateDt.setText(arrayList.get(position).getTv_updateDt());

        holder.itemView.setTag(position);

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

    public class BottleViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_customerNm;
        protected TextView tv_bottleWorkNm;
        protected TextView tv_updateDt;

        public BottleViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_customerNm = (TextView) itemView.findViewById(R.id.tv_customerNm);
            this.tv_bottleWorkNm = (TextView) itemView.findViewById(R.id.tv_bottleWorkNm);
            this.tv_updateDt = (TextView) itemView.findViewById(R.id.tv_updateDt);
        }
    }
}
