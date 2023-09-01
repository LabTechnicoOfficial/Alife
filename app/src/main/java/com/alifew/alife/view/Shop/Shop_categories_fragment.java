package com.alifew.alife.view.Shop;

import android.Manifest;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_category_adapter;
import com.alifew.alife.model.Category_add_response;
import com.alifew.alife.model.Category_response;
import com.alifew.alife.model.Unit_response;
import com.alifew.alife.model.delete_category_response;
import com.alifew.alife.model.edit_category_response;
import com.alifew.alife.model.get_shop_products_summary_response;
import com.alifew.alife.viewmodel.Category_add;
import com.alifew.alife.viewmodel.Category_fetch;
import com.alifew.alife.viewmodel.Delete_category;
import com.alifew.alife.viewmodel.Edit_category;
import com.alifew.alife.viewmodel.Get_category_summary;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Unit;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.shop_cateories_fragment;

public class Shop_categories_fragment<SharedViewModel> extends Fragment implements Shop_category_adapter.OnItemClickListener, Shop_category_adapter.OnItemEditListener, Shop_category_adapter.OnItemDeleteListener {
    ExtendedFloatingActionButton mFloatingActionButton;
    ImageView closeButton;
    TextInputLayout categoryError;
    TextInputEditText categoryText;
    RecyclerView recyclerView;
    TextView categoryADDButton;
    de.hdodenhof.circleimageview.CircleImageView categoryImage;
    private Shop_category_adapter adapter;
    private RecyclerView.LayoutManager layoutmanager;
    Category_fetch category_fetch;
    Category_add category_add;
    Edit_category edit_category;
    Delete_category delete_category;
    Unit unit;
    String id;
    List<Category_response> data;
    List<Unit_response> product_unit;
    Spinner categorySpinner;
    String unitProduct[], category_spinner_value;
    EditText search;
    TextView total_item, total_sell_price, total_profit, total_products,total_buyPrice;


    int check = 0, final_check = 0;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    int token = 0;
    String imgdata;
    int userId;
    String searchtext;
    double all_profit, all_sell_price, all_stock_product;
    int all_product;
    String type;
    Dialog alert;

    ProgressBar progressBar;
    NestedScrollView nestedScrollView, gridNestedScrollView;
    Get_category_summary get_category_summary;
    int page = 1, limit = 10, end = 0;

    public Shop_categories_fragment(String id) {
        this.id = id;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (search != null)     // for resetting this edittext
            search.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        get_category_summary();
        //  main();

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
                    categoryImage.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        categoryImage.setImageBitmap(bitmap);
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
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    check = 1;
                    Intent intent = new Intent(new Intent(Intent.ACTION_GET_CONTENT));
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_cateories_fragment, container, false);
        checkConnection();

