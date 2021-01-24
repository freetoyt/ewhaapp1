package com.gms.app.barcode;

import android.app.AlertDialog;
import android.content.Context;
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

    private Button btn_close, btn_freeback, btn_buyback, btn_version;


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

        btn_close = v.findViewById(R.id.btn_close);
        btn_freeback = v.findViewById(R.id.btn_freeback);
        btn_buyback = v.findViewById(R.id.btn_buyback);
        btn_version  = v.findViewById(R.id.btn_version);

        arrayList = MainActivity.getArrayList();



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


        btn_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1
                        = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
                builder1 .setTitle("대한특수가스")
                        .setMessage("대한특수가스 앱니다")
                        .setPositiveButton("확인", null);
                AlertDialog ad = builder1.create();

                ad.show();

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
