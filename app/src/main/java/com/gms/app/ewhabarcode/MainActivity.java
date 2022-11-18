package com.gms.app.ewhabarcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.app.ewhabarcode.dialog.CashDialog;
import com.gms.app.ewhabarcode.dialog.DummyDialog;
import com.gms.app.ewhabarcode.dialog.ManualDialog;
import com.gms.app.ewhabarcode.dialog.NoGasDialog;
import com.gms.app.ewhabarcode.dialog.StockReportDialog;
import com.gms.app.ewhabarcode.dialog.TankDialog;
import com.gms.app.ewhabarcode.domain.BottleVO;
import com.gms.app.ewhabarcode.domain.CustomerSimpleVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_ENABLE_BT = 3;
    public BluetoothAdapter mBluetoothAdapter = null;
    Set<BluetoothDevice> mDevices;
    int mPairedDeviceCount;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket;
    InputStream mInputStream;
    OutputStream mOutputStream;
    Thread mWorkerThread;
    int readBufferPositon;      //버퍼 내 수신 문자 저장 위치
    byte[] readBuffer;      //수신 버퍼
    byte mDelimiter = 10;

    private TextView tv_result;
    private static TextView tv_bottleCount;
    private int readBufferPosition; // 버퍼 내 문자 저장 위치

    private Button btn_logout, btn_setting,
            btn_rental, btn_back, btn_sales, btn_ln2,
            btn_noGas, btn_mass, btn_money, btn_rentB,
            btn_dummy, btn_tank, btn_order, btn_report,
            btn_scan, btn_manual, btn_history, btn_etc;

    private TextView main_label;
    private int REQUEST_TEST = 1;
    private static ArrayList<MainData> arrayList;
    private static MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private static final String shared = "file";
    private String userId = "";
    private String previousBottles = "";
    private String host = "";
    private String version = "";
    private static final boolean closeB = true;

    //qr code scanner object
    private IntentIntegrator qrScan;
    int tempInd = 0;
    static int iCount = 0;
    private LinearLayout titleLayout;

    String[] items;
    static boolean connectBlue = false; // bluetooth 연결 확인
    static boolean isScan = false;  //qrScan 여부
    static boolean isMainActivity = true;

    static SharedPreferences sharedPreferences = null;

    private static String appVersion = "";
    private static String appVersionCheckDate = "";
    private long versionCode = 0;
    private static boolean isGetC = false;

    @Override
    protected void onPause() {
        super.onPause();

        String arrStr = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                MainData mainD = arrayList.get(i);
                arrStr += mainD.getTv_bottleBarCd() + "@";
            }
            //SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("notSaveArray", arrStr);
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isMainActivity = true;
//        Log.d("MainActivity", "onResume");
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //WIFI에 연결됨
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                //LTE(이동통신망)에 연결됨
            }
        } else {
            // 연결되지않음
            AlertDialog.Builder builder1
                    = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_DARK);
            builder1.setTitle("대한특수가스")
                    .setMessage("인터넷이 연결되지 않았습니다 ")
                    .setPositiveButton("확인", null);
            AlertDialog ad = builder1.create();

            ad.show();
        }
        if (!isScan) {
            //SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
            String arrStr = sharedPreferences.getString("notSaveArray", "");

            if (arrStr != null && arrStr.length() > 1) {
                clearArrayList();
                String[] aCode = arrStr.split("@");
                Button btn_info = findViewById(R.id.btn_buyback);

                for (int i = 0; i < aCode.length; i++) {

                    // 저장된 용기 정보 불러오기
                    Gson gson = new Gson();
                    String sharedValue = sharedPreferences.getString(aCode[i], "");

                    if (sharedValue != null && sharedValue.length() > 10) {
                        BottleVO bottle = new BottleVO();

                        bottle = (BottleVO) gson.fromJson(sharedValue, bottle.getClass());

                        if (bottle != null && bottle.getBottleBarCd() != null) {
                            MainData mainData = new MainData(bottle.getBottleId(), bottle.getBottleBarCd(), bottle.getProductNm(), bottle.getMenuType() + "일", btn_info);
                            arrayList.add(mainData);
                        }
                    }
                }
                mainAdapter.notifyDataSetChanged();
                tv_bottleCount.setText("바코드 카운트 :  " + arrayList.size());
            }
        }
        if (checkCustomerData(sharedPreferences) && !isGetC)
            new HttpAsyncTask().execute(host + getString(R.string.api_customerList));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host = getString(R.string.host_name);
