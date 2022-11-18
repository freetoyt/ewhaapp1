package com.gms.app.ewhabarcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gms.app.ewhabarcode.adapter.MassAdapter;
import com.gms.app.ewhabarcode.adapter.MassData;
import com.gms.app.ewhabarcode.dialog.MassDialog;
import com.gms.app.ewhabarcode.dialog.MassManualDialog;
import com.gms.app.ewhabarcode.domain.BottleVO;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MassActivity extends AppCompatActivity {

    private static final String TAG = "MassActivity";
    private static final String shared = "file";
    private String userId = "";

    private static TextView tv_MassProductCount;

    private Button btn_massProduct, btn_massSale, btn_massRent, btn_massBack, btn_massRentBack,
            btn_massCome, btn_massOut, btn_goBack,btn_mscan,btn_mmanual,btn_info;

    private static ArrayList<MassData> mArrayList;
    private static MassAdapter massAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout titleLayout ;
    private IntentIntegrator qrScan;
    static boolean isScan = false;  //qrScan 여부
    private String host="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);

        host = getString(R.string.host_name);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        final SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        if(uid !=null) userId = uid;
        else
            userId = sharedPreferences.getString("id", "");

        tv_MassProductCount = (TextView)findViewById(R.id.tv_MassProductCount);   // 스캔한 용기 카운트수

        btn_massProduct = (Button)findViewById(R.id.btn_massProduct);       // 상품선택//
        btn_massSale = (Button)findViewById(R.id.btn_massSale);       // 대량판매//
        btn_massRent = (Button)findViewById(R.id.btn_massRent);       // 대량대여//
        btn_massBack = (Button)findViewById(R.id.btn_massBack);     //대량회수
        btn_massRentBack  = (Button)findViewById(R.id.btn_massRentBack);     //대여회수
        btn_mmanual = (Button)findViewById(R.id.btn_mmanual);     //수동입력
        btn_mscan  = (Button)findViewById(R.id.btn_mscan);     //스캔하기
        btn_goBack = (Button)findViewById(R.id.btn_goBack);     //메인으로
        btn_massCome = (Button)findViewById(R.id.btn_massCome);       // 대량입고
        btn_massOut = (Button)findViewById(R.id.btn_massOut);       // 대량출고
        btn_info  = (Button)findViewById(R.id.btn_buyback);     //앱정보

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tv_mass);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = new ArrayList<>();
        massAdapter = new MassAdapter(mArrayList);
        recyclerView.setAdapter(massAdapter);

        titleLayout = (LinearLayout)findViewById(R.id.title_layout);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //IntentIntegrator integrator = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        //qrScan.setCaptureActivity(MainActivity.class);
        //qrScan.initiateScan();

        btn_mscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qrScan.setPrompt("스캔중...");
                qrScan.setBeepEnabled(false);//바코드 인식시 소리
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MassActivity.this, MainActivity.class);
                intent.putExtra("uid",userId);
                MainActivity.isMainActivity = true;
                startActivity(intent);
            }
        });

        btn_massProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MassDialog massDialog = new MassDialog(MassActivity.this,btn_massProduct.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                massDialog.callFunction(mArrayList,massAdapter);
            }
        });

        btn_massSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {

                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massSale.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();

                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        btn_massRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massRent.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();

                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        btn_massBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massBack.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        btn_massRentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massRentBack.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        //수동입력
        btn_mmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MassManualDialog manualDialog = new MassManualDialog(MassActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                manualDialog.callFunction(mArrayList,massAdapter);
            }
        });

        btn_massCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massCome.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        btn_massOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mArrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massOut.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < mArrayList.size(); i++) {
                        if(mArrayList.get(i).getTv_mBottleId().equals(mArrayList.get(i).getTv_mProductCnt()))
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-1,";
                        else
                            tempStr += mArrayList.get(i).getTv_mBottleId() + "-" +mArrayList.get(i).getTv_mProductCnt() +",";
                    }
                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1
                        = new AlertDialog.Builder(MassActivity.this,AlertDialog.THEME_HOLO_DARK);
                builder1 .setTitle("대한특수가스")
                        .setMessage("대한특수가스 공장앱니다")
                        .setPositiveButton("확인", null);
                AlertDialog ad = builder1.create();

                ad.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                String arrStr ="";
                if(mArrayList !=null && mArrayList.size() > 0) {
                    for (int i = 0; i < mArrayList.size(); i++) {
                        MassData mainD = mArrayList.get(i);
                        arrStr += mainD.getTv_mBottleId() + "@";
                    }
                }
                isScan = false;
                Toast.makeText(MassActivity.this, "취소!", Toast.LENGTH_LONG).show();

            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MassActivity.this, "스캔완료!"+result.getContents(), Toast.LENGTH_SHORT).show();
                try {
                    boolean isBeen = false;
                    for(int i = 0; i < mArrayList.size() ; i++){
                        if(mArrayList.get(i).getTv_mBottleId().equals(result.getContents())) isBeen = true;
                    }

                    if(!isBeen) {
                        String url = host + getString(R.string.api_bottleDetail) + "?bottleBarCd=" + result.getContents();//AA315923";

                        // AsyncTask를 통해 HttpURLConnection 수행.
                        NetworkTask networkTask = new NetworkTask(url, null);
                        networkTask.execute();
                        //data를 json으로 변환
                        //JSONObject obj = new JSONObject(result.getContents());
                        //Toast.makeText(MainActivity.this, obj.getString("name"), Toast.LENGTH_LONG).show();
                    }
                    // } catch (JSONException e) {
                } catch (Exception e) {
                    e.printStackTrace();
                }

                qrScan.initiateScan();  //스캔모드 유지
                isScan = true;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    static  void  clearArrayList(){
        mArrayList.clear();
        massAdapter.notifyDataSetChanged();
        tv_MassProductCount.setText("상품 카운트 :  0");
        //iCount=0;
    }
    static public void  setTextBottleCount(){

        tv_MassProductCount.setText("상품 카운트 : "+mArrayList.size());
    }

    static  void  addMassData(BottleVO bottle){

        String host = "http://192.168.0.10/";
        try {
            if(bottle != null && bottle.getBottleBarCd().length() > 0){
                boolean isBeen = false;
                for(int i = 0; i < mArrayList.size() ; i++){
                    if(mArrayList.get(i).getTv_mBottleId().equals(bottle.getBottleBarCd())) isBeen = true;
                }
                if (!isBeen) {
                    MassData massData = new MassData(bottle.getProductNm(), bottle.getBottleBarCd(), bottle.getBottleCapa(),bottle.getBottleBarCd());

                    mArrayList.add(massData);
                    massAdapter.notifyDataSetChanged();

                    //                            iCount++;
                    tv_MassProductCount.setText("상품 카운트 : " + mArrayList.size());
                }
            }
            // } catch (JSONException e) {
        } catch (Exception e) {
            e.printStackTrace();
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
            //Log.d("Mainctivity onPostExecute","s="+s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            String bottleBarCd ="";
            String productNm ="";
            String bottleChargeDt = null;

            try {
                Gson gson = new Gson();
                BottleVO bottle = new BottleVO();
                bottle = (BottleVO) gson.fromJson(s, bottle.getClass());

                if(bottle != null && bottle.getBottleId() !=null && bottle.getBottleId().length() > 0) {

                    bottleBarCd = bottle.getBottleBarCd();
                    if (bottleBarCd != null && !bottleBarCd.equals("null") && bottleBarCd.length() > 5) {

                        productNm = bottle.getProductNm();
                        bottleChargeDt = bottle.getBottleCapa();

                        boolean updateFlag = true;
                        for (int i = 0; i < mArrayList.size(); i++) {
                            Log.d("***************** MainActivity  arrayList.get(i).getTv_mProductCnt() msg",mArrayList.get(i).getTv_mProductCnt());
                            if (mArrayList.get(i).getTv_mBottleId().equals(bottleBarCd))
                                updateFlag = false;
                        }

                        if (updateFlag) {
                            MassData massData = new MassData(productNm, bottleBarCd, bottleChargeDt, bottleBarCd);

                            mArrayList.add(massData);
                            massAdapter.notifyDataSetChanged();

//                            iCount++;
                            tv_MassProductCount.setText("상품 카운트 : " + mArrayList.size());

                            Toast.makeText(MassActivity.this, bottleBarCd+"를1 등록했습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MassActivity.this, "MassActivity 등록된 바코드입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MassActivity.this, "시스템에 등록되지 않은 바코드입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MassActivity.this, "시스템에 등록되지 않은 바코드입니다.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MassActivity.this, "서버 상태를 확인하세요", Toast.LENGTH_SHORT).show();
            }
        }
    }
}