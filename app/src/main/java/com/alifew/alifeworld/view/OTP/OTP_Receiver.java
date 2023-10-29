package com.alifew.alifeworld.view.OTP;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class OTP_Receiver extends BroadcastReceiver {
    private  static EditText editText;
    private  static EditText code1,code2,code3,code4,code5;
    public void setEditText(EditText code1,EditText code2,EditText code3,EditText code4,EditText code5)
    {
        OTP_Receiver.code1=code1;
        OTP_Receiver.code2=code2;
        OTP_Receiver.code3=code3;
        OTP_Receiver.code4=code4;
        OTP_Receiver.code5=code5;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : messages)
        {

            String msg = sms.getMessageBody();
            String numbers;
            numbers=msg.replaceAll("[^0-9]", "");
            int l=numbers.length();

            code1.setText(String.valueOf(numbers.charAt(0)));
            code2.setText(String.valueOf(numbers.charAt(1)));
            code3.setText(String.valueOf(numbers.charAt(2)));
            code4.setText(String.valueOf(numbers.charAt(3)));
            code5.setText(String.valueOf(numbers.charAt(4)));


        }
    }
}
