package com.epitech.cashmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Article {

    @Id
    private String barcode;
    private String name;
    private BigDecimal price;
    private String image;

    public Article() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Article{" +
                "barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
