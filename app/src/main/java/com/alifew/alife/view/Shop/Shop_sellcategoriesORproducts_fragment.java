package com.alifew.alife.view.Shop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.R;

import java.util.ArrayList;
import java.util.List;

public class Shop_sellcategoriesORproducts_fragment extends Fragment {

    String shop_id;
    private List<ProductSell> productSellList;
    AppCompatButton goButton;
    RadioGroup radioGroup;
    private FragmentManager fragmentManager;

    public Shop_sellcategoriesORproducts_fragment(String shop_id, List<ProductSell> productSellList) {
        this.shop_id = shop_id;
        this.productSellList = productSellList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sellcategories_o_rproducts_fragment, container, false);
        goButton = (AppCompatButton) view.findViewById(R.id.goButtonID);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupID);

        fragmentManager = getFragmentManager();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info == null) {
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                }else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "Select one from above", Toast.LENGTH_SHORT).show();
                    } else {
                        if (radioGroup.getCheckedRadioButtonId() == R.id.categoriesRadioID) {
                            productSellList = new ArrayList<>();
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out  // popExit
                            ).replace(R.id.frame_container, new Shop_sell_categories_fragment(shop_id, productSellList)).addToBackStack(null).commit();

                        } else if (radioGroup.getCheckedRadioButtonId() == R.id.productsRadioID) {
                            productSellList = new ArrayList<>();
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out  // popExit
                            ).replace(R.id.frame_container, new Shop_sell_products_fragment(shop_id, productSellList)).addToBackStack(null).commit();
                        }
                    }
                }
            }
        });

        return view;
    }
}