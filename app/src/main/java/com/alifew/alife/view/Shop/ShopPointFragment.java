package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_point_adapter;
import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.points.Shop_local_sell_point_response;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.ShopLocalSellPointsViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ShopPointFragment extends Fragment implements Shop_point_adapter.OnDeleteClickListener {


    SessionManagement sessionManagement;
    String shopID;
    RecyclerView itemView;
    ShopLocalSellPointsViewModel shopLocalSellPointsViewModel;
    List<Shop_local_sell_point_response> shopSellPointRulesList = new ArrayList<>();
    ExtendedFloatingActionButton floatingAddButton;

    Dialog loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_point, container, false);

        initView(view);

        loadPoints();

        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPoints();
            }
        });

        return view;
    }

    private void addPoints() {
        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setContentView(R.layout.add_local_sell_points_alert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        EditText amountText = alertDialog.findViewById(R.id.amountText);
        EditText pointsText = alertDialog.findViewById(R.id.pointsText);
        AppCompatButton submitButton = alertDialog.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(amountText.getText().toString().trim()) || TextUtils.isEmpty(pointsText.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "empty field", Toast.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    shopLocalSellPointsViewModel.addLocalSellPoint(shopID, amountText.getText().toString().trim(), pointsText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                        @Override
                        public void onChanged(CommonResponse commonResponse) {
                            loader.dismiss();
                            if (commonResponse.message.equals("Add successfully")) {
                                alertDialog.dismiss();
                                loadPoints();
                            }
                            Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void loadPoints() {
        shopLocalSellPointsViewModel.getShopLocalSellPoints(shopID).observe(getViewLifecycleOwner(), new Observer<List<Shop_local_sell_point_response>>() {
            @Override
            public void onChanged(List<Shop_local_sell_point_response> shopLocalSellPointResponses) {
                shopSellPointRulesList = shopLocalSellPointResponses;
                Shop_point_adapter adapter = new Shop_point_adapter(shopSellPointRulesList);
                adapter.setOnClickListener(ShopPointFragment.this::OnDeleteClick);
                itemView.setAdapter(adapter);
            }
        });
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getSession());

        itemView = view.findViewById(R.id.itemView);
        itemView.setHasFixedSize(true);
        itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopLocalSellPointsViewModel = new ViewModelProvider(getActivity()).get(ShopLocalSellPointsViewModel.class);

        floatingAddButton = view.findViewById(R.id.floatingAddButton);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

    }

    @Override
    public void OnDeleteClick(int position) {
        Shop_local_sell_point_response response = shopSellPointRulesList.get(position);
        shopLocalSellPointsViewModel.deleteLocalSellPoint(response.id).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {
                if (commonResponse.message.equals("Delete successful")) {
                    loadPoints();
                }

                Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}