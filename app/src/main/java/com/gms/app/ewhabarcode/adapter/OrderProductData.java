package com.gms.app.ewhabarcode.adapter;

import android.widget.Button;

public class OrderProductData {

    private String tv_productNm;
    private Integer tv_productId;
    private String tv_productCapa;
    private Integer tv_productPriceSeq;
    private String tv_orderCount;
    private String tv_orderProductEtc;
    private String tv_bottleChangeYnText;
    private String tv_bottleChangeYn;
    private String tv_bottleSaleYnText;
    private String tv_bottleSaleYn;
    private String tv_retrievedYnText;
    private String tv_retrievedYn;
    private String tv_asYnText;
    private String tv_asYn;
    private String tv_incomeYnText;
    private String tv_incomeYn;
    private String tv_outYnText;
    private String tv_outYn;
    private double tv_price;
    private String btn_delYn;
    private Button btn_info;
    private int salesCount;

    public OrderProductData(String tv_productNm, Integer tv_productId, String tv_productCapa, Integer tv_productPriceSeq, String tv_orderCount, String tv_orderProductEtc, String tv_bottleChangeYnText, String tv_bottleChangeYn, String tv_bottleSaleYnText, String tv_bottleSaleYn, String tv_retrievedYnText, String tv_retrievedYn, String tv_asYnText, String tv_asYn, String tv_incomeYnText, String tv_incomeYn, String tv_outYnText, String tv_outYn, double tv_price, String btn_delYn, int salesCount) {
        this.tv_productNm = tv_productNm;
        this.tv_productId = tv_productId;
        this.tv_productCapa = tv_productCapa;
        this.tv_productPriceSeq = tv_productPriceSeq;
        this.tv_orderCount = tv_orderCount;
        this.tv_orderProductEtc = tv_orderProductEtc;
        this.tv_bottleChangeYnText = tv_bottleChangeYnText;
        this.tv_bottleChangeYn = tv_bottleChangeYn;
        this.tv_bottleSaleYnText = tv_bottleSaleYnText;
        this.tv_bottleSaleYn = tv_bottleSaleYn;
        this.tv_retrievedYnText = tv_retrievedYnText;
        this.tv_retrievedYn = tv_retrievedYn;
        this.tv_asYnText = tv_asYnText;
        this.tv_asYn = tv_asYn;
        this.tv_incomeYnText = tv_incomeYnText;
        this.tv_incomeYn = tv_incomeYn;
        this.tv_outYnText = tv_outYnText;
        this.tv_outYn = tv_outYn;
        this.tv_price = tv_price;
        this.btn_delYn = btn_delYn;
        this.salesCount = salesCount;
    }

