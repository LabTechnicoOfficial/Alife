package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Sub_shop_adapter;
import com.alifew.alife.model.add_sub_shop_response;
import com.alifew.alife.model.fetch_shop_response;
import com.alifew.alife.model.fetch_sub_shop_response;
import com.alifew.alife.viewmodel.Add_sub_shop;
import com.alifew.alife.viewmodel.Fetch_shop;
import com.alifew.alife.viewmodel.Fetch_sub_shop;
import com.alifew.alife.session.SessionManagement;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.sub_shop_fragment;

public class Sub_shop_fragment extends Fragment implements Sub_shop_adapter.OnItemClickListener{

    ExtendedFloatingActionButton addShopButton;
    RecyclerView recyclerView, pendingRecyclerView;
    private RecyclerView.LayoutManager layoutmanager, pendingLayoutManager;
    Fetch_shop fetch_shop;
    Add_sub_shop add_sub_shop;
    Fetch_sub_shop fetch_sub_shop;
    List<fetch_shop_response> data;
    List<fetch_sub_shop_response> sub_shop;
    private Sub_shop_adapter adapter;
    MaterialButtonToggleGroup toggleButton;

    LinearLayout approvedLayout, pendingLayout;

    FragmentManager fragmentManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        int userId;
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        userId = sessionManagement.getSession();
        fetch_shop = new ViewModelProvider(getActivity()).get(Fetch_shop.class);
        data = new ArrayList<>();
        fetch_shop.getData().observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_response>>() {
            @Override
            public void onChanged(List<fetch_shop_response> fetch_shop_responses) {
                data = fetch_shop_responses;
            }
        });
        //start show sub_shop
        fetch_sub_shop = new ViewModelProvider(getActivity()).get(Fetch_sub_shop.class);
        fetch_sub_shop.getData(String.valueOf(userId)).observe(getViewLifecycleOwner(), new Observer<List<fetch_sub_shop_response>>() {
            @Override
            public void onChanged(List<fetch_sub_shop_response> fetch_sub_shop_responses) {
                sub_shop = new ArrayList<>();
                sub_shop = fetch_sub_shop_responses;
                adapter = new Sub_shop_adapter(sub_shop);
                adapter.setOnClickListener(Sub_shop_fragment.this::OnItemClick);
                recyclerView.setAdapter(adapter);

            }
        });

        //end show sub_shop


        addShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.add_shop_form);
                alert.show();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);

                ImageView closeButton = (ImageView) alert.findViewById(R.id.closeID);
                TextView saveButton = (TextView) alert.findViewById(R.id.saveID);
                TextInputLayout subShopError = (TextInputLayout) alert.findViewById(R.id.subShopErrorID);
                TextInputEditText subShopText = (TextInputEditText) alert.findViewById(R.id.subShopTextID);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String shop_id = String.valueOf(userId);
                        if (!(TextUtils.isEmpty(subShopText.getText().toString().trim()))) {
                            if (!(shop_id.equals(subShopText.getText().toString().trim()))) {
                                int check = 0;
                                String child_id = subShopText.getText().toString().trim();
                                for (int i = 0; i < data.size(); i++) {
                                    if (child_id.equals(data.get(i).getStore01e_id())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    check = 0;

                                    Dialog dialog = new Dialog(getActivity());
                                    dialog.setContentView(R.layout.loader);
                                    dialog.show();
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.setCancelable(false);

                                    add_sub_shop = new ViewModelProvider(getActivity()).get(Add_sub_shop.class);
                                    add_sub_shop.getData(shop_id, child_id).observe(getViewLifecycleOwner(), new Observer<add_sub_shop_response>() {
                                        @Override
                                        public void onChanged(add_sub_shop_response add_sub_shop_response) {

                                            //add successfull condition here
                                            if (add_sub_shop_response.getMessage().equals("Sub_shop added successfully")) {
                                                dialog.dismiss();
                                                alert.dismiss();
                                                Toast.makeText(getActivity(), add_sub_shop_response.getMessage(), Toast.LENGTH_SHORT).show();
                                                refreshFragment();
                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                                //Log.d("ERROR::: ", add_sub_shop_response.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "no shop found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "enter valid shop id", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(sub_shop_fragment, container, false);
        checkConnection();

        fragmentManager = getFragmentManager();
        addShopButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addShopButtonID);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewID);
        pendingRecyclerView = (RecyclerView) view.findViewById(R.id.pendingRecyclerViewID);
        approvedLayout = (LinearLayout) view.findViewById(R.id.aprroveLayoutID);
        pendingLayout = (LinearLayout) view.findViewById(R.id.pendingLayoutID);

        pendingRecyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(view.getContext());
        pendingLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(layoutmanager);
        pendingRecyclerView.setLayoutManager(pendingLayoutManager);
        //search = (EditText) view.findViewById(R.id.searchID);
        toggleButton = (MaterialButtonToggleGroup) view.findViewById(R.id.toggleGroupID);


        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(group.getCheckedButtonId()==R.id.approvedID){
                    pendingLayout.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), "Approve", Toast.LENGTH_SHORT).show();
                    approvedLayout.setVisibility(View.VISIBLE);


                }else if(group.getCheckedButtonId()==R.id.pendingID){
                    approvedLayout.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), "Pending", Toast.LENGTH_SHORT).show();
                    pendingLayout.setVisibility(View.VISIBLE);

                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addShopButton.getVisibility() == View.VISIBLE) {
                    addShopButton.hide();
                } else if (dy < 0 && addShopButton.getVisibility() != View.VISIBLE) {
                    addShopButton.show();
                }
            }
        });

        pendingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addShopButton.getVisibility() == View.VISIBLE) {
                    addShopButton.hide();
                } else if (dy < 0 && addShopButton.getVisibility() != View.VISIBLE) {
                    addShopButton.show();
                }
            }
        });

        return view;
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

    private void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }

    @Override
    public void OnItemClick(int position) {
        fetch_sub_shop_response shop=sub_shop.get(position);
        String subShop_id=shop.getStore01e_id();
        String subShop_name = shop.getStore01e_name();
        String subShop_location = shop.getStore01e_location();
        String subShop_image = shop.getStore01e_image();
        fragmentManager.beginTransaction().replace(R.id.frame_container, new Sub_shop_details_fragment(subShop_id, subShop_name, subShop_location, subShop_image)).addToBackStack(null).commit();
    }
}
