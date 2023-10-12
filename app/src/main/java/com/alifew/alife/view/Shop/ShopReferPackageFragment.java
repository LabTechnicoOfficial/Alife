package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;


public class ShopReferPackageFragment extends Fragment {

    com.alifew.alife.databinding.FragmentShopReferPackageBinding binding;
    String referID;

    public ShopReferPackageFragment(String referID) {
        this.referID = referID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = com.alifew.alife.databinding.FragmentShopReferPackageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        Toast.makeText(getActivity(), referID, Toast.LENGTH_SHORT).show();

        return view;
    }

    private void initView(View view) {
    }
}