    public OrderProductData(String tv_productNm, Integer tv_productId, String tv_productCapa, Integer tv_productPriceSeq, String tv_orderCount, String tv_orderProductEtc, String tv_bottleChangeYnText, String tv_bottleChangeYn, String tv_bottleSaleYnText, String tv_bottleSaleYn, String tv_retrievedYnText, String tv_retrievedYn, String tv_asYnText, String tv_asYn, String tv_incomeYnText, String tv_incomeYn, String tv_outYnText, String tv_outYn, double tv_price, String btn_delYn, Button btn_info, int salesCount) {
        this.tv_productNm = tv_productNm;
        this.tv_productId = tv_productId;
        this.tv_productCapa = tv_productCapa;
        this.tv_productPriceSeq = tv_productPriceSeq;
        this.tv_orderCount = tv_orderCount;
        this.tv_orderProductEtc = tv_orderProductEtc;
        this.tv_bottleChangeYnText = tv_bottleChangeYnText;
        this.tv_bottleChangeYn = tv_bottleChangeYn;
        this.tv_bottleSaleYnText = tv_bottleSaleYnText;
        this.tv_bottleSaleYn = tv_bottleSaleYn;
        this.tv_retrievedYnText = tv_retrievedYnText;
        this.tv_retrievedYn = tv_retrievedYn;
        this.tv_asYnText = tv_asYnText;
        this.tv_asYn = tv_asYn;
        this.tv_incomeYnText = tv_incomeYnText;
        this.tv_incomeYn = tv_incomeYn;
        this.tv_outYnText = tv_outYnText;
        this.tv_outYn = tv_outYn;
        this.tv_price = tv_price;
        this.btn_delYn = btn_delYn;
        this.btn_info = btn_info;
        this.salesCount = salesCount;
    }

//    public OrderProductData(String tv_productNm, Integer tv_productId, String tv_productCapa, Integer tv_productPriceSeq, String tv_orderCount, String tv_orderProductEtc, String tv_bottleChangeYnText, String tv_bottleChangeYn, String tv_bottleSaleYnText, String tv_bottleSaleYn, String tv_retrievedYnText, String tv_retrievedYn, String tv_asYnText, String tv_asYn, double tv_price, String btn_delYn, int salesCount) {
//        this.tv_productNm = tv_productNm;
//        this.tv_productId = tv_productId;
//        this.tv_productCapa = tv_productCapa;
//        this.tv_productPriceSeq = tv_productPriceSeq;
//        this.tv_orderCount = tv_orderCount;
//        this.tv_orderProductEtc = tv_orderProductEtc;
//        this.tv_bottleChangeYnText = tv_bottleChangeYnText;
//        this.tv_bottleChangeYn = tv_bottleChangeYn;
//        this.tv_bottleSaleYnText = tv_bottleSaleYnText;
//        this.tv_bottleSaleYn = tv_bottleSaleYn;
//        this.tv_retrievedYnText = tv_retrievedYnText;
//        this.tv_retrievedYn = tv_retrievedYn;
//        this.tv_asYnText = tv_asYnText;
//        this.tv_asYn = tv_asYn;
//        this.tv_price = tv_price;
//        this.btn_delYn = btn_delYn;
//        this.salesCount = salesCount;
//    }
//
//    public OrderProductData(String tv_productNm, Integer tv_productId, String tv_productCapa, Integer tv_productPriceSeq, String tv_orderCount, String tv_orderProductEtc, String tv_bottleChangeYnText, String tv_bottleChangeYn, String tv_bottleSaleYnText, String tv_bottleSaleYn, String tv_retrievedYnText, String tv_retrievedYn, String tv_asYnText, String tv_asYn, double tv_price, String btn_delYn, Button btn_info, int salesCount) {
//        this.tv_productNm = tv_productNm;
//        this.tv_productId = tv_productId;
//        this.tv_productCapa = tv_productCapa;
//        this.tv_productPriceSeq = tv_productPriceSeq;
//        this.tv_orderCount = tv_orderCount;
//        this.tv_orderProductEtc = tv_orderProductEtc;
//        this.tv_bottleChangeYnText = tv_bottleChangeYnText;
//        this.tv_bottleChangeYn = tv_bottleChangeYn;
//        this.tv_bottleSaleYnText = tv_bottleSaleYnText;
//        this.tv_bottleSaleYn = tv_bottleSaleYn;
//        this.tv_retrievedYnText = tv_retrievedYnText;
//        this.tv_retrievedYn = tv_retrievedYn;
//        this.tv_asYnText = tv_asYnText;
//        this.tv_asYn = tv_asYn;
//        this.tv_price = tv_price;
//        this.btn_delYn = btn_delYn;
//        this.btn_info = btn_info;
//        this.salesCount = salesCount;
//    }

    public String getTv_productNm() {
        return tv_productNm;
    }

    public void setTv_productNm(String tv_productNm) {
        this.tv_productNm = tv_productNm;
    }

    public Integer getTv_productId() {
        return tv_productId;
    }

    public void setTv_productId(Integer tv_productId) {
        this.tv_productId = tv_productId;
    }

