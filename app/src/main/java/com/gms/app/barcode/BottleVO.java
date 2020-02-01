package com.gms.app.barcode;

import java.util.Date;

public class BottleVO {
    /** Bottle_ID		*/
    private String bottleId;

    /** Bottle_ID		*/
    private String chBottleId;

    /** Bottle_BarCd	*/
    private String bottleBarCd;

    /** Product_ID */
    private Integer productId;

    /** Product Nm */
    private String productNm ;

    /** Product_Price_Seq */
    private Integer productPriceSeq;

    /** Gas ID */
    private Integer gasId ;

    /** Gas Nm */
    private String gasNm ;

    /** Bottle_Capa	*/
    private String bottleCapa;

    /** Bottle_Create_Dt	*/
    private Date bottleCreateDt;

    /** Bottle_Charge_Dt	*/
    private String bottleChargeDt;

    /** Bottle_Volumn	*/
    private String bottleVolumn;

    /** Bottle_Charge_Prss	*/
    private String bottleChargePrss;

    /** Bottle_Sales_YN	*/
    private String bottleOwnYn;

    /** Bottle_Work_CD	*/
    /*
     * 0300	용기작업코드
     * 0301	입고
     * 0302	진공배기
     * 0303	누출확인
     * 0304	충전기한확인
     * 0305	충전
     * 0306	출고
     * 0307	상차
     * 0308	판매
     * 0309	대여
     * 0310	회수
     * 0311	기타
     * 0388	신규
     * 0399	폐기
     */
    private String bottleWorkCd;

    private String bottleWorkCdNm;

    /** Bottle_Work_ID	*/
    private String bottleWorkId;

    /** Bottle_Work_Dt	*/
    private Date bottleWorkDt;

    /** Customer_ID		*/
    private Integer customerId;

    /** Customer_Nm		*/
    private String customerNm;


    /** Member_Comp_Seq	*/
    private Integer memberCompSeq;

    /** Order_ID		*/
    private Integer orderId;

    /** Order_Product_Seq	*/
    private Integer orderProductSeq;

    /** 삭제여부  Bottle_Type */
    /** E 공병, F 실병 */
    private String bottleType;

    /** 삭제여부  Delete_YN*/
    private String deleteYn;

    /** 복수 삭제용 Ids*/
    private String bottleIds;

    /** 검색용			*/
    /** SearchBottleiId	*/
    private String  searchBottleId;

    /** SearchGasId		*/
    private String  searchGasId;

    /** SearchProductId		*/
    private String  searchProductId;

    /** searchChargeDt	*/
    private String  searchChargeDt;

    /** searchCustomerNm	*/
    private String  searchCustomerNm;

    /** searchChargeDtFrom	*/
    private String  searchChargeDtFrom;

    /** searchChargeDtEnd	*/
    private String  searchChargeDtEnd;

    /** searchChargeDtEnd	*/
    private String  searchSalesYn;

    /** menuType
     * 1:list
     * 2:charge
     * 3:sales
     * 4.:rental
     * 	*/
    private int  menuType;


    /** Car Customer_ID		*/
    private String carCustomerId;

    private boolean success;

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getChBottleId() {
        return chBottleId;
    }

    public void setChBottleId(String chBottleId) {
        this.chBottleId = chBottleId;
    }

    public String getBottleBarCd() {
        return bottleBarCd;
    }

