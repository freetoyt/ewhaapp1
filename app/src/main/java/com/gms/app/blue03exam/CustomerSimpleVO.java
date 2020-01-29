package com.gms.app.blue03exam;


public class CustomerSimpleVO {
    /** Customer_ID       */
    private String customerId;

    /** Customer_Nm       */
    private String customerNm;

    public CustomerSimpleVO(String customerId, String customerNm) {
        this.customerId = customerId;
        this.customerNm = customerNm;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("customerId='").append(customerId).append('\'');
        sb.append(", customerNm='").append(customerNm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
