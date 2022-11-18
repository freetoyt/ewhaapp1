package com.gms.app.ewhabarcode.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {

    private ArrayList<OrderProductData> arrayList;
    private String shared = "file";
    int displayType = 1 ;
    int width = 0 ;

    public OrderProductAdapter(ArrayList<OrderProductData> arrayList, int displayType) {

        this.arrayList = arrayList;
        this.displayType = displayType;
    }

    public OrderProductAdapter(ArrayList<OrderProductData> arrayList, int displayType, int width) {

        this.arrayList = arrayList;
        this.displayType = displayType;
        this.width = width;
    }

    @Override
    public OrderProductAdapter.OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(displayType > 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_price,parent,false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product,parent,false);

        OrderProductAdapter.OrderProductViewHolder holder = new OrderProductAdapter.OrderProductViewHolder(view);
//        holder.tv_productNm.setWidth(width/8*3);
//        holder.tv_productCapa.setWidth(width/8*2);
//        holder.tv_orderProductEtc.setWidth(width/8*2);
//        holder.tv_orderCount.setWidth(width/8*1);
//        holder.btn_info.setWidth(width/8*1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderProductAdapter.OrderProductViewHolder holder, int position) {

        holder.tv_productNm.setText(arrayList.get(position).getTv_productNm());
        holder.tv_productId.setText(Integer.toString(arrayList.get(position).getTv_productId()));
        holder.tv_productCapa.setText(arrayList.get(position).getTv_productCapa());
        holder.tv_productPriceSeq.setText(Integer.toString(arrayList.get(position).getTv_productPriceSeq()));
        holder.tv_orderCount.setText( arrayList.get(position).getTv_orderCount());
        holder.tv_orderProductEtc.setText(arrayList.get(position).getTv_orderProductEtc());

        holder.tv_bottleChangeYnText.setText(arrayList.get(position).getTv_bottleChangeYnText());
        holder.tv_bottleChangeYn.setText(arrayList.get(position).getTv_bottleChangeYn());
        holder.tv_bottleSaleYnText.setText(arrayList.get(position).getTv_bottleSaleYnText());
        holder.tv_bottleSaleYn.setText(arrayList.get(position).getTv_bottleSaleYn());
        holder.tv_retrievedYnText.setText(arrayList.get(position).getTv_retrievedYnText());
        holder.tv_retrievedYn.setText(arrayList.get(position).getTv_retrievedYn());
        holder.tv_asYnText.setText(arrayList.get(position).getTv_asYnText());
        holder.tv_asYn.setText(arrayList.get(position).getTv_asYn());
        holder.tv_incomeYnText.setText(arrayList.get(position).getTv_incomeYnText());
        holder.tv_incomeYn.setText(arrayList.get(position).getTv_incomeYn());
        holder.tv_outYnText.setText(arrayList.get(position).getTv_outYnText());
        holder.tv_outYn.setText(arrayList.get(position).getTv_outYn());
        holder.tv_price.setText(NumberFormat.getInstance(Locale.getDefault()).format(arrayList.get(position).getTv_price()));

        if(arrayList.get(position).getSalesCount() <= 0){
            holder.tv_productNm.setTextColor(Color.parseColor("#0326F8"));
            holder.tv_productCapa.setTextColor(Color.parseColor("#0326F8"));
            holder.tv_orderCount.setTextColor(Color.parseColor("#0326F8"));
            holder.tv_orderProductEtc.setTextColor(Color.parseColor("#0326F8"));
        }
        holder.itemView.setTag(position);
//        Log.d("**************** onCreateViewHolder","holder.tv_productNm. =="+holder.tv_productNm.getTextSize());
//        Toast.makeText(v.getContext(), String.format("\"%s\"하였습니다.", arrayList.get(position).getBtn_delYn()+"==" ), Toast.LENGTH_SHORT).show();
        if(arrayList.get(position).getBtn_delYn().equals("Y")) {
            holder.btn_info.setVisibility(View.INVISIBLE);

//            holder.tv_productNm.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_productCapa.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_orderCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_orderProductEtc.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_bottleChangeYnText.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_bottleSaleYnText.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_retrievedYnText.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_asYnText.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
//            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_PX,29);
        }

        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), String.format("\"%s\"하였습니다.", holder.tv_productNm.getTextSize()+"==" ), Toast.LENGTH_SHORT).show();
//                String host = (v.getContext()).getString(R.string.host_name)+ (v.getContext()).getString(R.string.api_deleteWorkBottle);
                //String param = holder.tv_workReportSeq.getText().toString();
                remove(holder.getAdapterPosition());
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

    public class OrderProductViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_productNm;
        protected TextView tv_productId;
        protected TextView tv_productCapa;
        protected TextView tv_productPriceSeq;
        protected TextView tv_orderCount;
        protected TextView tv_orderProductEtc;
        protected TextView tv_bottleChangeYnText;
        protected TextView tv_bottleChangeYn;
        protected TextView tv_bottleSaleYnText;
        protected TextView tv_bottleSaleYn;
        protected TextView tv_retrievedYnText;
        protected TextView tv_retrievedYn;
        protected TextView tv_asYnText;
        protected TextView tv_asYn;
        protected TextView tv_incomeYnText;
        protected TextView tv_incomeYn;
        protected TextView tv_outYnText;
        protected TextView tv_outYn;
        protected TextView tv_price;
        protected Button btn_info;


        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_productNm = (TextView) itemView.findViewById(R.id.tv_productNm);
            this.tv_productId = (TextView) itemView.findViewById(R.id.tv_productId);
            this.tv_productCapa = (TextView) itemView.findViewById(R.id.tv_productCapa);
            this.tv_productPriceSeq = (TextView) itemView.findViewById(R.id.tv_productPriceSeq);
            this.tv_orderCount = (TextView)itemView.findViewById(R.id.tv_orderCount);
            this.tv_orderProductEtc = (TextView)itemView.findViewById(R.id.tv_orderProductEtc);

            this.tv_bottleChangeYnText = (TextView)itemView.findViewById(R.id.tv_bottleChangeYnText);
            this.tv_bottleChangeYn = (TextView)itemView.findViewById(R.id.tv_bottleChangeYn);
            this.tv_bottleSaleYnText = (TextView)itemView.findViewById(R.id.tv_bottleSaleYnText);
            this.tv_bottleSaleYn = (TextView)itemView.findViewById(R.id.tv_bottleSaleYn);
            this.tv_retrievedYnText = (TextView)itemView.findViewById(R.id.tv_retrievedYnText);
            this.tv_retrievedYn = (TextView)itemView.findViewById(R.id.tv_retrievedYn);
            this.tv_asYnText = (TextView)itemView.findViewById(R.id.tv_asYnText);
            this.tv_asYn = (TextView)itemView.findViewById(R.id.tv_asYn);
            this.tv_incomeYnText = (TextView)itemView.findViewById(R.id.tv_incomeYnText);
            this.tv_incomeYn = (TextView)itemView.findViewById(R.id.tv_incomeYn);
            this.tv_outYnText = (TextView)itemView.findViewById(R.id.tv_outYnText);
            this.tv_outYn = (TextView)itemView.findViewById(R.id.tv_outYn);
            this.tv_price = (TextView)itemView.findViewById(R.id.tv_price);
            this.btn_info = (Button)itemView.findViewById(R.id.btn_prdouctdel);
        }
    }

}
