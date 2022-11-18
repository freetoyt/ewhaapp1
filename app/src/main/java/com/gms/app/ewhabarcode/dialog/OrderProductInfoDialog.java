package com.gms.app.ewhabarcode.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.adapter.OrderProductAdapter;
import com.gms.app.ewhabarcode.adapter.OrderProductData;
import com.gms.app.ewhabarcode.domain.OrderProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderProductInfoDialog {
    private static ArrayList<OrderProductData> arrayList;
    private static OrderProductAdapter oderProductAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    private String host;
    private Context context;
    private String orderId="";

    public OrderProductInfoDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String str) {
        host = context.getString(R.string.host_name);
        orderId = str;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.order_product_info_dialog);
        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        final Button okButton = (Button) dlg.findViewById(R.id.okButton);

        RecyclerView recyclerView = (RecyclerView) dlg.findViewById(R.id.rc_productList);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        oderProductAdapter = new OrderProductAdapter(arrayList,1);
        recyclerView.setAdapter(oderProductAdapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        try {
            String url = host + context.getString(R.string.api_orderProductList)+"orderId="+orderId;

            new HttpAsyncProduct().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class HttpAsyncProduct extends AsyncTask<String, Void, List<OrderProductVO>> {
        private final String TAG = OrderProductInfoDialog.HttpAsyncProduct.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<OrderProductVO> doInBackground(String... params) {
            List<OrderProductVO> orderProductList = new ArrayList<>();
            String strUrl = params[0];
            try {
                // 요청
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                // 응답
                Response response = client.newCall(request).execute();

                Gson gson = new Gson();

                // import java.lang.reflect.Type
                Type listType = new TypeToken<ArrayList<OrderProductVO>>() {
                }.getType();
                orderProductList = gson.fromJson(response.body().string(), listType);

//                Log.d(TAG, "###############onCreate: " + orderProductList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return orderProductList;
        }

        @Override
        protected void onPostExecute(List<OrderProductVO> orderProductList) {
            super.onPostExecute(orderProductList);

            for (int i = 0; i < orderProductList.size(); i++) {
                OrderProductVO orderProductInfo = orderProductList.get(i);
                OrderProductData orderProductData = new OrderProductData(orderProductInfo.getProductNm(), orderProductInfo.getProductId(), orderProductInfo.getProductCapa(), orderProductInfo.getProductPriceSeq(), Integer.toString(orderProductInfo.getOrderCount()), orderProductInfo.getOrderProductEtc(), "교환(" + orderProductInfo.getBottleChangeYn() + ")", orderProductInfo.getBottleChangeYn(), "판매(" + orderProductInfo.getBottleSaleYn() + ")", orderProductInfo.getBottleSaleYn(), "회수(" + orderProductInfo.getRetrievedYn() + ")", orderProductInfo.getRetrievedYn(), "AS(" + orderProductInfo.getAsYn() + ")", orderProductInfo.getAsYn(),"입고(" + orderProductInfo.getIncomeYn() + ")", orderProductInfo.getIncomeYn(), "출고(" + orderProductInfo.getOutYn() + ")", orderProductInfo.getOutYn(), orderProductInfo.getOrderAmount(),"Y",orderProductInfo.getSalesCount());

                arrayList.add(orderProductData);
                oderProductAdapter.notifyDataSetChanged();
            }
        }

    }
}
