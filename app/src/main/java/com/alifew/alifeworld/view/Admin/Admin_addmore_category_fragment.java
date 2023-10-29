package com.alifew.alifeworld.view.Admin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Shop_admin_category_adapter;
import com.alifew.alifeworld.model.Category_response;
import com.alifew.alifeworld.model.add_shop_admin_access_response;
import com.alifew.alifeworld.model.admin_access;
import com.alifew.alifeworld.view.Shop.Shop_admin_details_fragment;
import com.alifew.alifeworld.view.Shop.Shop_admin_manager_details_fragment;
import com.alifew.alifeworld.viewmodel.Add_shop_admin_access;
import com.alifew.alifeworld.viewmodel.Category_fetch;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.alifeworld.R.layout.shop_admin_categories_fragments;

public class Admin_addmore_category_fragment extends Fragment implements Shop_admin_category_adapter.OnItemCheckListener {
    String shop_id, agent_id, agent_name, agent_image, agent_phone, agent_permission;
    List<Category_response> data;
    List<Category_response> exist_category;
    List<admin_access> access_category;
    private FragmentManager fragmentManager;
    ExtendedFloatingActionButton floatingActionButton;
    RecyclerView categotyView;
    private LinearLayoutManager layoutmanager;
    TextView selectAllButton;
    ImageView backButton;
    private Shop_admin_category_adapter adapter;
    int checkBoxState = 0;
    Dialog alertCustom;
    Add_shop_admin_access add_shop_admin_access;
    int token = 0;

    public Admin_addmore_category_fragment(String shop_id, String agent_id, String agent_permission, List<Category_response> exist_category) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
        this.exist_category = exist_category;
        this.agent_permission = agent_permission;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        Category_fetch category_fetch;
        category_fetch = new ViewModelProvider(getActivity()).get(Category_fetch.class);
        data = new ArrayList<>();
        access_category = new ArrayList<>();
        category_fetch.getdata(shop_id, 1, 10).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                data = category_responses;
                checkBoxState = 0;
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < exist_category.size(); j++) {
                        if (exist_category.get(j).getCatagory01y_id().equals(data.get(i).getCatagory01y_id())) {
                            data.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                adapter = new Shop_admin_category_adapter(data, checkBoxState);
                adapter.setOnCheckedChangeListener(Admin_addmore_category_fragment.this::OnItemCheck);
                categotyView.setAdapter(adapter);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.show();
                if (access_category.size() == 0) {
                    alertCustom.dismiss();
                    Toast.makeText(getActivity(), "select category", Toast.LENGTH_SHORT).show();
                } else {
                    add_shop_admin_access = new ViewModelProvider(getActivity()).get(Add_shop_admin_access.class);

                    for (int i = 0; i < access_category.size(); i++) {
                        if (token == 0) {
                            String category_id = access_category.get(i).getId();

                            add_shop_admin_access.getData(agent_id, category_id).observe(getViewLifecycleOwner(), new Observer<add_shop_admin_access_response>() {
                                @Override
                                public void onChanged(add_shop_admin_access_response add_shop_admin_access_response) {
                                    if (add_shop_admin_access_response.getMessage().equals("Add successfully")) {


                                    } else {
                                        // Toast.makeText(getActivity(), "something error", Toast.LENGTH_SHORT).show();
                                        token = 1;


                                    }
                                }
                            });

                        } else {
                            break;
                        }
                    }
                    if (token == 0) {
                        if (agent_permission.equals("1")) {
                            alertCustom.dismiss();
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out  // popExit
                            ).replace(R.id.frame_container, new Shop_admin_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();

                        } else if (agent_permission.equals("2")) {
                            alertCustom.dismiss();
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out  // popExit
                            ).replace(R.id.frame_container, new Shop_admin_manager_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();
                        }

                    } else {
                        Toast.makeText(getActivity(), "something error", Toast.LENGTH_SHORT).show();
                        alertCustom.dismiss();
                        token = 0;
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_categories_fragments, container, false);
        checkConnection();
        floatingActionButton = (ExtendedFloatingActionButton) view.findViewById(R.id.flaotingActionButtonID);
        categotyView = (RecyclerView) view.findViewById(R.id.categoryViewID);
        backButton = (ImageView) view.findViewById(R.id.backButton);
        selectAllButton = (TextView) view.findViewById(R.id.selectAllID);
        floatingActionButton.setText("Save");

        fragmentManager = getFragmentManager();

        categotyView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        categotyView.setLayoutManager(layoutmanager);

        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.loader);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);

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
                    adapter.setOnCheckedChangeListener(Admin_addmore_category_fragment.this::OnItemCheck);

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
                    adapter.setOnCheckedChangeListener(Admin_addmore_category_fragment.this::OnItemCheck);

                    categotyView.setAdapter(adapter);
                    access_category.removeAll(access_category);

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agent_permission.equals("1")) {
                    alertCustom.dismiss();
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frame_container, new Shop_admin_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();

                } else if (agent_permission.equals("2")) {
                    alertCustom.dismiss();
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frame_container, new Shop_admin_manager_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();
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

    private void checkConnection() {

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
