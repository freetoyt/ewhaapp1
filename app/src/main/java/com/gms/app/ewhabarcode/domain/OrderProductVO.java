package com.gms.app.ewhabarcode.domain;

public class OrderProductVO {

    /** Order_ID			*/
    private Integer orderId;

    /** Order_Product_Seq	*/
    private Integer orderProductSeq;

    /** Product_ID 			*/
    private Integer productId;

    /** Product_Price_Seq 	*/
    private Integer productPriceSeq;

    /** Order_Count      	*/
    private int orderCount;

    /** Order_Product_ETC     	*/
    private String orderProductEtc;

    /** Bottle_Change_YN 	*/
    private String bottleChangeYn;

    /** Bottle_Sale_YN 	*/
    private String bottleSaleYn;

    /** Retrieved_YN 	*/
    private String retrievedYn;

    /** AS_YN 	*/
    private String asYn;

    /** INCOME_YN 	*/
    private String incomeYn="N";

    /** OUT_YN 	*/
    private String outYn ="N";

    /** Order_Amount     	*/
    private double orderAmount;

    /** Order_VAT     	*/
    private double orderVat;

    /** Bottle_ID			*/
    private String bottleId;

    /** Product_Nm 			*/
    private String productNm;

    /** Product_Capa 			*/
    private String productCapa;

    /** GAS_ID 			*/
    private Integer gasId;

    /** GAS_CD 			*/
    private String gasCd;

    /** Bottle_Work_CD 			*/
    private String bottleWorkCd;

    /** 삭제여부  Bottle_Type */
    /** E 공병, F 실병 */
    private String bottleType;

    /* Order_Bottle_Seq*/
    private Integer orderBottleSeq;

    /* Sales_Count*/
    private int salesCount;

    private Integer customerId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderProductSeq() {
        return orderProductSeq;
    }

    public void setOrderProductSeq(Integer orderProductSeq) {
        this.orderProductSeq = orderProductSeq;
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

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderProductEtc() {
        return orderProductEtc;
    }

    public void setOrderProductEtc(String orderProductEtc) {
        this.orderProductEtc = orderProductEtc;
    }

    public String getBottleChangeYn() {
        return bottleChangeYn;
    }

    public void setBottleChangeYn(String bottleChangeYn) {
        this.bottleChangeYn = bottleChangeYn;
    }

    public String getBottleSaleYn() {
        return bottleSaleYn;
    }

    public void setBottleSaleYn(String bottleSaleYn) {
        this.bottleSaleYn = bottleSaleYn;
    }

    public String getRetrievedYn() {
        return retrievedYn;
    }

    public void setRetrievedYn(String retrievedYn) {
        this.retrievedYn = retrievedYn;
    }

    public String getAsYn() {
        return asYn;
    }

    public void setAsYn(String asYn) {
        this.asYn = asYn;
    }

    public String getIncomeYn() {
        return incomeYn;
    }

    public void setIncomeYn(String incomeYn) {
        this.incomeYn = incomeYn;
    }

    public String getOutYn() {
        return outYn;
    }

    public void setOutYn(String outYn) {
        this.outYn = outYn;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getOrderVat() {
        return orderVat;
    }

    public void setOrderVat(double orderVat) {
        this.orderVat = orderVat;
    }

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
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

    public Integer getGasId() {
        return gasId;
    }

    public void setGasId(Integer gasId) {
        this.gasId = gasId;
    }

    public String getGasCd() {
        return gasCd;
    }

    public void setGasCd(String gasCd) {
        this.gasCd = gasCd;
    }

    public String getBottleWorkCd() {
        return bottleWorkCd;
    }

    public void setBottleWorkCd(String bottleWorkCd) {
        this.bottleWorkCd = bottleWorkCd;
    }

    public String getBottleType() {
        return bottleType;
    }

    public void setBottleType(String bottleType) {
        this.bottleType = bottleType;
    }

    public Integer getOrderBottleSeq() {
        return orderBottleSeq;
    }

    public void setOrderBottleSeq(Integer orderBottleSeq) {
        this.orderBottleSeq = orderBottleSeq;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
