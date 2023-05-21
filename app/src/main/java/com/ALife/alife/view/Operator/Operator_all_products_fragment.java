package com.ALife.alife.view.Operator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Operator.Operator_product_gridOff_adapter;
import com.ALife.alife.adapter.Operator.Operator_product_grid_adapter;
import com.ALife.alife.adapter.get_product_offer_adapter;
import com.ALife.alife.adapter.get_product_type_adapter;
import com.ALife.alife.model.get_product_offer_response;
import com.ALife.alife.model.get_product_response;
import com.ALife.alife.model.get_product_type_response;
import com.ALife.alife.model.get_shop_products_summary_response;
import com.ALife.alife.model.shop_profile_response;
import com.ALife.alife.view.Product_details_fragment;
import com.ALife.alife.viewmodel.Agent_all_product_summary;
import com.ALife.alife.viewmodel.Get_operator_product;
import com.ALife.alife.viewmodel.Get_product;
import com.ALife.alife.viewmodel.Get_product_offer;
import com.ALife.alife.viewmodel.Get_product_type;
import com.ALife.alife.viewmodel.Shop_products_summary;
import com.ALife.alife.viewmodel.Shop_profile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ALife.alife.R.layout.operator_product_fragment;

public class Operator_all_products_fragment extends Fragment implements Operator_product_grid_adapter.OnItemClickListener, Operator_product_gridOff_adapter.OnItemClickListener, Operator_product_gridOff_adapter.OnItemTypeListener, Operator_product_gridOff_adapter.OnItemOfferListener {
    String agent_id, shop_id;
    String product_discount_all;
    ImageView select_product_image;
    String id1, id2;
    Shop_profile shop_profile;
    ImageView vaotureImage;
    RecyclerView recyclerView1, recyclerView2;
    ToggleButton gridBUtton;
    private Operator_product_grid_adapter adapter;
    private Operator_product_gridOff_adapter grid_adapter;

    LinearLayout gridOffLayout, gridSearchLayout;
    TextView fragmentTitle;

    Get_operator_product get_all_product;
    List<get_product_response> data = new ArrayList<>();
    List<get_product_response> datagrid = new ArrayList<>();
    List<get_product_response> datagridoff = new ArrayList<>();
    private GridLayoutManager layoutmanager;
    private static final int CAMERA_REQUEST = 1;
    int check = 0, vaoture_check = 0, final_check = 0, final_vaoture_check = 0;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap, vaoture_bitmap;
    int span = 1;
    String Category_unit;
    EditText productSearch, productSearchGrid;

    private String all_product_discount;
    TextView all_profit, all_product, all_selling_price, all_item, allDiscountText;
    private int total_product;
    Double totalProfit, totalDiscount, totalSelling_price;
    int page1 = 1, page2 = 1, limit1 = 10, limit2 = 10, end1 = 0, end2 = 0;
    NestedScrollView nestedScrollView, gridNestedScrollView;
    ProgressBar progressBar;
    int state = 0;
    Agent_all_product_summary agent_all_product_summary;

