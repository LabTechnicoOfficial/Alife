package com.ALife.alife.view.Operator;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.R;

import java.util.List;

public class Operator_sell_select_fragment extends Fragment {
    AppCompatButton goButton;
    RadioGroup radioGroup;

    FragmentManager fragmentManager;
    String agent_id,shop_id;
    List<ProductSell> sellList;

    public Operator_sell_select_fragment(String shop_id,String agent_id, List<ProductSell> sellList) {
        this.shop_id=shop_id;
        this.agent_id = agent_id;
        this.sellList = sellList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.operator_sell_select_fragment, container, false);

        goButton = (AppCompatButton) view.findViewById(R.id.goButtonID);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupID);

        fragmentManager = getFragmentManager();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Select one from above", Toast.LENGTH_SHORT).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == R.id.categoriesRadioID) {

                        fragmentManager.beginTransaction().setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out  // popExit
                        ).replace(R.id.frame_container, new Operator_sell_category_fragment(shop_id,agent_id, sellList)).addToBackStack(null).commit();

                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.productsRadioID) {

                        fragmentManager.beginTransaction().setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out  // popExit
                        ).replace(R.id.frame_container, new Operator_sell_products_fragment(shop_id,agent_id, sellList)).addToBackStack(null).commit();
                    }
                }
            }
        });
        return view;
    }
}