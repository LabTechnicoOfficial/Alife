package com.ALife.alife.view.Shop;

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

import com.ALife.alife.R;
import com.ALife.alife.adapter.Shop_admin_access_category_adapter;
import com.ALife.alife.model.Category_response;
import com.ALife.alife.model.get_shop_admin_information_response;
import com.ALife.alife.model.remove_shop_admin_category_response;
import com.ALife.alife.view.Admin.Admin_addmore_category_fragment;
import com.ALife.alife.viewmodel.Fetch_shop_admin_category;
import com.ALife.alife.viewmodel.Get_shop_admin_information;
import com.ALife.alife.viewmodel.Remove_shop_admin_category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ALife.alife.R.layout.shop_admin_details_fragment;

public class Shop_admin_details_fragment extends Fragment implements Shop_admin_access_category_adapter.OnItemRemoveListener {
    com.mikhaellopez.circularimageview.CircularImageView adminImage, image;
    TextView adminName, adminPhone, adminPermission;
    RecyclerView adminCategoryRecyclerView;
    LinearLayoutManager layoutmanager;
    LinearLayout addMoreButton;
    String shop_id, agent_id, agent_name, agent_image, agent_phone, agent_permission;
    private Shop_admin_access_category_adapter adapter;
    Fetch_shop_admin_category fetch_shop_admin_category;
    List<Category_response> data;
    Dialog alertCustom;
    Remove_shop_admin_category remove_shop_admin_category;
    private FragmentManager fragmentManager;
    Get_shop_admin_information get_shop_admin_information;

    public Shop_admin_details_fragment(String shop_id, String agent_id) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        get_shop_admin_information = new ViewModelProvider(getActivity()).get(Get_shop_admin_information.class);
        get_shop_admin_information.getData(agent_id).observe(getViewLifecycleOwner(), new Observer<get_shop_admin_information_response>() {
            @Override
            public void onChanged(get_shop_admin_information_response get_shop_admin_information_response) {
                agent_name = get_shop_admin_information_response.getAgent_name();
                agent_image = get_shop_admin_information_response.getAgent_image();
                agent_phone = get_shop_admin_information_response.getAgent_phone();
                agent_permission = get_shop_admin_information_response.getAgent_access();
                adminName.setText(agent_name);
                adminPhone.setText(agent_phone);
                Picasso.get().load(agent_image).into(adminImage);
                try {
                    if (agent_permission.equals("1")) {
                        adminPermission.setText("Assistant");
                    } else if (agent_permission.equals("2")) {
                        adminPermission.setText("Manager");
                    }
                } catch (Exception e) {

                }
            }
        });


        data = new ArrayList<>();
        fetch_shop_admin_category = new ViewModelProvider(getActivity()).get(Fetch_shop_admin_category.class);
        fetch_shop_admin_category.getSearchData(agent_id,"").observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> fetch_shop_admin_category_responses) {
                data = fetch_shop_admin_category_responses;
                adapter = new Shop_admin_access_category_adapter(data);
                adapter.setOnClickListener(Shop_admin_details_fragment.this::OnItemRemove);
                adminCategoryRecyclerView.setAdapter(adapter);
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_details_fragment, container, false);
        checkConnection();

        adminImage = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.adminImageID);
        adminName = (TextView) view.findViewById(R.id.adminNameID);
        adminPhone = (TextView) view.findViewById(R.id.adminPhoneID);
        adminPermission = (TextView) view.findViewById(R.id.adminPermissionID);
        adminCategoryRecyclerView = (RecyclerView) view.findViewById(R.id.adminCategoryViewID);
        addMoreButton = (LinearLayout) view.findViewById(R.id.addMoreID);

        adminCategoryRecyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        adminCategoryRecyclerView.setLayoutManager(layoutmanager);

        fragmentManager = getFragmentManager();
        adminName.setText(agent_name);
        adminPhone.setText(agent_phone);
        Picasso.get().load(agent_image).into(adminImage);


        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Admin_addmore_category_fragment(shop_id, agent_id, agent_permission, data)).addToBackStack(null).commit();
            }
        });

        adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog showImage;
                showImage = new Dialog(getActivity());
                showImage.setContentView(R.layout.shop_admin_image_alert);
                showImage.show();
                showImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                image = (com.mikhaellopez.circularimageview.CircularImageView) showImage.findViewById(R.id.adminAlertImageID);
                Picasso.get().load(agent_image).into(image);
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemRemove(int position) {
        Category_response category = data.get(position);
        String category_id = category.getCatagory01y_id();

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
                remove_shop_admin_category = new ViewModelProvider(getActivity()).get(Remove_shop_admin_category.class);
                remove_shop_admin_category.getData(agent_id, category_id).observe(getViewLifecycleOwner(), new Observer<remove_shop_admin_category_response>() {
                    @Override
                    public void onChanged(remove_shop_admin_category_response remove_shop_admin_category_response) {
                        if (remove_shop_admin_category_response.getMessage().equals("Remove successfully")) {
                            alertCustom.dismiss();
                            Toast.makeText(getActivity(), remove_shop_admin_category_response.getMessage(), Toast.LENGTH_SHORT).show();
                            refreshFragment();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentManager.beginTransaction().replace(R.id.frame_container, new Shop_admin_fragments(shop_id)).addToBackStack(null).commit();

    }
}
