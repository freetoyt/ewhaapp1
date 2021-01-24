package com.gms.app.barcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gms.app.barcode.adapter.MassAdapter;
import com.gms.app.barcode.adapter.MassData;
import com.gms.app.barcode.dialog.MassDialog;

import java.util.ArrayList;

public class MassActivity extends AppCompatActivity {

    private static final String TAG = "MassActivity";
    private static final String shared = "file";
    private String userId = "";

    private static TextView tv_MassProductCount;

    private Button btn_massProduct, btn_massSale, btn_massBack, btn_goBack;

    private static ArrayList<MassData> arrayList;
    private static MassAdapter massAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout titleLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        final SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        if(uid !=null) userId = uid;
        else
            userId = sharedPreferences.getString("id", "");

        tv_MassProductCount = (TextView)findViewById(R.id.tv_MassProductCount);   // 스캔한 용기 카운트수

        btn_massProduct = (Button)findViewById(R.id.btn_massProduct);       // 상품선택//
        btn_massSale = (Button)findViewById(R.id.btn_massSale);       // 대량판매//
        btn_massBack = (Button)findViewById(R.id.btn_back);     //대량회수
        btn_goBack = (Button)findViewById(R.id.btn_goBack);     //메인으로

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tv_mass);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        massAdapter = new MassAdapter(arrayList);
        recyclerView.setAdapter(massAdapter);

        titleLayout = (LinearLayout)findViewById(R.id.title_layout);

        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MassActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

        btn_massProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MassDialog massDialog = new MassDialog(MassActivity.this,btn_massSale.getText().toString());

                // 커스텀 다이얼로그를 호출한다.
                massDialog.callFunction(arrayList,massAdapter);
            }
        });

        btn_massSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massSale.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_mBottleId() + "-" +arrayList.get(i).getTv_mProductCnt() +",";
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

                if(arrayList.size() <= 0){
                    Toast.makeText(MassActivity.this, "상품을 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    MassActionDialog massActionDialog = new MassActionDialog(MassActivity.this, btn_massBack.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_mBottleId() + "-" +arrayList.get(i).getTv_mProductCnt() +",";
                    }
                    // 커스텀 다이얼로그를 호출한다.
                    massActionDialog.callFunction(tempStr, userId);

                }
            }
        });

    }

    static  void  clearArrayList(){
        arrayList.clear();
        massAdapter.notifyDataSetChanged();
        tv_MassProductCount.setText("상품 카운트 :  0");
        //iCount=0;
    }
    static public void  setTextBottleCount(){

        tv_MassProductCount.setText("상품 카운트 : "+arrayList.size());
    }
}