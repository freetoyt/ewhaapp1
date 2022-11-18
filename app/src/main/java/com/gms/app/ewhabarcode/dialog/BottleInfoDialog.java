package com.gms.app.ewhabarcode.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.adapter.BottleHistoryAdapter;
import com.gms.app.ewhabarcode.domain.BottleHistoryData;
import com.gms.app.ewhabarcode.domain.BottleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BottleInfoDialog {
    private String host;
    private Context context;
    private String str_info;
    String[] items;
    private static ArrayList<BottleHistoryData> arrayList;
    private static BottleHistoryAdapter bAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public BottleInfoDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String str) {

        str_info = str;
        host = context.getString(R.string.host_name);

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.bottle_info);
        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        final Button okButton = (Button) dlg.findViewById(R.id.okButton);

        final TextView tv_bottleId = (TextView) dlg.findViewById(R.id.tv_bottleId);
        final TextView tv_barCd = (TextView) dlg.findViewById(R.id.tv_barCd);
        final TextView tv_productNm = (TextView) dlg.findViewById(R.id.tv_productNm);
        final TextView tv_bottleCapa = (TextView) dlg.findViewById(R.id.tv_bottleCapa);
        final TextView tv_bottleChargeDt = (TextView) dlg.findViewById(R.id.tv_bottleChargeDt);
        final TextView tv_bottleVolumn = (TextView) dlg.findViewById(R.id.tv_bottleVolumn);

        RecyclerView recyclerView = (RecyclerView)dlg.findViewById(R.id.brv);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        bAdapter = new BottleHistoryAdapter(arrayList);
        recyclerView.setAdapter(bAdapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        try {
            Gson gson = new Gson();

            BottleVO bottle = new BottleVO();
            bottle = (BottleVO) gson.fromJson(str_info, bottle.getClass());

            tv_barCd.setText(bottle.getBottleBarCd());
            tv_bottleId.setText(bottle.getBottleId());
            tv_productNm.setText(bottle.getProductNm());
            tv_bottleCapa.setText(bottle.getBottleCapa());
            tv_bottleChargeDt.setText(bottle.getBottleChargeDt().substring(0,10));
            tv_bottleVolumn.setText(bottle.getBottleVolumn());

            new HttpAsyncTask().execute(host + context.getString(R.string.api_bottleHistoryList) +"bottleBarCd="+bottle.getBottleBarCd());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class HttpAsyncTask extends AsyncTask<String, Void, List<BottleVO>> {
        private final String TAG = BottleInfoDialog.HttpAsyncTask.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<BottleVO> doInBackground(String... params) {
            List<BottleVO> bottleList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<BottleVO>>() {
                }.getType();
                bottleList = gson.fromJson(response.body().string(), listType);

                //Log.d(TAG, "onCreate: " + bottleList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bottleList;
        }

        @Override
        protected void onPostExecute(List<BottleVO> bottleList) {
            super.onPostExecute(bottleList);
            for(int i=bottleList.size()-1 ; i >= 0 ; i--){

                String customerNm = "";
                if(bottleList.get(i).getCustomerNm()!=null) customerNm = bottleList.get(i).getCustomerNm();

                //Log.d(TAG, "bottleList.i: " + bottleList.get(i).getCustomerNm());
                BottleHistoryData hBottle = new BottleHistoryData(customerNm, bottleList.get(i).getBottleWorkCdNm(), bottleList.get(i).getBottleWorkDt().substring(0,10));

                arrayList.add(hBottle);
            }
            //bAdapter = new BottleHistoryAdapter(arrayList);
            bAdapter.notifyDataSetChanged();
        }
    }
}
