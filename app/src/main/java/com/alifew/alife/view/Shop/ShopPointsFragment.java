package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.databinding.FragmentShopPointsBinding;
import com.alifew.alife.session.SessionManagement;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class ShopPointsFragment extends Fragment {

    SessionManagement sessionManagement;
    int shopID;
    ExtendedFloatingActionButton addButton;
    FragmentShopPointsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopPointsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView(view);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void initView(View view) {

        sessionManagement = new SessionManagement(getActivity());
        shopID = sessionManagement.getSession();

    }
}