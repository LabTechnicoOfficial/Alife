package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_due_customer_adapter;
import com.alifew.alife.model.add_shop_due_customer_response;
import com.alifew.alife.model.customer_registration_response;
import com.alifew.alife.model.phone_verification_response;
import com.alifew.alife.model.shop_due_customer_response;
import com.alifew.alife.viewmodel.Customer_registration;
import com.alifew.alife.viewmodel.Phone_verification;
import com.alifew.alife.viewmodel.Shop_customer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.shop_due_customer_fragment;

public class Shop_due_customer_fragment extends Fragment implements Shop_due_customer_adapter.OnItemClickListener {
    RecyclerView customerView;
    EditText search;
    TextView totalDueText, totalDueTitle, totalCustomer;
    private String shop_id;
    Shop_customer shop_customer;
    private Shop_due_customer_adapter adapter;
    private List<shop_due_customer_response> customerList;
    ExtendedFloatingActionButton addCustomerButton;
    Phone_verification phone_verification;
    Customer_registration customer_registration;
    int check = 0;
    Dialog addCustomerAlert;
    List<shop_due_customer_response> temp = new ArrayList<>();
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int page = 1, limit = 10;

    public Shop_due_customer_fragment(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomerAlert.show();

                ImageView closeButton = addCustomerAlert.findViewById(R.id.closeID);
                AppCompatButton submitButton = addCustomerAlert.findViewById(R.id.submitButtonID);
                TextInputEditText phoneText = addCustomerAlert.findViewById(R.id.contactText);
                TextInputLayout phoneError = addCustomerAlert.findViewById(R.id.phoneErrorID);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        } else {
                            String phone = phoneText.getText().toString().trim();

                            phoneError.setErrorEnabled(false);
                            if (TextUtils.isEmpty(phone)) {
                                phoneError.setError(" ");
                            } else {
                                //do code
                                for (int i = 0; i < customerList.size(); i++) {
                                    if (phone.equals(customerList.get(i).getCustomer_phone())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    Toast.makeText(getActivity(), "customer already exit", Toast.LENGTH_SHORT).show();
                                    addCustomerAlert.dismiss();
                                    main();
                                } else {
                                    phone_verification = new ViewModelProvider(getActivity()).get(Phone_verification.class);
                                    phone_verification.customer_phone(phone).observe(getViewLifecycleOwner(), new Observer<phone_verification_response>() {
                                        @Override
                                        public void onChanged(phone_verification_response phone_verification_response) {
                                            if (!phone_verification_response.getId().equals("0")) {
                                                add_due(phone_verification_response.getId(), phone);
                                            } else {
                                                // add_customer(phone);
                                                //addCustomerAlert.dismiss();
                                                //Toast.makeText(getActivity(), "Customer Not Registered", Toast.LENGTH_SHORT).show();
                                                //  main();
                                                add_due("0", phone);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCustomerAlert.dismiss();
                    }
                });
            }
        });

    }

    private void add_customer(String phone) {
        customer_registration.getmessage(" ", " ", phone, " ", " ", " ").observe(getViewLifecycleOwner(), new Observer<customer_registration_response>() {
            @Override
            public void onChanged(customer_registration_response s) {
                if (!s.getMessage().equals("Registration fail")) {
                    // add_due(s);
                } else {
                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void add_due(String id, String phone) {
        customer_registration = new ViewModelProvider(getActivity()).get(Customer_registration.class);
        customer_registration.get_add_due_customer_response(shop_id, id, phone).observe(getViewLifecycleOwner(), new Observer<add_shop_due_customer_response>() {
            @Override
            public void onChanged(add_shop_due_customer_response add_shop_due_customer_response) {
                if (add_shop_due_customer_response.getMessage().equals("Customer added successfully")) {
                    //Toast.makeText(getActivity(), add_shop_due_customer_response.getMessage(), Toast.LENGTH_SHORT).show();
                    addCustomerAlert.dismiss();
                    success_alert();
                    main();

                } else {
                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void success_alert() {
        Dialog successAlert = new Dialog(getActivity());
        successAlert.setContentView(R.layout.successful_loader);
        successAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successAlert.setCancelable(false);
        successAlert.show();

        TextView titleText = (TextView) successAlert.findViewById(R.id.titleText);
        titleText.setText("Successfully Added");

        AppCompatButton okButton = (AppCompatButton) successAlert.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successAlert.dismiss();
            }
        });
    }

    private void main() {
        checkConnection();
        check = 0;
        page = 1;
        limit = 10;
        customerList = new ArrayList<>();
        due_customer(page, limit);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        //adapter.getFilter().filter(search.getText());
                        get_customer_by_search(search.getText().toString().trim());
                    } catch (Exception e) {
                    }
                } else {
                    page = 1;
                    limit = 10;

                    due_customer(page, limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private void get_customer_by_search(String value) {
        temp = new ArrayList<>();
        adapter = new Shop_due_customer_adapter(temp);
        adapter.setOnClickListener(Shop_due_customer_fragment.this::OnItemClick);
        customerView.setAdapter(adapter);
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getCustomer_phone().toLowerCase().contains(value.toLowerCase()) || customerList.get(i).customer_name.toLowerCase().contains(value.toLowerCase()) || customerList.get(i).customer_address.toLowerCase().contains(value.toLowerCase())) {
                temp.add(customerList.get(i));
            }
        }
        adapter = new Shop_due_customer_adapter(temp);
        adapter.setOnClickListener(Shop_due_customer_fragment.this::OnItemClick);
        customerView.setAdapter(adapter);

    }

    private void due_customer(int Page, int Limit) {
        progressBar.setVisibility(View.GONE);
        if (Page == 1) {
            shop_customer = new ViewModelProvider(getActivity()).get(Shop_customer.class);
            shop_customer.get_due_customer(shop_id).observe(getViewLifecycleOwner(), new Observer<List<shop_due_customer_response>>() {
                @Override
                public void onChanged(List<shop_due_customer_response> shop_due_customer_responses) {
                    //customerList = shop_due_customer_responses;

                    totalCustomer.setText(String.valueOf(shop_due_customer_responses.size()));

                    Double due_total = 0.0;
                    temp = new ArrayList<>();
                    customerList = new ArrayList<>();
                    adapter = new Shop_due_customer_adapter(temp);
                    adapter.setOnClickListener(Shop_due_customer_fragment.this::OnItemClick);
                    customerView.setAdapter(adapter);
                    // Toast.makeText(getActivity(),String.valueOf(shop_due_customer_responses.size()),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < shop_due_customer_responses.size(); i++) {
                        customerList.add(shop_due_customer_responses.get(i));
                        if (i < Limit) {
                            temp.add(shop_due_customer_responses.get(i));
                        }
                    }
                    for (int i = 0; i < customerList.size(); i++) {
                        due_total += Double.parseDouble(customerList.get(i).getTotal_due());
                    }
                    if (due_total >= 0.0) {
                        totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(due_total)));
                    } else {
                        due_total = due_total * (-1);
                        totalDueTitle.setTextColor(0xffff0000);
                        totalDueTitle.setText("মোট জমাঃ");
                        totalDueText.setTextColor(0xffff0000);

                        totalDueText.setText(String.valueOf(new DecimalFormat("##.##").format(due_total)));
                    }

                    showList(temp);

                }
            });
        } else {
            int x = (page - 1) * 10;

            for (int i = x; i < x + limit - 1; i++) {
                if (i < customerList.size() - 1) {
                    temp.add(customerList.get(i));
                } else {
                    break;
                }
            }
            showList(temp);
        }
    }

    private void showList(List<shop_due_customer_response> temp) {
        adapter = new Shop_due_customer_adapter(temp);
        adapter.setOnClickListener(Shop_due_customer_fragment.this::OnItemClick);
        customerView.setAdapter(adapter);
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_due_customer_fragment, container, false);
        checkConnection();

        search = (EditText) view.findViewById(R.id.searchID);
        totalDueText = (TextView) view.findViewById(R.id.totalDueID);
        totalDueTitle = (TextView) view.findViewById(R.id.totalDueTitleId);
        totalCustomer = (TextView) view.findViewById(R.id.totalCustomerID);
        customerView = (RecyclerView) view.findViewById(R.id.CustomerViewID);
        customerView.setHasFixedSize(true);
        customerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        addCustomerButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addCustomerID);

        customerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        //Add Customer Alert
        addCustomerAlert = new Dialog(getActivity());
        addCustomerAlert.setContentView(R.layout.add_due_customer_alert);
        addCustomerAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addCustomerAlert.setCancelable(false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    due_customer(page, limit);
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemClick(int position) {
        shop_due_customer_response clickItem = temp.get(position);

        String customer_id = clickItem.getCustomer_id();
        String customer_name = clickItem.getCustomer_name();
        String customer_contact = clickItem.getCustomer_phone();
        String customer_image = clickItem.getCustomer_image();
        String customer_address = clickItem.getCustomer_address();
        String total_due = clickItem.getTotal_due();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_customer_details_fragments(shop_id, customer_id, customer_name, customer_address, customer_contact, customer_image, total_due)).addToBackStack(null).commit();

    }
}
