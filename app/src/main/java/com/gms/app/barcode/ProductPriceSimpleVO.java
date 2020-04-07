package com.gms.app.barcode;

import java.io.Serializable;

public class ProductPriceSimpleVO {
    private static final long serialVersionUID = 8284009822300528104L;

    /** Product_ID */
    private Integer productId;

    /**Product_Nm */
    private String productNm;

    /** Product_Price_Seq */
    private Integer productPriceSeq;

    /** Product_Capa     */
    private String productCapa;

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
}
