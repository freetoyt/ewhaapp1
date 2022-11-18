package com.gms.app.ewhabarcode.dialog;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gms.app.ewhabarcode.MainActivity;
import com.gms.app.ewhabarcode.MainAdapter;
import com.gms.app.ewhabarcode.MainData;
import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.RequestHttpURLConnection;
import com.gms.app.ewhabarcode.domain.BottleVO;
import com.gms.app.ewhabarcode.domain.ProductPriceSimpleVO;
import com.gms.app.ewhabarcode.domain.SimpleBottleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DummyDialog {

    private Context context;
    MainData mainData;

    private static ArrayList<MainData> arrayList;
    private static MainAdapter mainAdapter;

    SharedPreferences sharedPreferences ;
    private String shared = "file";
    boolean isUpdate = true;
    private Spinner spinner, spinner1;

    List<SimpleBottleVO> productList = new ArrayList<>();
    List<String> spinnerArray =  new ArrayList<String>();
    List<String> arrayProductList = new ArrayList<String>();

    String productType = "";
    String buttonType = "";
    String host ="";

    String productNm= "";
    String prdouctCapa = "";


    public DummyDialog(Context context, String bType) {
        this.context = context;
        this.buttonType = bType;

        sharedPreferences = context.getSharedPreferences(shared, 0);
        host = context.getString(R.string.host_name);

        new HttpAsyncTask2().execute(host + context.getString(R.string.api_getDummyBottle));
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction( ArrayList<MainData> arrayList1, MainAdapter mainAdapter1  ){
        arrayList = arrayList1;
        mainAdapter = mainAdapter1;
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dummy_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        title.setText(buttonType);
        // Add Data to listView

        spinner = (Spinner)dlg.findViewById(R.id.spinner);
        spinner1 = (Spinner)dlg.findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                productNm = parent.getItemAtPosition(position).toString();
                String pValue= "";

                for(int i = 0 ; i < arrayProductList.size() ; i++){
//                    Log.d("DummyDialog","productNm="+productNm+"==pValue="+pValue);
//                    Log.d("MassDialog","size="+arrayProductList.get(i)+" position="+position+"---"+arrayProductList.get(position));
                    if(i==position) pValue = arrayProductList.get(position);
                }

                String item[] = pValue.split(";");
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, item);

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prdouctCapa = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_BarCd = "";
                for (int i = 0; i < productList.size(); i++) {
                    SimpleBottleVO sBottle = productList.get(i);
                    if(productNm.equals(sBottle.getProductNm()) && prdouctCapa.equals(sBottle.getBottleCapa())){
                        str_BarCd = sBottle.getBottleBarCd();
                    }
                }

                ArrayList<String > tempList = new ArrayList<>();
                for(int j=4; j > 0 ; j--){
                    tempList.add(str_BarCd+"_"+j);
                }
                for (int i = 0; i < arrayList.size(); i++) {
//                    Log.d("arrayList.get(i).getTv_bottleBarCd()",arrayList.get(i).getTv_bottleBarCd());
                    if (arrayList.get(i).getTv_bottleBarCd().equals(str_BarCd)){
                        for(int j=tempList.size()-1; j > -1 ; j--){
                            if (arrayList.get(i).getTv_bottleBarCd().equals(tempList.get(j))){
                                tempList.remove(j);
                            }
                        }
                        if(tempList.size() > 0){
                            str_BarCd = tempList.get(tempList.size()-1);
                        }
                    }
                }
                //MainActivity List 등록
                //MainActivity.insertList(str_BarCd);
                String url =context.getString(R.string.host_name)+context.getString(R.string.api_bottleDetail)+"?bottleBarCd="+str_BarCd;//AA315923";

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

                // 커스텀 다이얼로그를.종료한다.
                dlg.dismiss();

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
                //Log.d(TAG, "response.body().string(): " + result);
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
    private class HttpAsyncTask2 extends AsyncTask<String, Void, List<SimpleBottleVO>> {
        private final String TAG = HttpAsyncTask2.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected List<SimpleBottleVO> doInBackground(String... params) {

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
                productList = gson.fromJson(response.body().string(), listType);

                //Log.d(TAG, "onCreate: " + productList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return productList;
        }

        @Override
        protected void onPostExecute(List<SimpleBottleVO> productList) {
            super.onPostExecute(productList);

            List<ProductPriceSimpleVO> pdList1 = new ArrayList<ProductPriceSimpleVO>();

            for (int i = 0; i < productList.size(); i++) {
                ProductPriceSimpleVO simpleVO = new ProductPriceSimpleVO();
                simpleVO.setProductId(productList.get(i).getProductId());
                simpleVO.setProductNm(productList.get(i).getProductNm());
                if(i==0) {
                    pdList1.add(simpleVO);
                }

                if(i > 0 && !productList.get(i).getProductId().equals(productList.get(i-1).getProductId() )){
                    pdList1.add(simpleVO);
                }
            }
            String[] capaArray = new String[pdList1.size()];
            for (int i = 0; i < pdList1.size(); i++) {
                capaArray[i] ="";
                for (int j = 0; j<  productList.size() ; j++) {
                    if(pdList1.get(i).getProductId().equals(productList.get(j).getProductId())){
                        capaArray[i] = capaArray[i]+productList.get(j).getBottleCapa()+";";
                    }
                }
                spinnerArray.add(pdList1.get(i).getProductNm());
                arrayProductList.add(capaArray[i]);
            }

//            for (int i = 0; i < pdList1.size(); i++) {
//                spinnerArray.add(pdList1.get(i).getProductNm());
//                arrayProductList.add(capaArray[i]);
//            }

//            for (int i = 0; i < productList.size(); i++) {
//                if(i==0) {
//                    spinnerArray.add(productList.get(i).getProductNm());
//
//                    sb.append(productList.get(i).getBottleCapa()+";");
//                }
//
//                if(i > 0 && productList.get(i).getProductId() > productList.get(i-1).getProductId()) {
//                    spinnerArray.add(productList.get(i).getProductNm());
////                    Log.d("MassDialog  ","###sb.toString()="+sb.toString());
////                    Log.d("###MassDialog  ","###productList.get(i).getProductNm()="+productList.get(i).getProductNm());
//                    arrayProductList.add(sb.toString());
//
//                    sb = new StringBuffer();
//                    sb.append(productList.get(i).getBottleCapa()+";");
//
//                }else if (i > 0 && productList.get(i).getProductId() == productList.get(i-1).getProductId()) {
////                    Log.d("###MassDialog1  ","else sb.toString()="+sb.toString());
//                    sb.append(productList.get(i).getBottleCapa()+";");
//                }
//                if(i==productList.size()-1)  {
//                    sb.append(productList.get(i).getBottleCapa()+";");
////                    Log.d("###MassDialog1  ","111sb.toString()="+sb.toString());
////                    Log.d("###MassDialog2  ","111productList.get(i).getProductNm()="+productList.get(i).getProductNm());
//                    arrayProductList.add(productList.get(i).getBottleCapa());
//                }
//            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }
    }



    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
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

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_result.setText(s);
            String bottleBarCd="";
            String bottleId="";
            String productNm="";
            String bottleChargeDt = "";

            try {
                Gson gson = new Gson();
                BottleVO bottle = new BottleVO();
                bottle = (BottleVO) gson.fromJson(s, bottle.getClass());

                if(bottle != null && bottle.getBottleId().length() > 0) {

                    bottleId = bottle.getBottleId();
                    bottleBarCd = bottle.getBottleBarCd();
                    productNm = bottle.getProductNm();
                    productNm = bottle.getProductNm();
                    bottleChargeDt = bottle.getMenuType() + "일";

                    if (bottleBarCd != null && !bottleBarCd.equals("null") && bottleBarCd.length() > 5) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(shared, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(bottleBarCd, s);
                        editor.commit();

                        boolean updateFlag = true;
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getTv_bottleBarCd().equals(bottleBarCd))
                                updateFlag = false;
                        }

                        if (updateFlag) {
                            //tv_result.setText(bottleBarCd+" "+s);
                            mainData = new MainData(bottleId, bottleBarCd, productNm, bottleChargeDt, null);
                            //tv_result.setText(bottleBarCd+" "+s);
                            arrayList.add(mainData);
                            mainAdapter.notifyDataSetChanged();
                            MainActivity.setTextBottleCount();
                        } else {
                            Toast.makeText(context, "등록된 바코드입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "등록되지 않은 바코드입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
