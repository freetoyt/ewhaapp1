package com.gms.app.ewhabarcode.adapter;

import android.widget.Button;

public class OrderInfoData {

    private String tv_orderCustomerNm;
    private String tv_orderProductNm;
    private Integer tv_orderId;
    private String tv_orderProductCapa;
    private double tv_orderTotalAmount;
    private String tv_createId;
    private String tv_createNm;
    private String tv_orderProcessCd;
    private String tv_orderDeliveryDt;
    private String tv_customerCity;
    private String tv_orderEtc;
    private Button btn_delOrder;

    public OrderInfoData(String tv_orderCustomerNm, String tv_orderProductNm, Integer tv_orderId, String tv_orderProductCapa, double tv_orderTotalAmount, String tv_createId, String tv_createNm, String tv_orderProcessCd, String tv_orderDeliveryDt, String tv_customerCity, String tv_orderEtc) {
        this.tv_orderCustomerNm = tv_orderCustomerNm;
        this.tv_orderProductNm = tv_orderProductNm;
        this.tv_orderId = tv_orderId;
        this.tv_orderProductCapa = tv_orderProductCapa;
        this.tv_orderTotalAmount = tv_orderTotalAmount;
        this.tv_createId = tv_createId;
        this.tv_createNm = tv_createNm;
        this.tv_orderProcessCd = tv_orderProcessCd;
        this.tv_orderDeliveryDt = tv_orderDeliveryDt;
        this.tv_customerCity = tv_customerCity;
        this.tv_orderEtc = tv_orderEtc;
    }

    public OrderInfoData(String tv_orderCustomerNm, String tv_orderProductNm, Integer tv_orderId, String tv_orderProductCapa, double tv_orderTotalAmount, String tv_createId, String tv_createNm, String tv_orderProcessCd, String tv_orderDeliveryDt, String tv_customerCity, String tv_orderEtc, Button btn_delOrder) {
        this.tv_orderCustomerNm = tv_orderCustomerNm;
        this.tv_orderProductNm = tv_orderProductNm;
        this.tv_orderId = tv_orderId;
        this.tv_orderProductCapa = tv_orderProductCapa;
        this.tv_orderTotalAmount = tv_orderTotalAmount;
        this.tv_createId = tv_createId;
        this.tv_createNm = tv_createNm;
        this.tv_orderProcessCd = tv_orderProcessCd;
        this.tv_orderDeliveryDt = tv_orderDeliveryDt;
        this.tv_customerCity = tv_customerCity;
        this.tv_orderEtc = tv_orderEtc;
        this.btn_delOrder = btn_delOrder;
    }

    public String getTv_orderCustomerNm() {
        return tv_orderCustomerNm;
    }

    public void setTv_orderCustomerNm(String tv_orderCustomerNm) {
        this.tv_orderCustomerNm = tv_orderCustomerNm;
    }

    public String getTv_orderProductNm() {
        return tv_orderProductNm;
    }

    public void setTv_orderProductNm(String tv_orderProductNm) {
        this.tv_orderProductNm = tv_orderProductNm;
    }

    public Integer getTv_orderId() {
        return tv_orderId;
    }

    public void setTv_orderId(Integer tv_orderId) {
        this.tv_orderId = tv_orderId;
    }

    public String getTv_orderProductCapa() {
        return tv_orderProductCapa;
    }

    public void setTv_orderProductCapa(String tv_orderProductCapa) {
        this.tv_orderProductCapa = tv_orderProductCapa;
    }

    public double getTv_orderTotalAmount() {
        return tv_orderTotalAmount;
    }

    public void setTv_orderTotalAmount(double tv_orderTotalAmount) {
        this.tv_orderTotalAmount = tv_orderTotalAmount;
    }

    public String getTv_createId() {
        return tv_createId;
    }

    public void setTv_createId(String tv_createId) {
        this.tv_createId = tv_createId;
    }

    public String getTv_createNm() {
        return tv_createNm;
    }

    public void setTv_createNm(String tv_createNm) {
        this.tv_createNm = tv_createNm;
    }

    public String getTv_orderProcessCd() {
        return tv_orderProcessCd;
    }

    public void setTv_orderProcessCd(String tv_orderProcessCd) {
        this.tv_orderProcessCd = tv_orderProcessCd;
    }

    public String getTv_orderDeliveryDt() {
        return tv_orderDeliveryDt;
    }

    public void setTv_orderDeliveryDt(String tv_orderDeliveryDt) {
        this.tv_orderDeliveryDt = tv_orderDeliveryDt;
    }

    public String getTv_orderEtc() {
        return tv_orderEtc;
    }

    public void setTv_orderEtc(String tv_orderEtc) {
        this.tv_orderEtc = tv_orderEtc;
    }

    public String getTv_customerCity() {
        return tv_customerCity;
    }

    public void setTv_customerCity(String tv_customerCity) {
        this.tv_customerCity = tv_customerCity;
    }

    public Button getBtn_delOrder() {
        return btn_delOrder;
    }

    public void setBtn_delOrder(Button btn_delOrder) {
        this.btn_delOrder = btn_delOrder;
    }
}
