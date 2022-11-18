package com.gms.app.ewhabarcode.domain;

public class CustomerProductVO {
    /** Customer_ID       */
    private Integer customerId;

    /** Product_ID */
    private Integer productId;

    /**Product_Nm */
    private String productNm;

    /** Product_Price_Seq */
    private Integer productPriceSeq;

    /** Product_Capa     */
    private String productCapa;

    /**Bottle_Own_Count */
    private String bottleOwnCount;

    /**Bottle_Rent_Count */
    private String bottleRentCount;

    /**Bottle_Count */
    private String bottleCount;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductNm() {
        return productNm;
    }

    public void setProductNm(String productNm) {
        this.productNm = productNm;
    }

    public Integer getProductPriceSeq() {
        return productPriceSeq;
    }

    public void setProductPriceSeq(Integer productPriceSeq) {
        this.productPriceSeq = productPriceSeq;
    }

    public String getProductCapa() {
        return productCapa;
    }

    public void setProductCapa(String productCapa) {
        this.productCapa = productCapa;
    }

    public String getBottleOwnCount() {
        return bottleOwnCount;
    }

    public void setBottleOwnCount(String bottleOwnCount) {
        this.bottleOwnCount = bottleOwnCount;
    }

    public String getBottleRentCount() {
        return bottleRentCount;
    }

    public void setBottleRentCount(String bottleRentCount) {
        this.bottleRentCount = bottleRentCount;
    }

    public String getBottleCount() {
        return bottleCount;
    }

    public void setBottleCount(String bottleCount) {
        this.bottleCount = bottleCount;
    }

    public CustomerProductVO(String productNm, String bottleOwnCount, String bottleRentCount) {
        this.productNm = productNm;
        this.bottleOwnCount = bottleOwnCount;
        this.bottleRentCount = bottleRentCount;
    }

    public CustomerProductVO(String productNm, String bottleCount) {
        this.productNm = productNm;
        this.bottleCount = bottleCount;
    }
}
