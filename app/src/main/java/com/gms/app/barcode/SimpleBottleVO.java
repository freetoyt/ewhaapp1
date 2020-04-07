package com.gms.app.barcode;

import java.io.Serializable;

public class SimpleBottleVO implements Serializable {

    private static final long serialVersionUID = 3324009822300528104L;

    /** Bottle_ID		*/
    private String bottleId;

    /** Bottle_BarCd	*/
    private String bottleBarCd;

    /** Product Nm */
    private String productNm ;

    /** Bottle_Capa	*/
    private String bottleCapa;

    /** Receivable_Amount	*/
    private int receivableAmount;

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public void setBottleBarCd(String bottleBarCd) {
        this.bottleBarCd = bottleBarCd;
    }

    public void setProductNm(String productNm) {
        this.productNm = productNm;
    }

    public void setBottleCapa(String bottleCapa) {
        this.bottleCapa = bottleCapa;
    }

    public void setReceivableAmount(int receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getBottleId() {
        return bottleId;
    }

    public String getBottleBarCd() {
        return bottleBarCd;
    }

    public String getProductNm() {
        return productNm;
    }

    public String getBottleCapa() {
        return bottleCapa;
    }

    public int getReceivableAmount() {
        return receivableAmount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("bottleBarCd='").append(bottleBarCd).append('\'');
        sb.append(", productNm='").append(productNm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
