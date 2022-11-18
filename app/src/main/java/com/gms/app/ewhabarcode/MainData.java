package com.gms.app.ewhabarcode;

import android.widget.Button;

public class MainData {

    private String tv_bottleId;
    private String tv_bottleBarCd;
    private String tv_productNm;
    private String tv_chargedt;
    private Button btn_info;


    public MainData(String tv_bottleId, String tv_bottleBarCd, String tv_productNm, String tv_chargedt, Button btn_info) {
        this.tv_bottleId = tv_bottleId;
        this.tv_bottleBarCd = tv_bottleBarCd;
        this.tv_productNm = tv_productNm;
        this.tv_chargedt = tv_chargedt;
        this.btn_info = btn_info;
    }

    public String getTv_bottleId() {
        return tv_bottleId;
    }

    public void setTv_bottleId(String tv_bottleId) {
        this.tv_bottleId = tv_bottleId;
    }

    public Button getBtn_info() {
        return btn_info;
    }

    public void setBtn_info(Button btn_info) {
        this.btn_info = btn_info;
    }

    public String getTv_bottleBarCd() {
        return tv_bottleBarCd;
    }

    public String getTv_productNm() {
        return tv_productNm;
    }

    public void setTv_productNm(String tv_productNm) {
        this.tv_productNm = tv_productNm;
    }

    public void setTv_bottleBarCd(String tv_bottleBarCd) {
        this.tv_bottleBarCd = tv_bottleBarCd;
    }

    public String getTv_chargedt() {
        return tv_chargedt;
    }

    public void setTv_chargedt(String tv_chargedt) {
        this.tv_chargedt = tv_chargedt;
    }
}
