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
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gms.app.ewhabarcode.adapter.OrderProductAdapter;
import com.gms.app.ewhabarcode.adapter.OrderProductData;
import com.gms.app.ewhabarcode.dialog.OrderProductDialog;
import com.gms.app.ewhabarcode.domain.CustomerSimpleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private static ArrayList<OrderProductData> arrayList;
    private static OrderProductAdapter oderProductAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView ;
    private EditText message, et_deliveryReqDt, et_customerCity, et_orderEtc;
    private Button btn_addProduct, btn_okOrder, btn_okbtn, btn_orderList;
    private RadioGroup rg_deliveryReqAmpm;
    private RadioButton rb_am,rb_pm;

    String userId = "";
    String host ="";
    String value ="" ;
    String deliveryReqAmpm ="A";

    String[] items;

    ArrayList<String> listItems;
    ArrayList<String> listItemsTemp;
    List<String> spinnerArray;
    ListView listView ;
    ArrayAdapter adapter3 ;

    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        sharedPreferences = getSharedPreferences(shared,0);
        host = getString(R.string.host_name);

        // user_id 가져오기 0601 추가
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        //SharedPreferences 로그인 정보 유무 확인
        sharedPreferences = getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");

        if(userId == null || userId.length() <=0 ) userId = uid;

        if( userId == null || userId.length() <=0 ){
            Toast.makeText(OrderActivity.this, "사용자 정보가 없습니다.로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //String value = id.getText().toString();
            editor.clear();
            editor.commit();

            Intent intent1 = new Intent(OrderActivity.this,LoginActivity.class);
            startActivity(intent1);
        }

        message = (EditText) findViewById(R.id.mesgase);
        listView = (ListView) findViewById(R.id.listview);

        value = sharedPreferences.getString("clist", "");

        if(value ==null || value.length() <= 10)
            new HttpAsyncTask().execute(host + getString(R.string.api_customerList));
        items = value.split("#");

        listItemsTemp  = new ArrayList<>(Arrays.asList(items));
        listItems = new ArrayList<>(Arrays.asList(items));
        adapter3 = new ArrayAdapter(OrderActivity.this, R.layout.item_customer, R.id.tv_customer, listItems);
        listView.setAdapter(adapter3);

        btn_orderList = (Button)findViewById(R.id.btn_orderList);   //주문목록
        btn_addProduct = (Button)findViewById(R.id.btn_addProduct);       // 주문상품추가팝업
        btn_okbtn = (Button)findViewById(R.id.btn_okbtn);       // 메인
        btn_okOrder = (Button)findViewById(R.id.btn_okOrder);       // 주문
        et_deliveryReqDt = (EditText)findViewById(R.id.et_deliveryReqDt) ;
        et_customerCity = (EditText)findViewById(R.id.et_customerCity) ;
        et_orderEtc = (EditText)findViewById(R.id.et_orderEtc);

        rg_deliveryReqAmpm = (RadioGroup) findViewById(R.id.rg_deliveryReqAmpm);
        rb_am = (RadioButton) findViewById(R.id.rb_am);
        rb_pm = (RadioButton) findViewById(R.id.rb_pm);

        recyclerView = (RecyclerView) findViewById(R.id.rv_orderProduct);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //OrderProductData orderProductData = new OrderProductData("O2(의룡용)", "1", "2", "1", "1","etc","Y","N","N","N");

        arrayList = new ArrayList<>();
        //arrayList.add(orderProductData);
        oderProductAdapter = new OrderProductAdapter(arrayList,0);
        recyclerView.setAdapter(oderProductAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderActivity.this, "click item", Toast.LENGTH_SHORT).show();
                String text = (String)parent.getAdapter().getItem(position);
                message.setText(text);

            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 검색 수정 2020-06-19
                //adapter3.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 검색 수정 2020-06-19
                filter(s.toString());
            }
        });


        et_deliveryReqDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_deliveryReqDt.setInputType(1);
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(et_deliveryReqDt, InputMethodManager.SHOW_IMPLICIT);

                new DatePickerDialog(OrderActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        rg_deliveryReqAmpm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_am) {
                    deliveryReqAmpm = "A";
                }else if(checkedId == R.id.rb_pm){
                    deliveryReqAmpm = "P";
                }
            }
        });

        btn_orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderActivity.this, OrderListActivity.class);
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });


        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderProductDialog orderProductDialog = new OrderProductDialog(OrderActivity.this, btn_addProduct.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                orderProductDialog.callFunction(userId,arrayList,oderProductAdapter);
            }
        });

        btn_okOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customerNm = "";
                if(message.getText().toString().length() <=0 || et_deliveryReqDt.getText().toString().length() <=0){
                    if(message.getText().toString().length() <=0) {
                        Toast.makeText(OrderActivity.this, "거래처를 선택하세요", Toast.LENGTH_SHORT).show();
                        message.requestFocus();
                    } else if(et_deliveryReqDt.getText().toString().length() <=0) {
                        Toast.makeText(OrderActivity.this, "요청일을 선택하세요", Toast.LENGTH_SHORT).show();
                        et_deliveryReqDt.requestFocus();
                    }
                }else {
                    customerNm = message.getText().toString();
                    String deliveryReqDt = et_deliveryReqDt.getText().toString();
                    String orderEtc = et_orderEtc.getText().toString();
                    String customerCity = et_customerCity.getText().toString();
                    StringBuffer sbProducts = new StringBuffer();

                    if(arrayList.size() <=0){
                        Toast.makeText(OrderActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                    }else {

                        for (int i = 0; i < arrayList.size(); i++) {
                            //Toast.makeText(OrderActivity.this, String.format("\"%s %s를 \"하였습니다.", arrayList.get(i).getTv_productNm(), arrayList.get(i).getTv_productId()), Toast.LENGTH_SHORT).show();
                            sbProducts.append(arrayList.get(i).getTv_productId()).append("#")
                                    .append(arrayList.get(i).getTv_productPriceSeq()).append("#")
                                    .append(arrayList.get(i).getTv_orderProductEtc()).append("#")
                                    .append(arrayList.get(i).getTv_orderCount()).append("#")
                                    .append(arrayList.get(i).getTv_bottleChangeYn()).append("#")
                                    .append(arrayList.get(i).getTv_bottleSaleYn()).append("#")
                                    .append(arrayList.get(i).getTv_retrievedYn()).append("#")
                                    .append(arrayList.get(i).getTv_asYn()).append("#")
                                    .append(arrayList.get(i).getTv_incomeYn()).append("#")
                                    .append(arrayList.get(i).getTv_outYn()).append(";");

                        }

                        new HttpAsyncTask1().execute(host + getString(R.string.api_registerOrder) + "createId=" + userId + "&customerNm=" + customerNm + "&deliveryReqDt=" + deliveryReqDt + "&deliveryReqAmpm=" + deliveryReqAmpm + "&customerCity=" + customerCity+ "&orderEtc=" + orderEtc + "&orderIds=" + URLEncoder.encode(sbProducts.toString()));
                    }
                }
            }
        });

        btn_okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
    // 검색을 수행하는 메소드
    public void filter(String str) {

        listItems.clear();
        Iterator it = this.listItemsTemp.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2.toString().toLowerCase(Locale.getDefault()).contains(str)) {
                listItems.add(str2);
            }
        }
        this.adapter3.notifyDataSetChanged();
    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.et_deliveryReqDt);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

