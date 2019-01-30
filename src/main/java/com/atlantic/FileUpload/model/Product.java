package com.atlantic.FileUpload.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "product")
public class Product {

   @Id
    private int productId;
    private int customerId;
    private String purchaseOrderStatus;
    private String productName;
    private BigDecimal orderAmount;
    private Date pruchaseDate;


   public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getPruchaseDate() {
        return pruchaseDate;
    }

    public void setPruchaseDate(Date pruchaseDate) {
        this.pruchaseDate = pruchaseDate;
    }
}
