package com.gms.app.barcode.adapter;

public class MassData {

    private String tv_mProductNm;
    private String tv_mBottleId;
    private String tv_mProductCapa;
    private String tv_mProductCnt;

    public MassData(String tv_mProductNm, String tv_mBottleId, String tv_mProductCapa, String tv_mProductCnt) {
        this.tv_mProductNm = tv_mProductNm;
        this.tv_mBottleId = tv_mBottleId;
        this.tv_mProductCapa = tv_mProductCapa;
        this.tv_mProductCnt = tv_mProductCnt;
    }

    public String getTv_mProductNm() {
        return tv_mProductNm;
    }

    public void setTv_mProductNm(String tv_mProductNm) {
        this.tv_mProductNm = tv_mProductNm;
    }

    public String getTv_mBottleId() {
        return tv_mBottleId;
    }

    public void setTv_mBottleId(String tv_mBottleId) {
        this.tv_mBottleId = tv_mBottleId;
    }

    public String getTv_mProductCapa() {
        return tv_mProductCapa;
    }

    public void setTv_mProductCapa(String tv_mProductCapa) {
        this.tv_mProductCapa = tv_mProductCapa;
    }

    public String getTv_mProductCnt() {
        return tv_mProductCnt;
    }

    public void setTv_mProductCnt(String tv_mProductCnt) {
        this.tv_mProductCnt = tv_mProductCnt;
    }
}
