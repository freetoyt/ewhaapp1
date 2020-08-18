package com.gms.app.barcode.adapter;

public class ReportData {

    private String tv_reportCustomerNm;
    private String tv_reportProductNm;
    private String tv_reportProductCapa;
    private String tv_reportBottleWork;
    private int tv_reportProductCount;

    public ReportData(String tv_reportCustomerNm, String tv_reportProductNm, String tv_reportProductCapa, String tv_reportBottleWork, int tv_reportProductCount) {
        this.tv_reportCustomerNm = tv_reportCustomerNm;
        this.tv_reportProductNm = tv_reportProductNm;
        this.tv_reportProductCapa = tv_reportProductCapa;
        this.tv_reportBottleWork = tv_reportBottleWork;
        this.tv_reportProductCount = tv_reportProductCount;
    }

    public String getTv_reportCustomerNm() {
        return tv_reportCustomerNm;
    }

    public void setTv_reportCustomerNm(String tv_reportCustomerNm) {
        this.tv_reportCustomerNm = tv_reportCustomerNm;
    }

    public String getTv_reportProductNm() {
        return tv_reportProductNm;
    }

    public void setTv_reportProductNm(String tv_reportProductNm) {
        this.tv_reportProductNm = tv_reportProductNm;
    }

    public String getTv_reportProductCapa() {
        return tv_reportProductCapa;
    }

    public void setTv_reportProductCapa(String tv_reportProductCapa) {
        this.tv_reportProductCapa = tv_reportProductCapa;
    }

    public String getTv_reportBottleWork() {
        return tv_reportBottleWork;
    }

    public void setTv_reportBottleWork(String tv_reportBottleWork) {
        this.tv_reportBottleWork = tv_reportBottleWork;
    }

    public int getTv_reportProductCount() {
        return tv_reportProductCount;
    }

    public void setTv_reportProductCount(int tv_reportProductCount) {
        this.tv_reportProductCount = tv_reportProductCount;
    }
}
