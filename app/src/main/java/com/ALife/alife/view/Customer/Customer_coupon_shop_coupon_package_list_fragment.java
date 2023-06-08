package com.ALife.alife.view.Customer;

import android.os.Bundle;
import android.util.Log;
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

import com.ALife.alife.API.ApiUtilize;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer_package_adapter;
import com.ALife.alife.model.cupon.active_cupon;
import com.ALife.alife.model.cupon.cupon_api;
import com.ALife.alife.model.cupon.customerFor_cupon_response;
import com.ALife.alife.model.cupon.Package_response;
import com.ALife.alife.model.customer_profile_response;
import com.ALife.alife.viewmodel.Customer_profile;
import com.ALife.alife.viewmodel.SessionManagment_registration;
import com.ALife.alife.viewmodel.cuponViewmodel.CouponPackageViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_coupon_shop_coupon_package_list_fragment extends Fragment implements Customer_package_adapter.onItemClickListener {
    public int activePosition = 0;
    RecyclerView packagesView;
    String couponID, shopID, createdDate, endDate;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10, end = 0;
    CouponPackageViewModel couponPackageViewModel;
    private List<Package_response> packagesList;
    private List<customerFor_cupon_response> customerList;
    private Customer_package_adapter adapter;
    String customerID;
    Customer_profile customer_profile;
    String phone;
    String cupon_available;
    private cupon_api cupon_api;
    SessionManagment_registration sessionManagement;

    public Customer_coupon_shop_coupon_package_list_fragment(String shopID, String couponID, List<customerFor_cupon_response> customerList, String customerID, String cupon_available, String creadtedDate, String endDate) {
        this.couponID = couponID;
        this.shopID = shopID;
        this.customerList = customerList;
        this.customerID = customerID;
        this.cupon_available = cupon_available;
        this.createdDate = creadtedDate;
        this.endDate = endDate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getPosition();
        package_data();

    }

    private void package_data() {
        SessionManagment_registration sessionManagment_registration = new SessionManagment_registration(getActivity());
        String phone = sessionManagment_registration.getPhone();
        couponPackageViewModel.getPackage(couponID, shopID, phone, createdDate, endDate).observe(getViewLifecycleOwner(), new Observer<List<Package_response>>() {
            @Override
            public void onChanged(List<Package_response> package_respons) {
                packagesList = new ArrayList<>();
                packagesList = package_respons;
                Collections.sort(packagesList, new Comparator<Package_response>() {

                    @Override
                    public int compare(Package_response lhs, Package_response rhs) {
                        // TODO Auto-generated method stub

                        try {
                            Double v1 = (Double.parseDouble(lhs.getPackageSellAmount()));
                            Double v3 = (Double.parseDouble(rhs.getPackageSellAmount()));
                            return v3.compareTo(v1);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });

                List<customerFor_cupon_response> temp = new ArrayList<>();
                for (int i = 0; i < customerList.size(); i++)
                    temp.add(customerList.get(i));
                for (int i = 0; i < packagesList.size(); i++) {
                    Double sellAmount = Double.parseDouble(packagesList.get(i).getPackageSellAmount());
                    int count = 0;
                    for (int j = 0; j < temp.size(); j++) {
                        if (Double.parseDouble(temp.get(j).getSell_amount()) >= sellAmount) {
                            count++;
                            temp.remove(j);
                            j--;
                        }
                    }
                    packagesList.get(i).setMaximum_package_owner(String.valueOf(count));
                }

                adapter = new Customer_package_adapter(packagesList, cupon_available);
                adapter.setOnClickListener(Customer_coupon_shop_coupon_package_list_fragment.this::OnItemClick);
                packagesView.setAdapter(adapter);
            }
        });


    }

    private void getPosition() {
        cupon_api = ApiUtilize.cupon_response();
        Log.d("phione", sessionManagement.getPhone());
        Call<active_cupon> call = cupon_api.activeCupon(couponID, shopID, "01966928300", createdDate, endDate);
        call.enqueue(new Callback<active_cupon>() {
            @Override
            public void onResponse(Call<active_cupon> call, Response<active_cupon> response) {
                if (response.isSuccessful()) {
                    activePosition = (response.body().getPosition());

                }
            }

            @Override
            public void onFailure(Call<active_cupon> call, Throwable t) {
                Log.d("msg16", t.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_coupon_shop_coupon_package_list_fragment, container, false);
        sessionManagement = new SessionManagment_registration(getActivity());

        // Toast.makeText(getActivity(), String.valueOf(sessionManagement.getNewPhone()), Toast.LENGTH_SHORT).show();

        couponPackageViewModel = new ViewModelProvider(this).get(CouponPackageViewModel.class);

        packagesView = (RecyclerView) view.findViewById(R.id.packagesViewID);
        packagesView.setHasFixedSize(true);
        packagesView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
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

        customer_profile = new ViewModelProvider(getActivity()).get(Customer_profile.class);
        customer_profile.getData(String.valueOf(customerID)).observe(getViewLifecycleOwner(), new Observer<customer_profile_response>() {
            @Override
            public void onChanged(customer_profile_response customer_profile_response) {
                phone = customer_profile_response.getCustomer01r_phone();
            }
        });

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        List<customerFor_cupon_response> temp = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++)
            temp.add(customerList.get(i));
        List<customerFor_cupon_response> packageCustomerList = new ArrayList<>();
        int count = Integer.parseInt(packagesList.get(position).getMaximum_package_owner());
        double sellAmount = Double.parseDouble(packagesList.get(position).getPackageSellAmount());
        int packageSize = packagesList.size();
        int removecustomer = 0;
        for (int i = 0; i < position; i++) {
            removecustomer += Integer.parseInt(packagesList.get(i).getMaximum_package_owner());
        }

        if (count > 0) {
            for (int i = removecustomer; i < temp.size(); i++) {
                if (Double.parseDouble(temp.get(i).getSell_amount()) >= sellAmount) {
                    packageCustomerList.add(temp.get(i));
                }
            }
            Collections.sort(packageCustomerList, new Comparator<customerFor_cupon_response>() {

                @Override
                public int compare(customerFor_cupon_response lhs, customerFor_cupon_response rhs) {
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
        }

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.cus_frame_container, new Customer_coupon_package_customerList_fragment(customerList, phone)).addToBackStack(null).commit();

    }

}