//        Log.d("MainActivity", "onCreate");
        isMainActivity = true;
        // 버전 정보 가져오기
        PackageInfo packageInfo = null;

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            if (packageInfo != null) {
                version = packageInfo.versionName;
                /*
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    versionCode = packageInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
                } else {
                    versionCode = packageInfo.versionCode;
                }
                */
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Package Version", "NameNotFoundException");
        } catch (Exception ex) {
            version = "1.0.0";
        }

        // user_id 가져오기 0601 추가
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        //SharedPreferences 로그인 정보 유무 확인
        sharedPreferences = getSharedPreferences(shared, 0);
        userId = sharedPreferences.getString("id", "");

        if (userId == null || userId.length() <= 0) userId = uid;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        //String value = id.getText().toString();
        editor.putString("id", uid);
        editor.commit();

        // 사용자 정보 체크
        if (userId == null || userId.length() <= 0) {
            Toast.makeText(MainActivity.this, "사용자 정보가 없습니다.로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            editor = sharedPreferences.edit();

            editor.clear();
            editor.commit();

            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent1);
        }

        // 네트웍 상태체크
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //WIFI에 연결됨
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                //LTE(이동통신망)에 연결됨
            }
        } else {
            // 연결되지않음
            AlertDialog.Builder builder1
                    = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_DARK);
            builder1.setTitle("대한특수가스")
                    .setMessage("인터넷이 연결되지 않았습니다 ")
                    .setPositiveButton("확인", null);
            AlertDialog ad = builder1.create();

            ad.show();
        }
        Log.d("MainActivity", "Build.VERSION.SDK_INT =" + Build.VERSION.SDK_INT);
        Log.d("MainActivity", "Build.VERSION_CODES.S =" + Build.VERSION_CODES.S);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("MainActivity", "this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) =" + this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN));
            Log.d("MainActivity", "PackageManager.PERMISSION_GRANTED =" + PackageManager.PERMISSION_GRANTED);
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 블루투스를 감지 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    }
                });

                builder.show();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 블루투스를 연결 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 3);
                    }
                });
                builder.show();
            }
        }
        // 앱버전 체크
        /*
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
        Date currentTime = new Date ( );
        String dTime = formatter.format ( currentTime );
*/
        //if(!dTime.equals(appVersionCheckDate)) {
        /* 20210212
            String appVersionUrl = host + getString(R.string.api_appVersion);

            // AsyncTask를 통해 HttpURLConnection 수행.
            AppNetworkTask appNetworkTask = new AppNetworkTask(appVersionUrl, null);
            appNetworkTask.execute();
        //s}
        */
        // 거래처목록 가져오기
        String value = sharedPreferences.getString("clist", "");

        if (value == null || value.length() <= 10 || checkCustomerData(sharedPreferences)) {
            isGetC = true;
            new HttpAsyncTask().execute(host + getString(R.string.api_customerList));
        }
        tv_bottleCount = (TextView) findViewById(R.id.tv_bottleCount);   // 스캔한 용기 카운트수
        tv_bottleCount.setText("바코드 카운트 : " + iCount);

        btn_logout = (Button) findViewById(R.id.btn_logout);     //로그아웃
        btn_setting = (Button) findViewById(R.id.btn_setting);     //블루투스 연결

        btn_rental = (Button) findViewById(R.id.btn_rental);         // 대여
        btn_back = (Button) findViewById(R.id.btn_back);       // 회수
        btn_sales = (Button) findViewById(R.id.btn_sales);       // 판매
        btn_ln2 = (Button) findViewById(R.id.btn_ln2);       // LN2

        btn_noGas = (Button) findViewById(R.id.btn_noGas);       // 단품판매
        btn_mass = (Button) findViewById(R.id.btn_mass);     //대량
        btn_money = (Button) findViewById(R.id.btn_money);       // 입금/미수수
        btn_rentB = (Button) findViewById(R.id.btn_rentB);       // 대여용기

        btn_dummy = (Button) findViewById(R.id.btn_dummy);       // 미등록용기
        btn_tank = (Button) findViewById(R.id.btn_tank);     //Tank
        btn_order = (Button) findViewById(R.id.btn_buyback);         //주문
        btn_report = (Button) findViewById(R.id.btn_report);       // 업무일지

        btn_scan = (Button) findViewById(R.id.btn_scan);         // 스캔하기
        btn_manual = (Button) findViewById(R.id.btn_manual);       // 수동등록
        btn_history = (Button) findViewById(R.id.btn_history);       // 이전목록
        btn_etc = (Button) findViewById(R.id.btn_etc);        // 기타

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);   // topbottom
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        titleLayout = (LinearLayout) findViewById(R.id.title_layout);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //IntentIntegrator integrator = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        //qrScan.setCaptureActivity(MainActivity.class);
        //qrScan.initiateScan();

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qrScan.setPrompt("스캔중...");
                qrScan.setBeepEnabled(false);//바코드 인식시 소리
                qrScan.setOrientationLocked(true); //20210212(화면전환안되게)
                qrScan.initiateScan();
            }
        });


        main_label = (TextView) findViewById(R.id.main_label);

        btn_rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size() <= 0) {
                    Toast.makeText(MainActivity.this, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, "대여");

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }

                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size() <= 0) {
                    Toast.makeText(MainActivity.this, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, btn_back.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }

                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                }
            }
        });

        btn_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList.size() <= 0) {
                    Toast.makeText(MainActivity.this, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, "판매");

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }

                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                }
            }
        });

        btn_ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoGasDialog noGasDialog = new NoGasDialog(MainActivity.this, btn_ln2.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                noGasDialog.callFunction(userId);
            }
        });

        //단매판매
        btn_noGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoGasDialog noGasDialog = new NoGasDialog(MainActivity.this, btn_noGas.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                noGasDialog.callFunction(userId);
            }
        });

        btn_mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MassActivity.class);
                intent.putExtra("uid", userId);
                isMainActivity = false;
                startActivity(intent);
            }
        });

        btn_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 하단 창 띄우기
                CashDialog cash = new CashDialog(MainActivity.this);
                cash.callFunction(userId);
            }
        });

        btn_rentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockReportDialog customDialog = new StockReportDialog(MainActivity.this, btn_rentB.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                customDialog.callFunction();
            }
        });

        btn_dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DummyDialog dummyDialog = new DummyDialog(MainActivity.this, btn_dummy.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                dummyDialog.callFunction(arrayList, mainAdapter);

            }
        });

        btn_tank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TankDialog tankDialog = new TankDialog(MainActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                tankDialog.callFunction(userId);
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                intent.putExtra("uid", userId);
                isMainActivity = false;
                startActivity(intent);
            }
        });

