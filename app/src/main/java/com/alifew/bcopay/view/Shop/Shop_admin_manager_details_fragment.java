package com.alifew.bcopay.view.Shop;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.adapter.Shop_admin_access_category_adapter;
import com.alifew.bcopay.adapter.Shop_assistant_adapter;
import com.alifew.bcopay.model.Category_response;
import com.alifew.bcopay.model.fetch_shop_admin_response;
import com.alifew.bcopay.model.get_shop_admin_information_response;
import com.alifew.bcopay.model.remove_manager_assistant_response;
import com.alifew.bcopay.model.remove_shop_admin_category_response;
import com.alifew.bcopay.view.Admin.Admin_addmore_category_fragment;
import com.alifew.bcopay.viewmodel.Fetch_shop_admin_category;
import com.alifew.bcopay.viewmodel.Get_manager_assistantList;
import com.alifew.bcopay.viewmodel.Get_shop_admin_information;
import com.alifew.bcopay.viewmodel.Remove_manager_assistant;
import com.alifew.bcopay.viewmodel.Remove_shop_admin_category;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.bcopay.R.layout.shop_admin_manager_details_fragment;

public class Shop_admin_manager_details_fragment extends Fragment implements Shop_admin_access_category_adapter.OnItemRemoveListener, Shop_assistant_adapter.OnItemRemoveListener {
    com.mikhaellopez.circularimageview.CircularImageView adminImage, image;
    TextView adminName, adminPhone, adminPermission;
    String shop_id, agent_id, agent_name, agent_image, agent_phone, agent_permission;
    private FragmentManager fragmentManager;
    MaterialButtonToggleGroup toggleGroup;
    RecyclerView categoryView, assistantView;
    LinearLayoutManager categoryViewManager, assistantViewManager;
    ExtendedFloatingActionButton addCategoriesButton, addAssistantButton;
    LinearLayout categoryLayout, assistantLayout;


    Get_shop_admin_information get_shop_admin_information;
    List<Category_response> data;
    List<fetch_shop_admin_response> assistantList;
    Fetch_shop_admin_category fetch_shop_admin_category;
    Get_manager_assistantList get_manager_assistantList;
    private Shop_admin_access_category_adapter adapter;
    private Shop_assistant_adapter assistant_adapter;
    Dialog alertCustom;
    Remove_shop_admin_category remove_shop_admin_category;
    Remove_manager_assistant remove_manager_assistant;
    int page1 = 1, page2 = 1, limit = 10;

    public Shop_admin_manager_details_fragment(String shop_id, String agent_id) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        get_shop_admin_information = new ViewModelProvider(getActivity()).get(Get_shop_admin_information.class);
        get_shop_admin_information.getData(agent_id).observe(getViewLifecycleOwner(), new Observer<get_shop_admin_information_response>() {
            @Override
            public void onChanged(get_shop_admin_information_response get_shop_admin_information_response) {
                agent_name = get_shop_admin_information_response.getAgent_name();
                agent_image = get_shop_admin_information_response.getAgent_image();
                agent_phone = get_shop_admin_information_response.getAgent_phone();
                agent_permission = get_shop_admin_information_response.getAgent_access();
                adminName.setText(agent_name);
                adminPhone.setText(agent_phone);

                ImageHelper.imageLoader(getActivity(), adminImage, agent_image);

                try {
                    if (agent_permission.equals("1")) {
                        adminPermission.setText("Assistant");
                    } else if (agent_permission.equals("2")) {
                        adminPermission.setText("Manager");
                    }
                } catch (Exception e) {
                }

            }

        });