    public String getTv_productCapa() {
        return tv_productCapa;
    }

    public void setTv_productCapa(String tv_productCapa) {
        this.tv_productCapa = tv_productCapa;
    }

    public Integer getTv_productPriceSeq() {
        return tv_productPriceSeq;
    }

    public void setTv_productPriceSeq(Integer tv_productPriceSeq) {
        this.tv_productPriceSeq = tv_productPriceSeq;
    }

    public String getTv_orderCount() {
        return tv_orderCount;
    }

    public void setTv_orderCount(String tv_orderCount) {
        this.tv_orderCount = tv_orderCount;
    }

    public String getTv_orderProductEtc() {
        return tv_orderProductEtc;
    }

    public void setTv_orderProductEtc(String tv_orderProductEtc) {
        this.tv_orderProductEtc = tv_orderProductEtc;
    }

    public String getTv_bottleChangeYnText() {
        return tv_bottleChangeYnText;
    }

    public void setTv_bottleChangeYnText(String tv_bottleChangeYnText) {
        this.tv_bottleChangeYnText = tv_bottleChangeYnText;
    }

    public String getTv_bottleChangeYn() {
        return tv_bottleChangeYn;
    }

    public void setTv_bottleChangeYn(String tv_bottleChangeYn) {
        this.tv_bottleChangeYn = tv_bottleChangeYn;
    }

    public String getTv_bottleSaleYnText() {
        return tv_bottleSaleYnText;
    }

    public void setTv_bottleSaleYnText(String tv_bottleSaleYnText) {
        this.tv_bottleSaleYnText = tv_bottleSaleYnText;
    }

    public String getTv_bottleSaleYn() {
        return tv_bottleSaleYn;
    }

    public void setTv_bottleSaleYn(String tv_bottleSaleYn) {
        this.tv_bottleSaleYn = tv_bottleSaleYn;
    }

    public String getTv_retrievedYnText() {
        return tv_retrievedYnText;
    }

    public void setTv_retrievedYnText(String tv_retrievedYnText) {
        this.tv_retrievedYnText = tv_retrievedYnText;
    }

    public String getTv_retrievedYn() {
        return tv_retrievedYn;
    }

    public void setTv_retrievedYn(String tv_retrievedYn) {
        this.tv_retrievedYn = tv_retrievedYn;
    }

    public String getTv_asYnText() {
        return tv_asYnText;
    }

    public void setTv_asYnText(String tv_asYnText) {
        this.tv_asYnText = tv_asYnText;
    }

    public String getTv_asYn() {
        return tv_asYn;
    }

    public void setTv_asYn(String tv_asYn) {
        this.tv_asYn = tv_asYn;
    }

    public String getTv_incomeYnText() {
        return tv_incomeYnText;
    }

    public void setTv_incomeYnText(String tv_incomeYnText) {
        this.tv_incomeYnText = tv_incomeYnText;
    }

    public String getTv_incomeYn() {
        return tv_incomeYn;
    }

    public void setTv_incomeYn(String tv_incomeYn) {
        this.tv_incomeYn = tv_incomeYn;
    }

    public String getTv_outYnText() {
        return tv_outYnText;
    }

    public void setTv_outYnText(String tv_outYnText) {
        this.tv_outYnText = tv_outYnText;
    }

    public String getTv_outYn() {
        return tv_outYn;
    }

    public void setTv_outYn(String tv_outYn) {
        this.tv_outYn = tv_outYn;
    }

    public String getBtn_delYn() {
        return btn_delYn;
    }

    public void setBtn_delYn(String btn_delYn) {
        this.btn_delYn = btn_delYn;
    }

    public double getTv_price() {
        return tv_price;
    }

    public void setTv_price(double tv_price) {
        this.tv_price = tv_price;
    }

    public Button getBtn_info() {
        return btn_info;
    }

    public void setBtn_info(Button btn_info) {
        this.btn_info = btn_info;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }
}
