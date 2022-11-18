package com.gms.app.ewhabarcode.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.domain.CustomerSimpleVO;
import com.gms.app.ewhabarcode.domain.ProductPriceSimpleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TankDialog {

    private Context context;
    String[] items;

    ArrayList<String> listItems;
    ArrayList<String> listItemsTemp;
    List<String> spinnerArray;

    ListView listView ;
    ArrayAdapter adapter3 ;
    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;
    private Spinner spinner;
    private EditText productSeq;
    List<ProductPriceSimpleVO> productList = new ArrayList<>();

    String productType = "";
    String bottleWorkCd = "";
    String customerId="";
    String userId = "";
    String host ="";
    String value ="" ;

    String strAction = "";
    String strProductType = "";

    Integer productId = 0;
    Integer productPriceSeq = 0;
    int iProductCount = 0 ;

    public TankDialog(Context context) {
        this.context = context;


        sharedPreferences = context.getSharedPreferences(shared, 0);
        host = context.getString(R.string.host_name);

        value = sharedPreferences.getString("clist", "");
        //Log.e("noGasDialog ",buttonType);
        if(value ==null || value.length() <= 10)
            new HttpAsyncTask().execute(host + context.getString(R.string.api_customerList));

        new HttpAsyncTask2().execute(host + context.getString(R.string.api_tankProduct));

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction( String id ){

        userId = id;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.tank_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        title.setText("Tank");
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        final EditText productCount = (EditText) dlg.findViewById(R.id.productCount);

        final RadioGroup rg_type = dlg.findViewById(R.id.rg_type);
        final RadioButton rb_sale = dlg.findViewById(R.id.rb_sale);
        final RadioButton rb_charge = dlg.findViewById(R.id.rb_charge);

        final RadioGroup rg_unit= dlg.findViewById(R.id.rg_unit);
        final RadioButton rb_l = dlg.findViewById(R.id.rb_l);
        final RadioButton rb_kg = dlg.findViewById(R.id.rb_kg);
        // Add Data to listView
        listView = (ListView) dlg.findViewById(R.id.listview);
        spinner = (Spinner)dlg.findViewById(R.id.spinner);

        //value = sharedPreferences.getString("clist", "");
        //Log.d("noGasDialog  value ", value);
        items = value.split("#");

        listItemsTemp  = new ArrayList<>(Arrays.asList(items));
        listItems = new ArrayList<>(Arrays.asList(items));
        adapter3 = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, listItems);
        listView.setAdapter(adapter3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "click item", Toast.LENGTH_SHORT).show();
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_sale) {
                    bottleWorkCd = "0308";
                    strAction = "판매";
                }else if(checkedId == R.id.rb_charge){
                    bottleWorkCd = "0318";
                    strAction = "충전";
                }
            }
        });

        rg_unit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_l) {
                    productPriceSeq = 1;
                }else if(checkedId == R.id.rb_kg){
                    productPriceSeq = 2;
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(message.getText().toString().length() <=0){
                    Toast.makeText(context, "거래처를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    customerId = message.getText().toString();

                    boolean isGo = false;

                    for (int i = 0; i < productList.size(); i++) {
                        if (productType.equals(productList.get(i).getProductNm())) {
                            productId = productList.get(i).getProductId();
                        }
                    }

                    if(bottleWorkCd.length() <= 0){
                        Toast.makeText(context, "판매/충전을 선택하세요", Toast.LENGTH_SHORT).show();
                    }else {
                        if(productPriceSeq==0){
                            Toast.makeText(context, "단위(L/Kg)를 선택하세요", Toast.LENGTH_SHORT).show();
                        }else {
                            if(productCount.getText().toString().equals("") || productCount.getText().toString() == null ){
                                Toast.makeText(context, "용량을 입력하세요", Toast.LENGTH_SHORT).show();
                            }else{
                                iProductCount = Integer.parseInt(productCount.getText().toString());
                                isGo = true;
                            }

                        }
                    }

                    if(isGo) {

                        strProductType = productType;

                        AlertDialog.Builder ad = new AlertDialog.Builder(context);
                        ad.setMessage(String.format("\"%s에 %s를 %s\"하겠습니까?", message.getText().toString(), strProductType, strAction));

                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(context, String.format("\"%s에 %s를 %s\"하였습니다.", message.getText().toString(), strProductType, strAction), Toast.LENGTH_SHORT).show();
                                // 서버 전송
                                new HttpAsyncTask1().execute(host + context.getString(R.string.api_controlTank) + "userId=" + userId + "&customerNm=" + URLEncoder.encode(customerId )+ "&productId=" + productId + "&productPriceSeq=" + productPriceSeq + "&bottleWorkCd="+bottleWorkCd+"&productCount=" + iProductCount);
                                //MainActivity List 제거

                                // 커스텀 다이얼로그를 종료한다.
                                dlg.dismiss();
                            }
                        });

                        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.show();

                    }
                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
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

            // 거래처정보 SharedPreferences에 저장 0603
            int cCount = sharedPreferences.getInt("clistCount", 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(cCount > 0 || cCount == customerList.size()) isUpdate = false;
            else isUpdate = true;
            //String value = id.getText().toString();
            editor.putString("clist", sb.toString());
            editor.putInt("clistCount",customerList.size());
            editor.commit();

            //}
            if(isUpdate) {
                //Log.d("isUpdate ture", "ture ");
                listItems = new ArrayList<>(Arrays.asList(items));
                adapter3 = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, listItems);
                listView.setAdapter(adapter3);
            }
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        private final String TAG = HttpAsyncTask1.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            //List<CustomerSimpleVO> customerList = new ArrayList<>();
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
                            Toast.makeText(TankDialog.this.context, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }else if(result.equals("noUser")){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 사용하고자 하는 코드
                            Toast.makeText(TankDialog.this.context, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
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

        }
    }

    // 단품상품 가져오기
    private class HttpAsyncTask2 extends AsyncTask<String, Void, List<ProductPriceSimpleVO>> {
        private final String TAG = HttpAsyncTask2.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<ProductPriceSimpleVO> doInBackground(String... params) {

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
                Type listType = new TypeToken<ArrayList<ProductPriceSimpleVO>>() {
                }.getType();
                productList = gson.fromJson(response.body().string(), listType);

                //Log.d(TAG, "onCreate: " + productList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return productList;
        }

        @Override
        protected void onPostExecute(List<ProductPriceSimpleVO> productList) {
            super.onPostExecute(productList);

            Log.d("HttpAsyncTask2", productList.toString());
            spinnerArray =  new ArrayList<String>();

            for (int i = 0; i < productList.size(); i++) {
                if(productList.get(i).getProductId() != 60)
                    spinnerArray.add(productList.get(i).getProductNm().toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Spinner sItems = (Spinner) findViewById(R.id.spinner1);
            spinner.setAdapter(adapter);
        }
    }


}
