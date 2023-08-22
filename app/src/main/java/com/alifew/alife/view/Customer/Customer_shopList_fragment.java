package com.alifew.alife.view.Customer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Customer.Customer_allShop_adapter;
import com.alifew.alife.adapter.Customer.Customer_shopList_adapter;
import com.alifew.alife.adapter.Customer.Customer_shop_all_due_list_adapter;
import com.alifew.alife.adapter.Normal_sell_details_image_adapter;
import com.alifew.alife.adapter.Shop_barcode_type_adapter;
import com.alifew.alife.adapter.Shop_join_request_adapter;
import com.alifew.alife.adapter.Systemetic_sell_details_adapter;
import com.alifew.alife.model.Fetch_product_detail_by_bar_code_response;
import com.alifew.alife.model.accept_cancle_shop_join_request_response;
import com.alifew.alife.model.customer_shopList_response;
import com.alifew.alife.model.fetch_shop_response;
import com.alifew.alife.model.follow_customer_shop_response;
import com.alifew.alife.model.get_customer_all_due_details_response;
import com.alifew.alife.model.image;
import com.alifew.alife.model.normal_sell_details_response;
import com.alifew.alife.model.systemetic_sell_details_response;
import com.alifew.alife.model.unfollow_customer_shop_response;
import com.alifew.alife.viewmodel.Accept_cancle_shop_join_request;
import com.alifew.alife.viewmodel.Customer_shopList;
import com.alifew.alife.viewmodel.Fetch_shop;
import com.alifew.alife.viewmodel.Fetch_shop_join_request;
import com.alifew.alife.viewmodel.Follow_customer_shop;
import com.alifew.alife.viewmodel.Get_product;
import com.alifew.alife.viewmodel.Sell_details;
import com.alifew.alife.viewmodel.Unfollow_customer_shop;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.customer_shop_list_fragment;

public class Customer_shopList_fragment extends Fragment implements Customer_allShop_adapter.OnItemFollowListener, Customer_shopList_adapter.OnItemUnfollowListener, Customer_shopList_adapter.OnItemClickListener, Shop_join_request_adapter.OnItemAcceptListener, Shop_join_request_adapter.OnItemCancelListener, Customer_shop_all_due_list_adapter.OnItemClickListener, Customer_shopList_adapter.OnBarCodeScanClickListener {

    RecyclerView yourShoplistRecyclerview, allShopRecyclerView, requestShopView, showDetailsView;
    RecyclerView.LayoutManager yourShoplayoutmanager, allShopLayoutManager, requestShopLayoutManager;
    EditText search, all_search;
    String customer_id;
    Customer_shopList customer_shopList;

    Fetch_shop fetch_shop;
    private Customer_shopList_adapter adapter;
    public Customer_allShop_adapter adapter_all;
    List<customer_shopList_response> data;
    List<fetch_shop_response> data_all;
    List<get_customer_all_due_details_response> dueList;
    List<get_customer_all_due_details_response> convertList;
    MaterialButtonToggleGroup toggleButton;
    List<fetch_shop_response> shop_request;
    Fetch_shop_join_request fetch_shop_join_request;
    Accept_cancle_shop_join_request accept_Cancel_shop_join_request;
    private Shop_join_request_adapter request_adapter;
    LinearLayout yourShopLayout, allShopLayout, defaultLayout, requestLayout, requestValueLayout;
    LinearLayout notShowDetails, showDetailsLayout;
    HorizontalScrollView detailsLayout;

    ImageView customerRequestButton, downImage, upImage;
    int bellState = 0;
    TextView requestValue, title, totalDueText;

    Boolean state = true;
    private Double total_due;
    Customer_shop_all_due_list_adapter duelistadapter;
    Sell_details sell_details;
    Systemetic_sell_details_adapter sell_details_adapter;
    List<image> imageList;
    Normal_sell_details_image_adapter image_show_adapter;

