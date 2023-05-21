package com.ALife.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_admin_category_adapter;
import com.ALife.alife.model.Category_response;
import com.ALife.alife.model.admin_access;
import com.ALife.alife.model.fetch_shop_admin_response;
import com.ALife.alife.viewmodel.Category_fetch;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.ALife.alife.R.layout.shop_admin_categories_fragments;

public class Shop_admin_categories_fragment extends Fragment implements Shop_admin_category_adapter.OnItemCheckListener {
    String imageData, adminName, adminPhone, adminPassword;

    ExtendedFloatingActionButton floatingActionButton;
    RecyclerView categotyView;
    private LinearLayoutManager layoutmanager;
    private String shop_id;
    List<Category_response> data;
    List<admin_access> access_category;
    private List<fetch_shop_admin_response> adminList;
    private Shop_admin_category_adapter adapter;
    ImageView backButton;
    TextView selectAllButton;
    int checkBoxState = 0;
    private FragmentManager fragmentManager;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page=1,limit=10;
    Category_fetch category_fetch;
    public Shop_admin_categories_fragment(String shop_id, String imageData, String adminName, String adminPhone, String adminPassword, List<fetch_shop_admin_response> adminList) {
        this.shop_id = shop_id;
        this.imageData = imageData;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminPassword = adminPassword;
        this.adminList = adminList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (access_category.size() > 0) {
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frame_container, new Shop_admin_permission_fragment(shop_id, imageData, adminName, adminPhone, adminPassword, access_category, adminList)).addToBackStack(null).commit();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

    }

    private void main() {
        checkConnection();
        //SessionManagment sessionManagment = new SessionManagment(getActivity());
        //shop_id = String.valueOf(sessionManagment.getSession());


        data = new ArrayList<>();
        access_category = new ArrayList<>();
        page=1;
        getCategory(page,limit);

    }
    private void getCategory(int Page,int Limit)
    {
        category_fetch = new ViewModelProvider(getActivity()).get(Category_fetch.class);
        category_fetch.getdata(shop_id,1,10).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                progressBar.setVisibility(View.GONE);
                for(int i=0;i<category_responses.size();i++)
                {
                    data.add(category_responses.get(i));
                }
                //data = category_responses;
                checkBoxState = 0;
                adapter = new Shop_admin_category_adapter(data, checkBoxState);
                adapter.setOnCheckedChangeListener(Shop_admin_categories_fragment.this::OnItemCheck);
                // adapter.setOnChecked
                categotyView.setAdapter(adapter);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_categories_fragments, container, false);

        floatingActionButton = (ExtendedFloatingActionButton) view.findViewById(R.id.flaotingActionButtonID);
        categotyView = (RecyclerView) view.findViewById(R.id.categoryViewID);
        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        selectAllButton = (TextView) view.findViewById(R.id.selectAllID);

        fragmentManager = getFragmentManager();

        categotyView.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(view.getContext());
        categotyView.setLayoutManager(layoutmanager);

        categotyView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide();
                } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show();
                }
            }
        });

        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxState == 0) {
                    selectAllButton.setText("unselect all");
                    checkBoxState = 1;
                    adapter = new Shop_admin_category_adapter(data, checkBoxState);
                    adapter.setOnCheckedChangeListener(Shop_admin_categories_fragment.this::OnItemCheck);

                    categotyView.setAdapter(adapter);
                    access_category.removeAll(access_category);
                    admin_access access;
                    //access=new admin_access();

                    for (int i = 0; i < data.size(); i++) {
                        access = new admin_access();
                        access.setId(data.get(i).getCatagory01y_id());
                        access_category.add(access);
                    }
                } else if (checkBoxState == 1) {
                    selectAllButton.setText("select all");
                    checkBoxState = 0;
                    adapter = new Shop_admin_category_adapter(data, checkBoxState);
                    adapter.setOnCheckedChangeListener(Shop_admin_categories_fragment.this::OnItemCheck);

                    categotyView.setAdapter(adapter);
                    access_category.removeAll(access_category);

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_add_admin_fragment(shop_id, adminList)).addToBackStack(null).commit();

            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getCategory(page, limit);
                }
                if (scrollY > oldScrollY) {
                    floatingActionButton.hide();
                } else {
                    floatingActionButton.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(getActivity());
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();
                    refreshFragment();
                }
            });
        }
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public String OnItemCheck(int position, String status) {
        Category_response category = data.get(position);
        //Toast.makeText(getActivity(), "mesba", Toast.LENGTH_SHORT).show();
        String x = "5";

        if (status.equals("yess")) {
            admin_access access = new admin_access();
            access.setId(category.getCatagory01y_id());
            access_category.add(access);
            //Toast.makeText(getActivity(), "yess", Toast.LENGTH_SHORT).show();
            x = String.valueOf(access_category.size());
        } else if (status.equals("no")) {
            for (int i = 0; i < access_category.size(); i++) {
                if (access_category.get(i).getId().equals(category.getCatagory01y_id())) {
                    access_category.remove(i);
                    break;
                }
            }
            Toast.makeText(getActivity(), String.valueOf(access_category.size()), Toast.LENGTH_SHORT).show();
            x = String.valueOf(access_category.size());
        }
        return x;
    }
}
