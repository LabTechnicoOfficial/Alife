package com.alifew.alife.view.Shop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.adapter.Local_sell_product_adapter;
import com.alifew.alife.model.local_sell.add_local_sell_product_response;
import com.alifew.alife.model.local_sell.delete_local_sell_product_response;
import com.alifew.alife.model.local_sell.Get_local_sell_product_response;
import com.alifew.alife.viewmodel.Local_sell.Add_local_sell;
import com.alifew.alife.viewmodel.Local_sell.Get_local_sell;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Shop_local_sell_add_product_fragment extends Fragment implements Local_sell_product_adapter.onItemDeleteListener, Local_sell_product_adapter.onItemEditListener {

    String shopID;

    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String imgdata;
    final int IMAGE_REQUEST_CODE = 999;
    ImageView productImage;
    private Uri filepath;
    private Bitmap bitmap = null;
    Dialog loader;
    Add_local_sell add_local_sell;
    AppCompatButton addProductButton, editProductButton;
    RecyclerView productsView;

    public Shop_local_sell_add_product_fragment(String shopID) {
        this.shopID = shopID;
    }

    AppCompatButton addButton;
    Get_local_sell get_local_sell;
    private List<Get_local_sell_product_response> productList;
    private Local_sell_product_adapter adapter;
    private Double profit;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                check = 1;
                productImage.setImageBitmap(bitmap);

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    productImage.setImageBitmap(bitmap);
                    check = 1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void products_func() {

        //Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
        get_local_sell.getData_product(shopID).observe(getViewLifecycleOwner(), new Observer<List<Get_local_sell_product_response>>() {
            @Override
            public void onChanged(List<Get_local_sell_product_response> get_local_sell_product_responses) {
                productList = new ArrayList<>();
                productList = get_local_sell_product_responses;
                adapter = new Local_sell_product_adapter(productList);
                adapter.setOnClickListener(Shop_local_sell_add_product_fragment.this, Shop_local_sell_add_product_fragment.this);
                productsView.setAdapter(adapter);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_local_sell_add_product_fragment, container, false);

        initView(view);

        products_func();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 0;
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.shop_local_sell_add_product_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                TextInputEditText productDetailsText = dialog.findViewById(R.id.productDetailsTextID);
                TextInputEditText productPriceText = dialog.findViewById(R.id.productPriceTextID);
                TextInputEditText buyPriceText = dialog.findViewById(R.id.buyPriceTextID);
                TextView profitText = dialog.findViewById(R.id.profitTextID);

                TextInputLayout productDetailsError = dialog.findViewById(R.id.productDetailsErrorID);
                TextInputLayout roductPriceError = dialog.findViewById(R.id.productPriceErrorID);

                LinearLayout choseImageButton = dialog.findViewById(R.id.choseImageButtonId);
                productImage = dialog.findViewById(R.id.productImage);

                addProductButton = dialog.findViewById(R.id.addProductButtonID);
                productPriceText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                            if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                                profitText.setText("0");
                            } else {
                                profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                                profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                            }


                        } else {
                            profitText.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                buyPriceText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                            if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                                profitText.setText("0");
                            } else {
                                profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                                profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                            }


                        } else {
                            profitText.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                choseImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                        imageselect();
                    }
                });

                addProductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String productDetails = productDetailsText.getText().toString().trim();
                        String productPrice = productPriceText.getText().toString().trim();
                        String buyPrice = buyPriceText.getText().toString().trim();

                        if (TextUtils.isEmpty(productDetails) || TextUtils.isEmpty(productPrice)) {
                            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            //code
                            if (TextUtils.isEmpty(buyPrice)) {
                                buyPrice = "0.0";
                            }

                            loader.show();
                            add_local_sell = new ViewModelProvider(getActivity()).get(Add_local_sell.class);

                            if (check != 1) {
                                imgdata = "";
                            } else {
                                imgdata = imgToString(bitmap);
                            }

                            // Toast.makeText(getActivity(), imgdata, Toast.LENGTH_SHORT).show();
                            add_local_sell.addProduct(shopID, productDetails, productPrice, buyPrice, imgdata).observe(getViewLifecycleOwner(), new Observer<add_local_sell_product_response>() {
                                @Override
                                public void onChanged(add_local_sell_product_response add_local_sell_product_response) {
                                    loader.dismiss();
                                    if (add_local_sell_product_response.getMessage().equals("yess")) {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Product added", Toast.LENGTH_SHORT).show();

                                        products_func();
                                    } else {

                                    }
                                }
                            });
                        }
                    }
                });
            }
        });


        return view;
    }

    private void initView(View view) {
        get_local_sell = new ViewModelProvider(this).get(Get_local_sell.class);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        productsView = view.findViewById(R.id.productsViewID);
        productsView.setHasFixedSize(true);
        productsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addButton = view.findViewById(R.id.addButton);
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
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

    @Override
    public void OnItemDelete(int position) {


        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setContentView(R.layout.delete_alert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = alertDialog.findViewById(R.id.yesButton);
        TextView noButton = alertDialog.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_local_sell add_local_sell;
                add_local_sell = new ViewModelProvider(getActivity()).get(Add_local_sell.class);
                add_local_sell.deleteProduct(productList.get(position).getId()).observe(getViewLifecycleOwner(), new Observer<delete_local_sell_product_response>() {
                    @Override
                    public void onChanged(delete_local_sell_product_response delete_local_sell_product_response) {
                        if (delete_local_sell_product_response.getMessage().equals("Product deleted successfully")) {
                            products_func();
                        }
                        alertDialog.cancel();
                        Toast.makeText(getActivity(), delete_local_sell_product_response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

    }

    @Override
    public void OnItemEdit(int position) {
        check = 0;
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.shop_local_sell_edit_product_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextInputEditText productDetailsText = dialog.findViewById(R.id.productDetailsTextID);
        TextInputEditText productPriceText = dialog.findViewById(R.id.productPriceTextID);
        TextInputEditText buyPriceText = dialog.findViewById(R.id.buyPriceTextID);
        TextView profitText = dialog.findViewById(R.id.profitTextID);

        productDetailsText.setText(productList.get(position).getProduct_details());
        productPriceText.setText(productList.get(position).getPrice());
        buyPriceText.setText(productList.get(position).getBuy_price());
        if (!TextUtils.isEmpty(productList.get(position).getBuy_price())) {
            Double profitvalue = Double.parseDouble(productList.get(position).getPrice()) - Double.parseDouble(productList.get(position).getBuy_price());
            profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profitvalue)));
        }


        TextInputLayout productDetailsError = dialog.findViewById(R.id.productDetailsErrorID);
        TextInputLayout roductPriceError = dialog.findViewById(R.id.productPriceErrorID);

        LinearLayout choseImageButton = dialog.findViewById(R.id.choseImageButtonId);
        ImageView productImage = dialog.findViewById(R.id.productImage);

        ImageHelper.imageLoader(getActivity(), productImage, productList.get(position).getImage());

        editProductButton = dialog.findViewById(R.id.saveProductButtonID);
        productPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                    if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                        profitText.setText("0");
                    } else {
                        profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                        profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                    }


                } else {
                    profitText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buyPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(productPriceText.getText().toString().trim()) && !TextUtils.isEmpty(buyPriceText.getText().toString().trim())) {
                    if (Double.parseDouble(buyPriceText.getText().toString().trim()) == 0.0) {
                        profitText.setText("0");
                    } else {
                        profit = Double.parseDouble(productPriceText.getText().toString().trim()) - Double.parseDouble(buyPriceText.getText().toString().trim());
                        profitText.setText(String.valueOf(new DecimalFormat("##.##").format(profit)));
                    }


                } else {
                    profitText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        choseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productDetails = productDetailsText.getText().toString().trim();
                String productPrice = productPriceText.getText().toString().trim();
                String buyPrice = buyPriceText.getText().toString().trim();

                if (TextUtils.isEmpty(productDetails) || TextUtils.isEmpty(productPrice)) {
                    Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                } else {
                    //code
                    if (TextUtils.isEmpty(buyPrice)) {
                        buyPrice = "0.0";
                    }

                    loader.show();
                    add_local_sell = new ViewModelProvider(getActivity()).get(Add_local_sell.class);

                    if (check != 1) {
                        imgdata = "";
                    } else {
                        imgdata = imgToString(bitmap);
                    }
                    add_local_sell.editProduct(productList.get(position).getId(), productDetails, productPrice, buyPrice, imgdata).observe(getViewLifecycleOwner(), new Observer<delete_local_sell_product_response>() {
                        @Override
                        public void onChanged(delete_local_sell_product_response delete_local_sell_product_response) {
                            loader.dismiss();
                            if (delete_local_sell_product_response.getMessage().equals("Product Updated")) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Product Updated", Toast.LENGTH_SHORT).show();

                                products_func();
                            } else {
                                Toast.makeText(getActivity(), "Fail to Update", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    // Toast.makeText(getActivity(), imgdata, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
