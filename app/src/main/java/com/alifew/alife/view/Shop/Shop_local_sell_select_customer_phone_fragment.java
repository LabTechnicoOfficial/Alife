package com.alifew.alife.view.Shop;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alifew.alife.DB.AppDatabase;
import com.alifew.alife.DB.dao.CustomerDao;
import com.alifew.alife.DB.entity.Customer;
import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_local_sell_select_customer_adapter;
import com.alifew.alife.model.local_sell.LocalSell_property;
import com.alifew.alife.model.local_sell.customer_phone_response;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Local_sell.Get_local_sell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Shop_local_sell_select_customer_phone_fragment extends Fragment implements Shop_local_sell_select_customer_adapter.OnItemClickListener {
    private String shop_id;
    Get_local_sell get_local_sell;
    private List<customer_phone_response> phoneList;
    RecyclerView recyclerView;
    private Shop_local_sell_select_customer_adapter adapter;

    SessionManagement sessionManagement;
    CustomerDao customerDao;
    List<Customer> customerList = new ArrayList<>();

    EditText searchEditText;
    String searchKey = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_local_sell_select_customer_phone_fragment, container, false);

        initView(view);

        getPhone(searchKey);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPhone(s.toString().trim());
            }
        });


        return view;
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        //shop_id = sessionManagement.getSaveShopName()
        shop_id = String.valueOf(sessionManagement.getSession());
        // Toast.makeText(getActivity(), sessionManagement.getSaveShopName(), Toast.LENGTH_SHORT).show();
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);
        recyclerView = view.findViewById(R.id.itemView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        AppDatabase db = AppDatabase.getDatabase(getActivity());
        customerDao = db.customerDao();

        searchEditText = view.findViewById(R.id.searchEditText);
    }

    private void getPhone(String searchKey) {

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customerList = customerDao.getAllCustomer(searchKey);
                adapter = new Shop_local_sell_select_customer_adapter(customerList);
                adapter.setOnClickListener(Shop_local_sell_select_customer_phone_fragment.this::itemClick);
                recyclerView.setAdapter(adapter);
            }
        });


    }

    @Override
    public void itemClick(int position) {
        LocalSell_property.Customer_phone = customerList.get(position).getPhone();
        LocalSell_property.customerName = customerList.get(position).getCustomerName();
        // getActivity().getSupportFragmentManager().popBackStack();

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frame_container, new Shop_local_sell_fragment(shop_id, 3)).commit();


    }
}