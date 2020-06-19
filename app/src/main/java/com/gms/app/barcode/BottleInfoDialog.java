package com.gms.app.barcode;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;


public class BottleInfoDialog {
    private Context context;
    private String str_info;


    public BottleInfoDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String str) {
        Log.i("BottleInfoDialog",str);
        str_info = str;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.bottle_info);
        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        final Button okButton = (Button) dlg.findViewById(R.id.okButton);

        final TextView tv_bottleId = (TextView) dlg.findViewById(R.id.tv_bottleId);
        final TextView tv_barCd = (TextView) dlg.findViewById(R.id.tv_barCd);
        final TextView tv_productNm = (TextView) dlg.findViewById(R.id.tv_productNm);
        final TextView tv_bottleCapa = (TextView) dlg.findViewById(R.id.tv_bottleCapa);
        final TextView tv_bottleChargeDt = (TextView) dlg.findViewById(R.id.tv_bottleChargeDt);
        final TextView tv_bottleVolumn = (TextView) dlg.findViewById(R.id.tv_bottleVolumn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });


        try {
            Gson gson = new Gson();

            BottleVO bottle = new BottleVO();
            bottle = (BottleVO) gson.fromJson(str_info, bottle.getClass());

            tv_barCd.setText(bottle.getBottleBarCd());
            tv_bottleId.setText(bottle.getBottleId());
            tv_productNm.setText(bottle.getProductNm());
            tv_bottleCapa.setText(bottle.getBottleCapa());
            tv_bottleChargeDt.setText(bottle.getBottleChargeDt().substring(0,10));
            tv_bottleVolumn.setText(bottle.getBottleVolumn());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
