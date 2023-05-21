package com.ALife.alife.EarningApp.Model;


import com.ALife.alife.EarningApp.Model.AddInterval.addInterval_api;
import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_api;
import com.ALife.alife.EarningApp.Model.AddLimit.update_addLimit_api;
import com.ALife.alife.EarningApp.Model.CashOut.cashOut_api;
import com.ALife.alife.EarningApp.Model.CashOut.pending_cashOut_api;
import com.ALife.alife.EarningApp.Model.Login.Login_API;
import com.ALife.alife.EarningApp.Model.Notice.Notice_API;
import com.ALife.alife.EarningApp.Model.Profile.Profile_API;
import com.ALife.alife.EarningApp.Model.Reffer.Reffer_API;
import com.ALife.alife.EarningApp.Model.Registration.Area_API;
import com.ALife.alife.EarningApp.Model.Registration.Registration_API;
import com.ALife.alife.EarningApp.Model.Registration.UserCheck_API;
import com.ALife.alife.EarningApp.Model.Reward.Reward_API;
import com.ALife.alife.EarningApp.Model.SendMoney.sendMoney_api;
import com.ALife.alife.EarningApp.Model.Team.Team_API;
import com.ALife.alife.EarningApp.Model.Transaction.transactionHistory_api;
import com.ALife.alife.EarningApp.Model.UserValidation.userValidation_api;
import com.ALife.alife.EarningApp.Model.VerifyPassword.verifyPassword_api;
import com.ALife.alife.EarningApp.Model.Video_ad.Video_ad_api;
import com.ALife.alife.EarningApp.Model.addRequest.check_request_api;
import com.ALife.alife.EarningApp.Model.addRequest.update_request_api;


public class APIUtilize {
    public APIUtilize() {
    }

    public static final String BASE_URL = "https://alifew.com/ALive/earning/";

    public static UserCheck_API userCheckApi() {
        return Retrofit_client.getClient(BASE_URL).create(UserCheck_API.class);
    }

    public static Registration_API registrationInterface() {
        return Retrofit_client.getClient(BASE_URL).create(Registration_API.class);
    }

    public static Login_API loginApi() {
        return Retrofit_client.getClient(BASE_URL).create(Login_API.class);
    }

    public static Profile_API profileApi() {
        return Retrofit_client.getClient(BASE_URL).create(Profile_API.class);
    }

    public static Notice_API noticeApi() {
        return Retrofit_client.getClient(BASE_URL).create(Notice_API.class);
    }

    public static Team_API teamApi() {
        return Retrofit_client.getClient(BASE_URL).create(Team_API.class);
    }

    public static Area_API areaApi() {
        return Retrofit_client.getClient(BASE_URL).create(Area_API.class);
    }

    public static cashOut_api cashOut_response() {
        return Retrofit_client.getClient(BASE_URL).create(cashOut_api.class);
    }

    public static pending_cashOut_api pending_cashOut_response() {
        return Retrofit_client.getClient(BASE_URL).create(pending_cashOut_api.class);
    }

    public static userValidation_api userValidation() {
        return Retrofit_client.getClient(BASE_URL).create(userValidation_api.class);
    }

    public static sendMoney_api sendMoney() {
        return Retrofit_client.getClient(BASE_URL).create(sendMoney_api.class);
    }

    public static transactionHistory_api transactionHistory() {
        return Retrofit_client.getClient(BASE_URL).create(transactionHistory_api.class);
    }

    public static Reffer_API refferApi() {
        return Retrofit_client.getClient(BASE_URL).create(Reffer_API.class);
    }

    public static Reward_API rewardApi() {
        return Retrofit_client.getClient(BASE_URL).create(Reward_API.class);
    }

    public static verifyPassword_api verifyPassword() {
        return Retrofit_client.getClient(BASE_URL).create(verifyPassword_api.class);
    }

    public static addLimit_api addLimit() {
        return Retrofit_client.getClient(BASE_URL).create(addLimit_api.class);
    }

    public static update_addLimit_api update_addLimit() {
        return Retrofit_client.getClient(BASE_URL).create(update_addLimit_api.class);
    }

    public static addInterval_api addInterval() {
        return Retrofit_client.getClient(BASE_URL).create(addInterval_api.class);
    }
    public static check_request_api check_request()
    {
        return Retrofit_client.getClient(BASE_URL).create(check_request_api.class);
    }
    public static update_request_api  update_request()
    {
        return Retrofit_client.getClient(BASE_URL).create(update_request_api.class);
    }

    public static Video_ad_api videoAdApi(){
        return Retrofit_client.getClient(BASE_URL).create(Video_ad_api.class);
    }
}
