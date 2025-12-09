package com.alifew.bcopay.API;

import com.alifew.bcopay.model.*;
import com.alifew.bcopay.model.app_info.AppInfoApi;
import com.alifew.bcopay.model.logout.Logout_api;
import com.alifew.bcopay.model.otp_login.OTPLoginApi;
import com.alifew.bcopay.model.points.Shop_local_sell_points_api;
import com.alifew.bcopay.model.refer.ReferApi;
import com.alifew.bcopay.model.slider.SliderApi;
import com.alifew.bcopay.model.cupon.cupon_api;
import com.alifew.bcopay.model.local_sell.local_sell_api;
import com.alifew.bcopay.model.shop_notification.shop_notification_api;

public class ApiUtilize {
    private ApiUtilize() {
    }

    public static final String BASE_URL = "https://alifew.com/ALive/";

    public static Shop_registration_api getshop_registration_response() {

        return Retrofit_client.getClient(BASE_URL).create(Shop_registration_api.class);
    }

    public static shop_registration_varefy getshop_varefy_response() {

        return Retrofit_client.getClient(BASE_URL).create(shop_registration_varefy.class);
    }

    public static Customer_registration_api getcustomer_registration_response() {

        return Retrofit_client.getClient(BASE_URL).create(Customer_registration_api.class);
    }

    public static Customer_registration_varefy getcustomer_varefy_response() {

        return Retrofit_client.getClient(BASE_URL).create(Customer_registration_varefy.class);
    }

    public static Shop_login_api get_ShopLoginresponse() {

        return Retrofit_client.getClient(BASE_URL).create(Shop_login_api.class);
    }

    public static Customer_login_api get_CustomerLoginresponse() {

        return Retrofit_client.getClient(BASE_URL).create(Customer_login_api.class);
    }

    public static Shop_details_api get_Shop() {

        return Retrofit_client.getClient(BASE_URL).create(Shop_details_api.class);
    }

    public static Customer_details_api get_Customer() {

        return Retrofit_client.getClient(BASE_URL).create(Customer_details_api.class);
    }

    public static Category_fetch_api get_Category() {

        return Retrofit_client.getClient(BASE_URL).create(Category_fetch_api.class);
    }

    public static Category_add_api Category_add_response() {

        return Retrofit_client.getClient(BASE_URL).create(Category_add_api.class);
    }

