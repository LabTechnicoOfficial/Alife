package com.ALife.alife.view.Shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer.Customer_join_request_adapter;
import com.ALife.alife.adapter.Normal_sell_details_image_adapter;
import com.ALife.alife.adapter.Shop_customer_allduelist_adapter;
import com.ALife.alife.adapter.Systemetic_sell_details_adapter;
import com.ALife.alife.adapter.get_shop_allcustomer_adapter;
import com.ALife.alife.adapter.get_shop_customer_adapter;
import com.ALife.alife.model.accept_cancle_customer_join_request_response;
import com.ALife.alife.model.add_remove_shop_customer_response;
import com.ALife.alife.model.customer_registration_response;
import com.ALife.alife.model.get_shop_all_due_details_response;
import com.ALife.alife.model.get_shop_customer_response;
import com.ALife.alife.model.image;
import com.ALife.alife.model.normal_sell_details_response;
import com.ALife.alife.model.systemetic_sell_details_response;
import com.ALife.alife.viewmodel.Accept_cancle_customer_join_request;
import com.ALife.alife.viewmodel.Add_remove_shop_customer;
import com.ALife.alife.viewmodel.Customer_registration;
import com.ALife.alife.viewmodel.Fetch_all_customer;
import com.ALife.alife.viewmodel.Fetch_customer_join_request;
import com.ALife.alife.viewmodel.Sell_details;
import com.ALife.alife.viewmodel.Shop_customer;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ALife.alife.R.layout.shop_customer_list_fragments;

public class Shop_customer_list_fragments extends Fragment implements get_shop_customer_adapter.OnItemClickListener, get_shop_customer_adapter.OnRemoveItemListener, get_shop_allcustomer_adapter.OnAddItemListener, Customer_join_request_adapter.OnItemAcceptListener, Customer_join_request_adapter.OnItemCancelListener, Shop_customer_allduelist_adapter.OnItemClickListener {
    RecyclerView yourCustomerView, allCustomerView, requestCustomerView, detailsView;
    private get_shop_customer_adapter adapter;
    private get_shop_allcustomer_adapter adapter_all;
    private Customer_join_request_adapter request_adapter;
    private Shop_customer_allduelist_adapter duelist_adapter;
    Systemetic_sell_details_adapter sell_details_adapter;
    private RecyclerView.LayoutManager yourcustomerlayoutmanager, allCustomerLayoutManager, requestLayoutManager;
    Shop_customer shop_customer;
    Fetch_all_customer fetch_all_customer;
    Fetch_customer_join_request fetch_customer_join_request;
    Accept_cancle_customer_join_request accept_Cancel_customer_join_request;
    Sell_details sell_details;
    LinearLayout allCustomerLayout, defaultLayout, requestLayout, requestValueLayout;
    RelativeLayout yourCustomerLayout;
    MaterialButtonToggleGroup toggleGroup;
    EditText search, all_search;
    String id, category_id;
    List<get_shop_customer_response> data;
    List<get_shop_customer_response> data_all;
    List<get_shop_customer_response> selected_data;
    List<get_shop_customer_response> temp;
    List<get_shop_customer_response> customer_request;
    List<get_shop_all_due_details_response> dueList;
    List<get_shop_all_due_details_response> convertList;
    TextView requestValue, title, totalDueText;
    ExtendedFloatingActionButton addCustomerButton;
    private Double total_due;
    Dialog customCustomerAlert;
    LinearLayout showDetailsButton;
    Boolean showDetailsState = true;
    RelativeLayout notShowDetails;
    HorizontalScrollView detailsLayout;

    ImageView bellButton;
    int bellState = 0;

    CircularImageView customerImage;
    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    int token = 0;
    String imgdata;
    Customer_registration customer_registration;
    ImageView downImage, upImage;
    private List<image> imageList;
    private Normal_sell_details_image_adapter image_show_adapter;

    ProgressBar progressBar, progressBar2, showDetailsProgressBar;
    NestedScrollView nestedScrollView, nestedScrollView2, nestedScrollView3, showDetailsNestedScrollView;
    int page1 = 1, page2 = 1, page3 = 1, limit = 10, limit2 = 20, end1 = 0, end3 = 0;
    int select_type;

    public Shop_customer_list_fragments(String id) {
        this.id = id;
    }

