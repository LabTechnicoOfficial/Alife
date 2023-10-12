package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.adapter.refer.ShopReferAdapter;
import com.alifew.alife.databinding.FragmentShopPointsBinding;
import com.alifew.alife.model.refer.ReferResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.refer.ShopReferViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ShopPointsFragment extends Fragment {

    SessionManagement sessionManagement;
    int shopID;
    ExtendedFloatingActionButton addButton;
    FragmentShopPointsBinding binding;
    List<ReferResponse> referList;
    ShopReferViewModel referViewModel;
    ShopReferAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopPointsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "+", Toast.LENGTH_SHORT).show();
            }
        });

        load_data();


        return view;
    }

    private void load_data() {

        binding.progressBar.setVisibility(View.VISIBLE);
        referViewModel.getReferList(String.valueOf(shopID)).observe(getViewLifecycleOwner(), referResponses -> {
            binding.progressBar.setVisibility(View.GONE);
            referList = referResponses;
            adapter = new ShopReferAdapter(referList);
            binding.itemView.setAdapter(adapter);
        });
    }

    private void initView(View view) {
        referViewModel = new ViewModelProvider(getActivity()).get(ShopReferViewModel.class);
        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();

        binding.itemView.setHasFixedSize(true);
        binding.itemView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.itemView.setItemViewCacheSize(100);

    }
}