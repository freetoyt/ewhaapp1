package com.gms.app.barcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gms.app.barcode.adapter.ReportAdapter;
import com.gms.app.barcode.adapter.ReportData;
import com.gms.app.barcode.domain.WorkBottleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReportActivity extends AppCompatActivity {

    private Button btn_okbtn;

    private LinearLayoutManager linearLayoutManager;

    private static ArrayList<ReportData> arrayList;
    private static ReportAdapter reportAdapter;
    private static final String shared = "file";
    private String userId = "";
    private String host="";
    private static final String TAG = "ReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        host = getString(R.string.host_name);

        //SharedPreferences 로그인 정보 유무 확인
        final SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");

        //Log.d(TAG,"userId="+userId);
        btn_okbtn = (Button)findViewById(R.id.btn_okbtn);       // 기타

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_report);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        //ReportData reportData = new ReportData("fff","산소", "11", "대여", 1);
        //Toast.makeText(MainActivity.this ,"onPostExecute."+arrayList.size()+"--isScan="+isScan, Toast.LENGTH_SHORT).show();
        //arrayList.add(reportData);
        reportAdapter = new ReportAdapter(arrayList);
        recyclerView.setAdapter(reportAdapter);

        new HttpAsyncTask().execute(host +  getString(R.string.api_reportList)+"?userId="+userId);

        btn_okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                // user_id 가져오기 0601 추가
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, List<WorkBottleVO>> {
        private final String TAG = HttpAsyncTask.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<WorkBottleVO> doInBackground(String... params) {
            List<WorkBottleVO> workReportList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<WorkBottleVO>>() {
                }.getType();
                workReportList = gson.fromJson(response.body().string(), listType);

                Log.d(TAG, "onCreate: " + workReportList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return workReportList;
        }

        @Override
        protected void onPostExecute(List<WorkBottleVO> workReportList) {
            super.onPostExecute(workReportList);

            for (int i = 0; i < workReportList.size(); i++) {
                WorkBottleVO workBottle = workReportList.get(i);

                ReportData reportData = new ReportData(workBottle.getCustomerNm(),workBottle.getProductNm(), workBottle.getProductCapa(), workBottle.getBottleWorkCdNm(), workBottle.getProductCount());
                //Toast.makeText(MainActivity.this ,"onPostExecute."+arrayList.size()+"--isScan="+isScan, Toast.LENGTH_SHORT).show();
                arrayList.add(reportData);
                reportAdapter.notifyDataSetChanged();
            }

        }
    }
}