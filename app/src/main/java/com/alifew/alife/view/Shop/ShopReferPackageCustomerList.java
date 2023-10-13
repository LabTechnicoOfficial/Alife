package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.databinding.FragmentShopReferPackageCustomerListBinding;


public class ShopReferPackageCustomerList extends Fragment {


    FragmentShopReferPackageCustomerListBinding  binding;
    String packageID;

    public ShopReferPackageCustomerList(String id) {
        packageID = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopReferPackageCustomerListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        load_data();

        return view;
    }

    private void load_data() {

        Toast.makeText(getActivity(), packageID, Toast.LENGTH_SHORT).show();
    }

    private void initView(View view) {

    }
}