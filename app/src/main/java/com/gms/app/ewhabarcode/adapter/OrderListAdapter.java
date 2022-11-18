package com.gms.app.ewhabarcode.adapter;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.RequestHttpURLConnection;
import com.gms.app.ewhabarcode.dialog.OrderProductInfoDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderInfoData> arrayList;
    private String shared = "file";
    private String userId = "";
    int width = 0 ;

    public OrderListAdapter(ArrayList<OrderInfoData> arrayList) {
        this.arrayList = arrayList;
    }

    public OrderListAdapter(ArrayList<OrderInfoData> arrayList, int width) {

        this.arrayList = arrayList;
        this.width = width;
    }

    @Override
    public OrderListAdapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);

        OrderListAdapter.OrderListViewHolder holder = new OrderListAdapter.OrderListViewHolder(view);
        final SharedPreferences sharedPreferences = (view.getContext()).getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListAdapter.OrderListViewHolder holder, int position) {

        holder.tv_orderCustomerNm.setText(arrayList.get(position).getTv_orderCustomerNm());
        holder.tv_orderProductNm.setText(arrayList.get(position).getTv_orderProductNm());
        holder.tv_orderId.setText(Integer.toString(arrayList.get(position).getTv_orderId()));
        holder.tv_orderProductCapa.setText(arrayList.get(position).getTv_orderProductCapa());
        holder.tv_orderTotalAmount.setText( NumberFormat.getInstance(Locale.getDefault()).format(arrayList.get(position).getTv_orderTotalAmount()));
        holder.tv_createNm.setText(arrayList.get(position).getTv_createNm());
        holder.tv_createId.setText(arrayList.get(position).getTv_createId());
        holder.tv_orderProcessCd.setText(arrayList.get(position).getTv_orderProcessCd());
        holder.tv_orderDeliveryDt.setText(arrayList.get(position).getTv_orderDeliveryDt());
        holder.tv_customerCity.setText(arrayList.get(position).getTv_customerCity());
        holder.tv_orderEtc.setText(arrayList.get(position).getTv_orderEtc());
        holder.tv_orderProductNm.setTypeface(null, Typeface.BOLD);
        holder.itemView.setTag(position);

        final String createId = holder.tv_createId.getText().toString();

        if(!userId.equals(createId)){
            holder.btn_delOrder.setVisibility(View.INVISIBLE);
        }else{
            holder.btn_delOrder.setVisibility(View.VISIBLE);
        }

        holder.tv_orderCustomerNm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = holder.tv_orderId.getText().toString();
//                Toast.makeText(v.getContext(), "주문 정보 가져오기."+orderId, Toast.LENGTH_SHORT).show();
                OrderProductInfoDialog orderProductInfo = new OrderProductInfoDialog(v.getContext());
                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                orderProductInfo.callFunction(orderId);

            }
        });

        holder.tv_orderProductNm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = holder.tv_orderId.getText().toString();
                Toast.makeText(v.getContext(), "주문 정보 가져오기."+orderId, Toast.LENGTH_SHORT).show();
                OrderProductInfoDialog orderProductInfo = new OrderProductInfoDialog(v.getContext());
                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                orderProductInfo.callFunction(orderId);
            }
        });

        holder.btn_delOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), String.format("\"%s에 %s를 %s\"하였습니다.", holder.tv_workReportSeq.getText(), holder.tv_reportProductNm.getText(), holder.tv_reportProductCapa.getText()), Toast.LENGTH_SHORT).show();
                String host = (v.getContext()).getString(R.string.host_name)+ (v.getContext()).getString(R.string.api_deleteOrder);
                String orderId = holder.tv_orderId.getText().toString();
//                String createId = holder.tv_createId.getText().toString();

//                final SharedPreferences sharedPreferences = (v.getContext()).getSharedPreferences(shared,0);
//                String userId = sharedPreferences.getString("id", "");

                if(userId!=null && createId!=null && userId.equals(createId)){

                    HttpAsyncReport AsyncReport = new HttpAsyncReport(host+ "orderId="+orderId+"&updateId="+userId,null, holder);
                    AsyncReport.execute();
//                    remove(holder.getAdapterPosition());
                }else {
                    Toast.makeText(v.getContext(), "등록한 주문만 삭제가 가능합니다.", Toast.LENGTH_SHORT).show();
                }
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

    public class OrderListViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_orderCustomerNm;
        private TextView tv_orderProductNm;
        private TextView tv_orderId;
        private TextView tv_orderProductCapa;
        private TextView tv_orderTotalAmount;
        private TextView tv_createId;
        private TextView tv_createNm;
        private TextView tv_orderProcessCd;
        private TextView tv_orderDeliveryDt;
        private TextView tv_customerCity;
        private TextView tv_orderEtc;
        private Button btn_delOrder;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_orderCustomerNm = (TextView) itemView.findViewById(R.id.tv_orderCustomerNm);
            this.tv_orderProductNm = (TextView) itemView.findViewById(R.id.tv_orderProductNm);
            this.tv_orderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            this.tv_orderProductCapa = (TextView) itemView.findViewById(R.id.tv_orderProductCapa);
            this.tv_orderTotalAmount = (TextView)itemView.findViewById(R.id.tv_orderTotalAmount);
            this.tv_createId = (TextView)itemView.findViewById(R.id.tv_createId);
            this.tv_createNm = (TextView)itemView.findViewById(R.id.tv_createNm);
            this.tv_orderProcessCd = (TextView)itemView.findViewById(R.id.tv_orderProcessCd);
            this.tv_orderDeliveryDt = (TextView)itemView.findViewById(R.id.tv_orderDeliveryDt);
            this.tv_customerCity = (TextView)itemView.findViewById(R.id.tv_customerCity);
            this.tv_orderEtc = (TextView)itemView.findViewById(R.id.tv_orderEtc);
            this.btn_delOrder = (Button)itemView.findViewById(R.id.btn_delOrder);
        }
    }


    public class HttpAsyncReport extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        private OrderListAdapter.OrderListViewHolder holder;
        public HttpAsyncReport(String url, ContentValues values, OrderListAdapter.OrderListViewHolder holder) {

            this.url = url;
            this.values = values;
            this.holder = holder;
        }
        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if(s != null && s.length() > 0 && s.equals("success")) {
                    remove(holder.getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
