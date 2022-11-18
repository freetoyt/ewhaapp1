package com.gms.app.ewhabarcode.domain;

public class WorkBottleVO {
    /**Work_Report_Seq      */
    private Integer workReportSeq;

    /**Work_Seq      */
    private int workSeq;

    /** Customer_Nm		*/
    private String customerNm;

    /** Bottle_Work_CD	*/
	/*
	0301	입고
	0302	진공배기
	0303	누출확인
	0304	충전기한확인
	0305	출고
	0306	상차
	0307	판매
	0308	회수
	0309	폐기
	*/
    private String bottleWorkCd;

    /** Product_ID 			*/
    private Integer productId;

    /**Product_Nm */
    private String productNm;

    /** Product_Price_Seq */
    private Integer productPriceSeq;

    /** Product_Capa     */
    private String productCapa;

    private String bottleWorkCdNm;


    /** Order_Total_Amount     	*/
    private double orderTotalAmount;

    private int productCount;

    public Integer getWorkReportSeq() {
        return workReportSeq;
    }

    public void setWorkReportSeq(Integer workReportSeq) {
        this.workReportSeq = workReportSeq;
    }

    public int getWorkSeq() {
        return workSeq;
    }

    public void setWorkSeq(int workSeq) {
        this.workSeq = workSeq;
    }

    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    public String getBottleWorkCd() {
        return bottleWorkCd;
    }

    public void setBottleWorkCd(String bottleWorkCd) {
        this.bottleWorkCd = bottleWorkCd;
    }

    public String getProductNm() {
        return productNm;
    }

    public void setProductNm(String productNm) {
        this.productNm = productNm;
    }

    public String getProductCapa() {
        return productCapa;
    }

    public void setProductCapa(String productCapa) {
        this.productCapa = productCapa;
    }

    public String getBottleWorkCdNm() {
        return bottleWorkCdNm;
    }

    public void setBottleWorkCdNm(String bottleWorkCdNm) {
        this.bottleWorkCdNm = bottleWorkCdNm;
    }

    public double getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(double orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductPriceSeq() {
        return productPriceSeq;
    }

    public void setProductPriceSeq(Integer productPriceSeq) {
        this.productPriceSeq = productPriceSeq;
    }
}
