package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.R;
import com.alifew.alife.adapter.refer.ShopReferPackageCustomerAdapter;
import com.alifew.alife.databinding.FragmentShopReferPackageCustomerListBinding;
import com.alifew.alife.model.CommonResponse;
import com.alifew.alife.model.refer.ReferPackageCustomerResponse;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.refer.ShopReferViewModel;

import java.util.List;


public class ShopReferPackageCustomerListFragment extends Fragment implements ShopReferPackageCustomerAdapter.OnAddIconClickListener {


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

        binding.resultButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new ShopReferPackageResultFragment(packageID)).addToBackStack(null).commit();
        });

        return view;
    }

    private void load_data() {
        loader.show();
//          Toast.makeText(getActivity(), String.valueOf(shopID)+" p:"+ packageID, Toast.LENGTH_SHORT).show();
        shopReferViewModel.getReferPackageCustomer(String.valueOf(shopID), packageID).observe(getViewLifecycleOwner(), referPackageCustomerResponses -> {
            loader.dismiss();
            customerList = referPackageCustomerResponses;
            adapter = new ShopReferPackageCustomerAdapter(customerList);
            adapter.setOnItemClickListener(ShopReferPackageCustomerListFragment.this);
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

    @Override
    public void onAddIconClick(int position) {
        ReferPackageCustomerResponse response = customerList.get(position);
        String pos = String.valueOf(position + 1);

        Dialog referAddGiftAlert = new Dialog(getActivity());
        referAddGiftAlert.setContentView(R.layout.refer_add_gift_alert);
        referAddGiftAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        referAddGiftAlert.setCancelable(false);
        referAddGiftAlert.show();

        Window window = referAddGiftAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView positionText = referAddGiftAlert.findViewById(R.id.positionText);
        TextView phoneText = referAddGiftAlert.findViewById(R.id.phoneText);
        TextView pointsText = referAddGiftAlert.findViewById(R.id.pointsText);
        EditText nameText = referAddGiftAlert.findViewById(R.id.nameText);
        ImageView closeButton = referAddGiftAlert.findViewById(R.id.closeButton);
        Button submitButton = referAddGiftAlert.findViewById(R.id.submitButton);
        closeButton.setOnClickListener(v -> {
            referAddGiftAlert.dismiss();
        });

        positionText.setText(pos);
        phoneText.setText(response.referPhone);
        pointsText.setText(String.valueOf(response.referPoints));

        submitButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(nameText.getText().toString().trim())) {
                Toast.makeText(getActivity(), "empty gift name", Toast.LENGTH_SHORT).show();
            } else {

                loader.show();
                shopReferViewModel.addCustomerReferGift(packageID, shopID, response.referPhone, String.valueOf(response.referPoints), pos, nameText.getText().toString().trim()).observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
                    @Override
                    public void onChanged(CommonResponse commonResponse) {
                        loader.dismiss();

                        Toast.makeText(getActivity(), commonResponse.message, Toast.LENGTH_SHORT).show();

                        if (commonResponse.message.equals("success")) {
                            referAddGiftAlert.dismiss();
                        }
                    }
                });
            }
        });


    }
}