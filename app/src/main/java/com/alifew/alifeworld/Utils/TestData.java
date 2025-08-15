package com.alifew.alifeworld.Utils;

import com.alifew.alifeworld.model.slider.Customer_slider_response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestData {
    public static final List<Customer_slider_response.Slider> bannerList = new ArrayList<>(Arrays.asList(
            new Customer_slider_response.Slider("101", "https://fastly.picsum.photos/id/2/5000/3333.jpg?hmac=_KDkqQVttXw_nM-RyJfLImIbafFrqLsuGO5YuHqD-qQ", "5001", "active"),
            new Customer_slider_response.Slider("102", "https://fastly.picsum.photos/id/4/5000/3333.jpg?hmac=ghf06FdmgiD0-G4c9DdNM8RnBIN7BO0-ZGEw47khHP4", "5002", "inactive"),
            new Customer_slider_response.Slider("103", "https://fastly.picsum.photos/id/10/2500/1667.jpg?hmac=J04WWC_ebchx3WwzbM-Z4_KC_LeLBWr5LZMaAkWkF68", "5003", "inactive"),
            new Customer_slider_response.Slider("104", "https://fastly.picsum.photos/id/12/2500/1667.jpg?hmac=Pe3284luVre9ZqNzv1jMFpLihFI6lwq7TPgMSsNXw2w", "5003", "inactive")

    ));
}

