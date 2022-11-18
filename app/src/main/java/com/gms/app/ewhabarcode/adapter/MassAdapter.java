package com.gms.app.ewhabarcode.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;

import java.util.ArrayList;

public class MassAdapter extends RecyclerView.Adapter<MassAdapter.ProductViewHolder> {

    private ArrayList<MassData> arrayList;
    private String shared = "file";


    public MassAdapter(ArrayList<MassData> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public MassAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.massproduct_list,parent,false);

        ProductViewHolder holder = new ProductViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MassAdapter.ProductViewHolder holder, int position) {

        holder.tv_mBottleId.setText(arrayList.get(position).getTv_mBottleId());
        holder.tv_mProductNm.setText(arrayList.get(position).getTv_mProductNm());
        holder.tv_mProductCapa.setText(arrayList.get(position).getTv_mProductCapa());
        holder.tv_mProductCnt.setText(arrayList.get(position).getTv_mProductCnt());

        holder.itemView.setTag(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                ad.setMessage("상품을 삭제하시겠습니까?");

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(v.getContext(), "용기를 삭제하였습니다", Toast.LENGTH_SHORT).show();
                        //리스트 삭제
                        remove(holder.getAdapterPosition());
                        //MassActivity.setTextBottleCount();
                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

                return true;
            }
        });
    }


    /*
    TODO 버튼 추가시 처리 예정

    //in RecyclerView.Adapter<MHolder>
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        holder.button.setOnClickListener(view -> {
            holder.textView.setText(items.get(holder.getAdapterPosition()));
        });
    }
    */

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

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_mProductNm;
        protected TextView tv_mBottleId;
        protected TextView tv_mProductCapa;
        protected TextView tv_mProductCnt;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_mBottleId = (TextView) itemView.findViewById(R.id.tv_mBottleId);
            this.tv_mProductNm = (TextView) itemView.findViewById(R.id.tv_mProductNm);
            this.tv_mProductCapa = (TextView) itemView.findViewById(R.id.tv_mProductCapa);
            this.tv_mProductCnt = (TextView) itemView.findViewById(R.id.tv_mProductCnt);


        }
    }
}
