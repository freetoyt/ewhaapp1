package com.gms.app.barcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.barcode.dialog.BottleInfoDialog;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;
    private String shared = "file";

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, int position) {

        holder.tv_bottleId.setText(arrayList.get(position).getTv_bottleId());
        holder.tv_productNm.setText(arrayList.get(position).getTv_productNm());
        holder.tv_bottleBarCd.setText(arrayList.get(position).getTv_bottleBarCd());
        holder.tv_chargedt.setText(arrayList.get(position).getTv_chargedt());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curNmae = holder.tv_productNm.getText().toString();

            }
        });

        //final TextView main_label = holder.btn_info;
        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.textView.setText(items.get(holder.getAdapterPosition()));

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(shared,0);
                String value = sharedPreferences.getString(holder.tv_bottleBarCd.getText().toString(), "");
                //Toast.makeText(view.getContext(), "value "+value, Toast.LENGTH_SHORT).show();

                //info 정보 띄우기
                BottleInfoDialog bottleInfo = new BottleInfoDialog(view.getContext());
                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                bottleInfo.callFunction(value);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                ad.setMessage("용기를 삭제하시겠습니까?");

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(v.getContext(), "용기를 삭제하였습니다", Toast.LENGTH_SHORT).show();
                        //리스트 삭제
                        remove(holder.getAdapterPosition());
                        MainActivity.setTextBottleCount();
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

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_bottleId;
        protected TextView tv_productNm;
        protected TextView tv_bottleBarCd;
        protected TextView tv_chargedt;
        protected Button btn_info;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_bottleId = (TextView) itemView.findViewById(R.id.tv_bottleId);
            this.tv_productNm = (TextView) itemView.findViewById(R.id.tv_productNm);
            this.tv_bottleBarCd = (TextView) itemView.findViewById(R.id.tv_bottleBarCd);
            this.tv_chargedt = (TextView) itemView.findViewById(R.id.tv_chargedt);
            this.btn_info = (Button)itemView.findViewById(R.id.btn_info);
        }
    }
}
