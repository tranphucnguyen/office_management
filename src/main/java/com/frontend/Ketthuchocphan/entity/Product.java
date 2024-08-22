package com.frontend.Ketthuchocphan.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id_products")
    private int id;
    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "regular_price", nullable = false)
    private BigDecimal regularPrice;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "product_img")
    private String product_img;
    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "active", nullable = false)
    private boolean active;
    

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getproduct_img() {
        return product_img;
    }
    public void setproduct_img(String product_img) {
        this.product_img = product_img;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }
    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
