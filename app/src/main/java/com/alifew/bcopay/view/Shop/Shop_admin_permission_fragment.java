package com.alifew.bcopay.view.Shop;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.add_shop_admin_access_response;
import com.alifew.bcopay.model.add_shop_admin_response;
import com.alifew.bcopay.model.admin_access;
import com.alifew.bcopay.model.fetch_shop_admin_response;
import com.alifew.bcopay.viewmodel.Add_shop_admin;
import com.alifew.bcopay.viewmodel.Add_shop_admin_access;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.bcopay.R.layout.shop_admin_permission_fragment;

public class Shop_admin_permission_fragment extends Fragment {
    String shopID, imageData, adminName, adminPhone, adminPassword;
    List<admin_access> access_category;

    ImageView backButton;
    com.mikhaellopez.circularimageview.CircularImageView adminImage;
    TextView adminNameText, adminPhoneText, accessCategoryText;
    AppCompatButton submitButton;
    int radio = 0;
    RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    Dialog alertCustom;
    Add_shop_admin add_shop_admin;
    Add_shop_admin_access add_shop_admin_access;
    int token = 0;
    int size = 0;
    private List<fetch_shop_admin_response> adminList;


    public Shop_admin_permission_fragment(String shopID, String imageData, String adminName, String adminPhone, String adminPassword, List<admin_access> access_category, List<fetch_shop_admin_response> adminList) {
        this.shopID = shopID;
        this.imageData = imageData;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminPassword = adminPassword;
        this.access_category = new ArrayList<>();
        this.access_category = access_category;
        this.adminList = adminList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_permission_fragment, container, false);
        fragmentManager = getFragmentManager();

        adminImage = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.admin_imageID);
        adminNameText = (TextView) view.findViewById(R.id.adminNameID);
        adminPhoneText = (TextView) view.findViewById(R.id.adminPhoneID);
        accessCategoryText = (TextView) view.findViewById(R.id.accessCategoryID);
        backButton = (ImageView) view.findViewById(R.id.backButton);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupID);
        submitButton = (AppCompatButton) view.findViewById(R.id.submitButton);

        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.loader);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);

        adminNameText.setText(adminName);
        adminPhoneText.setText(adminPhone);
        accessCategoryText.setText(String.valueOf(access_category.size()));

        Bitmap bm = StringToBitMap(imageData);
        adminImage.setImageBitmap(bm);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sellPermissionID) {
                    radio = 1;
                } else if (checkedId == R.id.allPermissionID) {
                    radio = 2;
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio == 0) {
                    Toast.makeText(getActivity(), "Grant Permission", Toast.LENGTH_SHORT).show();

                } else {
                    alertCustom.show();
                    add_shop_admin = new ViewModelProvider(getActivity()).get(Add_shop_admin.class);
                    add_shop_admin_access = new ViewModelProvider(getActivity()).get(Add_shop_admin_access.class);
                    add_shop_admin.getData(adminName, adminPhone, adminPassword, imageData, shopID, String.valueOf(radio)).observe(getViewLifecycleOwner(), new Observer<add_shop_admin_response>() {
                        @Override
                        public void onChanged(add_shop_admin_response add_shop_admin_response) {
                            if (add_shop_admin_response.getMessage().equals("Add successfully")) {
                                String agent_id = add_shop_admin_response.getAgent_id();

                                for (int i = 0; i < access_category.size(); i++) {
                                    if (token == 0) {
                                        String category_id = access_category.get(i).getId();
                                        add_shop_admin_access.getData(agent_id, category_id).observe(getViewLifecycleOwner(), new Observer<add_shop_admin_access_response>() {
                                            @Override
                                            public void onChanged(add_shop_admin_access_response add_shop_admin_access_response) {
                                                if (add_shop_admin_access_response.getMessage().equals("Add successfully")) {
                                                    size++;

                                                } else {
                                                    token = 1;

                                                }
                                            }
                                        });
                                    } else {
                                        alertCustom.dismiss();
                                        Toast.makeText(getActivity(), "Something Error", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                                if (token == 0) {
                                    alertCustom.dismiss();
                                    fragmentManager.beginTransaction().setCustomAnimations(
                                            R.anim.slide_in,  // enter
                                            R.anim.fade_out,  // exit
                                            R.anim.fade_in,   // popEnter
                                            R.anim.slide_out  // popExit
                                    ).replace(R.id.frame_container, new Shop_admin_fragments(shopID)).addToBackStack(null).commit();
                                }

                            } else {
                                alertCustom.dismiss();
                                Toast.makeText(getActivity(), "something error.Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.show();

                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_add_admin_fragment(shopID, adminList)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
