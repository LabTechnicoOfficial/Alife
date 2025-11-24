package com.alifew.bcopay.Custom_Type;

import java.util.List;

public class ProductSell {
    // private String offer_type;
    private String product_id;
    private String product_name;
    private String product_image;
    private String type_id;
    private String type_name;
    private String discount;
    private String amount;
    private String price;
    private String buy_price;
    private String unit_price;
    private String unit_price_with_discount;
    private List<ProductSel_type> typeList;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUnit_price_with_discount() {
        return unit_price_with_discount;
    }

    public void setUnit_price_with_discount(String unit_price_with_discount) {
        this.unit_price_with_discount = unit_price_with_discount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

   /* public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }*/

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public List<ProductSel_type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<ProductSel_type> typeList) {
        this.typeList = typeList;
    }

  /*  public Product_sell_offer getSell_offer() {
        return sell_offer;
    }

    public void setSell_offer(Product_sell_offer sell_offer) {
        this.sell_offer = sell_offer;
    }*/
}
