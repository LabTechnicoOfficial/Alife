package com.alifew.alife.view.Shop;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alifew.alife.R;
import com.alifew.alife.adapter.get_gridoff_product_adapter;
import com.alifew.alife.adapter.get_product_adapter;
import com.alifew.alife.adapter.get_product_offer_adapter;
import com.alifew.alife.adapter.get_product_type_adapter;
import com.alifew.alife.model.Update_product_status_response;
import com.alifew.alife.model.delete_category_response;
import com.alifew.alife.model.get_product_offer_response;
import com.alifew.alife.model.Get_product_response;
import com.alifew.alife.model.get_product_type_response;
import com.alifew.alife.model.get_shop_products_summary_response;
import com.alifew.alife.model.push_notification_response;
import com.alifew.alife.model.set_all_discount_response;
import com.alifew.alife.model.shop_profile_response;
import com.alifew.alife.view.Product_details_fragment;
import com.alifew.alife.viewmodel.Delete_category;
import com.alifew.alife.viewmodel.Get_all_shop_product;
import com.alifew.alife.viewmodel.Get_product_offer;
import com.alifew.alife.viewmodel.Get_product_type;
import com.alifew.alife.viewmodel.Push_notification;
import com.alifew.alife.viewmodel.Set_all_discount;
import com.alifew.alife.viewmodel.Shop_products_summary;
import com.alifew.alife.viewmodel.Shop_profile;
import com.alifew.alife.viewmodel.Update_product_status;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.alifew.alife.R.layout.shop_all_product_fragment;


public class Shop_all_products_fragment extends Fragment implements get_product_adapter.OnItemClickListener, get_product_adapter.OnItemSellListener, get_gridoff_product_adapter.OnItemClickListener, get_gridoff_product_adapter.OnItemDeleteListener, get_gridoff_product_adapter.OnItemHideListener, get_gridoff_product_adapter.OnItemTypeListener, get_gridoff_product_adapter.OnItemOfferListener, get_gridoff_product_adapter.OnItemSellListener {
    String shop_id;
    String product_discount_all;
    Set_all_discount set_all_discount;
    Push_notification push_notification;
    ImageView select_product_image;
    String id1, id2;
    ImageView vaotureImage;
    RecyclerView recyclerView1, recyclerView2;
    ToggleButton gridBUtton;
    private get_product_adapter adapter;
    private get_gridoff_product_adapter grid_adapter;
    ExtendedFloatingActionButton offersButton;

    LinearLayout gridOffLayout, gridSearchLayout, setDiscountLayout;
    TextView fragmentTitle, totalBuyPriceText;

    Get_all_shop_product get_all_product;
    List<Get_product_response> data = new ArrayList<>();
    List<Get_product_response> datagrid = new ArrayList<>();
    List<Get_product_response> datagridoff = new ArrayList<>();
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
    TextView all_profit, all_product, all_selling_price, allDiscountText;
    private int total_product;
    Double totalProfit, totalDiscount, totalSelling_price;
    Shop_profile shop_profile;
    Dialog loaderDialog;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView, gridNestedScrollView;
    int page1 = 1, page2 = 1, limit1 = 10, limit2 = 10, end1 = 0, end2 = 0;
    Shop_products_summary products_summary;
    int state = 0;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        get_products_summary();
        //main();
        //start add product

        //end add product

        offersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_offer_allProducts_fragment(shop_id)).addToBackStack(null).commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_all_product_fragment, container, false);
        checkConnection();

        offersButton = (ExtendedFloatingActionButton) view.findViewById(R.id.offersButtonID);

        recyclerView1 = view.findViewById(R.id.itemView);
        recyclerView2 = view.findViewById(R.id.recyclerView2ID);

        gridBUtton = (ToggleButton) view.findViewById(R.id.toggleButtonID);

        gridOffLayout = (LinearLayout) view.findViewById(R.id.gridOffLayoutID);
        gridSearchLayout = (LinearLayout) view.findViewById(R.id.gridLayout);
        setDiscountLayout = (LinearLayout) view.findViewById(R.id.setDiscountLayoutID);

        productSearch = (EditText) view.findViewById(R.id.searchEditText);
        productSearchGrid = (EditText) view.findViewById(R.id.gridProductSearchID);

        all_product = (TextView) view.findViewById(R.id.totalProductsID);
        all_profit = (TextView) view.findViewById(R.id.totalProfitID);
        all_selling_price = (TextView) view.findViewById(R.id.totalSellPriceID);
        allDiscountText = (TextView) view.findViewById(R.id.allDiscountID);
        totalBuyPriceText = view.findViewById(R.id.totalBuyPriceID);

        fragmentTitle = (TextView) view.findViewById(R.id.fragmentTitleID);

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        fragmentTitle.setText("All Products");

        loaderDialog = new Dialog(getActivity());
        loaderDialog.setContentView(R.layout.loader);
        loaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loaderDialog.setCancelable(false);
        //loaderDialog.show();

        setDiscountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog setDiscountAlert = new Dialog(getActivity());
                setDiscountAlert.setContentView(R.layout.set_discount_alert);
                setDiscountAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                setDiscountAlert.setCancelable(false);
                setDiscountAlert.show();

                ImageView closeButton = (ImageView) setDiscountAlert.findViewById(R.id.closeID);

                TextInputEditText discountText = (TextInputEditText) setDiscountAlert.findViewById(R.id.discountTextID);
                TextInputLayout discountError = (TextInputLayout) setDiscountAlert.findViewById(R.id.discountErrorID);
                TextView doneButton = (TextView) setDiscountAlert.findViewById(R.id.doneButtonID);
                get_all_product_discount();
                discountText.setText(allDiscountText.getText().toString().trim());
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        } else {
                            String discount = discountText.getText().toString().trim();

                            discountError.setErrorEnabled(false);
                            if (TextUtils.isEmpty(discount)) {
                                discountError.setError(" ");
                            } else {
                                //do your code
                                set_all_discount = new ViewModelProvider(getActivity()).get(Set_all_discount.class);
                                push_notification = new ViewModelProvider(getActivity()).get(Push_notification.class);
                                set_all_discount.getData(shop_id, discount).observe(getViewLifecycleOwner(), new Observer<set_all_discount_response>() {
                                    @Override
                                    public void onChanged(set_all_discount_response set_all_discount_response) {
                                        if (set_all_discount_response.getMessage().equals("yess")) {
                                            push_notification.all_discount_notification(shop_id, discount).observe(getViewLifecycleOwner(), new Observer<push_notification_response>() {
                                                @Override
                                                public void onChanged(push_notification_response push_notification_response) {
                                                    if (push_notification_response.getMessage().equals("success")) {
                                                        setDiscountAlert.dismiss();
                                                        main();
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(getActivity(), "Something wrong!!! Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }

                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDiscountAlert.dismiss();
                    }
                });
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        gridNestedScrollView = (NestedScrollView) view.findViewById(R.id.gridNestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    offersButton.hide();
                } else {
                    offersButton.show();
                }
                //offersButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end2 == 0) {

                        progressBar.setVisibility(View.VISIBLE);
                        page2++;
                        get_product2(page2, limit2);
                    }

                }
            }
        });

        gridNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    offersButton.hide();
                } else {
                    offersButton.show();
                }

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (end1 == 0) {

                        progressBar.setVisibility(View.VISIBLE);
                        page1++;
                        get_product1(page1, limit2);
                    }
                }
            }
        });

        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && offersButton.getVisibility() == View.VISIBLE) {
                    offersButton.hide();
                } else if (dy < 0 && offersButton.getVisibility() != View.VISIBLE) {
                    offersButton.show();
                }
            }
        });

        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && offersButton.getVisibility() == View.VISIBLE) {
                    offersButton.hide();
                } else if (dy < 0 && offersButton.getVisibility() != View.VISIBLE) {
                    offersButton.show();
                }
            }
        });


        return view;
    }

    private void main() {
        checkConnection();
        // product_discount_all = "0";
        state = 0;
        //Toast.makeText(getActivity(),"bnbnnn",Toast.LENGTH_SHORT).show();

      /*  layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutmanager);
        gridOffLayout.setVisibility(View.GONE);*/
        get_all_product_discount();
        gridBUtton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    span = 2;
                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);
                    // loaderDialog.show();
                    showProduct1();

                } else {
                    span = 1;
                    layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutmanager);
                    //loaderDialog.show();
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
                allDiscountText.setText(shop_profile_response.getAll_discount());
                if (span == 2) {
                    layoutmanager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutmanager);
                    loaderDialog.dismiss();
                    showProduct1();
                } else {
                    if (state == 0) {
                        layoutmanager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                        recyclerView1.setLayoutManager(layoutmanager);
                        gridOffLayout.setVisibility(View.GONE);
                        state = 1;
                        loaderDialog.dismiss();
                        showProduct2();
                    }
                }
                //= shop_profile_response.getAll_discount();


            }
        });


    }

    private void get_products_summary() {
        products_summary = new ViewModelProvider(getActivity()).get(Shop_products_summary.class);
        products_summary.getData_all(shop_id).observe(getViewLifecycleOwner(), new Observer<get_shop_products_summary_response>() {
            @Override
            public void onChanged(get_shop_products_summary_response get_shop_products_summary_response) {
                all_product.setText(String.valueOf(new DecimalFormat("##").format(get_shop_products_summary_response.getAll_product())));
                all_profit.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_profit())));
                all_selling_price.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_sell_price())));
                // all_stock.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_stock())));
                // progressBar.setVisibility(View.GONE);
                totalBuyPriceText.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_buy_price())));
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
                    main();
                }
            });

        }
    }

    public Shop_all_products_fragment(String shop_id) {
        this.shop_id = shop_id;

    }


    public void showProduct1() {
        gridOffLayout.setVisibility(View.GONE);
        gridSearchLayout.setVisibility(View.VISIBLE);
        page1 = 1;
        end1 = 0;
        datagridoff = new ArrayList<>();

        get_product1(page1, limit2);


        productSearchGrid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(productSearchGrid.getText().toString().trim())) {
                    page1 = 1;
                    end1 = 0;
                    datagridoff = new ArrayList<>();
                    get_product1(page1, limit2);
                } else {
                    try {
                        // adapter.getFilter().filter(productSearchGrid.getText());
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

    private void get_product_by_search(String value, int position) {
        get_all_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_product.getSearchData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                if (position == 1) {
                    data = new ArrayList<>();
                    adapter = new get_product_adapter(data, allDiscountText.getText().toString().trim());
                    adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemSell);
                    recyclerView2.setAdapter(adapter);
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_id().contains(value) || get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                        adapter = new get_product_adapter(data, allDiscountText.getText().toString().trim());
                        adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemSell);
                        recyclerView2.setAdapter(adapter);

                    }

                } else {
                    data = new ArrayList<>();
                    grid_adapter = new get_gridoff_product_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemDelete, Shop_all_products_fragment.this::OnItemHide, Shop_all_products_fragment.this::OnItemType, Shop_all_products_fragment.this::OnItemOffer, Shop_all_products_fragment.this::OnItemSell);
                    recyclerView1.setAdapter(grid_adapter);
                    for (int i = 0; i < get_product_responses.size(); i++) {
                        String brand_code = get_product_responses.get(i).getBrand() + get_product_responses.get(i).getCode();
                        if ((get_product_responses.get(i).getProduct_id().contains(value) || get_product_responses.get(i).getProduct_name().toLowerCase().contains(value.toLowerCase())) || (get_product_responses.get(i).getBrand().toLowerCase().contains(value.toLowerCase())) || (brand_code.toLowerCase().contains(value.toLowerCase()))) {
                            data.add(get_product_responses.get(i));
                        }
                    }
                    grid_adapter = new get_gridoff_product_adapter(data, allDiscountText.getText().toString().trim());
                    grid_adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemDelete, Shop_all_products_fragment.this::OnItemHide, Shop_all_products_fragment.this::OnItemType, Shop_all_products_fragment.this::OnItemOffer, Shop_all_products_fragment.this::OnItemSell);
                    recyclerView1.setAdapter(grid_adapter);

                }
            }
        });
    }

    public void get_product1(int page, int limit) {

        get_all_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_product.getData(shop_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    datagridoff = new ArrayList<>();
                    adapter = new get_product_adapter(datagridoff, allDiscountText.getText().toString().trim());

                    // data = datagridoff;
                    //adapter.setOnClickListener(Showdetails.this);
                    adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemSell);

                    loaderDialog.dismiss();
                    recyclerView2.setAdapter(adapter);
                }
                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagridoff.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end1 = 1;
                }
                adapter = new get_product_adapter(datagridoff, allDiscountText.getText().toString().trim());

                data = datagridoff;
                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemSell);

                loaderDialog.dismiss();
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
        grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
        data = datagrid;

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
                        // grid_adapter.getFilter().filter(productSearch.getText());
                        get_product_by_search(productSearch.getText().toString().trim(), 2);
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

        get_all_product = new ViewModelProvider(getActivity()).get(Get_all_shop_product.class);
        get_all_product.getData(shop_id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<Get_product_response>>() {
            @Override
            public void onChanged(List<Get_product_response> get_product_responses) {
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    datagrid = new ArrayList<>();
                    grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
                    //data = datagrid;

                    //adapter.setOnClickListener(Showdetails.this);
                    grid_adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemDelete, Shop_all_products_fragment.this::OnItemHide, Shop_all_products_fragment.this::OnItemType, Shop_all_products_fragment.this::OnItemOffer, Shop_all_products_fragment.this::OnItemSell);


                    recyclerView1.setAdapter(grid_adapter);
                }
                for (int i = 0; i < get_product_responses.size(); i++) {
                    datagrid.add(get_product_responses.get(i));
                }
                if (get_product_responses.size() < limit) {
                    end2 = 1;
                }
                grid_adapter = new get_gridoff_product_adapter(datagrid, allDiscountText.getText().toString().trim());
                data = datagrid;

                //adapter.setOnClickListener(Showdetails.this);
                grid_adapter.setOnClickListener(Shop_all_products_fragment.this::OnItemClick, Shop_all_products_fragment.this::OnItemDelete, Shop_all_products_fragment.this::OnItemHide, Shop_all_products_fragment.this::OnItemType, Shop_all_products_fragment.this::OnItemOffer, Shop_all_products_fragment.this::OnItemSell);


                recyclerView1.setAdapter(grid_adapter);
                loaderDialog.dismiss();

            }
        });
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
        Get_product_response clickItem = data.get(position);
        String product_description = clickItem.getProduct_description();
        String vaoture_no = clickItem.getVaoture_no();
        String vaoture_image = clickItem.getVaoture_image();
        if (TextUtils.isEmpty(product_description)) {
            product_description = "";
        }
        if (TextUtils.isEmpty(vaoture_no)) {
            vaoture_no = "";
        }
        if (TextUtils.isEmpty(vaoture_image)) {
            vaoture_image = "";
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Product_details_fragment(id1, id2, clickItem.getProduct_id(), Category_unit)).addToBackStack(null).commit();


    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        // adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemDelete(int position) {
        String response_product_id = data.get(position).getProduct_id();
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.confirm_alert);
        alert.show();

        TextView yesButton = alert.findViewById(R.id.yesButton);
        TextView noButton = alert.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_category delete_category;
                delete_category = new ViewModelProvider(getActivity()).get(Delete_category.class);
                delete_category.getdelete_product(response_product_id).observe(getViewLifecycleOwner(), new Observer<delete_category_response>() {
                    @Override
                    public void onChanged(delete_category_response delete_category_response) {
                        if (delete_category_response.getMessage().equals("Product deleted successfully")) {

                            Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            alert.cancel();
                            get_products_summary();
                            //  main();  // refreshFragment();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            alert.cancel();
                        }
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });
    }


    @Override
    public void OnItemHide(int position) {
        Dialog alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.loader);
        alertCustom.show();
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);

        Get_product_response clickItem = data.get(position);
        String status = clickItem.getStatus();
        Update_product_status update_product_status;
        update_product_status = new ViewModelProvider(getActivity()).get(Update_product_status.class);
        if (status.equals("0")) {
            update_product_status.getData(clickItem.getProduct_id(), "1").observe(getViewLifecycleOwner(), new Observer<Update_product_status_response>() {
                @Override
                public void onChanged(Update_product_status_response s) {
                    //main();
                    showProduct2();
                    // data.get(position).setStatus("1");
                    //grid_adapter.notifyDataSetChanged();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            alertCustom.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 1500);
                }

            });
        } else {
            update_product_status.getData(clickItem.getProduct_id(), "0").observe(getViewLifecycleOwner(), new Observer<Update_product_status_response>() {
                @Override
                public void onChanged(Update_product_status_response s) {
                    //main();
                    // data.get(position).setStatus("0");
                    //grid_adapter.notifyDataSetChanged();
                    showProduct2();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            alertCustom.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 1500);

                }


            });
        }

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

    @Override
    public void OnItemSell(int position) {
        Get_product_response product = data.get(position);

    }
}