    public void notifi() {

        fetch_customer_join_request.getData(id).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_response> get_shop_customer_responses) {
                customer_request = get_shop_customer_responses;

                request_adapter = new Customer_join_request_adapter(customer_request);
                request_adapter.ClickListener(Shop_customer_list_fragments.this::OnItemAccept, Shop_customer_list_fragments.this::OnItemCancel);
                requestCustomerView.setAdapter(request_adapter);
                if (customer_request.size() > 0) {
                    //requestValueLayout.setVisibility(View.VISIBLE);
                    //requestValue.setText(String.valueOf(customer_request.size()));
                } else {
                    // requestValueLayout.setVisibility(View.GONE);
                }

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data = new ArrayList<>();
        data_all = new ArrayList<>();
        main();
    }

    private void main() {
        checkConnection();
        fetch_customer_join_request = new ViewModelProvider(getActivity()).get(Fetch_customer_join_request.class);
        notifi();
        //show();
        accept_Cancel_customer_join_request = new ViewModelProvider(getActivity()).get(Accept_cancle_customer_join_request.class);
        //  customer_request();
        // Toast.makeText(getActivity(),String.valueOf(customer_request.size()),Toast.LENGTH_SHORT).show();
        own_customer();
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (group.getCheckedButtonId() == R.id.yourCustomerID) {
                    allCustomerLayout.setVisibility(View.GONE);
                    yourCustomerLayout.setVisibility(View.VISIBLE);
                    notifi();
                    own_customer();

                } else if (group.getCheckedButtonId() == R.id.allCustomerID) {
                    yourCustomerLayout.setVisibility(View.GONE);
                    allCustomerLayout.setVisibility(View.VISIBLE);
                    notifi();
                    all_customer();
                }
            }
        });
        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showDetailsState == true) {
                    downImage.setVisibility(View.GONE);
                    upImage.setVisibility(View.VISIBLE);
                    notShowDetails.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.VISIBLE);
                    page3 = 1;
                    end3 = 0;
                    dueList = new ArrayList<>();
                    duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
                    duelist_adapter.SetOnClickListener(Shop_customer_list_fragments.this::OnDueLick);
                    detailsView.setAdapter(duelist_adapter);

                    due_details(page3, limit2);
                    showDetailsState = false;
                } else if (showDetailsState == false) {
                    upImage.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.GONE);
                    downImage.setVisibility(View.VISIBLE);
                    notShowDetails.setVisibility(View.VISIBLE);

                    showDetailsState = true;
                }
            }
        });

      /*  bellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bellState == 0) {
                    bellState = 1;
                    defaultLayout.setVisibility(View.GONE);
                    requestLayout.setVisibility(View.VISIBLE);
                    notifi();

                    title.setText("Customer's Request");
                    //  customer_request();
                } else if (bellState == 1) {
                    bellState = 0;
                    requestLayout.setVisibility(View.GONE);
                    defaultLayout.setVisibility(View.VISIBLE);
                    notifi();
                    title.setText("Customers");
                }

            }
        });*/
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCustomerAlert = new Dialog(getActivity());
                customCustomerAlert.setContentView(R.layout.custom_customer_alert);
                customCustomerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customCustomerAlert.setCancelable(false);
                customCustomerAlert.show();

                ImageView closeButton = (ImageView) customCustomerAlert.findViewById(R.id.closeID);
                customerImage = (CircularImageView) customCustomerAlert.findViewById(R.id.customerImageID);
                TextInputEditText customerNameText = (TextInputEditText) customCustomerAlert.findViewById(R.id.customerNameTextID);
                TextInputEditText contactText = (TextInputEditText) customCustomerAlert.findViewById(R.id.contactTextID);
                TextInputEditText locationText = (TextInputEditText) customCustomerAlert.findViewById(R.id.locationTextID);

                TextInputLayout customerNameError = (TextInputLayout) customCustomerAlert.findViewById(R.id.customerNameErrorID);
                TextInputLayout contactError = (TextInputLayout) customCustomerAlert.findViewById(R.id.contactErrorID);
                TextInputLayout locationError = (TextInputLayout) customCustomerAlert.findViewById(R.id.locationErrorID);

                TextView doneButton = (TextView) customCustomerAlert.findViewById(R.id.doneButtonID);

                customerImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageSelect();
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String customerName = customerNameText.getText().toString().trim();
                        String contact = contactText.getText().toString().trim();
                        String location = locationText.getText().toString().trim();

                        customerNameError.setErrorEnabled(false);
                        contactError.setErrorEnabled(false);
                        locationError.setErrorEnabled(false);

                        if (TextUtils.isEmpty(customerName) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(location)) {
                            if (TextUtils.isEmpty(customerName)) {
                                customerNameError.setError(" ");
                            } else if (TextUtils.isEmpty(contact)) {
                                contactError.setError(" ");
                            } else if (TextUtils.isEmpty(location)) {
                                locationError.setError(" ");
                            }
                        } else {
                            if (final_check != 1) {
                                imgdata = "xxx";
                            } else {
                                imgdata = imgToString(bitmap);
                            }
                            customer_registration = new ViewModelProvider(getActivity()).get(Customer_registration.class);
                            customer_registration.getvarification(contact).observe(getViewLifecycleOwner(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    if (!(s.equals("yes"))) {

                                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                    } else {

                                        registration(customerName, location, contact, imgdata);

                                    }
                                }
                            });
                        }
                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customCustomerAlert.dismiss();
                    }
                });
            }
        });
    }

    private void due_details(int page, int limit) {


        shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
        shop_customer.get_dueList(id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_all_due_details_response>>() {
            @Override
            public void onChanged(List<get_shop_all_due_details_response> get_shop_all_due_details_responses) {
                progressBar2.setVisibility(View.GONE);
                for (int i = 0; i < get_shop_all_due_details_responses.size(); i++) {
                    dueList.add(get_shop_all_due_details_responses.get(i));
                }
                if (get_shop_all_due_details_responses.size() < limit) {
                    end3 = 1;
                }
                convertList = new ArrayList<>();

                for (int i = 0; i < dueList.size(); i++) {
                    convertList.add(i, dueList.get(dueList.size() - 1 - i));
                }
                duelist_adapter = new Shop_customer_allduelist_adapter(dueList);
                duelist_adapter.SetOnClickListener(Shop_customer_list_fragments.this::OnDueLick);
                detailsView.setAdapter(duelist_adapter);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    customerImage.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        customerImage.setImageBitmap(bitmap);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    private void imageSelect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    check = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    check = 1;
                    Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_customer_list_fragments, container, false);
        checkConnection();

        allCustomerView = (RecyclerView) view.findViewById(R.id.allCustomerViewID);
        yourCustomerView = (RecyclerView) view.findViewById(R.id.yourCustomerViewID);
        requestCustomerView = (RecyclerView) view.findViewById(R.id.requestCustomerViewID);
        detailsView = (RecyclerView) view.findViewById(R.id.detailsViewID);

        yourCustomerLayout = view.findViewById(R.id.yourCustomerLayoutID);
        allCustomerLayout = view.findViewById(R.id.allCustomerLayoutID);
        defaultLayout = view.findViewById(R.id.defaultLayoutID);
        requestLayout = view.findViewById(R.id.requestLayoutID);
        requestValueLayout = (LinearLayout) view.findViewById(R.id.requestValueLayoutID);
        showDetailsButton = (LinearLayout) view.findViewById(R.id.showDetailsID);
        detailsLayout = (HorizontalScrollView) view.findViewById(R.id.detailsLayoutID);

        toggleGroup = view.findViewById(R.id.toggleGroupID);
        search = (EditText) view.findViewById(R.id.searchID);
        all_search = (EditText) view.findViewById(R.id.allCustomerSearchID);

        requestValue = (TextView) view.findViewById(R.id.requestValueID);
        title = (TextView) view.findViewById(R.id.one);
        totalDueText = (TextView) view.findViewById(R.id.totalDueID);

        yourCustomerView.setHasFixedSize(true);
        allCustomerView.setHasFixedSize(true);
        requestCustomerView.setHasFixedSize(true);
        detailsView.setHasFixedSize(true);

        yourcustomerlayoutmanager = new LinearLayoutManager(view.getContext());
        allCustomerLayoutManager = new LinearLayoutManager(view.getContext());
        requestLayoutManager = new LinearLayoutManager(view.getContext());

        allCustomerView.setLayoutManager(allCustomerLayoutManager);
        yourCustomerView.setLayoutManager(yourcustomerlayoutmanager);
        requestCustomerView.setLayoutManager(requestLayoutManager);
        detailsView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        addCustomerButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addCustomerID);
        notShowDetails = view.findViewById(R.id.notShowDetailsID);

        downImage = view.findViewById(R.id.downImageID);
        upImage = view.findViewById(R.id.upImageID);


        yourCustomerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addCustomerButton.getVisibility() == View.VISIBLE) {
                    addCustomerButton.hide();
                } else if (dy < 0 && addCustomerButton.getVisibility() != View.VISIBLE) {
                    addCustomerButton.show();
                }
            }
        });


        progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2ID);
        showDetailsProgressBar = (ProgressBar) view.findViewById(R.id.showDetailsProgressBarID);

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);
        nestedScrollView2 = (NestedScrollView) view.findViewById(R.id.nestedRecyclerView2ID);
        nestedScrollView3 = (NestedScrollView) view.findViewById(R.id.nestedRecyclerView3ID);


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end1 == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page1++;
                        filter(page1, limit);

                    }
                }
            }
        });

        nestedScrollView2.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    progressBar2.setVisibility(View.VISIBLE);


                }
            }
        });

        nestedScrollView3.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end3 == 0) {
                        showDetailsProgressBar.setVisibility(View.VISIBLE);
                        page3++;
                        due_details(page3, limit2);
                    }

                }
            }
        });


        return view;
    }


    @Override
    public void OnItemClick(int position) {
        get_shop_customer_response clickItem = data.get(position);

        String customer_id = clickItem.getCustomer01r_id();
        String customer_name = clickItem.getCustomer01r_name();
        String customer_contact = clickItem.getCustomer01r_phone();
        String customer_image = clickItem.getCustomer01r_image();
        String customer_address = clickItem.getCustomer01r_address();
        String total_due = clickItem.getTotal_due();

        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_customer_details_fragments(id, customer_id, customer_name, customer_address, customer_contact, customer_image,total_due)).addToBackStack(null).commit();
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
                    main();
                    //refreshFragment();
                }
            });

        }
    }

    private void filter(int page, int limit) {
        shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
        shop_customer.getData(id, page, limit).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_response> get_shop_customer_responses) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < get_shop_customer_responses.size(); i++) {
                    data.add(get_shop_customer_responses.get(i));
                }
                if (get_shop_customer_responses.size() < limit) {
                    end1 = 1;
                }
                total_due = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    total_due += Double.parseDouble(data.get(i).getTotal_due());
                }
                adapter = new get_shop_customer_adapter(data);
                totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(total_due)));
                adapter.setOnClickListener(Shop_customer_list_fragments.this::OnItemClick, Shop_customer_list_fragments.this::OnRemoveItem);
                yourCustomerView.setAdapter(adapter);
            }
        });
    }

    private void filter_all() {
        fetch_all_customer = new ViewModelProvider(getActivity()).get(Fetch_all_customer.class);
        fetch_all_customer.getData().observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_response> get_shop_customer_responses) {
                data_all = get_shop_customer_responses;
                for (int i = 0; i < data_all.size(); i++) {
                    for (int j = 0; j < selected_data.size(); j++) {
                        if (selected_data.get(j).getCustomer01r_id().equals(data_all.get(i).getCustomer01r_id())) {
                            data_all.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                adapter_all = new get_shop_allcustomer_adapter(data_all);
                adapter_all.setOnClickListener(Shop_customer_list_fragments.this::OnAddItem);

                allCustomerView.setAdapter(adapter_all);

            }
        });
    }

    private void selected_filter() {
        shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
        selected_data = new ArrayList<>();
        shop_customer.get_selected_customer(id).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_response> get_shop_customer_responses) {

                selected_data = get_shop_customer_responses;
                filter_all();

            }
        });


    }

    private void own_customer() {
        page1 = 1;
        select_type = 1;
        end1 = 0;
        data = new ArrayList<>();
        adapter = new get_shop_customer_adapter(data);
        adapter.setOnClickListener(Shop_customer_list_fragments.this::OnItemClick, Shop_customer_list_fragments.this::OnRemoveItem);
        yourCustomerView.setAdapter(adapter);
        filter(page1, limit);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        // adapter.getFilter().filter(search.getText());
                        getSearchCustomer(search.getText().toString().trim());
                    } catch (Exception e) {
                    }
                } else {
                    page1 = 1;
                    select_type = 1;
                    end1 = 0;
                    data = new ArrayList<>();
                    adapter = new get_shop_customer_adapter(data);
                    adapter.setOnClickListener(Shop_customer_list_fragments.this::OnItemClick, Shop_customer_list_fragments.this::OnRemoveItem);
                    yourCustomerView.setAdapter(adapter);
                    filter(page1, limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void getSearchCustomer(String value) {
        shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
        shop_customer.getSearchData(id, value).observe(getViewLifecycleOwner(), new Observer<List<get_shop_customer_response>>() {
            @Override
            public void onChanged(List<get_shop_customer_response> get_shop_customer_responses) {
                data = new ArrayList<>();
                data = get_shop_customer_responses;
                adapter = new get_shop_customer_adapter(data);
                adapter.setOnClickListener(Shop_customer_list_fragments.this::OnItemClick, Shop_customer_list_fragments.this::OnRemoveItem);
                yourCustomerView.setAdapter(adapter);

            }
        });
    }

    private void all_customer() {
        selected_filter();
        // filter_all();
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
                    selected_filter();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void OnRemoveItem(int position) {
        get_shop_customer_response clickItem = data.get(position);
        String customer_id = clickItem.getCustomer01r_id();
        // if (clickItem.getTotal_due().equals("0")) {
        Add_remove_shop_customer add_remove_shop_customer;
        add_remove_shop_customer = new ViewModelProvider(getActivity()).get(Add_remove_shop_customer.class);
        add_remove_shop_customer.getDataRemove(id, customer_id).observe(getViewLifecycleOwner(), new Observer<add_remove_shop_customer_response>() {
            @Override
            public void onChanged(add_remove_shop_customer_response add_remove_shop_customer_response) {
                if (add_remove_shop_customer_response.getMessage().equals("Remove successfully")) {
                    main();// refreshFragment();
                }
            }
        });
        // } else {
        // Toast.makeText(getActivity(), "customer having due payment will not remove", Toast.LENGTH_SHORT).show();
        //}

    }

    @Override
    public void OnAddItem(int position) {
        get_shop_customer_response clickItem = data_all.get(position);
        // Toast.makeText(getActivity(),clickItem.getCustomer01r_id(),Toast.LENGTH_SHORT).show();

        String customer_id = clickItem.getCustomer01r_id();
        Add_remove_shop_customer add_remove_shop_customer;
        // Toast.makeText(getActivity(),clickItem.getCustomer01r_id(),Toast.LENGTH_SHORT).show();
        add_remove_shop_customer = new ViewModelProvider(getActivity()).get(Add_remove_shop_customer.class);
        add_remove_shop_customer.getDataAdd(id, customer_id).observe(getViewLifecycleOwner(), new Observer<add_remove_shop_customer_response>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(add_remove_shop_customer_response add_remove_shop_customer_response) {
                if (add_remove_shop_customer_response.getMessage().equals("Customer added successfully")) {
                    //Toast.makeText(getActivity(),add_remove_shop_customer_response.getMessage(),Toast.LENGTH_SHORT).show();

                    // main();
                    allCustomerLayout.setVisibility(View.GONE);
                    toggleGroup.clearChecked();
                    yourCustomerLayout.setVisibility(View.VISIBLE);
                    toggleGroup.check(R.id.yourCustomerID);

                    refreshFragment();
                }
            }
        });
    }

    @Override
    public void OnItemAccept(int position) {

        get_shop_customer_response request = customer_request.get(position);
        String customer_id = request.getCustomer01r_id();
        accept_Cancel_customer_join_request.getData1(id, customer_id).observe(getViewLifecycleOwner(), new Observer<accept_cancle_customer_join_request_response>() {
            @Override
            public void onChanged(accept_cancle_customer_join_request_response accept_Cancel_customer_join_request_response) {
                if (accept_Cancel_customer_join_request_response.getMessage().equals("Request Accepted")) {
                    notifi();

                }
            }
        });

    }

    @Override
    public void OnItemCancel(int position) {
        get_shop_customer_response request = customer_request.get(position);
        String customer_id = request.getCustomer01r_id();
        accept_Cancel_customer_join_request.getData2(id, customer_id).observe(getViewLifecycleOwner(), new Observer<accept_cancle_customer_join_request_response>() {
            @Override
            public void onChanged(accept_cancle_customer_join_request_response accept_Cancel_customer_join_request_response) {
                if (accept_Cancel_customer_join_request_response.getMessage().equals("Canceld successfully")) {
                    notifi();

                }
            }
        });
    }

    public void registration(String name, String address, String phone, String image) {
        Random r = new Random();
        int rn = r.nextInt(100) + 999999;

        customer_registration.getmessage(name, address, phone, String.valueOf(rn), image, "xxx").observe(getViewLifecycleOwner(), new Observer<customer_registration_response>() {
            @Override
            public void onChanged(customer_registration_response s) {

                if (!(s.getMessage().equals("Registration fail"))) {
                    add_customer_to_shop(s.getCustomer_id());
                } else {
                    Toast.makeText(getActivity(), s.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void add_customer_to_shop(String s) {
        Add_remove_shop_customer add_remove_shop_customer;
        add_remove_shop_customer = new ViewModelProvider(getActivity()).get(Add_remove_shop_customer.class);
        add_remove_shop_customer.getDatamanually(id, s).observe(getViewLifecycleOwner(), new Observer<add_remove_shop_customer_response>() {
            @Override
            public void onChanged(add_remove_shop_customer_response add_remove_shop_customer_response) {
                if (add_remove_shop_customer_response.getMessage().equals("Customer added successfully")) {
                    customCustomerAlert.dismiss();
                    main();
                } else {
                    Toast.makeText(getActivity(), "Something Error.Try again..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void OnDueLick(int position) {
        get_shop_all_due_details_response due = dueList.get(position);
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
}