    public void setBottleBarCd(String bottleBarCd) {
        this.bottleBarCd = bottleBarCd;
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

    public Integer getGasId() {
        return gasId;
    }

    public void setGasId(Integer gasId) {
        this.gasId = gasId;
    }

    public String getGasNm() {
        return gasNm;
    }

    public void setGasNm(String gasNm) {
        this.gasNm = gasNm;
    }

    public String getBottleCapa() {
        return bottleCapa;
    }

    public void setBottleCapa(String bottleCapa) {
        this.bottleCapa = bottleCapa;
    }

    public Date getBottleCreateDt() {
        return bottleCreateDt;
    }

    public void setBottleCreateDt(Date bottleCreateDt) {
        this.bottleCreateDt = bottleCreateDt;
    }

    public String getBottleChargeDt() {
        return bottleChargeDt;
    }

    public void setBottleChargeDt(String bottleChargeDt) {
        this.bottleChargeDt = bottleChargeDt;
    }

    public String getBottleVolumn() {
        return bottleVolumn;
    }

    public void setBottleVolumn(String bottleVolumn) {
        this.bottleVolumn = bottleVolumn;
    }

    public String getBottleChargePrss() {
        return bottleChargePrss;
    }

    public void setBottleChargePrss(String bottleChargePrss) {
        this.bottleChargePrss = bottleChargePrss;
    }

    public String getBottleOwnYn() {
        return bottleOwnYn;
    }

    public void setBottleOwnYn(String bottleOwnYn) {
        this.bottleOwnYn = bottleOwnYn;
    }

    public String getBottleWorkCd() {
        return bottleWorkCd;
    }

    public void setBottleWorkCd(String bottleWorkCd) {
        this.bottleWorkCd = bottleWorkCd;
    }

    public String getBottleWorkCdNm() {
        return bottleWorkCdNm;
    }

    public void setBottleWorkCdNm(String bottleWorkCdNm) {
        this.bottleWorkCdNm = bottleWorkCdNm;
    }

    public String getBottleWorkId() {
        return bottleWorkId;
    }

    public void setBottleWorkId(String bottleWorkId) {
        this.bottleWorkId = bottleWorkId;
    }

    public Date getBottleWorkDt() {
        return bottleWorkDt;
    }

    public void setBottleWorkDt(Date bottleWorkDt) {
        this.bottleWorkDt = bottleWorkDt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    public Integer getMemberCompSeq() {
        return memberCompSeq;
    }

    public void setMemberCompSeq(Integer memberCompSeq) {
        this.memberCompSeq = memberCompSeq;
    }

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

    public String getBottleType() {
        return bottleType;
    }

    public void setBottleType(String bottleType) {
        this.bottleType = bottleType;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }

    public String getBottleIds() {
        return bottleIds;
    }

    public void setBottleIds(String bottleIds) {
        this.bottleIds = bottleIds;
    }

    public String getSearchBottleId() {
        return searchBottleId;
    }

    public void setSearchBottleId(String searchBottleId) {
        this.searchBottleId = searchBottleId;
    }

    public String getSearchGasId() {
        return searchGasId;
    }

    public void setSearchGasId(String searchGasId) {
        this.searchGasId = searchGasId;
    }

    public String getSearchProductId() {
        return searchProductId;
    }

    public void setSearchProductId(String searchProductId) {
        this.searchProductId = searchProductId;
    }

    public String getSearchChargeDt() {
        return searchChargeDt;
    }

    public void setSearchChargeDt(String searchChargeDt) {
        this.searchChargeDt = searchChargeDt;
    }

    public String getSearchCustomerNm() {
        return searchCustomerNm;
    }

    public void setSearchCustomerNm(String searchCustomerNm) {
        this.searchCustomerNm = searchCustomerNm;
    }

    public String getSearchChargeDtFrom() {
        return searchChargeDtFrom;
    }

    public void setSearchChargeDtFrom(String searchChargeDtFrom) {
        this.searchChargeDtFrom = searchChargeDtFrom;
    }

    public String getSearchChargeDtEnd() {
        return searchChargeDtEnd;
    }

    public void setSearchChargeDtEnd(String searchChargeDtEnd) {
        this.searchChargeDtEnd = searchChargeDtEnd;
    }

    public String getSearchSalesYn() {
        return searchSalesYn;
    }

    public void setSearchSalesYn(String searchSalesYn) {
        this.searchSalesYn = searchSalesYn;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getCarCustomerId() {
        return carCustomerId;
    }

    public void setCarCustomerId(String carCustomerId) {
        this.carCustomerId = carCustomerId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
