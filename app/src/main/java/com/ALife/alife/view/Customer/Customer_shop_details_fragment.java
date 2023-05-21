package com.ALife.alife.view.Customer;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Normal_sell_details_image_adapter;
import com.ALife.alife.adapter.Systemetic_sell_details_adapter;
import com.ALife.alife.adapter.shop_customer_due_list_adapter;
import com.ALife.alife.model.get_shop_customer_due_list_response;
import com.ALife.alife.model.image;
import com.ALife.alife.model.local_sell.get_local_sell_details_response;
import com.ALife.alife.model.normal_sell_details_response;
import com.ALife.alife.model.systemetic_sell_details_response;
import com.ALife.alife.view.Shop.Shop_customer_details_fragments;
import com.ALife.alife.viewmodel.Get_shop_customer_due_list;
import com.ALife.alife.viewmodel.Local_sell.Get_local_sell;
import com.ALife.alife.viewmodel.Push_notification;
import com.ALife.alife.viewmodel.Sell_details;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ALife.alife.R.layout.customer_shop_details_fragments;

public class Customer_shop_details_fragment extends Fragment implements shop_customer_due_list_adapter.OnDueClickListener,Normal_sell_details_image_adapter.ImageClickListener {
    ImageView shopImage;
    TextView shopName, shopLocation, contactTextview, totalDueText,total_due_title;
    String customer_id, shop_id, name, location, phone, image;
    RecyclerView dueListView;
    List<get_shop_customer_due_list_response> transactionList;
    List<get_shop_customer_due_list_response> convertList;
    Get_shop_customer_due_list get_shop_customer_due_list;
    Sell_details sell_details;
    Get_local_sell get_local_sell;
    shop_customer_due_list_adapter adapter;
    Systemetic_sell_details_adapter sell_details_adapter;
    List<image> imageList;
    Normal_sell_details_image_adapter image_show_adapter;
    ExtendedFloatingActionButton showProductsButton;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page=1,limit=20,end=0;
    public Customer_shop_details_fragment(String customer_id, String shop_id, String name, String location, String phone, String image) {
        this.customer_id = customer_id;
        this.shop_id = shop_id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.image = image;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkConnection();
        main();
    }

