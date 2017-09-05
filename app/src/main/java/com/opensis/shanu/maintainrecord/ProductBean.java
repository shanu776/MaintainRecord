package com.opensis.shanu.maintainrecord;

/**
 * Created by Shanu on 8/24/2017.
 */

public class ProductBean {
    Integer id;
    String keyid;
    String product_name;
    String product_quantity;
    String product_price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public ProductBean(String keyid, String product_name, String product_quantity, String product_price) {
        this.keyid = keyid;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public ProductBean(Integer id, String keyid, String product_name, String product_quantity, String product_price) {
        this.id = id;
        this.keyid = keyid;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public ProductBean() {

    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", keyid='" + keyid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_quantity='" + product_quantity + '\'' +
                ", product_price='" + product_price + '\'' +
                '}';
    }
}
