package com.gms.app.ewhabarcode.domain;

import java.util.Date;

public class OrderVO {
    /** Order_ID		*/
    private Integer orderId;

    /** Member_Comp_Seq    	*/
    private Integer memberCompSeq;

    /** Customer_ID        	*/
    private Integer customerId;

    /** Customer_ID        	*/
    private String customerNm;

    /** Order_Type_CD
     * 	0101 상품주문
     *	0102 취소주문
     *	0103 자동주문
     *	0104 회수요청
     *	0105 점검요청
     *	0106 기타요청				*/
    private String orderTypeCd;

    /** Delivery_Req_Dt    	*/
    private Date deliveryReqDt;

    /** Delivery_Req_AmPm
     * 	A:오전
     P:오후					*/
    private String deliveryReqAmpm;

    /** Order_Etc          	*/
    private String orderEtc;

    /** Order_Product_Nm       	*/
    private String orderProductNm;

    /** Order_Product_Capa     	*/
    private String orderProductCapa;

    /** Order_Process_CD
     *
     * 0210 주문접수
     * 0220 주문완료
     * 0230 생산완료
     * 0240 납품
     * 0250  판매완료
     * 0260 거래명세서완료
     * 0270 세금계산서완료
     * 0280 입금완료
     * 0290 거래완료
     * 0299 삭제			*/
    private String orderProcessCd;

    /** Order_Process_Nm   	*/
    private String orderProcessCdNm;

    /** Order_Delivery_Dt   	*/
    private String orderDeliveryDt;

    /** Sales_ID           	*/
    private String salesId;

    /** Order_Total_Amount     	*/
    private double orderTotalAmount;

    /** productCount     	*/
    private int productCount;

    /** Create_Id           	*/
    private String createId;

    /** Create_Nm           	*/
    private String createNm;

    /** Customer_City           	*/
    private String customerCity;

    /** 검색용			*/
    /** SearchCustomerNm	*/
    private String  searchCustomerNm;

    /** searchChargeDt	*/
    private String  searchOrderDt;

    /** searchChargeDtFrom	*/
    private String  searchOrderDtFrom;

    /** searchChargeDtEnd	*/
    private String  searchOrderDtEnd;

    /** 상태변경용 chOrderId	*/
    private Integer  chOrderId;

    /** 주문상태 검색	*/
    private String searchOrderProcessCd;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMemberCompSeq() {
        return memberCompSeq;
    }

    public void setMemberCompSeq(Integer memberCompSeq) {
        this.memberCompSeq = memberCompSeq;
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

    public String getOrderTypeCd() {
        return orderTypeCd;
    }

    public void setOrderTypeCd(String orderTypeCd) {
        this.orderTypeCd = orderTypeCd;
    }

    public Date getDeliveryReqDt() {
        return deliveryReqDt;
    }

    public void setDeliveryReqDt(Date deliveryReqDt) {
        this.deliveryReqDt = deliveryReqDt;
    }

    public String getDeliveryReqAmpm() {
        return deliveryReqAmpm;
    }

    public void setDeliveryReqAmpm(String deliveryReqAmpm) {
        this.deliveryReqAmpm = deliveryReqAmpm;
    }

    public String getOrderEtc() {
        return orderEtc;
    }

    public void setOrderEtc(String orderEtc) {
        this.orderEtc = orderEtc;
    }

    public String getOrderProductNm() {
        return orderProductNm;
    }

    public void setOrderProductNm(String orderProductNm) {
        this.orderProductNm = orderProductNm;
    }

    public String getOrderProductCapa() {
        return orderProductCapa;
    }

    public void setOrderProductCapa(String orderProductCapa) {
        this.orderProductCapa = orderProductCapa;
    }

    public String getOrderProcessCd() {
        return orderProcessCd;
    }

    public void setOrderProcessCd(String orderProcessCd) {
        this.orderProcessCd = orderProcessCd;
    }

    public String getOrderProcessCdNm() {
        return orderProcessCdNm;
    }

    public void setOrderProcessCdNm(String orderProcessCdNm) {
        this.orderProcessCdNm = orderProcessCdNm;
    }

    public String getOrderDeliveryDt() {
        return orderDeliveryDt;
    }

    public void setOrderDeliveryDt(String orderDeliveryDt) {
        this.orderDeliveryDt = orderDeliveryDt;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getSearchCustomerNm() {
        return searchCustomerNm;
    }

    public void setSearchCustomerNm(String searchCustomerNm) {
        this.searchCustomerNm = searchCustomerNm;
    }

    public String getSearchOrderDt() {
        return searchOrderDt;
    }

    public void setSearchOrderDt(String searchOrderDt) {
        this.searchOrderDt = searchOrderDt;
    }

    public String getSearchOrderDtFrom() {
        return searchOrderDtFrom;
    }

    public void setSearchOrderDtFrom(String searchOrderDtFrom) {
        this.searchOrderDtFrom = searchOrderDtFrom;
    }

    public String getSearchOrderDtEnd() {
        return searchOrderDtEnd;
    }

    public void setSearchOrderDtEnd(String searchOrderDtEnd) {
        this.searchOrderDtEnd = searchOrderDtEnd;
    }

    public Integer getChOrderId() {
        return chOrderId;
    }

    public void setChOrderId(Integer chOrderId) {
        this.chOrderId = chOrderId;
    }

    public String getSearchOrderProcessCd() {
        return searchOrderProcessCd;
    }

    public void setSearchOrderProcessCd(String searchOrderProcessCd) {
        this.searchOrderProcessCd = searchOrderProcessCd;
    }
}