    private void main() {
        checkConnection();
        transactionList=new ArrayList<>();
        page=1;
        limit=20;
        end=0;
        getDuelist(page,limit);
        get_shop_customer_due_list = new ViewModelProvider(getActivity()).get(Get_shop_customer_due_list.class);
       /* get_shop_customer_due_list.getData(shop_id, customer_id,1,2).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_due_list_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_due_list_response> get_shop_customer_due_list_responses) {
                transactionList = get_shop_customer_due_list_responses;
                convertList = new ArrayList<>();
                Double all_due=0.0;
                if(transactionList.size()>0)
                {
                    all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                }
                if(all_due>=0.0)
                {
                    total_due_title.setText("মোট বাকিঃ");
                    totalDueText.setText(new DecimalFormat("##.##").format(all_due));
                }else if(all_due<0.0)
                {
                    all_due=all_due*(-1);
                    total_due_title.setText("মোট জমাঃ");
                    totalDueText.setText(new DecimalFormat("##.##").format(all_due));
                }
                //totalDueText.setText(transactionList.get(transactionList.size() - 1).getTotal_due());


                for (int i = 0; i < transactionList.size(); i++) {
                    convertList.add(i, transactionList.get(transactionList.size() - 1 - i));
                }
                adapter = new shop_customer_due_list_adapter(transactionList);
                adapter.SetOnClickListener(Customer_shop_details_fragment.this::OnDueLick);
                dueListView.setAdapter(adapter);
            }
        });*/

        showProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_categories_fragment(shop_id)).addToBackStack(null).commit();

            }
        });
    }
    public void getDuelist(int Page,int Limit)
    {
        get_shop_customer_due_list = new ViewModelProvider(getActivity()).get(Get_shop_customer_due_list.class);
        //push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
        get_shop_customer_due_list.getData(shop_id, customer_id,phone,Page,Limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_due_list_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_due_list_response> get_shop_customer_due_list_responses) {
                progressBar.setVisibility(View.GONE);

                if(get_shop_customer_due_list_responses.size()<Limit)
                {
                    end=1;
                }
                if(Page==1)
                {
                    convertList = new ArrayList<>();
                    transactionList=new ArrayList<>();
                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Customer_shop_details_fragment.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                    transactionList = get_shop_customer_due_list_responses;
                    convertList = new ArrayList<>();
                    Double all_due=0.0;
                    if(transactionList.size()>0)
                    {
                        all_due = Double.parseDouble(transactionList.get(0).getTotal_due());
                    }
                    if(all_due>=0.0)
                    {
                        total_due_title.setText("মোট বাকিঃ");
                        totalDueText.setText(new DecimalFormat("##.##").format(all_due));
                    }else if(all_due<0.0)
                    {
                        all_due=all_due*(-1);
                        total_due_title.setText("মোট জমাঃ");
                        totalDueText.setText(new DecimalFormat("##.##").format(all_due));
                    }
                    //totalDueText.setText(transactionList.get(transactionList.size() - 1).getTotal_due());


                    for (int i = 0; i < transactionList.size(); i++) {
                        convertList.add(i, transactionList.get(transactionList.size() - 1 - i));
                    }
                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Customer_shop_details_fragment.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                }else
                {
                    for(int i=0;i<get_shop_customer_due_list_responses.size();i++)
                    {
                        transactionList.add(get_shop_customer_due_list_responses.get(i));
                    }
                    adapter = new shop_customer_due_list_adapter(transactionList);
                    adapter.SetOnClickListener(Customer_shop_details_fragment.this::OnDueLick);
                    dueListView.setAdapter(adapter);
                }



            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(customer_shop_details_fragments, container, false);
        checkConnection();

        showProductsButton = (ExtendedFloatingActionButton) view.findViewById(R.id.showProductsButtonID);

        shopImage = (ImageView) view.findViewById(R.id.shopImageID);
        shopName = (TextView) view.findViewById(R.id.shopNameID);
        shopLocation = (TextView) view.findViewById(R.id.shopLocationID);
        contactTextview = (TextView) view.findViewById(R.id.contactID);
        totalDueText = (TextView) view.findViewById(R.id.totalDueID);
        total_due_title=(TextView)view.findViewById(R.id.totalDueTilte);
        nestedScrollView=(NestedScrollView)view.findViewById(R.id.nestedRecyclerViewID);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarID);
        dueListView = (RecyclerView) view.findViewById(R.id.dueViewID);
        dueListView.setHasFixedSize(true);
        dueListView.setLayoutManager(new LinearLayoutManager(getContext()));

        Picasso.get().load(image).into(shopImage);
        shopName.setText(name);
        shopLocation.setText(location);
        contactTextview.setText(phone);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    showProductsButton.hide();
                } else {
                    showProductsButton.show();
                }
                //offersButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end == 0) {

                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        getDuelist(page, limit);
                    }

                }
            }
        });
        dueListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && showProductsButton.getVisibility() == View.VISIBLE) {
                    showProductsButton.hide();
                } else if (dy < 0 && showProductsButton.getVisibility() != View.VISIBLE) {
                    showProductsButton.show();
                }
            }
        });

        return view;
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
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

    @Override
    public void OnDueLick(int position) {
        get_shop_customer_due_list_response due = transactionList.get(position);

        String sell_id = due.getSell_id();
        String sell_type = due.getSell_type();
        if (sell_type.equals("systemetic")) {
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.sell_customer_history_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            RecyclerView recyclerView = (RecyclerView) alertCustom.findViewById(R.id.productViewID);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            sell_details = new ViewModelProvider(getActivity()).get(Sell_details.class);
            sell_details.systemetic_sell_details(sell_id).observe(getViewLifecycleOwner(), new Observer<List<systemetic_sell_details_response>>() {
                @Override
                public void onChanged(List<systemetic_sell_details_response> systemetic_sell_details_responses) {
                    sell_details_adapter = new Systemetic_sell_details_adapter(systemetic_sell_details_responses);
                    recyclerView.setAdapter(sell_details_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        } else if(sell_type.equals("normally")){
            //Toast.makeText(getActivity(), sell_type, Toast.LENGTH_SHORT).show();
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.normal_sell_details_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            TextView descriptionText = (TextView) alertCustom.findViewById(R.id.descriptionTextID);
            RecyclerView multipleImages = (RecyclerView) alertCustom.findViewById(R.id.multipleImageViewID);
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            multipleImages.setHasFixedSize(true);
            multipleImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            sell_details = new ViewModelProvider(getActivity()).get(Sell_details.class);
            sell_details.normal_sell_details(sell_id).observe(getViewLifecycleOwner(), new Observer<normal_sell_details_response>() {
                @Override
                public void onChanged(normal_sell_details_response normal_sell_details_response) {
                    descriptionText.setText(normal_sell_details_response.getDescription());
                    imageList = normal_sell_details_response.getImage();
                    image_show_adapter = new Normal_sell_details_image_adapter(imageList);
                    image_show_adapter.setOnClickListener(Customer_shop_details_fragment.this::ImageClick);
                    multipleImages.setAdapter(image_show_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        }
        else if(sell_type.equals("local"))
        {
            Dialog alertCustom = new Dialog(getActivity());
            alertCustom.setContentView(R.layout.normal_sell_details_alert);
            alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertCustom.setCancelable(false);
            alertCustom.show();

            TextView descriptionText = (TextView) alertCustom.findViewById(R.id.descriptionTextID);
            RecyclerView multipleImages = (RecyclerView) alertCustom.findViewById(R.id.multipleImageViewID);
            ImageView closeButton = (ImageView) alertCustom.findViewById(R.id.closeID);

            multipleImages.setHasFixedSize(true);
            multipleImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            get_local_sell=new ViewModelProvider(getActivity()).get(Get_local_sell.class);
            get_local_sell.getDetails(sell_id).observe(getViewLifecycleOwner(), new Observer<get_local_sell_details_response>() {
                @Override
                public void onChanged(get_local_sell_details_response get_local_sell_details_response) {
                    descriptionText.setText(get_local_sell_details_response.getDescription());
                    imageList = get_local_sell_details_response.getImage();
                    image_show_adapter = new Normal_sell_details_image_adapter(imageList);
                    image_show_adapter.setOnClickListener(Customer_shop_details_fragment.this::ImageClick);
                    multipleImages.setAdapter(image_show_adapter);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertCustom.dismiss();
                }
            });
        }

    }

    @Override
    public void ImageClick(int position) {
        image item=imageList.get(position);
        String image = item.getImage();

        Dialog imageDialog = new Dialog(getActivity());
        imageDialog.setContentView(R.layout.multiple_image_show_alert);
        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageDialog.show();
        imageDialog.setCancelable(false);

        ImageView individualImage = (ImageView) imageDialog.findViewById(R.id.individualImageID);
        RelativeLayout hideLayout = (RelativeLayout) imageDialog.findViewById(R.id.hideLayoutID);

        ImageView closeButton = (ImageView) imageDialog.findViewById(R.id.closeID);
        ImageView deleteImage = (ImageView) imageDialog.findViewById(R.id.individualDeleteID);
        Picasso.get().load(image).into(individualImage);
        hideLayout.setVisibility(View.INVISIBLE);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.cancel();
            }
        });
    }
}
