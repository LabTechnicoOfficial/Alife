package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alifew.alife.R;
import com.alifew.alife.databinding.FragmentShopReferPackageCustomerListBinding;
import com.alifew.alife.databinding.FragmentShopReferPackageResultBinding;
import com.alifew.alife.session.SessionManagement;

public class ShopReferPackageResultFragment extends Fragment {
    FragmentShopReferPackageResultBinding binding;
    SessionManagement sessionManagement;
    int shopID;
    String packageID;

    public ShopReferPackageResultFragment(String packageID) {
        this.packageID = packageID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopReferPackageResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);
        
        load_data();

        return view;
    }

    private void load_data() {
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();
    }
}