//        btn_as.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(arrayList.size() <= 0){
//                    Toast.makeText(MainActivity.this, "용기를 선택하세요", Toast.LENGTH_LONG).show();
//                }else {
//
//                    String tempStr = "";
//                    for (int i = 0; i < arrayList.size(); i++) {
//                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
//                    }
//
//                    // 하단 창 띄우기
//                    AsSheetDialog asSheet = new AsSheetDialog(MainActivity.this,tempStr);
//                    asSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//
//                }
//            }
//        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                // user_id 가져오기 0601 추가
                intent.putExtra("uid", userId);
                startActivity(intent);
            }
        });

        //수동입력
        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ManualDialog manualDialog = new ManualDialog(MainActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                manualDialog.callFunction(arrayList, mainAdapter);
            }
        });


        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousBottles = sharedPreferences.getString("previousBottles", "");
                //Log.d("previousBottles", previousBottles);

                if (previousBottles != null && previousBottles.length() > 0) {
                    String[] aCode = previousBottles.split(",");
                    Button btn_info = findViewById(R.id.btn_buyback);

                    for (int i = 0; i < aCode.length; i++) {
                        //Log.d("previousBottles", "aCode " + aCode[i]);

                        // 저장된 용기 정보 불러오기
                        Gson gson = new Gson();
                        String sharedValue = sharedPreferences.getString(aCode[i], "");

                        BottleVO bottle = new BottleVO();
                        bottle = (BottleVO) gson.fromJson(sharedValue, bottle.getClass());
                        boolean updateFlag = true;
                        for (int j = 0; j < arrayList.size(); j++) {
                            if (arrayList.get(j).getTv_bottleBarCd().equals(bottle.getBottleBarCd()))
                                updateFlag = false;
                        }
                        if (updateFlag) {
                            MainData mainData = new MainData(bottle.getBottleId(), bottle.getBottleBarCd(), bottle.getProductNm(), bottle.getMenuType() + "일", btn_info);
                            arrayList.add(mainData);
                        }
                    }
                    mainAdapter.notifyDataSetChanged();
                    tv_bottleCount.setText("바코드 카운트 :  " + arrayList.size());
                } else {
                    Toast.makeText(MainActivity.this, "이전 작업 내역이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList.size() <= 0) {
                    Toast.makeText(MainActivity.this, "용기를 선택하세요", Toast.LENGTH_LONG).show();
                } else {

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }

                    // 하단 창 띄우기
                    BottomSheetDialog bottomSheet = new BottomSheetDialog(MainActivity.this, tempStr);
                    bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

                }
            }
        });
        /*
        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() > 0 ){

                    AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                    ad.setMessage("리스트를 삭제하시겠습니까?");

                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(MainActivity.this, "리스트를 삭제하였습니다", Toast.LENGTH_SHORT).show();
                            MainActivity.clearArrayList();
                            dialog.dismiss();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("notSaveArray");
                            editor.commit();
                        }
                    });

                    ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }else{
                    Toast.makeText(MainActivity.this, "삭제할 용기목록이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences 로그인 정보 유무 확인
                //SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //String value = id.getText().toString();
                editor.clear();
                editor.commit();

                try {
                    if (mSocket != null)
                        mSocket.close();
                } catch (IOException e) {

                }
                Toast.makeText(MainActivity.this, "로그아웃 되었습니다,", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDevice();
            }
        });

        //에뮬레이터 구동시 주석처리 필요
        if (!connectBlue)
            CheckBluetooth();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("디버깅", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 제한");
                    builder.setMessage("위치 정보 및 액세스 권한이 허용되지 않았으므로 블루투스를 검색 및 연결할수 없습니다.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                break;
            }
            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("디버깅", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 제한");
                    builder.setMessage("블루투스 스캔권한이 허용되지 않았습니다.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                break;
            }
            case 3: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("디버깅", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 제한");
                    builder.setMessage("블루투스 연결 권한이 허용되지 않았습니다.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                break;
            }
        }
        return;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Log.d("onWindowFocusChangee==========","btn_setting.width = " + btn_setting.getWidth() + "\nmain_label.width = " + main_label.getWidth());
        ViewGroup.LayoutParams params = main_label.getLayoutParams();
        params.width = titleLayout.getWidth() - btn_setting.getWidth() * 4;

        main_label.setLayoutParams(params);
    }


    @Override
    public void onBackPressed() {
        //Toast.makeText(this,"한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);

        ad.setMessage("앱(V" + version + ")을 종료하시겠습니까?");

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                moveTaskToBack(MainActivity.closeB);
                finish();
                Process.killProcess(Process.myPid());
                dialog.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "--");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                String arrStr = "";
                if (arrayList != null && arrayList.size() > 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        MainData mainD = arrayList.get(i);
                        arrStr += mainD.getTv_bottleBarCd() + "@";
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("notSaveArray", arrStr);
                    editor.commit();
                }
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
                isScan = false;
            } else {
                //qrcode 결과가 있으면
                //Toast.makeText(MainActivity.this, "스캔완료!"+result.getContents(), Toast.LENGTH_SHORT).show();
                try {
                    boolean isBeen = false;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getTv_bottleBarCd().equals(result.getContents()))
                            isBeen = true;
                    }

                    if (!isBeen) {
                        String url = host + getString(R.string.api_bottleDetail) + "?bottleBarCd=" + result.getContents();//AA315923";

                        // AsyncTask를 통해 HttpURLConnection 수행.
                        NetworkTask networkTask = new NetworkTask(url, null);
                        networkTask.execute();
                        //data를 json으로 변환
                        JSONObject obj = new JSONObject(result.getContents());
                        //Toast.makeText(MainActivity.this, obj.getString("name"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                qrScan.initiateScan();  //스캔모드 유지
                isScan = true;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onButtonClicked(String text) {
        //super(text);
        //mTextView.setText(text);
    }


    // 이하 Bluetooth 연동 소스
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void CheckBluetooth() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // 장치가 블루투스 지원하지 않는 경우
            Toast.makeText(MainActivity.this, "Bluetooth를 이용할수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 장치가 블루투스 지원하는 경우
            if (!mBluetoothAdapter.isEnabled()) {
                // 블루투스를 지원하지만 비활성 상태인 경우
                // 블루투스를 활성 상태로 바꾸기 위해 사용자 동의 요첨
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("권한 제한");
//                    builder.setMessage("블루투스 연결 권한이 허용되지 않았습니다.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                    return;
//                }
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                // 블루투스를 지원하며 활성 상태인 경우
                // 페어링된 기기 목록을 보여주고 연결할 장치를 선택.

                selectDevice();
            }
        }
    }

    private void selectDevice() {
        //페어링되었던 기기 목록 획득

        // Use this check to determine whether Bluetooth classic is supported on the device.
// Then you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, "지원하지 않습니다", Toast.LENGTH_SHORT).show();
            finish();
        }
// Use this check to determine whether BLE is supported on the device. Then
// you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "지원하지 않습니다", Toast.LENGTH_SHORT).show();
            finish();
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//           // return;
//        }
        mDevices = mBluetoothAdapter.getBondedDevices();
        //페어링되었던 기기 갯수
        mPairedDeviceCount = mDevices.size();
        //Alertdialog 생성(activity에는 context입력)
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //AlertDialog 제목 설정
        builder.setTitle("기기를 선택해주세요");

        // 페어링 된 블루투스 장치의 이름 목록 작성
        final List<String> listItems = new ArrayList<String>();
        for (BluetoothDevice device : mDevices) {
            listItems.add(device.getName());
        }
        if (listItems.size() == 0) {
            //no bonded device => searching
            //Log.d("Bluetooth", "No bonded device");
        } else {
            // 취소 항목 추가
            listItems.add("Cancel");

            final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                //각 아이템의 click에 따른 listener를 설정
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Dialog dialog_ = (Dialog) dialog;
                    // 연결할 장치를 선택하지 않고 '취소'를 누른 경우
                    if (which == listItems.size() - 1) {
                        Toast.makeText(dialog_.getContext(), "취소를 선택했습니다", Toast.LENGTH_SHORT).show();

                    } else {
                        //취소가 아닌 디바이스를 선택한 경우 해당 기기에 연결
                        connectToSelectedDevice(items[which].toString());
                        connectBlue = true;
                        //Toast.makeText(MainActivity.this, "블루투스가 connectBlue."+connectBlue, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setCancelable(false);    // 뒤로 가기 버튼 사용 금지
            AlertDialog alert = builder.create();
            if (mSocket != null && mSocket.isConnected()) {
                Toast.makeText(MainActivity.this, "블루투스가 연결되었습니다.", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "블루투스가 연결되지 않았습니다", Toast.LENGTH_SHORT).show();
            }
            alert.show();   //alert 시작
        }
    }

    private void connectToSelectedDevice(final String selectedDeviceName) {
        //블루투스 기기에 연결하는 과정이 시간이 걸리기 때문에 그냥 함수로 수행을 하면 GUI에 영향을 미친다
        //따라서 연결 과정을 thread로 수행하고 thread의 수행 결과를 받아 다음 과정으로 넘어간다.

        //handler는 thread에서 던지는 메세지를 보고 다음 동작을 수행시킨다.
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                //Log.i("***************** MainActivity  connectToSelectedDevice msg",msg.toString());
                if (msg.what == 1) // 연결 완료
                {
                    try {

                        //연결이 완료되면 소켓에서 outstream과 inputstream을 얻는다. 블루투스를 통해
                        //데이터를 주고 받는 통로가 된다.
                        //mOutputStream = mSocket.getOutputStream();
                        InputStream tempInput = mSocket.getInputStream();
                        mInputStream = tempInput;

                        // 데이터 수신 준비
                        beginListenForData();
                        //receiveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {    //연결 실패
                    //Toast.makeText(MainActivity.this ,"장치를 확인해주세요", Toast.LENGTH_SHORT).show();
                    try {
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //연결과정을 수행할 thread 생성
        Thread thread = new Thread(new Runnable() {
            public void run() {
                //선택된 기기의 이름을 갖는 bluetooth device의 object
                mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
                //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                Log.d("MainActivity", "PackageActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT)  =" + ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) );
                Log.d("MainActivity", "PackageManager.PERMISSION_GRANTED =" + PackageManager.PERMISSION_GRANTED);
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    //;
//                }
                UUID uuid = (mRemoteDevice.getUuids())[0].getUuid();

                try {
                    // 소켓 생성
                    mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);

                    // RFCOMM 채널을 통한 연결, socket에 connect하는데 시간이 걸린다. 따라서 ui에 영향을 주지 않기 위해서는
                    // Thread로 연결 과정을 수행해야 한다.
                    mSocket.connect();
                    mHandler.sendEmptyMessage(1);
                    connectBlue = true;
                } catch (Exception e) {
                    // 블루투스 연결 중 오류 발생
                    mHandler.sendEmptyMessage(-1);
                    //mSocket.close();
                    Log.e("Excception e", e.getMessage());
                }
            }
        });

        //연결 thread를 수행한다
        thread.start();
    }

    //기기에 저장되어 있는 해당 이름을 갖는 블루투스 디바이스의 bluetoothdevice 객채를 출력하는 함수
    //bluetoothdevice객채는 기기의 맥주소뿐만 아니라 다양한 정보를 저장하고 있다.

    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
        mDevices = mBluetoothAdapter.getBondedDevices();
        //pair 목록에서 해당 이름을 갖는 기기 검색, 찾으면 해당 device 출력
        for (BluetoothDevice device : mDevices) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }

    //블루투스 데이터 수신 Listener
    protected void beginListenForData() {
        final Handler handler = new Handler();
        readBuffer = new byte[1024];  //  수신 버퍼
        readBufferPositon = 0;        //   버퍼 내 수신 문자 저장 위치
        //Log.i("***************** MainActivity  beginListenForData msg","start");
        // 문자열 수신 쓰레드
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted()) {

                    try {
                        int bytesAvailable = 0;
                        if(mInputStream!=null)
                            bytesAvailable = mInputStream.available();

                        if (bytesAvailable > 0) { //데이터가 수신된 경우
                            connectBlue = true;
                            byte[] packetBytes = new byte[bytesAvailable];
                            //mInputStream.read(packetBytes);
                            int bytesInt = mInputStream.read(packetBytes);
                            String readMessage = new String(packetBytes, 0, bytesInt);

                            if(readMessage.indexOf("\u0001") > 0){
                                readMessage = readMessage.substring(readMessage.indexOf("\u0001")+1,readMessage.length());
                            }
                            if(readMessage.indexOf("\u0003") > 0){
                                readMessage = readMessage.substring(0,readMessage.indexOf("\u0003"));
                            }

                            if(readMessage !=null && readMessage.length() > 0) {

                                //String url = host + "api/bottleDetail.do?bottleBarCd=" + readMessage.substring(5, readMessage.length());//AA315923";
                                String url = host + getString(R.string.api_bottleDetail)+"?bottleBarCd=" + readMessage;//AA315923";
                                // AsyncTask를 통해 HttpURLConnection 수행.
                                NetworkTask networkTask = new NetworkTask(url, null);
                                networkTask.execute();
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //데이터 수신 thread 시작
        mWorkerThread.start();
    }

    static public  void  clearArrayList(){
        arrayList.clear();
        mainAdapter.notifyDataSetChanged();
        tv_bottleCount.setText("바코드 카운트 :  0");
        iCount = 0;
    }

    static  ArrayList<MainData>  getArrayList(){
        return arrayList;
    }

    static public void  setTextBottleCount(){

        tv_bottleCount.setText("바코드 카운트 : "+arrayList.size());
    }

    public void SendResetSignal(){
        String msg = "bs00000";
        try {
            mOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCustomerData(SharedPreferences sharedPreferences){
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String toDate = df.format(today);

        String getDate = sharedPreferences.getString("cdate", "");
        //Log.d(TAG, "checkCustomerData: " + getDate+ "==toDate: "+toDate);
        if(getDate != null && getDate.equals(toDate)){
            return false;
        }else
            return true;
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
            String bottleBarCd ="";
            String bottleId ="";
            String productNm ="";
            String bottleChargeDt = null;
            Button btn_info = findViewById(R.id.btn_buyback);
            try {
                Gson gson = new Gson();
                BottleVO bottle = new BottleVO();
                bottle = (BottleVO) gson.fromJson(s, bottle.getClass());
                if(bottle != null && bottle.getBottleBarCd() !=null && bottle.getBottleBarCd().length() > 5){
                    if(!isMainActivity) {

                        MassActivity.addMassData(bottle);
                    }else{

                        bottleId = bottle.getBottleId();
                        bottleBarCd = bottle.getBottleBarCd();
                        productNm = bottle.getProductNm();
                        bottleChargeDt = bottle.getMenuType() + "일";

                        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(bottleBarCd, s);
                        editor.commit();

                        boolean updateFlag = true;
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getTv_bottleBarCd().equals(bottleBarCd))
                                updateFlag = false;
                        }

                        if (updateFlag) {
                            MainData mainData = new MainData(bottleId, bottleBarCd, productNm, bottleChargeDt, btn_info);

                            arrayList.add(mainData);
                            mainAdapter.notifyDataSetChanged();
                            iCount++;
                            tv_bottleCount.setText("바코드 카운트 : " + arrayList.size());
                            Toast.makeText(MainActivity.this, bottleBarCd+"를 등록했습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "등록된 바코드입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "시스템에 등록되지 않은 바코드입니다.", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "서버 상태를 확인하세요", Toast.LENGTH_SHORT).show();
            }
        }
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
            //SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Date today = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String toDate = df.format(today);

            editor.putString("clist", sb.toString());
            editor.putString("cdate", toDate);
            editor.commit();
            isGetC = false;
        }
    }

    public class AppNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public AppNetworkTask(String url, ContentValues values) {

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

            try {
                if(s != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
                    Date currentTime = new Date ( );
                    appVersionCheckDate = formatter.format ( currentTime );

                    appVersion = s;
                    long iAppVersion = Long.parseLong(appVersion);
                    versionCode = Long.parseLong(getString(R.string.VersionCode));
                    if (iAppVersion > versionCode) {

                        // Linkify the message
                        //final SpannableString spans = new SpannableString(Html.fromHtml("<a href=\"http://www.google.com\">Check this link out</a>")); // msg should have url to enable clicking
                        //Linkify.addLinks(spans, Linkify.ALL);

                        AlertDialog.Builder builder2
                                = new AlertDialog.Builder(MainActivity.this,AlertDialog.THEME_HOLO_DARK);
                        builder2 .setTitle("대한특수가스")
                                .setMessage("새로운 버전의 앱이 있습니다. \n앱을 업데이트 해주세요")
                                .setPositiveButton("확인",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                moveTaskToBack(MainActivity.closeB);
                                                finish();
                                                Process.killProcess(Process.myPid());
                                                dialog.dismiss();
                                            }
                                        });
                        AlertDialog ad = builder2.create();

                        ad.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
