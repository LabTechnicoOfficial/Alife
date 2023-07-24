package com.alifew.alife.view.Shop;

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

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_admin_adapter;
import com.alifew.alife.model.delete_shop_admin_response;
import com.alifew.alife.model.fetch_shop_admin_response;
import com.alifew.alife.model.update_shop_admin_status_response;
import com.alifew.alife.viewmodel.Delete_shop_admin;
import com.alifew.alife.viewmodel.Fetch_shop_adminList;
import com.alifew.alife.viewmodel.Update_shop_admin_status;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.shop_admin_fragments;

public class Shop_admin_fragments extends Fragment implements Shop_admin_adapter.OnItemClickListener, Shop_admin_adapter.OnItemDeleteListener, Shop_admin_adapter.OnItemActiveListener {
    private ExtendedFloatingActionButton floatingButton;
    private RecyclerView adminRecyclerviewView;
    LinearLayoutManager layoutmanager;

    private FragmentManager fragmentManager;
    private Shop_admin_adapter adapter;
    Fetch_shop_adminList fetch_shop_admin;
    Delete_shop_admin delete_shop_admin;
    Update_shop_admin_status update_shop_admin_status;
    String shop_id;
    List<fetch_shop_admin_response> data;
    Dialog alertCustom;

    public Shop_admin_fragments(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        //SessionManagment sessionManagment = new SessionManagment(getActivity());
        //shop_id = String.valueOf(sessionManagment.getSession());
        fetch_shop_admin = new ViewModelProvider(getActivity()).get(Fetch_shop_adminList.class);
        data = new ArrayList<>();
        fetch_shop_admin.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_admin_response>>() {
            @Override
            public void onChanged(List<fetch_shop_admin_response> fetch_shop_admin_responses) {
                data = fetch_shop_admin_responses;
                adapter = new Shop_admin_adapter(data, getActivity());
                adapter.setOnClickListener(Shop_admin_fragments.this::OnItemClick, Shop_admin_fragments.this::OnItemDelete, Shop_admin_fragments.this::OnItemActive);
                adminRecyclerviewView.setAdapter(adapter);
            }
        });


        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_add_admin_fragment(shop_id,data)).addToBackStack(null).commit();

            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_fragments, container, false);

        checkConnection();

        fragmentManager = getFragmentManager();

        floatingButton = (ExtendedFloatingActionButton) view.findViewById(R.id.add_adminID);
        adminRecyclerviewView = (RecyclerView) view.findViewById(R.id.adminViewID);
        adminRecyclerviewView.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(view.getContext());
        adminRecyclerviewView.setLayoutManager(layoutmanager);

        adminRecyclerviewView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatingButton.getVisibility() == View.VISIBLE) {
                    floatingButton.hide();
                } else if (dy < 0 && floatingButton.getVisibility() != View.VISIBLE) {
                    floatingButton.show();
                }
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }

    @Override
    public void OnItemClick(int position) {
        fetch_shop_admin_response admin = data.get(position);
        String agent_id = admin.getAgent_id();
        String agent_name = admin.getAgent_name();
        String agent_image = admin.getAgent_image();
        String agent_phone = admin.getAgent_phone();
        String agent_permission = admin.getAgent_access();

        if(agent_permission.equals("1")){
            fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new Shop_admin_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();
        }else if(agent_permission.equals("2")){
            fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.frame_container, new Shop_admin_manager_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();
        }
    }

    @Override
    public void OnItemDelete(int position) {
        fetch_shop_admin_response admin = data.get(position);
        String agent_id = admin.getAgent_id();

        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.delete_alert);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.show();
        TextView yesButton = (TextView) alertCustom.findViewById(R.id.yesButton);
        TextView noButton = (TextView) alertCustom.findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_shop_admin = new ViewModelProvider(getActivity()).get(Delete_shop_admin.class);
                delete_shop_admin.getData(agent_id).observe(getViewLifecycleOwner(), new Observer<delete_shop_admin_response>() {
                    @Override
                    public void onChanged(delete_shop_admin_response delete_shop_admin_response) {
                        if (delete_shop_admin_response.getMessage().equals("deleted successfully")) {
                            alertCustom.dismiss();
                            Toast.makeText(getActivity(), delete_shop_admin_response.getMessage(), Toast.LENGTH_SHORT).show();

                            refreshFragment();
                        } else {
                            Toast.makeText(getActivity(), delete_shop_admin_response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void OnItemActive(int position) {
        fetch_shop_admin_response admin = data.get(position);
        String agent_id = admin.getAgent_id();
        String agent_status = admin.getStatus();
        String value;
        if (agent_status.equals("1")) {
            value = "0";
        } else {
            value = "1";
        }
        update_shop_admin_status = new ViewModelProvider(getActivity()).get(Update_shop_admin_status.class);
        update_shop_admin_status.getData(agent_id, value).observe(getViewLifecycleOwner(), new Observer<update_shop_admin_status_response>() {
            @Override
            public void onChanged(update_shop_admin_status_response update_shop_admin_status_response) {
                if (update_shop_admin_status_response.getMessage().equals("Edited successfully")) {
                    refreshFragment();
                }
            }
        });
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
}