        recyclerView = view.findViewById(R.id.itemView);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutmanager);
        search = (EditText) view.findViewById(R.id.searchEditText);
        total_item = (TextView) view.findViewById(R.id.totalItemsID);
        total_products = (TextView) view.findViewById(R.id.totalProductsID);
        total_sell_price = (TextView) view.findViewById(R.id.totalSellPriceID);
        total_profit = (TextView) view.findViewById(R.id.totalProfitID);
        total_buyPrice=(TextView) view.findViewById(R.id.totalBuyPriceID);


        mFloatingActionButton = (ExtendedFloatingActionButton) view.findViewById(R.id.add_categoryID);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedRecyclerViewID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    mFloatingActionButton.hide();
                } else {
                    mFloatingActionButton.show();
                }
                //mFloatingActionButton.show();
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (end == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        filter(page, limit);
                    }
                }
            }
        });

        alert = new Dialog(getActivity());
        alert.setContentView(R.layout.category_form);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);

        return view;
    }

    private void filter(int Page, int Limit) {
        progressBar.setVisibility(View.GONE);
        if (Page == 1) {
            data = new ArrayList<>();
            adapter = new Shop_category_adapter(data, type);

            //adapter.setOnClickListener(Showdetails.this);
            adapter.setOnClickListener(Shop_categories_fragment.this::OnItemClick, Shop_categories_fragment.this::OnItemEdit, Shop_categories_fragment.this::OnItemDelete);


            recyclerView.setAdapter(adapter);

        }
        category_fetch.getdata(id, Page, Limit).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {

                for (int i = 0; i < category_responses.size(); i++) {
                    data.add(category_responses.get(i));
                }
                if (category_responses.size() < Limit) {
                    end = 1;
                }

                adapter = new Shop_category_adapter(data, type);

                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_categories_fragment.this::OnItemClick, Shop_categories_fragment.this::OnItemEdit, Shop_categories_fragment.this::OnItemDelete);


                recyclerView.setAdapter(adapter);
            }

        });
    }

    @Override
    public void OnItemClick(int position) {
        Category_response clickItem = data.get(position);

        String Category_id = clickItem.getCatagory01y_id();
        String Category_unit = clickItem.getCatagory01y_unit();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_products_fragment(id, Category_id, Category_unit)).addToBackStack(null).commit();

    }

    @Override
    //  View view = inflater.inflate(fragment_products, container, false);
    public void OnItemEdit(int position) {
        Category_response clickItem = data.get(position);
        String Category_name = clickItem.getCatagory01y_name();
        String Category_id = clickItem.getCatagory01y_id();

        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.category_form);
        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        categoryImage = (de.hdodenhof.circleimageview.CircleImageView) alert.findViewById(R.id.categoryImageID);
        closeButton = (ImageView) alert.findViewById(R.id.closeID);
        categoryError = (TextInputLayout) alert.findViewById(R.id.categoryErrorID);
        categoryText = (TextInputEditText) alert.findViewById(R.id.categoryTextID);
        categoryADDButton = (TextView) alert.findViewById(R.id.add_ID);
        TextView editCategories = (TextView) alert.findViewById(R.id.layout1);
        editCategories.setText("Edit Categories");
        categoryADDButton.setText("Done");
        categoryText.setText(Category_name);

        //edit

        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageSelect();
            }
        });

        categoryADDButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String category = categoryText.getText().toString().trim();

                categoryError.setErrorEnabled(false);
                if (TextUtils.isEmpty(category)) {
                    categoryError.setError(" ");
                } else {

                    if (final_check == 1) {
                        imgdata = imgToString(bitmap);
                        token = 1;
                    } else {
                        imgdata = "xyz";
                        token = 0;
                    }
                    edit_category = new ViewModelProvider(getActivity()).get(Edit_category.class);
                    edit_category.getdata(imgdata, category, Category_id, token).observe(getViewLifecycleOwner(), new Observer<edit_category_response>() {
                        @Override
                        public void onChanged(edit_category_response category_edit_response) {
                            if (category_edit_response.getMessage().equals("Category Updated")) {

                                Toast toast = Toast.makeText(getActivity(), category_edit_response.getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                alert.cancel();
                                get_category_summary();
                                //refreshFragment();
                            } else {
                                Toast toast = Toast.makeText(getActivity(), category_edit_response.getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    });

                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

    }

    @Override
    public void OnItemDelete(int position) {
        Category_response clickItem = data.get(position);

        String Category_id = clickItem.getCatagory01y_id();
        String category_product = clickItem.getTotal_product();

        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.delete_alert);
        if (Integer.parseInt(category_product) == 0) {

            alert.show();

            TextView yesButton = alert.findViewById(R.id.yesButton);
            TextView noButton = alert.findViewById(R.id.noButton);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_category = new ViewModelProvider(getActivity()).get(Delete_category.class);
                    delete_category.getdata(Category_id).observe(getViewLifecycleOwner(), new Observer<delete_category_response>() {
                        @Override
                        public void onChanged(delete_category_response delete_category_response) {
                            if (delete_category_response.getMessage().equals("Category deleted successfully")) {

                                Toast toast = Toast.makeText(getActivity(), delete_category_response.getMessage(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                alert.cancel();
                                get_category_summary();
                                //refreshFragment();
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
        } else {
            Toast.makeText(getActivity(), "Category contains Products", Toast.LENGTH_SHORT).show();
        }
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

    public void main() {
        checkConnection();

        unit = new ViewModelProvider(getActivity()).get(Unit.class);
        category_add = new ViewModelProvider(getActivity()).get(Category_add.class);
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        //userId = sessionManagment.getSession();
        type = sessionManagement.getType();
        category_fetch = new ViewModelProvider(getActivity()).get(Category_fetch.class);

        // id = String.valueOf(userId);
        data = new ArrayList<>();
        page = 1;
        end = 0;
        filter(page, limit);


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            //OnTextChanged
            int mesbaul;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(TextUtils.isEmpty(search.getText().toString().trim()))) {
                    try {
                        // adapter.getFilter().filter(search.getText());
                        data = new ArrayList<>();
                        adapter = new Shop_category_adapter(data, type);

                        //adapter.setOnClickListener(Showdetails.this);
                        adapter.setOnClickListener(Shop_categories_fragment.this::OnItemClick, Shop_categories_fragment.this::OnItemEdit, Shop_categories_fragment.this::OnItemDelete);


                        recyclerView.setAdapter(adapter);
                        get_category_by_search(search.getText().toString().trim());
                    } catch (Exception e) {

                    }
                } else {
                    page = 1;
                    end = 0;
                    filter(page, limit);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(getActivity());
                alert.setContentView(R.layout.category_form);
                alert.show();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                categoryImage = (de.hdodenhof.circleimageview.CircleImageView) alert.findViewById(R.id.categoryImageID);
                closeButton = (ImageView) alert.findViewById(R.id.closeID);
                categoryError = (TextInputLayout) alert.findViewById(R.id.categoryErrorID);
                categoryText = (TextInputEditText) alert.findViewById(R.id.categoryTextID);
                categoryADDButton = (TextView) alert.findViewById(R.id.add_ID);
                categorySpinner = (Spinner) alert.findViewById(R.id.categorySpinnerID);
                product_unit = new ArrayList<>();
                unitProduct = new String[]{};

                unit.getdata("xyz").observe(getViewLifecycleOwner(), new Observer<List<Unit_response>>() {
                    @Override
                    public void onChanged(List<Unit_response> unit_responses) {
                        product_unit = unit_responses;
                        unitProduct = new String[product_unit.size() + 1];
                        unitProduct[0] = "None";

                        for (int j = 0; j < product_unit.size(); j++) {
                            unitProduct[j + 1] = product_unit.get(j).getName();


                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, unitProduct);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        categorySpinner.setAdapter(adapter);
                    }
                });

                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        category_spinner_value = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(getActivity(), category_spinner_value, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                categoryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageSelect();
                    }
                });
                categoryADDButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        } else {

                            String category = categoryText.getText().toString().trim();

                            categoryError.setErrorEnabled(false);
                            if (TextUtils.isEmpty(category)) {
                                categoryError.setError(" ");
                            }
                            if (category_spinner_value.equals("None")) {
                                Toast.makeText(getActivity(), "Select an Unit Item", Toast.LENGTH_SHORT).show();
                            } else {

                                if (final_check == 1) {
                                    imgdata = imgToString(bitmap);
                                } else {
                                    imgdata = "";
                                }
                                //imgdata = imgToString(bitmap);
                                Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.loader);
                                dialog.show();
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);

                                category_add.getdata(imgdata, category, category_spinner_value, id).observe(getViewLifecycleOwner(), new Observer<Category_add_response>() {
                                    @Override
                                    public void onChanged(Category_add_response category_add_response) {
                                        if (category_add_response.getMessage().equals("Category added successfully")) {

                                            dialog.dismiss();
                                            alert.cancel();

                                            Toast toast = Toast.makeText(getActivity(), category_add_response.getMessage(), Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();

                                            get_category_summary();
                                        } else {
                                            dialog.dismiss();
                                            Toast toast = Toast.makeText(getActivity(), category_add_response.getMessage(), Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
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
                        alert.cancel();
                    }
                });

            }
        });

    }

    private void get_category_by_search(String value) {
        category_fetch.getCategory(id, value).observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> category_responses) {
                data = category_responses;
                adapter = new Shop_category_adapter(data, type);

                //adapter.setOnClickListener(Showdetails.this);
                adapter.setOnClickListener(Shop_categories_fragment.this::OnItemClick, Shop_categories_fragment.this::OnItemEdit, Shop_categories_fragment.this::OnItemDelete);


                recyclerView.setAdapter(adapter);

            }
        });
    }

    public void get_category_summary() {
        get_category_summary = new ViewModelProvider(getActivity()).get(Get_category_summary.class);
        get_category_summary.get_summaryShop(id).observe(getViewLifecycleOwner(), new Observer<get_shop_products_summary_response>() {
            @Override
            public void onChanged(get_shop_products_summary_response get_shop_products_summary_response) {
                String all_category = get_shop_products_summary_response.getTotal_category();
                Double all_stock = get_shop_products_summary_response.getAll_stock();
                Double all_product = get_shop_products_summary_response.getAll_product();
                Double all_price = get_shop_products_summary_response.getAll_sell_price();
                Double all_profit = get_shop_products_summary_response.getAll_profit();
                total_item.setText(all_category);
                total_products.setText(String.valueOf(new DecimalFormat("##.##").format(all_product)));
                total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(all_price)));
                total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(all_profit)));
                total_buyPrice.setText(String.valueOf(new DecimalFormat("##.##").format(get_shop_products_summary_response.getAll_buy_price())));
                main();
            }
        });
    }
}
