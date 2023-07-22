package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Slider.ShopSliderAdapter;
import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.slider.SliderResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.banner.SliderViewModel;

import java.util.ArrayList;
import java.util.List;


public class ShopSliderFragment extends Fragment implements ShopSliderAdapter.SwitchChangeListener {

    RecyclerView sliderView;
    SliderViewModel sliderViewModel;
    String shopID;
    SessionManagement sessionManagement;
    Dialog loader;
    List<SliderResponse> sliderList;
    ShopSliderAdapter sliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_slider, container, false);

        initView(view);

        loader.show();
        loadBanner();

        return view;
    }

    private void loadBanner() {
        //loader.show();
        //Toast.makeText(getActivity(), shopID, Toast.LENGTH_SHORT).show();
        sliderViewModel.getBannerList(shopID).observe(getViewLifecycleOwner(), new Observer<List<SliderResponse>>() {
            @Override
            public void onChanged(List<SliderResponse> sliderResponses) {
                loader.dismiss();
                try {
                    sliderList = new ArrayList<>();
                    sliderList = sliderResponses;
                    sliderAdapter = new ShopSliderAdapter(sliderList);
                    sliderAdapter.setOnClickListener(ShopSliderFragment.this::OnSwitchChange);
                    sliderView.setAdapter(sliderAdapter);
                } catch (Exception e) {
                    Log.d("dataxx", "exception: " + e.getMessage());
                }

            }
        });

    }

    private void initView(View view) {
        sliderView = view.findViewById(R.id.bannerView);
        sliderView.setHasFixedSize(true);
        sliderView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sliderViewModel = new ViewModelProvider(getActivity()).get(SliderViewModel.class);

        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getSession());

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }


    @Override
    public void OnSwitchChange(int position, boolean isChecked) {
        SliderResponse response = sliderList.get(position);
        String status = isChecked ? "active" : "inactive";
        String sliderID = response.id;

        //loader.show();

        sliderViewModel.updateBannerStatus(shopID, sliderID, status).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {
                //loader.dismiss();
                if (commonResponse.message.equals("edited successfully")) {
                    Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();
                    loadBanner();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}