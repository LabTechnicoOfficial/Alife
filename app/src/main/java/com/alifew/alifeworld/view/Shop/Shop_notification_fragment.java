package com.alifew.alifeworld.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.shop_notification.shop_notification_response;
import com.alifew.alifeworld.viewmodel.Shop_notification.Shop_notification;

public class Shop_notification_fragment extends Fragment {

    String shopID;
    EditText messageEditText;
    AppCompatButton sendButton;
    Dialog loader;
    Shop_notification shop_notification;

    public Shop_notification_fragment(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_notification_fragment, container, false);

        shop_notification = new ViewModelProvider(this).get(Shop_notification.class);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        messageEditText = view.findViewById(R.id.messageEditTextID);
        sendButton = view.findViewById(R.id.sendButtonID);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();

                if (TextUtils.isEmpty(messageText)) {
                    Toast.makeText(getActivity(), "Empty message", Toast.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    shop_notification.getData(shopID, messageText).observe(getViewLifecycleOwner(), new Observer<shop_notification_response>() {
                        @Override
                        public void onChanged(shop_notification_response shop_notification_response) {
                            String message = shop_notification_response.getMessage();
                            loader.dismiss();

                            if (message.equals("approve")) {
                                Toast.makeText(getActivity(), "Notification sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        return view;
    }
}