    ProgressBar yourShopProgressBar, allShopProgressBar, requestProgressBar, showDetailsProgressBar;
    NestedScrollView yourShopNestedScrollView, allShopNestedScrollView, requestNestedScrollView, showDetailsNestedScrollView;
    int page1 = 1, page2 = 1, page3 = 1, limit = 10, limit2 = 20, end1 = 0, end2 = 0;
    int select_type;

    TextView barcodeText;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    String barcodeData;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    Get_product getProductViewModel;

    public Customer_shopList_fragment(String customer_id) {
        this.customer_id = customer_id;
    }

    public void notifi() {

        fetch_shop_join_request.getData(customer_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_response>>() {
            @Override
            public void onChanged(List<fetch_shop_response> fetch_shop_responses) {
                shop_request = fetch_shop_responses;

                request_adapter = new Shop_join_request_adapter(shop_request);
                request_adapter.OnClickListener(Customer_shopList_fragment.this::OnItemAccept, Customer_shopList_fragment.this::OnItemCancel);
                requestShopView.setAdapter(request_adapter);
                if (shop_request.size() > 0) {
                    requestValueLayout.setVisibility(View.VISIBLE);
                    requestValue.setText(String.valueOf(shop_request.size()));
                } else {
                    requestValueLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        data = new ArrayList<>();
        data_all = new ArrayList<>();
        shop_request = new ArrayList<>();
        fetch_shop_join_request = new ViewModelProvider(getActivity()).get(Fetch_shop_join_request.class);
        accept_Cancel_shop_join_request = new ViewModelProvider(getActivity()).get(Accept_cancle_shop_join_request.class);
        main();

    }

    private void main() {
        notifi();
        own_shop();


        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (group.getCheckedButtonId() == R.id.yourShopID) {
                    allShopLayout.setVisibility(View.GONE);
                    yourShopLayout.setVisibility(View.VISIBLE);
                    own_shop();
                    notifi();

                } else if (group.getCheckedButtonId() == R.id.allShopID) {
                    yourShopLayout.setVisibility(View.GONE);
                    allShopLayout.setVisibility(View.VISIBLE);
                    all_shop();
                    notifi();
                }
            }
        });

        customerRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bellState == 0) {
                    bellState = 1;
                    defaultLayout.setVisibility(View.GONE);
                    requestLayout.setVisibility(View.VISIBLE);
                    title.setText("Shop's Request");
                    notifi();


                } else if (bellState == 1) {
                    bellState = 0;
                    requestLayout.setVisibility(View.GONE);
                    defaultLayout.setVisibility(View.VISIBLE);

                    title.setText("Shops");
                    notifi();
                }

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(customer_shop_list_fragment, container, false);

        yourShoplistRecyclerview = (RecyclerView) view.findViewById(R.id.YourShopViewID);
        allShopRecyclerView = (RecyclerView) view.findViewById(R.id.AllShopViewID);
        requestShopView = (RecyclerView) view.findViewById(R.id.requestShopViewID);
        showDetailsView = (RecyclerView) view.findViewById(R.id.showDetailsViewID);

        yourShopLayout = (LinearLayout) view.findViewById(R.id.yourShopLayoutID);
        allShopLayout = (LinearLayout) view.findViewById(R.id.AllShopLayoutID);
        defaultLayout = (LinearLayout) view.findViewById(R.id.defaultLayoutID);
        requestLayout = (LinearLayout) view.findViewById(R.id.requestLayoutID);
        requestValueLayout = (LinearLayout) view.findViewById(R.id.requestValueLayoutID);
        showDetailsLayout = (LinearLayout) view.findViewById(R.id.showDetailsID);
        notShowDetails = (LinearLayout) view.findViewById(R.id.notShowDetailsID);
        detailsLayout = (HorizontalScrollView) view.findViewById(R.id.detailsLayoutID);

        toggleButton = (MaterialButtonToggleGroup) view.findViewById(R.id.toggleGroupID);
        search = (EditText) view.findViewById(R.id.searchEditText);
        all_search = (EditText) view.findViewById(R.id.AllShopSearchID);
        customerRequestButton = (ImageView) view.findViewById(R.id.customerRequestButtonID);
        requestValue = (TextView) view.findViewById(R.id.requestValueID);
        title = (TextView) view.findViewById(R.id.one);
        totalDueText = (TextView) view.findViewById(R.id.totalDueID);

        downImage = (ImageView) view.findViewById(R.id.downImageID);
        upImage = (ImageView) view.findViewById(R.id.upImageID);

        yourShoplistRecyclerview.setHasFixedSize(true);
        allShopRecyclerView.setHasFixedSize(true);
        requestShopView.setHasFixedSize(true);
        showDetailsView.setHasFixedSize(true);

        yourShoplayoutmanager = new LinearLayoutManager(view.getContext());
        allShopLayoutManager = new LinearLayoutManager(view.getContext());
        requestShopLayoutManager = new LinearLayoutManager(view.getContext());

        yourShoplistRecyclerview.setLayoutManager(yourShoplayoutmanager);
        allShopRecyclerView.setLayoutManager(allShopLayoutManager);
        requestShopView.setLayoutManager(requestShopLayoutManager);
        showDetailsView.setLayoutManager(new LinearLayoutManager(getContext()));

        getProductViewModel = new ViewModelProvider(this).get(Get_product.class);

        showDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == true) {
                    downImage.setVisibility(View.GONE);
                    upImage.setVisibility(View.VISIBLE);
                    notShowDetails.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.VISIBLE);
                    dueList = new ArrayList<>();
                    page2 = 1;
                    end2 = 0;
                    duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
                    duelistadapter.SetOnClickListener(Customer_shopList_fragment.this::OnDueLick);
                    showDetailsView.setAdapter(duelistadapter);
                    due_details(page2, limit2);
                    state = false;
                } else if (state == false) {
                    upImage.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.GONE);
                    downImage.setVisibility(View.VISIBLE);
                    notShowDetails.setVisibility(View.VISIBLE);

                    state = true;
                }
            }
        });

        yourShopProgressBar = (ProgressBar) view.findViewById(R.id.YourShopProgressBarID);

        showDetailsProgressBar = (ProgressBar) view.findViewById(R.id.showDetailsProgressBarID);

        yourShopNestedScrollView = (NestedScrollView) view.findViewById(R.id.YourShopNestedRecyclerViewID);
        showDetailsNestedScrollView = (NestedScrollView) view.findViewById(R.id.showDetailsNestedRecyclerViewID);

        yourShopNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // yourShopProgressBar.setVisibility(View.VISIBLE);
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end1 == 0) {
                        yourShopProgressBar.setVisibility(View.VISIBLE);
                        page1++;
                        filter(page1, limit);
                    }

                }
            }
        });


        showDetailsNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //showDetailsProgressBar.setVisibility(View.VISIBLE);
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end2 == 0) {
                        showDetailsProgressBar.setVisibility(View.VISIBLE);
                        page2++;
                        filter(page1, limit2);
                    }

                }
            }
        });


        return view;
    }

    private void due_details(int page, int limit) {

        customer_shopList = new ViewModelProvider(getActivity()).get(Customer_shopList.class);
        customer_shopList.getDue_details(customer_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_customer_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_customer_all_due_details_response> get_customer_all_due_details_responses) {
                showDetailsProgressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_customer_all_due_details_responses.size(); i++) {
                    dueList.add(get_customer_all_due_details_responses.get(i));
                }
                if (get_customer_all_due_details_responses.size() < limit) {
                    end2 = 1;
                }
                if (dueList.size() > 0) {
                    for (int i = 0; i < dueList.size(); i++) {
                        convertList.add(i, dueList.get(dueList.size() - 1 - i));
                    }
                }
                duelistadapter = new Customer_shop_all_due_list_adapter(dueList);
                duelistadapter.SetOnClickListener(Customer_shopList_fragment.this::OnDueLick);
                showDetailsView.setAdapter(duelistadapter);
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

    public void shop_request() {
        fetch_shop_join_request.getData(customer_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_response>>() {
            @Override
            public void onChanged(List<fetch_shop_response> fetch_shop_responses) {
                shop_request = fetch_shop_responses;
                request_adapter = new Shop_join_request_adapter(shop_request);
                request_adapter.OnClickListener(Customer_shopList_fragment.this::OnItemAccept, Customer_shopList_fragment.this::OnItemCancel);
                requestShopView.setAdapter(request_adapter);

                if (shop_request.size() > 0) {
                    requestValueLayout.setVisibility(View.VISIBLE);
                    requestValue.setText(String.valueOf(shop_request.size()));
                }
            }
        });

    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    private void filter(int page, int limit) {
        customer_shopList = new ViewModelProvider(getActivity()).get(Customer_shopList.class);
        customer_shopList.getData(customer_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<customer_shopList_response>>() {
            @Override
            public void onChanged(List<customer_shopList_response> customer_shopList_responses) {
                yourShopProgressBar.setVisibility(View.GONE);
                //data = customer_shopList_responses;
                for (int i = 0; i < customer_shopList_responses.size(); i++) {
                    data.add(customer_shopList_responses.get(i));
                }
                if (customer_shopList_responses.size() < limit) {
                    end1 = 1;
                }
                total_due = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    total_due += Double.parseDouble(data.get(i).getTotal_due());
                }
                totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(total_due)));
                adapter = new Customer_shopList_adapter(data);
                adapter.setOnClickListener(Customer_shopList_fragment.this::OnItemUnfollow, Customer_shopList_fragment.this::OnItemClick, Customer_shopList_fragment.this::OnBarCodeScanClick);
                yourShoplistRecyclerview.setAdapter(adapter);
            }
        });

    }

    private void filter_all() {
        fetch_shop = new ViewModelProvider(getActivity()).get(Fetch_shop.class);
        fetch_shop.getData().observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_response>>() {
            @Override
            public void onChanged(List<fetch_shop_response> fetch_shop_responses) {
                data_all = fetch_shop_responses;
                for (int i = 0; i < data_all.size(); i++) {
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).getStore01e_id().equals(data_all.get(i).getStore01e_id())) {
                            data_all.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                adapter_all = new Customer_allShop_adapter(data_all);
                adapter_all.setOnClickListener(Customer_shopList_fragment.this::OnItemFollow);
                allShopRecyclerView.setAdapter(adapter_all);
            }
        });
    }

    private void own_shop() {
        end1 = 0;
        page1 = 1;
        select_type = 1;
        data = new ArrayList<>();
        adapter = new Customer_shopList_adapter(data);
        adapter.setOnClickListener(Customer_shopList_fragment.this::OnItemUnfollow, Customer_shopList_fragment.this::OnItemClick, Customer_shopList_fragment.this::OnBarCodeScanClick);
        yourShoplistRecyclerview.setAdapter(adapter);
        filter(page1, limit);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        //adapter.getFilter().filter(search.getText());
                        data = new ArrayList<>();
                        adapter = new Customer_shopList_adapter(data);
                        adapter.setOnClickListener(Customer_shopList_fragment.this::OnItemUnfollow, Customer_shopList_fragment.this::OnItemClick, Customer_shopList_fragment.this::OnBarCodeScanClick);
                        yourShoplistRecyclerview.setAdapter(adapter);
                        get_search_shop(search.getText().toString().trim());
                    } catch (Exception e) {
                    }
                } else {
                    page1 = 1;
                    end1 = 0;
                    select_type = 1;
                    data = new ArrayList<>();
                    adapter = new Customer_shopList_adapter(data);
                    adapter.setOnClickListener(Customer_shopList_fragment.this::OnItemUnfollow, Customer_shopList_fragment.this::OnItemClick, Customer_shopList_fragment.this::OnBarCodeScanClick);
                    yourShoplistRecyclerview.setAdapter(adapter);
                    filter(page1, limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void get_search_shop(String value) {
        customer_shopList = new ViewModelProvider(getActivity()).get(Customer_shopList.class);
        customer_shopList.getSearchData(customer_id, value).observe(getViewLifecycleOwner(), new Observer<List<customer_shopList_response>>() {
            @Override
            public void onChanged(List<customer_shopList_response> customer_shopList_responses) {
                data = customer_shopList_responses;
                adapter = new Customer_shopList_adapter(data);
                adapter.setOnClickListener(Customer_shopList_fragment.this::OnItemUnfollow, Customer_shopList_fragment.this::OnItemClick, Customer_shopList_fragment.this::OnBarCodeScanClick);
                yourShoplistRecyclerview.setAdapter(adapter);
            }
        });
    }

    private void all_shop() {
        filter_all();
        all_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(all_search.getText().toString()))) {
                    try {
                        adapter_all.getFilter().filter(all_search.getText());
                    } catch (Exception e) {

                    }
                } else {
                    filter_all();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void OnItemFollow(int position) {
        fetch_shop_response clickItem = data_all.get(position);
        String shop_id = clickItem.getStore01e_id();
        Follow_customer_shop follow_customer_shop;
        follow_customer_shop = new ViewModelProvider(getActivity()).get(Follow_customer_shop.class);
        follow_customer_shop.getData(shop_id, customer_id).observe(getViewLifecycleOwner(), new Observer<follow_customer_shop_response>() {
            @Override
            public void onChanged(follow_customer_shop_response follow_customer_shop_response) {
                if (follow_customer_shop_response.getMessage().equals("Shop added successfully")) {
                    refreshFragment();
                }
            }
        });
    }

    @Override
    public void OnItemUnfollow(int position) {
        customer_shopList_response clickItem = data.get(position);
        String shop_id = clickItem.getStore01e_id();
        Double total_due = Double.parseDouble(clickItem.getTotal_due());
        if (total_due <= 0) {
            Unfollow_customer_shop unfollow_customer_shop;
            unfollow_customer_shop = new ViewModelProvider(getActivity()).get(Unfollow_customer_shop.class);
            unfollow_customer_shop.getData(shop_id, customer_id).observe(getViewLifecycleOwner(), new Observer<unfollow_customer_shop_response>() {
                @Override
                public void onChanged(unfollow_customer_shop_response unfollow_customer_shop_response) {
                    if (unfollow_customer_shop_response.getMessage().equals("Unfollow successfully")) {
                        refreshFragment();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Due Remaining.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnItemClick(int position) {
        customer_shopList_response clickItem = data.get(position);
        String shop_id = clickItem.getStore01e_id();
        String shop_name = clickItem.getStore01e_name();
        String shop_location = clickItem.getStore01e_location();
        String shop_phone = clickItem.getStore01e_phone();
        String shop_image = clickItem.getStore01e_image();
        // String total_due=clickItem.getTotal_due();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_shop_details_fragment(customer_id, shop_id, shop_name, shop_location, shop_phone, shop_image)).addToBackStack(null).commit();

    }

    @Override
    public void OnItemAccept(int position) {
        fetch_shop_response request = shop_request.get(position);
        String shop_id = request.getStore01e_id();
        accept_Cancel_shop_join_request.getData1(customer_id, shop_id).observe(getActivity(), new Observer<accept_cancle_shop_join_request_response>() {
            @Override
            public void onChanged(accept_cancle_shop_join_request_response accept_Cancel_shop_join_request_response) {
                if (accept_Cancel_shop_join_request_response.getMessage().equals("Request Accepted")) {
                    notifi();

                }
            }
        });

    }

    @Override
    public void OnItemCancel(int position) {
        fetch_shop_response request = shop_request.get(position);
        String shop_id = request.getStore01e_id();
        accept_Cancel_shop_join_request.getData2(customer_id, shop_id).observe(getActivity(), new Observer<accept_cancle_shop_join_request_response>() {
            @Override
            public void onChanged(accept_cancle_shop_join_request_response accept_Cancel_shop_join_request_response) {
                if (accept_Cancel_shop_join_request_response.getMessage().equals("Canceld successfully")) {
                    notifi();

                }
            }
        });
    }

    @Override
    public void OnDueLick(int position) {

        get_customer_all_due_details_response due = dueList.get(position);
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
        } else {
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
    public void OnBarCodeScanClick(int position) {
        customer_shopList_response response = data.get(position);
        String shop_id = response.getStore01e_id();

        Dialog barCodeAlert = new Dialog(getActivity());
        barCodeAlert.setContentView(R.layout.barcode_scan_alert);
        barCodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        barCodeAlert.setCancelable(false);
        barCodeAlert.show();

        Window window = barCodeAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        AppCompatButton okButton = barCodeAlert.findViewById(R.id.ok);
        AppCompatButton reScanButton = barCodeAlert.findViewById(R.id.reScanButton);
        ImageView closeButton = barCodeAlert.findViewById(R.id.closeButtonID);
        barcodeText = barCodeAlert.findViewById(R.id.barcode_text);
        surfaceView = barCodeAlert.findViewById(R.id.surface_view);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (barcodeData.isEmpty()) {
                    Toast.makeText(getActivity(), "no barcode detected", Toast.LENGTH_SHORT).show();
                } else {
                    getProductViewModel.fetch_product_detail_by_bar_code(shop_id, barcodeData).observe(getViewLifecycleOwner(), new Observer<Fetch_product_detail_by_bar_code_response>() {

                        @Override
                        public void onChanged(Fetch_product_detail_by_bar_code_response response) {

                            if (response != null) {
                                barCodeAlert.dismiss();
                                vieProductDetails(response);
                            } else {
                                Toast.makeText(getActivity(), "কোন পণ্য পাওয়া যাইনি", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        reScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialiseDetectorsAndSources();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barCodeAlert.dismiss();
            }
        });

        initialiseDetectorsAndSources();

    }

    private void initialiseDetectorsAndSources() {
        barcodeData = "";
        barcodeText.setText(barcodeData);

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1080, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getActivity(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                // toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                // barcodeDetector.release();
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                // toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }

    private void vieProductDetails(Fetch_product_detail_by_bar_code_response response) {
        Dialog productDetailsAlert = new Dialog(getActivity());
        productDetailsAlert.setContentView(R.layout.product_details_from_bar_code);
        productDetailsAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        productDetailsAlert.setCancelable(false);
        productDetailsAlert.show();

        Window window = productDetailsAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = productDetailsAlert.findViewById(R.id.closeButton);
        ImageView productImage = productDetailsAlert.findViewById(R.id.productImage);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDetailsAlert.dismiss();
            }
        });


        ImageHelper.imageLoader(getActivity(),  productImage, response.productImage);

        TextView titleText = productDetailsAlert.findViewById(R.id.titleText);
        TextView categoryTitleText = productDetailsAlert.findViewById(R.id.categoryTitleText);
        TextView buyPriceText = productDetailsAlert.findViewById(R.id.buyPriceText);
        TextView sellPriceText = productDetailsAlert.findViewById(R.id.sellPriceText);
        TextView stockAmountText = productDetailsAlert.findViewById(R.id.stockAmountText);

        categoryTitleText.setText(Html.fromHtml("Category: " + "<b>" + response.category.catagory01yName + "<b>"));
        titleText.setText(response.productName);
        buyPriceText.setText(getActivity().getResources().getText(R.string.buy_price) + ": " + response.buyPrice + " tk");
        buyPriceText.setVisibility(View.INVISIBLE);
        sellPriceText.setText(getActivity().getResources().getText(R.string.sell_price) + ": " + response.sellingPrice + " tk");
        stockAmountText.setText("Stock: " + response.stockAmount + " " + response.productUnit);

        ConstraintLayout typeLayout = productDetailsAlert.findViewById(R.id.typeLayout);
        RecyclerView typeView = productDetailsAlert.findViewById(R.id.typeView);
        typeView.setHasFixedSize(true);
        typeView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (response.type.size() > 0) {

            // Log.d("dataxx", "page: "+String.valueOf(response.type.size()));
            typeLayout.setVisibility(View.VISIBLE);
            Shop_barcode_type_adapter adapter = new Shop_barcode_type_adapter(response.type, response.productUnit);
            typeView.setAdapter(adapter);
        } else {
            typeLayout.setVisibility(View.GONE);
        }
    }
}
