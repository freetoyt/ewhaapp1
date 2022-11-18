package com.gms.app.ewhabarcode.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.adapter.CustomerProductAdaptor;
import com.gms.app.ewhabarcode.domain.CustomerProductVO;
import com.gms.app.ewhabarcode.domain.CustomerSimpleVO;
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

public class StockReportDialog {
    private Context context;
    String[] items;
    String[] bottleItems;
    ArrayList<String> listItems;
    ArrayList<String> listItemsTemp;
    ArrayList<CustomerProductVO> arrayList;
    ListView listView ;
    ListView lv_count ;
    ArrayAdapter adapter3 ;

    CustomerProductAdaptor customerProductAdaptor;
    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;

    String buttonType = "";
    String host ="";
    String value ="" ;
    CustomerProductVO customerProduct;

    public StockReportDialog(Context context, String bType) {
        this.context = context;
        this.buttonType = bType;

        sharedPreferences = context.getSharedPreferences(shared, 0);
        host = context.getString(R.string.host_name);

        value = sharedPreferences.getString("clist", "");
        //Log.e("CustomDialog ",buttonType);
        if(value ==null || value.length() <= 10)
            new HttpAsyncTask().execute(host + context.getString(R.string.api_customerList));

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(){

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.report_bottle);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);

        title.setText(buttonType);
        // Add Data to listView
        listView = (ListView) dlg.findViewById(R.id.listview);

        items = value.split("#");

        listItems = new ArrayList<>(Arrays.asList(items));
        listItemsTemp  = new ArrayList<>(Arrays.asList(items));
        adapter3 = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, listItems);
        listView.setAdapter(adapter3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "click item", Toast.LENGTH_SHORT).show();
                String text = (String)parent.getAdapter().getItem(position);
                message.setText(text);

                // 서버 전송
                new HttpAsyncTaskB().execute(host + context.getString(R.string.api_customrBottleList) +"customerNm=" + URLEncoder.encode(text) );
            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filter(s.toString());
                //adapter3.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // TODO Add Data to lv_bottle
        lv_count = (ListView) dlg.findViewById(R.id.lv_bottle);
        arrayList = new ArrayList<>();
        /*
        customerProductAdaptor = new CustomerProductAdaptor(context,R.layout.customer_bottles,arrayList);
        lv_count.setAdapter(customerProductAdaptor);
        */


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "확인 했습니다.", Toast.LENGTH_SHORT).show();

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
                //this.mAccountList.add(str2);
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

            //Log.d("HttpAsyncTask", customerList.toString());
            //CustomerSimpleAdapter adapter = new CustomerSimpleAdapter(customerList);
            StringBuffer sb = new StringBuffer();
            items = new String[customerList.size()];
            for (int i = 0; i < customerList.size(); i++) {
                items[i] = customerList.get(i).getCustomerNm().toString();
                sb.append(customerList.get(i).getCustomerNm().toString());
                sb.append("#");
            }
            int cCount = sharedPreferences.getInt("clistCount", 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(cCount > 0 || cCount == customerList.size()) isUpdate = false;
            else isUpdate = true;
            //String value = id.getText().toString();
            editor.putString("clist", sb.toString());
            editor.putInt("clistCount",customerList.size());
            editor.commit();

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
                            Toast.makeText(StockReportDialog.this.context, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }else if(result.equals("noUser")){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 사용하고자 하는 코드
                            Toast.makeText(StockReportDialog.this.context, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
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

    private class HttpAsyncTaskB extends AsyncTask<String, Void, List<CustomerProductVO>> {
        private final String TAG = HttpAsyncTaskB.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<CustomerProductVO> doInBackground(String... params) {
            List<CustomerProductVO> bottleList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<CustomerProductVO>>() {
                }.getType();
                bottleList = gson.fromJson(response.body().string(), listType);

               //
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bottleList;
        }

        @Override
        protected void onPostExecute(List<CustomerProductVO> bottleList) {
            super.onPostExecute(bottleList);

            arrayList = new ArrayList<CustomerProductVO>();

            for (int i = 0; i < bottleList.size(); i++) {

                customerProduct = new CustomerProductVO(bottleList.get(i).getProductNm() + "_" + bottleList.get(i).getProductCapa(),bottleList.get(i).getBottleRentCount());

                if( customerProduct.getBottleCount()!=null && !customerProduct.getBottleCount().equals("0")) {
                    arrayList.add(customerProduct);
                }
            }

            if(arrayList.size()==0){
                customerProduct = new CustomerProductVO("용기가 없습니다","");
                arrayList.add(customerProduct);
            }
            customerProductAdaptor = new CustomerProductAdaptor(context,R.layout.customer_bottles,arrayList);
            lv_count.setAdapter(customerProductAdaptor);
            customerProductAdaptor.notifyDataSetChanged();

        }
    }
}
