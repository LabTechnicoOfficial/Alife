package com.alifew.alifeworld.view.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.coupon.Customer_coupon_adapter;
import com.alifew.alifeworld.model.cupon.cupon_response;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CouponViewModel;
import com.alifew.alifeworld.viewmodel.cuponViewmodel.CustomerFor_cupon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Customer_coupon_shop_coupon_list_fragment extends Fragment implements Customer_coupon_adapter.onItemClickListener {

    String shopID;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10, end = 0;
    RecyclerView couponView;
    CouponViewModel couponViewModel;
    private Customer_coupon_adapter adapter;
    private List<cupon_response> couponList;
    List<CustomerFor_cupon_response> customerList;
    String customerID;
    String cupon_available;
    public Customer_coupon_shop_coupon_list_fragment(String shopID, String customerID) {
        this.shopID = shopID;
        this.customerID = customerID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coupon_data();
    }

    private void coupon_data() {
        couponViewModel.getCupon(shopID).observe(getViewLifecycleOwner(), new Observer<List<cupon_response>>() {
            @Override
            public void onChanged(List<cupon_response> cupon_responses) {
                couponList = new ArrayList<>();
                couponList = cupon_responses;
                adapter = new Customer_coupon_adapter(couponList);
                adapter.setOnClickListener(Customer_coupon_shop_coupon_list_fragment.this::OnItemClick);
                couponView.setAdapter(adapter);
                // shop_coupon_adapter.setOnClickListener(Shop_coupon_fragment.this::OnItemClick);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_coupon_shop_coupon_list_fragment, container, false);

        couponViewModel = new ViewModelProvider(this).get(CouponViewModel.class);

        couponView = (RecyclerView) view.findViewById(R.id.couponViewID);
        couponView.setHasFixedSize(true);
        couponView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        //filter(page, limit);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        cupon_response response = couponList.get(position);

        String couponID = response.getId();
        String createDate = response.getCreation_date();
        String endDate = response.getEnd_date();

        CustomerFor_cupon customerFor_cupon;
        customerFor_cupon = new ViewModelProvider(this).get(CustomerFor_cupon.class);

        customerList = new ArrayList<>();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.getEnd_date() + " 23:59:59";
        customerFor_cupon.getData(shopID, createDate, endDate).observe(getViewLifecycleOwner(), new Observer<List<CustomerFor_cupon_response>>() {
            @Override
            public void onChanged(List<CustomerFor_cupon_response> customerFor_cupon_responses) {
                customerList = customerFor_cupon_responses;
                Collections.sort(customerList, new Comparator<CustomerFor_cupon_response>() {

                    @Override
                    public int compare(CustomerFor_cupon_response lhs, CustomerFor_cupon_response rhs) {
                        // TODO Auto-generated method stub

                        try {
                            Double v1 = (Double.parseDouble(lhs.getSell_amount()));
                            Double v3 = (Double.parseDouble(rhs.getSell_amount()));
                            return v3.compareTo(v1);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });
                try {
                    Date currentDate = myFormat.parse(currentTime);
                    Date targetDate = myFormat.parse(targetdate);
                    if(currentDate.getTime()>targetDate.getTime())
                    {
                        cupon_available="0";
                    }else
                    {
                        cupon_available="1";
                    }

                }catch (Exception e)
                {

                }
                //Toast.makeText(getActivity(), couponID, Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.cus_frame_container, new Customer_coupon_shop_coupon_package_list_fragment(shopID, couponID, customerList, customerID,cupon_available,createDate,endDate)).addToBackStack(null).commit();


            }
        });

    }
}