        data = new ArrayList<>();
        categoryList();
        //start toggol for select option
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (group.getCheckedButtonId() == R.id.categoryListID) {
                    assistantLayout.setVisibility(View.GONE);
                    categoryLayout.setVisibility(View.VISIBLE);
                    categoryList();


                } else if (group.getCheckedButtonId() == R.id.assistantListID) {
                    categoryLayout.setVisibility(View.GONE);
                    assistantLayout.setVisibility(View.VISIBLE);
                    assistantList();
                }
            }
        });
        //end toggol for select option

        addCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Admin_addmore_category_fragment(shop_id, agent_id, agent_permission, data)).addToBackStack(null).commit();
            }
        });

        addAssistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_add_assistant_fragment(shop_id, agent_id, agent_permission, assistantList)).addToBackStack(null).commit();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_admin_manager_details_fragment, container, false);
        checkConnection();

        adminImage = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.adminImageID);
        adminName = (TextView) view.findViewById(R.id.adminNameID);
        adminPhone = (TextView) view.findViewById(R.id.adminPhoneID);
        adminPermission = (TextView) view.findViewById(R.id.adminPermissionID);
        toggleGroup = (MaterialButtonToggleGroup) view.findViewById(R.id.toggleGroupID);
        addCategoriesButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addCategoriesID);
        addAssistantButton = (ExtendedFloatingActionButton) view.findViewById(R.id.addAssistantID);

        categoryLayout = (LinearLayout) view.findViewById(R.id.categoriesLayoutID);
        assistantLayout = (LinearLayout) view.findViewById(R.id.assistantLayoutID);

        categoryView = (RecyclerView) view.findViewById(R.id.categoryViewID);
        assistantView = (RecyclerView) view.findViewById(R.id.assistantViewID);
        categoryView.setHasFixedSize(true);
        assistantView.setHasFixedSize(true);

        categoryViewManager = new LinearLayoutManager(view.getContext());
        assistantViewManager = new LinearLayoutManager(view.getContext());

        categoryView.setLayoutManager(categoryViewManager);
        assistantView.setLayoutManager(assistantViewManager);

        fragmentManager = getFragmentManager();
        adminName.setText(agent_name);
        adminPhone.setText(agent_phone);

        ImageHelper.imageLoader(getActivity(), adminImage, agent_image);


        adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog showImage;
                showImage = new Dialog(getActivity());
                showImage.setContentView(R.layout.shop_admin_image_alert);
                showImage.show();
                showImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                image = (com.mikhaellopez.circularimageview.CircularImageView) showImage.findViewById(R.id.adminAlertImageID);

                ImageHelper.imageLoader(getActivity(), image, agent_image);
            }
        });

        categoryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addCategoriesButton.getVisibility() == View.VISIBLE) {
                    addCategoriesButton.hide();
                } else if (dy < 0 && addCategoriesButton.getVisibility() != View.VISIBLE) {
                    addCategoriesButton.show();
                }
            }
        });

        assistantView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addAssistantButton.getVisibility() == View.VISIBLE) {
                    addAssistantButton.hide();
                } else if (dy < 0 && addAssistantButton.getVisibility() != View.VISIBLE) {
                    addAssistantButton.show();
                }
            }
        });
        return view;
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

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentManager.beginTransaction().replace(R.id.frame_container, new Shop_admin_fragments(shop_id)).addToBackStack(null).commit();

    }

    public void categoryList() {
        data = new ArrayList<>();
        fetch_shop_admin_category = new ViewModelProvider(getActivity()).get(Fetch_shop_admin_category.class);
        fetch_shop_admin_category.getSearchData(agent_id, "").observe(getViewLifecycleOwner(), new Observer<List<Category_response>>() {
            @Override
            public void onChanged(List<Category_response> fetch_shop_admin_category_responses) {
                data = fetch_shop_admin_category_responses;
                adapter = new Shop_admin_access_category_adapter(data);
                adapter.setOnClickListener(Shop_admin_manager_details_fragment.this::OnItemRemove);
                categoryView.setAdapter(adapter);
            }
        });
    }

    public void assistantList() {
        assistantList = new ArrayList<>();
        get_manager_assistantList = new ViewModelProvider(getActivity()).get(Get_manager_assistantList.class);
        get_manager_assistantList.getData(agent_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_admin_response>>() {
            @Override
            public void onChanged(List<fetch_shop_admin_response> fetch_shop_admin_responses) {
                assistantList = fetch_shop_admin_responses;
                assistant_adapter = new Shop_assistant_adapter(assistantList);
                assistant_adapter.setOnClickListener(Shop_admin_manager_details_fragment.this::OnItemRemoveAssistant);
                assistantView.setAdapter(assistant_adapter);
            }
        });

    }

    @Override
    public void OnItemRemove(int position) {
        Category_response category = data.get(position);
        String category_id = category.getCatagory01y_id();

        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.confirm_alert);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.show();
        TextView yesButton = (TextView) alertCustom.findViewById(R.id.yesButton);
        TextView noButton = (TextView) alertCustom.findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_shop_admin_category = new ViewModelProvider(getActivity()).get(Remove_shop_admin_category.class);
                remove_shop_admin_category.getData(agent_id, category_id).observe(getViewLifecycleOwner(), new Observer<remove_shop_admin_category_response>() {
                    @Override
                    public void onChanged(remove_shop_admin_category_response remove_shop_admin_category_response) {
                        if (remove_shop_admin_category_response.getMessage().equals("Remove successfully")) {
                            alertCustom.dismiss();
                            Toast.makeText(getActivity(), remove_shop_admin_category_response.getMessage(), Toast.LENGTH_SHORT).show();
                            refreshFragment();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void OnItemRemoveAssistant(int position) {
        fetch_shop_admin_response assistant = assistantList.get(position);
        String assistant_id = assistant.getAgent_id();
        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.confirm_alert);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.show();
        TextView yesButton = (TextView) alertCustom.findViewById(R.id.yesButton);
        TextView noButton = (TextView) alertCustom.findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.dismiss();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_manager_assistant = new ViewModelProvider(getActivity()).get(Remove_manager_assistant.class);
                remove_manager_assistant.getData(agent_id, assistant_id).observe(getViewLifecycleOwner(), new Observer<remove_manager_assistant_response>() {
                    @Override
                    public void onChanged(remove_manager_assistant_response remove_manager_assistant_response) {
                        if (remove_manager_assistant_response.getMessage().equals("Remove successfully")) {
                            alertCustom.dismiss();
                            Toast.makeText(getActivity(), remove_manager_assistant_response.getMessage(), Toast.LENGTH_SHORT).show();
                            refreshFragment();
                        }

                    }
                });

            }
        });

    }
}
