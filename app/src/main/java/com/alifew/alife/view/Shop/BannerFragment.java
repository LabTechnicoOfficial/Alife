package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.alifew.alife.viewmodel.banner.BannerViewModel;


public class BannerFragment extends Fragment {

    RecyclerView bannerView;
    BannerViewModel bannerViewModel;
    String shopID;
    SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        
        initView(view);

        loadBanner();
        
        return view;
    }

    private void loadBanner() {
        Toast.makeText(getActivity(), shopID, Toast.LENGTH_SHORT).show();
    }

    private void initView(View view) {
        bannerView = view.findViewById(R.id.bannerView);
        bannerView.setHasFixedSize(true);
        bannerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sessionManagement = new SessionManagement(getActivity());
        shopID = String.valueOf(sessionManagement.getSession());
    }
}