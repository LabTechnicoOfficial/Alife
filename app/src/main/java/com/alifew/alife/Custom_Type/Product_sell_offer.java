package com.alifew.alife.Custom_Type;

public class Product_sell_offer {
    private String offer_type;
    private String offer_minimum_amount;
    private String offer_minimum_price;
    private String offer_percentage;

    public Product_sell_offer(String offer_type, String offer_minimum_amount, String offer_minimum_price, String offer_percentage) {
        this.offer_type = offer_type;
        this.offer_minimum_amount = offer_minimum_amount;
        this.offer_minimum_price = offer_minimum_price;
        this.offer_percentage = offer_percentage;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public String getOffer_minimum_amount() {
        return offer_minimum_amount;
    }

    public String getOffer_minimum_price() {
        return offer_minimum_price;
    }

    public String getOffer_percentage() {
        return offer_percentage;
    }
}
