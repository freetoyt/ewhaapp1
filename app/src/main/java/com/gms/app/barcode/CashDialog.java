package com.gms.app.barcode;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CashDialog {
    private Context context;
    String[] items;
    String[] bottleItems;
    ArrayList<String> listItems;
    ArrayList<String> listItemsTemp;
    ArrayList<String> bottleArray;
    ListView listView ;
    ListView lv_bottle ;
    ArrayAdapter adapter3 ;
    ArrayAdapter adapterB;
    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;

    String incomeWay = "";
    String buttonType = "";
    String customerId="";
    String userId = "";
    String host ="";
    String value ="" ;
    int receivableAmount = 0;
    int incomeAmount = 0;
    String strAction="";
    TextView tv_receivableAllAmount;

    public CashDialog(Context context) {
        this.context = context;
        this.buttonType = "입금 & 미수금";

        sharedPreferences = context.getSharedPreferences(shared, 0);
        host = context.getString(R.string.host_name);

        value = sharedPreferences.getString("clist", "");
        //Log.e("CustomDialog ",buttonType);
        if(value ==null || value.length() <= 10)
            new HttpAsyncTask().execute(host + "api/customerAllList.do");

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String id ){

        userId = id;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.cash_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        final TextView tv_receivable = (TextView) dlg.findViewById(R.id.tv_receivable);
        final EditText et_receivable = (EditText) dlg.findViewById(R.id.et_receivable);

        final TextView tv_receivableAllTitle = (TextView) dlg.findViewById(R.id.tv_receivableAllTitle);
        tv_receivableAllAmount = (TextView) dlg.findViewById(R.id.tv_receivableAllAmount);

        final TextView tv_income = (TextView) dlg.findViewById(R.id.tv_income);
        final EditText et_income = (EditText) dlg.findViewById(R.id.et_income);

        final RadioGroup rg_gender = dlg.findViewById(R.id.rg_way);
        final RadioButton rb_cash = dlg.findViewById(R.id.rb_cash);
        final RadioButton rb_card = dlg.findViewById(R.id.rb_card);

        title.setText(buttonType);
        // Add Data to listView
        listView = (ListView) dlg.findViewById(R.id.listview);

        items = value.split(",");

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
                new HttpAsyncTaskB().execute(host + "api/customerBottle.do?customerNm=" + text );

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
        lv_bottle = (ListView) dlg.findViewById(R.id.lv_bottle);
/*
        items = value.split(",");

        listItems = new ArrayList<>(Arrays.asList(items));
        adapter3 = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, listItems);
        listView.setAdapter(adapter3);
        */
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_cash) {
                    Toast.makeText(context,"현금을 선택했습니다", Toast.LENGTH_SHORT).show();
                    incomeWay = "CASH";
                }else if(checkedId == R.id.rb_card){
                    Toast.makeText(context,"카드을 선택했습니다", Toast.LENGTH_SHORT).show();
                    incomeWay = "CARD";
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isGo = true;
                if(message.getText().toString().length() <=0){
                    Toast.makeText(context, "거래처를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    if(et_receivable.getText() == null && et_income.getText() == null) {
                        Toast.makeText(context, "수금액과 입금액을 입력하세요", Toast.LENGTH_SHORT).show();
                        isGo =false;
                    }else if( (et_receivable.getText()!=null && et_receivable.getText().toString().length() <= 0 ) && et_income.getText().toString().length() <=0 ) {
                            Toast.makeText(context, "수금액과 입금액을 입력하세요", Toast.LENGTH_SHORT).show();
                    }else{
                        if(et_receivable.getText().toString().length() >0){
                            receivableAmount = Integer.parseInt(et_receivable.getText().toString());
                            strAction = "미수금";
                        }
                        if(et_income.getText().toString().length() > 0) {
                            incomeAmount = Integer.parseInt(et_income.getText().toString());
                            if(strAction.equals("미수금")) strAction = "미수금 & 입금";
                            else strAction ="& 입금";
                            if (incomeWay.length() <= 0) {
                                Toast.makeText(context, "수금수단을 선택하세요", Toast.LENGTH_SHORT).show();
                                isGo =false;
                            }
                        }
                        if(isGo) {
                            Toast.makeText(context, String.format("\"%s에  %s\" 를 하였습니다.", message.getText().toString(), strAction), Toast.LENGTH_SHORT).show();
                            customerId = message.getText().toString();

                            // 서버 전송
                            new HttpAsyncTask1().execute(host + "api/controlCashFlow.do?userId=" + userId + "&customerNm=" + customerId + "&incomeAmount=" + incomeAmount + "&receivableAmount=" + receivableAmount + "&incomeWay=" + incomeWay);

                            // 커스텀 다이얼로그를 종료한다.
                            dlg.dismiss();
                        }
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
                sb.append(",");
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
                            Toast.makeText(CashDialog.this.context, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
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

    private class HttpAsyncTaskB extends AsyncTask<String, Void, List<SimpleBottleVO>> {
        private final String TAG = HttpAsyncTaskB.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<SimpleBottleVO> doInBackground(String... params) {
            List<SimpleBottleVO> bottleList = new ArrayList<>();
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
                Type listType = new TypeToken<ArrayList<SimpleBottleVO>>() {
                }.getType();
                bottleList = gson.fromJson(response.body().string(), listType);

                //Log.d(TAG, "onCreate: " + bottleList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bottleList;
        }

        @Override
        protected void onPostExecute(List<SimpleBottleVO> bottleList) {
            super.onPostExecute(bottleList);

            int receivableAmount = 0;
            bottleItems = new String[bottleList.size()];
            for (int i = 0; i < bottleList.size(); i++) {
                bottleItems[i] = bottleList.get(i).getBottleBarCd()+" ["+bottleList.get(i).getProductNm()+", "+bottleList.get(i).getBottleCapa()+"]";
                receivableAmount = bottleList.get(i).getReceivableAmount();
            }
            DecimalFormat df = new DecimalFormat( "##,###.##" ); //format설정

            df.format( receivableAmount );

            tv_receivableAllAmount.setText(df.format( receivableAmount ));
            bottleArray = new ArrayList<>(Arrays.asList(bottleItems));
            adapterB = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, bottleArray);
            lv_bottle.setAdapter(adapterB);

        }
    }
}