    public Operator_all_products_fragment(String shop_id, String agent_id) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
    }

    public void showProduct1() {
        gridOffLayout.setVisibility(View.GONE);
        gridSearchLayout.setVisibility(View.VISIBLE);
        end1 = 0;
        page1 = 1;
        datagridoff = new ArrayList<>();
        get_product1(page1, limit1);


        productSearchGrid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(productSearchGrid.getText().toString().trim())) {
                    datagridoff = new ArrayList<>();
                    adapter = new Operator_product_grid_adapter(datagridoff, allDiscountText.getText().toString().trim());
                    page1 = 1;
                    end1 = 0;
                    get_product1(page1, limit1);
                } else {
                    try {
                        get_product_by_search(productSearchGrid.getText().toString(), 1);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void get_product_by_search(String value, int position) {
        get_all_product = new ViewModelProvider(getActivity()).get(Get_operator_product.class);
        get_all_product.getSearchData(agent_id).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                if (position == 1) {
                    data = new ArrayList<>();
                    adapter = new Operator_product_grid_adapter(data, allDiscountText.getText().toString().trim());
                    adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick);
                    recyclerView2.setAdapter(adapter);

                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    adapter = new Operator_product_grid_adapter(data, allDiscountText.getText().toString().trim());
                    adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick);
                    recyclerView2.setAdapter(adapter);

                } else if (position == 2) {
                    data = new ArrayList<>();
                    grid_adapter = new Operator_product_gridOff_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick, Operator_all_products_fragment.this::OnItemType, Operator_all_products_fragment.this::OnItemOffer);

                    recyclerView1.setAdapter(grid_adapter);
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    grid_adapter = new Operator_product_gridOff_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick, Operator_all_products_fragment.this::OnItemType, Operator_all_products_fragment.this::OnItemOffer);

                    recyclerView1.setAdapter(grid_adapter);

                }
            }
        });
    }

    public void get_product1(int page, int limit) {

        get_all_product = new ViewModelProvider(getActivity()).get(Get_operator_product.class);
        get_all_product.getData(agent_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);

                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagridoff.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end1 = 1;
                }
                data = datagridoff;
                adapter = new Operator_product_grid_adapter(datagridoff, allDiscountText.getText().toString().trim());

                //data = get_product_responses;
                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick);
                recyclerView2.setAdapter(adapter);

            }
        });
    }

    public void showProduct2() {
        gridSearchLayout.setVisibility(View.GONE);
        gridOffLayout.setVisibility(View.VISIBLE);
        page2 = 1;
        end2 = 0;
        datagrid = new ArrayList<>();
        grid_adapter = new Operator_product_gridOff_adapter(datagrid, allDiscountText.getText().toString().trim());
        data = datagrid;
        //adapter.setOnClickListener(Showdetails.this);
        grid_adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick, Operator_all_products_fragment.this::OnItemType, Operator_all_products_fragment.this::OnItemOffer);

        recyclerView1.setAdapter(grid_adapter);
        get_product2(page2, limit2);

        productSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(productSearch.getText().toString().trim())) {
                    page2 = 1;
                    end2 = 0;
                    get_product2(page2, limit2);
                } else {
                    try {
                        grid_adapter.getFilter().filter(productSearch.getText());
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void get_product2(int page, int limit) {
        get_all_product = new ViewModelProvider(getActivity()).get(Get_operator_product.class);
        get_all_product.getData(agent_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_product_response>>() {
            @Override
            public void onChanged(List<get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    datagrid = new ArrayList<>();
                    grid_adapter = new Operator_product_gridOff_adapter(datagrid, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick, Operator_all_products_fragment.this::OnItemType, Operator_all_products_fragment.this::OnItemOffer);
                    recyclerView1.setAdapter(grid_adapter);
                }
                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagrid.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end2 = 1;
                }
                data = datagrid;
                grid_adapter = new Operator_product_gridOff_adapter(datagrid, allDiscountText.getText().toString().trim());

                //adapter.setOnClickListener(Showdetails.this);
                grid_adapter.setOnClickListener(Operator_all_products_fragment.this::OnItemClick, Operator_all_products_fragment.this::OnItemType, Operator_all_products_fragment.this::OnItemOffer);
                recyclerView1.setAdapter(grid_adapter);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        get_products_summary();
        main();
        //start add product

        //end add product
    }

    private void main() {
        checkConnection();
        get_all_product_discount();


        gridBUtton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    span = 2;
                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);

                    showProduct1();

                } else {
                    span = 1;
                    layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutmanager);
                    showProduct2();
                }
            }
        });
    }

    private void get_all_product_discount() {
        shop_profile = new ViewModelProvider(getActivity()).get(Shop_profile.class);
        shop_profile.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<shop_profile_response>() {
            @Override
            public void onChanged(shop_profile_response shop_profile_response) {
                //product_discount_all=shop_profile_response.getAll_discount();
                allDiscountText.setText(shop_profile_response.getAll_discount());
                if (span == 2) {
                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);
                    showProduct1();
                } else {
                    if (state == 0) {
                        layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                        recyclerView1.setLayoutManager(layoutmanager);
                        gridOffLayout.setVisibility(View.GONE);
                        state = 1;
                        showProduct2();
                    }
                }
            }
        });

    }

    private void get_products_summary() {
        agent_all_product_summary = new ViewModelProvider(getActivity()).get(Agent_all_product_summary.class);
        agent_all_product_summary.getSummary(shop_id, agent_id).observe(getViewLifecycleOwner(), new Observer<get_shop_products_summary_response>() {
            @Override
            public void onChanged(get_shop_products_summary_response get_shop_products_summary_response) {
                all_item.setText(String.valueOf(get_shop_products_summary_response.getAll_product()));
                all_profit.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_profit())));
                all_selling_price.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_sell_price())));
                all_product.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_stock())));
                main();
            }
        });
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(operator_product_fragment, container, false);
        checkConnection();
        recyclerView1 = view.findViewById(R.id.recyclerViewID);
        recyclerView2 = view.findViewById(R.id.recyclerView2ID);

        gridBUtton = (ToggleButton) view.findViewById(R.id.toggleButtonID);
        gridOffLayout = (LinearLayout) view.findViewById(R.id.gridOffLayoutID);
        gridSearchLayout = (LinearLayout) view.findViewById(R.id.gridLayoutID);
        fragmentTitle = (TextView) view.findViewById(R.id.fragmentTitleID);
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        productSearch = (EditText) view.findViewById(R.id.productSearchID);
        productSearchGrid = (EditText) view.findViewById(R.id.gridProductSearchID);
        all_product = (TextView) view.findViewById(R.id.totalProductsID);
        all_item = (TextView) view.findViewById(R.id.totalItemsID);
        all_profit = (TextView) view.findViewById(R.id.totalProfitID);
        all_selling_price = (TextView) view.findViewById(R.id.totalSellPriceID);
        allDiscountText = (TextView) view.findViewById(R.id.allDiscountID);

        ExtendedFloatingActionButton addProductButton = (ExtendedFloatingActionButton) view.findViewById(R.id.add_productID);
        addProductButton.setVisibility(View.GONE);
        fragmentTitle.setText("All Products");

        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {


                }
            }
        });
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                }
            }
        });
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        gridNestedScrollView = (NestedScrollView) view.findViewById(R.id.gridNestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end2 == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page2++;
                        get_product2(page2, limit2);

                    }
                }
                if (scrollY > oldScrollY) {
                    addProductButton.hide();
                } else {
                    addProductButton.show();
                }
            }
        });
        gridNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end1 == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page1++;
                        get_product1(page1, limit2);
                    }
                }
                if (scrollY > oldScrollY) {
                    addProductButton.hide();
                } else {
                    addProductButton.show();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (productSearch != null) {
            productSearch.setText("");
        }
        // gridBUtton.setChecked(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    select_product_image.setImageBitmap(bitmap);
                }
                if (vaoture_check == 1) {
                    final_vaoture_check = 1;
                    vaoture_check = 0;
                    vaoture_bitmap = (Bitmap) bundle.get("data");
                    vaotureImage.setImageBitmap(vaoture_bitmap);
                }

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        select_product_image.setImageBitmap(bitmap);
                    }
                    if (vaoture_check == 1) {
                        final_vaoture_check = 1;
                        vaoture_check = 0;
                        vaoture_bitmap = BitmapFactory.decodeStream(inputStream);
                        vaotureImage.setImageBitmap(vaoture_bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void OnItemClick(int position) {
        get_product_response clickItem = data.get(position);
        String product_description = clickItem.getProduct_description();
        String vaoture_no = clickItem.getVaoture_no();
        String vaoture_image = clickItem.getVaoture_image();
        if (TextUtils.isEmpty(product_description)) {
            product_description = "null";
        }
        if (TextUtils.isEmpty(vaoture_no)) {
            vaoture_no = "null";
        }
        if (TextUtils.isEmpty(vaoture_image)) {
            vaoture_image = "null";
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Product_details_fragment(id1, id2, clickItem.getProduct_id(), Category_unit)).addToBackStack(null).commit();


    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        // adapter.notifyDataSetChanged();
    }

    get_product_type_adapter adapter_type;

    @Override
    public void OnItemType(int position) {
        Dialog typeAlert = new Dialog(getActivity());
        typeAlert.setContentView(R.layout.type_open_alert);
        typeAlert.show();
        typeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        typeAlert.setCancelable(false);
        ImageView closeButton = typeAlert.findViewById(R.id.closeID);
        TextView noTypesText = typeAlert.findViewById(R.id.noTypesID);
        RecyclerView typeOpenRecyclerView = typeAlert.findViewById(R.id.typeOpenRecyclerViewID);

        LinearLayoutManager typeOpenLayoutmanager = new LinearLayoutManager(getActivity());

        typeOpenRecyclerView.setHasFixedSize(true);
        typeOpenRecyclerView.setLayoutManager(typeOpenLayoutmanager);
        Get_product_type get_product_type;
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        //  get_product_type_adapter adapter_type;
        //data = new ArrayList<>();
        get_product_type.getdata(data.get(position).getProduct_id()).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                if (get_product_type_responses.size() > 0) {
                    adapter_type = new get_product_type_adapter(get_product_type_responses);
                    // data = get_product_type_responses;
                    //adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                    typeOpenRecyclerView.setAdapter(adapter_type);
                } else {
                    //Toast.makeText(getActivity(), "no type availabel", Toast.LENGTH_SHORT).show();
                    typeOpenRecyclerView.setVisibility(View.GONE);
                    noTypesText.setVisibility(View.VISIBLE);

                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeAlert.dismiss();
            }
        });
    }

    Get_product_offer get_product_offer;
    get_product_offer_adapter offer_adapter;

    @Override
    public void OnItemOffer(int position) {
        Dialog offerAlert = new Dialog(getActivity());
        offerAlert.setContentView(R.layout.offer_open_alert);
        offerAlert.show();
        offerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        offerAlert.setCancelable(false);
        ImageView closeButton = offerAlert.findViewById(R.id.closeID);
        TextView noOffersText = offerAlert.findViewById(R.id.noOffersID);
        RecyclerView offerOpenRecyclerView = offerAlert.findViewById(R.id.offerOpenRecyclerViewID);

        LinearLayoutManager offerOpenLayoutmanager = new LinearLayoutManager(getActivity());

        offerOpenRecyclerView.setHasFixedSize(true);
        offerOpenRecyclerView.setLayoutManager(offerOpenLayoutmanager);
        get_product_offer = new ViewModelProvider(getActivity()).get(Get_product_offer.class);
        //offer = new ArrayList<>();
        get_product_offer.getdata(data.get(position).getProduct_id()).observe(getViewLifecycleOwner(), new Observer<List<get_product_offer_response>>() {
            @Override
            public void onChanged(List<get_product_offer_response> get_product_offer_responses) {
                if (get_product_offer_responses.size() > 0) {


                    offer_adapter = new get_product_offer_adapter(get_product_offer_responses, data.get(position).getProduct_unit(), data.get(position).getSelling_price());
                    // offer = get_product_offer_responses;
                    //offer_adapter.setOnClickListener(Product_details_fragment.this::OnItemClick);
                    offerOpenRecyclerView.setAdapter(offer_adapter);
                } else {
                    //Toast.makeText(getActivity(), "no offer availabel", Toast.LENGTH_SHORT).show();
                    offerOpenRecyclerView.setVisibility(View.GONE);
                    noOffersText.setVisibility(View.VISIBLE);

                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerAlert.dismiss();
            }
        });
    }


}
