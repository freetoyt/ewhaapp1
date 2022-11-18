package com.gms.app.ewhabarcode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    private Context context;
    private String bottles;
    private String shared = "file";
    private String userId = "";

    private static ArrayList<MainData> arrayList;

    private Button  btn_come, btn_incar, btn_out, btn_freechange,btn_freeback, btn_buyback, btn_salesBack,btn_deleteAll,btn_info;


    public BottomSheetDialog(Context context, String bottles) {
        this.context = context;
        this.bottles = bottles;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        //SharedPreferences 로그인 정보 유무 확인
        final SharedPreferences sharedPreferences = context.getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");
//        Toast.makeText(context, "용기를 선택하세요 userId= "+userId, Toast.LENGTH_SHORT).show();
        btn_incar = v.findViewById(R.id.btn_incar);
        btn_come = v.findViewById(R.id.btn_come);
        btn_out = v.findViewById(R.id.btn_out);
        btn_freechange = v.findViewById(R.id.btn_freechange);
        btn_freeback  = v.findViewById(R.id.btn_freeback);
        btn_buyback  = v.findViewById(R.id.btn_buyback);
        btn_salesBack = v.findViewById(R.id.btn_salesBack);
        btn_deleteAll  = v.findViewById(R.id.btn_deleteAll);
        btn_info  = v.findViewById(R.id.btn_info);

        arrayList = MainActivity.getArrayList();

        btn_incar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() <= 0){
                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    CustomDialog customDialog = new CustomDialog(context, btn_incar.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();
                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                    dismiss();
                }
            }
        });

        btn_come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(arrayList.size() <= 0){
                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    CustomDialog customDialog = new CustomDialog(context, btn_come.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();
                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                    dismiss();
                }

            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() <= 0){
                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    CustomDialog customDialog = new CustomDialog(context, btn_out.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();
                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                    dismiss();
                }
            }
        });

        btn_freechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() <= 0){
                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    CustomDialog customDialog = new CustomDialog(context, btn_freechange.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }
                    //Toast.makeText(MainActivity.this, tempStr, Toast.LENGTH_SHORT).show();
                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                    dismiss();
                }
            }
        });



        btn_freeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(context, btn_freeback.getText().toString());

                String tempStr = "";
                for (int i = 0; i < arrayList.size(); i++) {
                    tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                }
                // 커스텀 다이얼로그를 호출한다.
                customDialog.callFunction(tempStr, userId);
                dismiss();
            }
        });

        btn_buyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(context, btn_buyback.getText().toString());

                String tempStr = "";
                for (int i = 0; i < arrayList.size(); i++) {
                    tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                }

                // 커스텀 다이얼로그를 호출한다.
                customDialog.callFunction(tempStr, userId);
                dismiss();
            }
        });

        btn_salesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() <= 0){
                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_SHORT).show();
                }else {
                    CustomDialog customDialog = new CustomDialog(context,  btn_salesBack.getText().toString());

                    String tempStr = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
                    }

                    // 커스텀 다이얼로그를 호출한다.
                    customDialog.callFunction(tempStr, userId);
                    dismiss();
                }
            }
        });

        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() > 0 ){

                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    ad.setMessage("리스트를 삭제하시겠습니까?");

                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(context, "리스트를 삭제하였습니다", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "삭제할 용기목록이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

//        btn_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(arrayList.size() <= 0){
//                    Toast.makeText(context, "용기를 선택하세요", Toast.LENGTH_LONG).show();
//                }else {
//
//                    String tempStr = "";
//                    for (int i = 0; i < arrayList.size(); i++) {
//                        tempStr += arrayList.get(i).getTv_bottleBarCd() + ",";
//                    }
//
//                    // 하단 창 띄우기
//                    AsSheetDialog asSheet = new AsSheetDialog(context,tempStr);
//                    asSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//
//                }
//            }
//        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1
                        = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
                builder1 .setTitle("대한특수가스")
                        .setMessage("대한특수가스 영업용앱니다")
                        .setPositiveButton("확인", null);
                AlertDialog ad = builder1.create();

                ad.show();
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