//    private void init() {
//        //message.setText("");
//        et_deliveryReqDt.setText(null);
//        et_orderEtc.setText(null);
//        recyclerViewInitSetting();
//    }
//
//
//    //리사이클러뷰 초기화 셋팅
//    public void recyclerViewInitSetting(){
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        //recyclerView.setLayoutManager(linearLayoutManager);
//        arrayList.clear();
//        oderProductAdapter = new OrderProductAdapter(arrayList);
//        recyclerView.setAdapter(oderProductAdapter);
//    }


    private class HttpAsyncTask extends AsyncTask<String, Void, List<CustomerSimpleVO>> {
        private final String TAG = HttpAsyncTask.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<CustomerSimpleVO> doInBackground(String... params) {
            List<CustomerSimpleVO> customerList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<CustomerSimpleVO>>() {
                }.getType();
                customerList = gson.fromJson(response.body().string(), listType);

                //Log.d(TAG, "onCreate: " + customerList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return customerList;
        }

        @Override
        protected void onPostExecute(List<CustomerSimpleVO> customerList) {
            super.onPostExecute(customerList);

            StringBuffer sb = new StringBuffer();
            items = new String[customerList.size()];
            for (int i = 0; i < customerList.size(); i++) {
                items[i] = customerList.get(i).getCustomerNm().toString();
                sb.append(customerList.get(i).getCustomerNm().toString());
                sb.append("#");
            }

            //if((buttonType.equals("판매") || buttonType.equals("대여") || buttonType.equals("회수"))) {
            // 거래처정보 SharedPreferences에 저장 0603
            int cCount = sharedPreferences.getInt("clistCount", 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(cCount > 0 || cCount == customerList.size()) isUpdate = false;
            else isUpdate = true;
            //String value = id.getText().toString();
            editor.putString("clist", sb.toString());
            editor.putInt("clistCount",customerList.size());
            editor.commit();
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        private final String TAG = HttpAsyncTask1.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            String strUrl = params[0];
            String result= "";
            try {
                // 요청
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                // 응답
                Response response = client.newCall(request).execute();
                result = response.body().string();

                if(result.equals("fail")){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 사용하고자 하는 코드
                            Toast.makeText(OrderActivity.this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }else if(result.equals("noUser")){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 사용하고자 하는 코드
                            Toast.makeText(OrderActivity.this, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(OrderActivity.this, "등록했습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderActivity.this, OrderListActivity.class);
            intent.putExtra("uid",userId);
            startActivity(intent);
        }
    }
}