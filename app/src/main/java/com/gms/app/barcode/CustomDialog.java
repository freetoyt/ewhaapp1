package com.gms.app.barcode;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CustomDialog {

    private Context context;
    String[] items;
    ArrayList<String> listItems;
    ListView listView ;
    ArrayAdapter adapter3 ;
    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;

    String bottleType = "B";
    String buttonType = "";
    String bottles;
    String customerId="";
    String userId = "";
    String host ="";
    String value ="" ;

    public CustomDialog(Context context, String bType) {
        this.context = context;
        this.buttonType = bType;

        sharedPreferences = context.getSharedPreferences(shared, 0);
        host = context.getString(R.string.host_name);

        if(buttonType.equals("판매") || buttonType.equals("대여") || buttonType.equals("회수")) {
            value = sharedPreferences.getString("clist", "");
            Log.e("CustomDialog ",buttonType);
            if(value ==null || value.length() <= 10)
                new HttpAsyncTask().execute(host + "api/customerAllList.do");
        }else {
            if(!buttonType.equals("충전")) {

                new HttpAsyncTask().execute(host + "api/carList.do");
            }
        }
        //new HttpAsyncTask().execute("http://172.30.57.228:8080/api/carList.do");
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String bottle , String id ){

        bottles= bottle;
        userId = id;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        final RadioGroup rg_gender = dlg.findViewById(R.id.rg_gender);
        final RadioButton rb_man = dlg.findViewById(R.id.rb_man);
        final RadioButton rb_woman = dlg.findViewById(R.id.rb_woman);

        title.setText(buttonType);
        // Add Data to listView
        listView = (ListView) dlg.findViewById(R.id.listview);


        if(buttonType.equals("판매") || buttonType.equals("대여") || buttonType.equals("회수")) {
            //value = sharedPreferences.getString("clist", "");
            Log.d("CustomerDialog  value ", value);
            items = value.split(",");

            listItems = new ArrayList<>(Arrays.asList(items));
            adapter3 = new ArrayAdapter(context, R.layout.item_customer, R.id.tv_customer, listItems);
            listView.setAdapter(adapter3);
        }

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
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_man) {
                    Toast.makeText(context,"실병 선택", Toast.LENGTH_SHORT).show();
                    bottleType = "F";
                }else if(checkedId == R.id.rb_woman){
                    Toast.makeText(context,"공병 선택", Toast.LENGTH_SHORT).show();
                    bottleType = "E";
                }
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(message.getText().toString().length() <=0){
                    Toast.makeText(context, "거래처를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    if (bottleType.equals("B")) {
                        Toast.makeText(context, "실병/공병을 선택하세요", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, String.format("\"%s=%s=%s\" 을 입력하였습니다.", message.getText().toString(), bottleType, buttonType), Toast.LENGTH_SHORT).show();

                        customerId = message.getText().toString();
                        //작업한 용기목록저장
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("previousBottles",bottles);
                        editor.commit();
                        Log.d("@@@@@@@@@@@@@okButton: ","previousBottles: " + bottles);
                        // 서버 전송
                        new HttpAsyncTask1().execute(host + "api/controlAction.do?userId=" + userId + "&bottles=" + bottles + "&customerNm=" + customerId + "&bottleType=" + bottleType + "&bottleWorkCd=" + buttonType);

                        //MainActivity List 제거
                        MainActivity.clearArrayList();

                        // 커스텀 다이얼로그를 종료한다.
                        dlg.dismiss();
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
    /*
        public void searchItem(String textToSearch){
            if(items!=null) {
                for (String item : items) {

                    if (!item.contains(textToSearch)) {
                        listItems.remove(item);
                    }
                }

                adapter3.notifyDataSetChanged();
            }else {
                Toast.makeText(context, "items is null", Toast.LENGTH_SHORT).show();
            }
        }
    */
    // 검색을 수행하는 메소드
    public void search(String charText) {
        Log.d("search","start =="+charText);
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        listItems.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            listItems = new ArrayList<>(Arrays.asList(items));        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            int j=0;
            for(int i = 0;i < items.length; i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                Log.d("search",charText);
                if (items[i].toLowerCase().contains(charText.toLowerCase()))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    listItems.add(items[i]);
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter3.notifyDataSetChanged();
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

                Log.d(TAG, "onCreate: " + customerList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return customerList;
        }

        @Override
        protected void onPostExecute(List<CustomerSimpleVO> customerList) {
            super.onPostExecute(customerList);


            Log.d("HttpAsyncTask", customerList.toString());
            //CustomerSimpleAdapter adapter = new CustomerSimpleAdapter(customerList);
            StringBuffer sb = new StringBuffer();
            items = new String[customerList.size()];
            for (int i = 0; i < customerList.size(); i++) {
                items[i] = customerList.get(i).getCustomerNm().toString();
                sb.append(customerList.get(i).getCustomerNm().toString());
                sb.append(",");
            }

            if((buttonType.equals("판매") || buttonType.equals("대여") || buttonType.equals("회수"))) {
                int cCount = sharedPreferences.getInt("clistCount", 0);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(cCount > 0 || cCount == customerList.size()) isUpdate = false;
                else isUpdate = true;
                //String value = id.getText().toString();
                editor.putString("clist", sb.toString());
                editor.putInt("clistCount",customerList.size());
                editor.commit();

            }
            if(isUpdate) {
                Log.d("isUpdate ture", "ture ");
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
                Log.d(TAG, "response.body().string(): " + result);
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
}
