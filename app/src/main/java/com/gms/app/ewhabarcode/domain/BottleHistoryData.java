package com.gms.app.ewhabarcode.domain;

public class BottleHistoryData {

    private String tv_customerNm;
    private String tv_bottleWorkNm;
    private String tv_updateDt;

    public BottleHistoryData(String tv_customerNm, String tv_bottleWorkNm, String tv_updateDt) {
        this.tv_customerNm = tv_customerNm;
        this.tv_bottleWorkNm = tv_bottleWorkNm;
        this.tv_updateDt = tv_updateDt;
    }

    public String getTv_customerNm() {
        return tv_customerNm;
    }

    public void setTv_customerNm(String tv_customerNm) {
        this.tv_customerNm = tv_customerNm;
    }

    public String getTv_bottleWorkNm() {
        return tv_bottleWorkNm;
    }

    public void setTv_bottleWorkNm(String tv_bottleWorkNm) {
        this.tv_bottleWorkNm = tv_bottleWorkNm;
    }

    public String getTv_updateDt() {
        return tv_updateDt;
    }

    public void setTv_updateDt(String tv_updateDt) {
        this.tv_updateDt = tv_updateDt;
    }
}
