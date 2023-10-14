package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.adapter.refer.ShopReferPackageCustomerAdapter;
import com.alifew.alife.databinding.FragmentShopReferPackageCustomerListBinding;
import com.alifew.alife.model.refer.ReferPackageCustomerResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.refer.ShopReferViewModel;

import java.util.List;


public class ShopReferPackageCustomerListFragment extends Fragment {


    FragmentShopReferPackageCustomerListBinding binding;
    String packageID;
    ShopReferViewModel shopReferViewModel;

    public ShopReferPackageCustomerListFragment(String id) {
        packageID = id;
    }

    SessionManagement sessionManagement;
    int shopID;

    Dialog loader;
    ShopReferPackageCustomerAdapter adapter;
    List<ReferPackageCustomerResponse> customerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopReferPackageCustomerListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        load_data();

        return view;
    }

    private void load_data() {
        loader.show();
      //  Toast.makeText(getActivity(), String.valueOf(shopID)+" p:"+ packageID, Toast.LENGTH_SHORT).show();
        shopReferViewModel.getReferPackageCustomer(String.valueOf(shopID), packageID).observe(getViewLifecycleOwner(), referPackageCustomerResponses -> {
            loader.dismiss();
            customerList = referPackageCustomerResponses;
            adapter = new ShopReferPackageCustomerAdapter(customerList);
            binding.itemView.setAdapter(adapter);

        });
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();
        shopReferViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);
        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.itemView.setItemViewCacheSize(100);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }
}