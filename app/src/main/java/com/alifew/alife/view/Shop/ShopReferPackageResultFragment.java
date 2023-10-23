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

import com.alifew.alife.R;
import com.alifew.alife.adapter.refer.ShopReferCustomerResultAdapter;
import com.alifew.alife.databinding.FragmentShopReferPackageCustomerListBinding;
import com.alifew.alife.databinding.FragmentShopReferPackageResultBinding;
import com.alifew.alife.model.refer.ReferResultCustomerResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.refer.ShopReferViewModel;

import java.util.List;

public class ShopReferPackageResultFragment extends Fragment {
    FragmentShopReferPackageResultBinding binding;
    SessionManagement sessionManagement;
    int shopID;
    String packageID;
    ShopReferCustomerResultAdapter adapter;

    ShopReferViewModel shopReferViewModel;

    Dialog loader;
    List<ReferResultCustomerResponse.Customer> resultCustomerList;

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
        loader.dismiss();
        shopReferViewModel.getResultCustomerList(packageID).observe(getViewLifecycleOwner(), new Observer<ReferResultCustomerResponse>() {
            @Override
            public void onChanged(ReferResultCustomerResponse referResultCustomerResponse) {
                loader.dismiss();
                resultCustomerList = referResultCustomerResponse.customerList;
                adapter = new ShopReferCustomerResultAdapter(resultCustomerList);
                binding.itemView.setAdapter(adapter);
            }
        });


    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();

        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

        shopReferViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }
}