    public static get_product_api get_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_product_api.class);
    }

    public static add_product_api add_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_product_api.class);
    }

    public static edit_category_api edit_category_response() {

        return Retrofit_client.getClient(BASE_URL).create(edit_category_api.class);
    }

    public static edit_category_api2 edit_category_response2() {

        return Retrofit_client.getClient(BASE_URL).create(edit_category_api2.class);
    }

    public static delete_category_api delete_category_response() {

        return Retrofit_client.getClient(BASE_URL).create(delete_category_api.class);
    }

    public static add_product_type_api add_product_type_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_product_type_api.class);
    }

    public static get_product_type_api get_product_type_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_product_type_api.class);
    }

    public static delete_product_api delete_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(delete_product_api.class);
    }

    public static edit_type_count_api edit_type_count_response() {

        return Retrofit_client.getClient(BASE_URL).create(edit_type_count_api.class);
    }

    public static delete_type_count_api delete_type_count_response() {

        return Retrofit_client.getClient(BASE_URL).create(delete_type_count_api.class);
    }

    public static update_product_api1 update_product_response1() {

        return Retrofit_client.getClient(BASE_URL).create(update_product_api1.class);
    }

    public static update_product_api2 update_product_response2() {

        return Retrofit_client.getClient(BASE_URL).create(update_product_api2.class);
    }

    public static update_product_api3 update_product_response3() {

        return Retrofit_client.getClient(BASE_URL).create(update_product_api3.class);
    }

    public static update_product_api4 update_product_response4() {

        return Retrofit_client.getClient(BASE_URL).create(update_product_api4.class);
    }

    public static get_single_product_api get_single_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_single_product_api.class);
    }

    public static shop_profile_api shop_profile_response() {

        return Retrofit_client.getClient(BASE_URL).create(shop_profile_api.class);
    }

    public static update_shop_api1 update_shop_response1() {

        return Retrofit_client.getClient(BASE_URL).create(update_shop_api1.class);
    }

    public static update_shop_api2 update_shop_response2() {

        return Retrofit_client.getClient(BASE_URL).create(update_shop_api2.class);
    }

    public static get_shop_customer_api get_shop_customer_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_shop_customer_api.class);
    }

    public static customer_profile_api customer_profile_response() {

        return Retrofit_client.getClient(BASE_URL).create(customer_profile_api.class);
    }

    public static update_customer_api1 update_customer_response1() {

        return Retrofit_client.getClient(BASE_URL).create(update_customer_api1.class);
    }

    public static update_customer_api2 update_customer_response2() {

        return Retrofit_client.getClient(BASE_URL).create(update_customer_api2.class);
    }

    public static add_product_offer_api add_product_offer_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_product_offer_api.class);
    }

    public static get_product_offer_api get_product_offer_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_product_offer_api.class);
    }

    public static Unit_api get_unit() {

        return Retrofit_client.getClient(BASE_URL).create(Unit_api.class);
    }

    public static Update_product_status_api update_product_status() {

        return Retrofit_client.getClient(BASE_URL).create(Update_product_status_api.class);
    }

    public static Imagetoserver_api imagetoserver_response() {

        return Retrofit_client.getClient(BASE_URL).create(Imagetoserver_api.class);
    }

    public static get_product_multiple_image_api get_product_multiple_image_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_product_multiple_image_api.class);
    }

    public static delete_product_image_api delete_product_image_response() {

        return Retrofit_client.getClient(BASE_URL).create(delete_product_image_api.class);
    }

    public static add_sub_shop_api add_sub_shop_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_sub_shop_api.class);
    }

    public static fetch_shop_api fetch_shop_response() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_api.class);
    }

    public static fetch_sub_shop_api fetch_sub_shop_response() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_sub_shop_api.class);
    }

    public static customer_shopList_api customer_shopList_response() {

        return Retrofit_client.getClient(BASE_URL).create(customer_shopList_api.class);
    }

    public static follow_customer_shop_api follow_customer_shop_response() {

        return Retrofit_client.getClient(BASE_URL).create(follow_customer_shop_api.class);
    }

    public static unfollow_customer_shop_api unfollow_customer_shop_response() {

        return Retrofit_client.getClient(BASE_URL).create(unfollow_customer_shop_api.class);
    }

    public static fetch_all_customer_api fetch_all_customer_response() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_all_customer_api.class);
    }

    public static add_shop_customer_api add_shop_customer_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_shop_customer_api.class);
    }

    public static remove_shop_customer_api remove_shop_customer_response() {

        return Retrofit_client.getClient(BASE_URL).create(remove_shop_customer_api.class);
    }

    public static add_shop_admin_api add_shop_admin_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_shop_admin_api.class);
    }

    public static add_shop_admin_access_api add_shop_admin_access_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_shop_admin_access_api.class);
    }

    public static fetch_shop_adminList_api fetch_shop_admin_response() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_adminList_api.class);
    }

    public static delete_shop_admin_api delete_shop_admin_response() {

        return Retrofit_client.getClient(BASE_URL).create(delete_shop_admin_api.class);
    }

    public static update_shop_admin_status_api update_shop_admin_status_response() {

        return Retrofit_client.getClient(BASE_URL).create(update_shop_admin_status_api.class);
    }

    public static fetch_shop_admin_category_api fetch_shop_admin_category_response() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_admin_category_api.class);
    }

    public static remove_shop_admin_category_api remove_shop_admin_category_response() {

        return Retrofit_client.getClient(BASE_URL).create(remove_shop_admin_category_api.class);
    }

    public static shop_admin_login_api shop_admin_login_response() {

        return Retrofit_client.getClient(BASE_URL).create(shop_admin_login_api.class);
    }

    public static get_shop_admin_information_api get_shop_admin_information_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_shop_admin_information_api.class);
    }

    public static get_shop_all_product_api get_shop_all_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_shop_all_product_api.class);
    }

    public static get_shop_admin_all_product_api get_shop_admin_all_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_shop_admin_all_product_api.class);
    }

    public static get_manager_assistantList_api get_manager_assistantList_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_manager_assistantList_api.class);
    }

    public static add_manager_assistant_api add_manager_assistant_response() {

        return Retrofit_client.getClient(BASE_URL).create(add_manager_assistant_api.class);
    }

    public static remove_manager_assistant_api remove_manager_assistant_response() {

        return Retrofit_client.getClient(BASE_URL).create(remove_manager_assistant_api.class);
    }

    public static get_shop_all_product_api get_all_shop_product_response() {

        return Retrofit_client.getClient(BASE_URL).create(get_shop_all_product_api.class);
    }

    public static fetch_customer_join_request_api fetch_customer_join_request() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_customer_join_request_api.class);
    }

    public static accept_customer_join_request_api accept_customer_join_request_response() {

        return Retrofit_client.getClient(BASE_URL).create(accept_customer_join_request_api.class);
    }

    public static cancle_customer_join_request_api Cancel_customer_join_request_response() {

        return Retrofit_client.getClient(BASE_URL).create(cancle_customer_join_request_api.class);
    }

    public static fetch_shop_join_request_api fetch_shop_join_request() {

        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_join_request_api.class);
    }

    public static accept_shop_join_request_api accept_shop_join_request_response() {

        return Retrofit_client.getClient(BASE_URL).create(accept_shop_join_request_api.class);
    }

    public static cancle_shop_join_request_api Cancel_shop_join_request_response() {

        return Retrofit_client.getClient(BASE_URL).create(cancle_shop_join_request_api.class);
    }

    public static get_all_product_discount_api get_all_product_discount_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_all_product_discount_api.class);
    }

    public static fetch_shop_selected_CustomerList_api fetch_shop_selected_customerList_response() {
        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_selected_CustomerList_api.class);
    }

    public static add_product_sell_api add_product_sell_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_product_sell_api.class);
    }

    public static add_sell_details_api add_sell_details_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_sell_details_api.class);
    }

    public static update_product_stock_by_sell_api update_product_stock_by_sell_response() {
        return Retrofit_client.getClient(BASE_URL).create(update_product_stock_by_sell_api.class);
    }

    public static update_product_type_by_sell_api update_product_type_by_sell_response() {
        return Retrofit_client.getClient(BASE_URL).create(update_product_type_by_sell_api.class);
    }

    public static add_sell_payment_cash_api add_sell_payment_cash_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_sell_payment_cash_api.class);
    }

    public static add_payment_transaction_api add_sell_payment_due_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_payment_transaction_api.class);
    }

    public static get_count_for_type_api get_count_for_type_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_count_for_type_api.class);
    }

    public static get_daily_sell_details_api get_daily_sell_details_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_daily_sell_details_api.class);
    }

    public static get_daily_sell_cash_api get_daily_sell_cash_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_daily_sell_cash_api.class);
    }

    public static get_daily_sell_due_api get_daily_sell_due_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_daily_sell_due_api.class);
    }

    public static systemetic_sell_details_api get_sell_productlist_response() {
        return Retrofit_client.getClient(BASE_URL).create(systemetic_sell_details_api.class);
    }

    public static get_operator_all_product_api get_operator_all_product_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_operator_all_product_api.class);
    }

    public static shop_daily_tally_khata_api get_sell_list_response() {
        return Retrofit_client.getClient(BASE_URL).create(shop_daily_tally_khata_api.class);
    }

    public static get_shop_customer_due_list_api get_shop_customer_due_list_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_customer_due_list_api.class);
    }

    public static add_normal_sell_api add_normal_sell_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_normal_sell_api.class);
    }

    public static add_shop_customer_manually_api add_shop_customer_manually_response() {
        return Retrofit_client.getClient(BASE_URL).create(add_shop_customer_manually_api.class);
    }

    public static set_all_discount_api set_all_discount_response() {
        return Retrofit_client.getClient(BASE_URL).create(set_all_discount_api.class);
    }

    public static shop_selected_days_tally_khata_api shop_selected_days_tally_khata_response() {
        return Retrofit_client.getClient(BASE_URL).create(shop_selected_days_tally_khata_api.class);
    }

    public static shop_selected_days_sell_details_api shop_selected_days_sell_details_response() {
        return Retrofit_client.getClient(BASE_URL).create(shop_selected_days_sell_details_api.class);
    }

    public static shop_all_tally_khata_api shop_all_tally_khata_respone() {
        return Retrofit_client.getClient(BASE_URL).create(shop_all_tally_khata_api.class);
    }

    public static shop_all_sell_details_api shop_all_sell_details_respone() {
        return Retrofit_client.getClient(BASE_URL).create(shop_all_sell_details_api.class);
    }

    public static get_shop_all_due_details_api get_shop_all_due_details_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_all_due_details_api.class);
    }

    public static customer_token_update_api token_response() {
        return Retrofit_client.getClient(BASE_URL).create(customer_token_update_api.class);
    }

    public static shop_token_update_api token_update_response() {
        return Retrofit_client.getClient(BASE_URL).create(shop_token_update_api.class);
    }

    public static product_offer_edit_api offer_edit_response() {
        return Retrofit_client.getClient(BASE_URL).create(product_offer_edit_api.class);
    }

    public static delete_product_offer_api offer_delete_response() {
        return Retrofit_client.getClient(BASE_URL).create(delete_product_offer_api.class);
    }

    public static OTP_api otp_response() {
        return Retrofit_client.getClient(BASE_URL).create(OTP_api.class);
    }

    public static update_customer_password_api update_customer_password() {
        return Retrofit_client.getClient(BASE_URL).create(update_customer_password_api.class);
    }

    public static update_shop_password_api update_shop_password() {
        return Retrofit_client.getClient(BASE_URL).create(update_shop_password_api.class);
    }

    public static phone_verification_shop_api verification_shop() {
        return Retrofit_client.getClient(BASE_URL).create(phone_verification_shop_api.class);
    }

    public static phone_verification_customer_api verification_customer() {
        return Retrofit_client.getClient(BASE_URL).create(phone_verification_customer_api.class);
    }

    public static push_notification_all_product_discount_api all_discount_notification() {
        return Retrofit_client.getClient(BASE_URL).create(push_notification_all_product_discount_api.class);
    }

    public static push_notification_sell_shop_api sell_shop_notification() {
        return Retrofit_client.getClient(BASE_URL).create(push_notification_sell_shop_api.class);
    }

    public static push_notification_sell_customer_api sell_customer_notification() {
        return Retrofit_client.getClient(BASE_URL).create(push_notification_sell_customer_api.class);
    }

    public static systemetic_sell_details_api systemetic_sell_details() {
        return Retrofit_client.getClient(BASE_URL).create(systemetic_sell_details_api.class);
    }

    public static add_normal_product_image_api add_normal_product_image() {
        return Retrofit_client.getClient(BASE_URL).create(add_normal_product_image_api.class);
    }

    public static get_customer_all_due_details_api customer_due_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_customer_all_due_details_api.class);
    }

    public static shop_due_customer_api due_customer() {
        return Retrofit_client.getClient(BASE_URL).create(shop_due_customer_api.class);
    }

    public static normal_sell_details_api normal_sell_details() {
        return Retrofit_client.getClient(BASE_URL).create(normal_sell_details_api.class);
    }

    public static get_shop_daily_due_details_api shop_daily_due_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_daily_due_details_api.class);
    }

    public static get_shop_selected_days_due_details_api shop_selected_days_due_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_selected_days_due_details_api.class);
    }

    public static add_shop_due_customer_api add_shop_due_customer() {
        return Retrofit_client.getClient(BASE_URL).create(add_shop_due_customer_api.class);
    }

    public static get_shop_business_summary_api shop_business_summary() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_business_summary_api.class);
    }

    public static add_shop_business_summary_api add_shop_business_summary() {
        return Retrofit_client.getClient(BASE_URL).create(add_shop_business_summary_api.class);
    }

    public static customer_due_shop_list_api customer_due_shop_list() {
        return Retrofit_client.getClient(BASE_URL).create(customer_due_shop_list_api.class);
    }

    public static get_customer_daily_due_details_api get_customer_daily_due_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_customer_daily_due_details_api.class);
    }

    public static get_customer_selected_days_due_details_api get_customer_selected_days_due_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_customer_selected_days_due_details_api.class);
    }

    public static add_business_summary_image_api add_summary_image() {
        return Retrofit_client.getClient(BASE_URL).create(add_business_summary_image_api.class);
    }

    public static add_shop_all_product_offer_api add_shop_all_product_offer() {
        return Retrofit_client.getClient(BASE_URL).create(add_shop_all_product_offer_api.class);
    }

    public static get_shop_all_product_offer_api get_shop_all_product_offer() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_all_product_offer_api.class);
    }

    public static delete_shop_all_product_offer_api delete_shop_all_product_offer() {
        return Retrofit_client.getClient(BASE_URL).create(delete_shop_all_product_offer_api.class);
    }

    public static shop_due_customer_by_search shop_due_customer_by_search() {
        return Retrofit_client.getClient(BASE_URL).create(shop_due_customer_by_search.class);
    }

    public static get_all_product_offer_api get_all_product_offer() {
        return Retrofit_client.getClient(BASE_URL).create(get_all_product_offer_api.class);
    }

    public static get_shop_category_products_summary_api category_products_summary() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_category_products_summary_api.class);
    }

    public static get_shop_all_products_summary_api all_products_summary() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_all_products_summary_api.class);
    }

    public static get_shop_category_summary_api shop_category_summary_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_category_summary_api.class);
    }

    public static get_shop_business_summary_details_api get_shop_business_summary_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_business_summary_details_api.class);
    }

    public static get_category_product_by_search_api category_product_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_category_product_by_search_api.class);
    }

    public static Category_fetch_by_search_api category_fetch_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(Category_fetch_by_search_api.class);
    }

    public static get_all_shop_product_by_search_api get_all_shop_product_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_all_shop_product_by_search_api.class);
    }

    public static get_shop_customer_by_search_api get_shop_customer_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_shop_customer_by_search_api.class);
    }

    public static fetch_shop_admin_category_by_search_api fetch_shop_admin_category_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_admin_category_by_search_api.class);
    }

    public static fetch_shop_admin_category_summary_api fetch_shop_admin_category_summary_response() {
        return Retrofit_client.getClient(BASE_URL).create(fetch_shop_admin_category_summary_api.class);
    }

    public static get_operator_all_product_by_search_api get_operator_all_product_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_operator_all_product_by_search_api.class);
    }

    public static get_operator_all_product_summary_api get_operator_all_product_summary_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_operator_all_product_summary_api.class);
    }

    public static get_customer_shop_by_search_api get_customer_shop_by_search_response() {
        return Retrofit_client.getClient(BASE_URL).create(get_customer_shop_by_search_api.class);
    }

    public static add_local_business_title_api add_local_business_title() {
        return Retrofit_client.getClient(BASE_URL).create(add_local_business_title_api.class);
    }

    public static add_local_business_subtitle_api add_local_business_subtitle() {
        return Retrofit_client.getClient(BASE_URL).create(add_local_business_subtitle_api.class);
    }

    public static add_local_business_details_api add_local_business_details() {
        return Retrofit_client.getClient(BASE_URL).create(add_local_business_details_api.class);
    }

    public static get_local_business_title_api get_local_business_title() {
        return Retrofit_client.getClient(BASE_URL).create(get_local_business_title_api.class);
    }

    public static get_local_business_subtitle_api get_local_business_subtitle() {
        return Retrofit_client.getClient(BASE_URL).create(get_local_business_subtitle_api.class);
    }

    public static get_local_business_details_api get_local_business_details() {
        return Retrofit_client.getClient(BASE_URL).create(get_local_business_details_api.class);
    }

    public static get_version_api get_version() {
        return Retrofit_client.getClient(BASE_URL).create(get_version_api.class);
    }

    public static get_local_page_item_list_api get_local_page_item_list() {
        return Retrofit_client.getClient(BASE_URL).create(get_local_page_item_list_api.class);
    }

    public static update_shop_customer_record_api update_shop_customer_record() {
        return Retrofit_client.getClient(BASE_URL).create(update_shop_customer_record_api.class);
    }

    public static shop_status_api shop_status() {
        return Retrofit_client.getClient(BASE_URL).create(shop_status_api.class);
    }

    public static addMessageApi addMessageApi() {
        return Retrofit_client.getClient(BASE_URL).create(addMessageApi.class);
    }

    public static payment_method_api payment_method_response() {
        return Retrofit_client.getClient(BASE_URL).create(payment_method_api.class);
    }

    public static user_instraction_api user_instraction() {
        return Retrofit_client.getClient(BASE_URL).create(user_instraction_api.class);
    }

    public static shop_sell_history_api shop_sell_history() {
        return Retrofit_client.getClient(BASE_URL).create(shop_sell_history_api.class);
    }

    public static Earning_API earningApi() {
        return Retrofit_client.getClient(BASE_URL).create(Earning_API.class);
    }

    public static getUser_deviceToken_api getUserToken() {
        return Retrofit_client.getClient(BASE_URL).create(getUser_deviceToken_api.class);
    }

    public static last_logintime_api last_logintime() {
        return Retrofit_client.getClient(BASE_URL).create(last_logintime_api.class);
    }

    public static update_last_logintime_api update_last_logintime() {
        return Retrofit_client.getClient(BASE_URL).create(update_last_logintime_api.class);
    }

    public static cupon_api cupon_response() {
        return Retrofit_client.getClient(BASE_URL).create(cupon_api.class);
    }

    public static local_sell_api local_sell_api() {
        return Retrofit_client.getClient(BASE_URL).create(local_sell_api.class);
    }

    public static customer_exist_check_api customer_exist_check_api() {
        return Retrofit_client.getClient(BASE_URL).create(customer_exist_check_api.class);
    }

    public static shop_notification_api shop_notification_api() {
        return Retrofit_client.getClient(BASE_URL).create(shop_notification_api.class);
    }

    public static SliderApi bannerApi() {
        return Retrofit_client.getClient(BASE_URL).create(SliderApi.class);
    }

    public static Shop_local_sell_points_api shopLocalSellPointsApi() {
        return Retrofit_client.getClient(BASE_URL).create(Shop_local_sell_points_api.class);
    }

    public static Logout_api logoutApi() {
        return Retrofit_client.getClient(BASE_URL).create(Logout_api.class);
    }

    public static ReferApi referApi(){
        return Retrofit_client.getClient(BASE_URL).create(ReferApi.class);
    }

    public static AppInfoApi appInfoApi(){
        return Retrofit_client.getClient(BASE_URL).create(AppInfoApi.class);
    }

    public static OTPLoginApi otpLoginApi(){
        return Retrofit_client.getClient(BASE_URL).create(OTPLoginApi.class);
    }
}
