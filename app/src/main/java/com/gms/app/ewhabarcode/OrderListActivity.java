package com.gms.app.ewhabarcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gms.app.ewhabarcode.adapter.OrderInfoData;
import com.gms.app.ewhabarcode.adapter.OrderListAdapter;
import com.gms.app.ewhabarcode.domain.OrderVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderListActivity extends AppCompatActivity {

    private Button btn_writeOrder, btn_searchOrder, btn_searchDt, btn_main;
    private EditText et_searchCustomerNm, et_searchReqDt;
    private Spinner sp_orderStatus;
//    private RadioGroup rg_orderProcessCd;
//    private RadioButton rb_0210,rb_0240;
    private LinearLayoutManager linearLayoutManager;

    private static ArrayList<OrderInfoData> arrayList;
    private static OrderListAdapter orderListAdapter;
    private static final String shared = "file";
    private String userId = "";
    private String host="";
    private int currentPage = 1;
    private  String searchOrderProcessCd = "0210";
    private  String  searchOrderDt = "";
    private static final String TAG = "OrderListActivity";

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.et_searchReqDt);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        host = getString(R.string.host_name);
        // user_id 가져오기 0601 추가
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        //SharedPreferences 로그인 정보 유무 확인
        final SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");

        if(userId == null || userId.length() <=0 ) userId = uid;

        if( userId == null || userId.length() <=0 ){
            Toast.makeText(OrderListActivity.this, "사용자 정보가 없습니다.로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //String value = id.getText().toString();
            editor.clear();
            editor.commit();

            Intent intent1 = new Intent(OrderListActivity.this,LoginActivity.class);
            startActivity(intent1);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_orderList);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(arrayList);
        recyclerView.setAdapter(orderListAdapter);

        Date now = Calendar.getInstance().getTime();
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        searchOrderDt = formatter.format(now);

        new HttpAsyncTask().execute(host +  getString(R.string.api_orderList)+"searchOrderProcessCd="+searchOrderProcessCd+"&searchOrderDt="+searchOrderDt+"&currentPage="+currentPage++);

        //Log.d(TAG,"userId="+userId);
        btn_main = (Button)findViewById(R.id.btn_main);       // 기타
        btn_writeOrder= (Button)findViewById(R.id.btn_writeOrder);       //주문등록
        btn_searchOrder= (Button)findViewById(R.id.btn_searchOrder);       //주문
        btn_searchDt = (Button)findViewById(R.id.btn_searchDt);

        et_searchCustomerNm = (EditText) findViewById(R.id.et_searchCustomerNm);       //검색어
        et_searchReqDt = (EditText) findViewById(R.id.et_searchReqDt);
        et_searchReqDt.setText(searchOrderDt);

        sp_orderStatus = (Spinner)findViewById(R.id.sp_orderStatus);

//        rg_orderProcessCd = (RadioGroup) findViewById(R.id.rg_orderProcessCd);
//        rb_0210 = (RadioButton) findViewById(R.id.rb_0210);
//        rb_0240 = (RadioButton) findViewById(R.id.rb_0240);

        btn_searchDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_searchReqDt.setInputType(1);
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(et_searchReqDt, InputMethodManager.SHOW_IMPLICIT);

                new DatePickerDialog(OrderListActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_writeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderListActivity.this, OrderActivity.class);
                // user_id 가져오기 0601 추가
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });

        sp_orderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String searchOrderProcessCdNm = parent.getItemAtPosition(position).toString();

                if (searchOrderProcessCdNm.equals("주문접수"))
                    searchOrderProcessCd = "0210";
                else if (searchOrderProcessCdNm.equals("납품완료"))
                    searchOrderProcessCd = "0240";
                else if (searchOrderProcessCdNm.equals("판매완료"))
                    searchOrderProcessCd = "0250";

                searchOrderDt = et_searchReqDt.getText().toString();
                currentPage = 1;
                arrayList.clear();
                orderListAdapter.notifyDataSetChanged();
                new HttpAsyncTask().execute(host + getString(R.string.api_orderList) + "searchCustomerNm=" + et_searchCustomerNm.getText().toString() + "&searchOrderProcessCd=" + searchOrderProcessCd + "&searchOrderDt="+searchOrderDt  + "&currentPage=" + currentPage++);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_searchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (et_searchCustomerNm.getText() != null && et_searchCustomerNm.getText().toString().length() > 0) {
                    currentPage = 1;
                    arrayList.clear();
                    orderListAdapter.notifyDataSetChanged();
                    searchOrderDt = et_searchReqDt.getText().toString();
                    new HttpAsyncTask().execute(host + getString(R.string.api_orderList) + "searchCustomerNm=" + et_searchCustomerNm.getText().toString() + "&searchOrderProcessCd=" + searchOrderProcessCd +"&searchOrderDt="+searchOrderDt+"&currentPage=" + currentPage++);
//                }else {
//                    Toast.makeText(OrderListActivity.this, "거개처명을 입력하세요.", Toast.LENGTH_LONG).show();
//                }
            }
        });

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                // user_id 가져오기 0601 추가
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(OrderListActivity.this, "목록을 추가합니다.", Toast.LENGTH_LONG).show();
                    new HttpAsyncTask().execute(host +  getString(R.string.api_orderList)+"searchCustomerNm=" + et_searchCustomerNm.getText().toString() + "&searchOrderProcessCd=" + searchOrderProcessCd +"&searchOrderDt="+et_searchReqDt.getText().toString()+"&currentPage="+currentPage++);
                }
            }
        });
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, List<OrderVO>> {
        private final String TAG = HttpAsyncTask.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<OrderVO> doInBackground(String... params) {
            List<OrderVO> orderList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<OrderVO>>() {
                }.getType();
                orderList = gson.fromJson(response.body().string(), listType);

//                Log.d(TAG, "###############onCreate: " + orderList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return orderList;
        }

        @Override
        protected void onPostExecute(List<OrderVO> orderList) {
            super.onPostExecute(orderList);
            Button btn_info = findViewById(R.id.btn_delOrder);
            for (int i = 0; i < orderList.size(); i++) {
                OrderVO orderInfo = orderList.get(i);

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                String deliveryReqD = transFormat.format(orderInfo.getDeliveryReqDt());

                OrderInfoData orderData = new OrderInfoData(orderInfo.getCustomerNm(),orderInfo.getOrderProductNm(), orderInfo.getOrderId(),orderInfo.getOrderProductCapa(), orderInfo.getOrderTotalAmount(), orderInfo.getCreateId(),orderInfo.getCreateNm(),orderInfo.getOrderProcessCdNm(),deliveryReqD,orderInfo.getCustomerCity(),orderInfo.getOrderEtc(),btn_info);
//                Toast.makeText(OrderListActivity.this ,"onPostExecute."+orderData.getTv_orderDeliveryDt(), Toast.LENGTH_SHORT).show();
                arrayList.add(orderData);
                orderListAdapter.notifyDataSetChanged();
            }
        }
    }
}