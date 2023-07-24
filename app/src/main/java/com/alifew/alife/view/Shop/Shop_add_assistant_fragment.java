package com.alifew.alife.view.Shop;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Shop_add_assistant_adapter;
import com.alifew.alife.model.add_manager_assistant_response;
import com.alifew.alife.model.admin_access;
import com.alifew.alife.model.fetch_shop_admin_response;
import com.alifew.alife.viewmodel.Add_manager_assistant;
import com.alifew.alife.viewmodel.Fetch_shop_adminList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.alifew.alife.R.layout.shop_add_assistant_fragment;

public class Shop_add_assistant_fragment extends Fragment implements Shop_add_assistant_adapter.OnItemCheckListener {
    ExtendedFloatingActionButton saveButton;
    RecyclerView assistantView;
    TextView selectButton;
    ImageView backButton;
    FragmentManager fragmentManager;

    String shop_id, agent_id, agent_permission;
    List<fetch_shop_admin_response> assistantList;
    List<fetch_shop_admin_response> more_assistantList;
    List<admin_access> access_asssistantList;

    Dialog alertCustom;
    LinearLayoutManager assistantViewManager;
    Fetch_shop_adminList fetch_shop_admin;
    Add_manager_assistant add_manager_assistant;
    private Shop_add_assistant_adapter adapter;
    int checkBoxState = 0, token = 0;

    public Shop_add_assistant_fragment(String shop_id, String agent_id, String agent_permission, List<fetch_shop_admin_response> assistantList) {
        this.shop_id = shop_id;
        this.agent_id = agent_id;
        this.agent_permission = agent_permission;
        this.assistantList = assistantList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnection();
        access_asssistantList = new ArrayList<>();
        fetch_all_other_assistant();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.show();
                if (access_asssistantList.size() <= 0) {
                    alertCustom.dismiss();
                    Toast.makeText(getActivity(), "select assistant", Toast.LENGTH_SHORT).show();
                } else {
                    add_manager_assistant = new ViewModelProvider(getActivity()).get(Add_manager_assistant.class);
                    for (int i = 0; i < access_asssistantList.size(); i++) {
                        String assistant_id=access_asssistantList.get(i).getId();


                        if (token == 0) {

                            add_manager_assistant.getData(agent_id, assistant_id).observe(getViewLifecycleOwner(), new Observer<add_manager_assistant_response>() {
                                @Override
                                public void onChanged(add_manager_assistant_response add_manager_assistant_response) {
                                    if (add_manager_assistant_response.getMessage().equals("Add successfully")) {

                                    } else {
                                        token = 1;
                                    }
                                }
                            });
                        } else {
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
                        ).replace(R.id.frame_container, new Shop_admin_manager_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();

                    } else {
                        Toast.makeText(getActivity(), "something error", Toast.LENGTH_SHORT).show();
                        alertCustom.dismiss();
                        token = 0;
                    }

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(shop_add_assistant_fragment, container, false);
        checkConnection();

        saveButton = (ExtendedFloatingActionButton) view.findViewById(R.id.saveButtonID);
        assistantView = (RecyclerView) view.findViewById(R.id.assistantViewID);
        selectButton = (TextView) view.findViewById(R.id.selectAllID);
        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        fragmentManager = getFragmentManager();

        assistantView.setHasFixedSize(true);
        assistantViewManager = new LinearLayoutManager(view.getContext());

        assistantView.setLayoutManager(assistantViewManager);

        alertCustom = new Dialog(getActivity());
        alertCustom.setContentView(R.layout.loader);
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);

        assistantView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && saveButton.getVisibility() == View.VISIBLE) {
                    saveButton.hide();
                } else if (dy < 0 && saveButton.getVisibility() != View.VISIBLE) {
                    saveButton.show();
                }
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxState == 0) {
                    selectButton.setText("unselect all");
                    checkBoxState = 1;
                    adapter = new Shop_add_assistant_adapter(more_assistantList, checkBoxState);
                    //adapter.setOnCheckedChangeListener(Admin_addmore_category_fragment.this::OnItemCheck);
                    adapter.setOnItemClickListener(Shop_add_assistant_fragment.this::OnItemCheck);

                    assistantView.setAdapter(adapter);
                    access_asssistantList.removeAll(access_asssistantList);
                    admin_access access;
                    //access=new admin_access();

                    for (int i = 0; i < more_assistantList.size(); i++) {
                        access = new admin_access();
                        access.setId(more_assistantList.get(i).getAgent_id());
                        access_asssistantList.add(access);
                    }

                } else if (checkBoxState == 1) {
                    selectButton.setText("select all");
                    checkBoxState = 0;
                    adapter = new Shop_add_assistant_adapter(more_assistantList, checkBoxState);
                    adapter.setOnItemClickListener(Shop_add_assistant_fragment.this::OnItemCheck);

                    assistantView.setAdapter(adapter);
                    access_asssistantList.removeAll(access_asssistantList);

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustom.dismiss();
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_admin_manager_details_fragment(shop_id, agent_id)).addToBackStack(null).commit();
            }
        });

        return view;
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

    public void fetch_all_other_assistant() {
        more_assistantList = new ArrayList<>();
        fetch_shop_admin=new ViewModelProvider(getActivity()).get(Fetch_shop_adminList.class);
        fetch_shop_admin.getData(shop_id).observe(getViewLifecycleOwner(), new Observer<List<fetch_shop_admin_response>>() {
            @Override
            public void onChanged(List<fetch_shop_admin_response> fetch_shop_admin_responses) {
                more_assistantList = fetch_shop_admin_responses;
                checkBoxState = 0;
                for (int i = 0; i < more_assistantList.size(); i++) {
                    if (more_assistantList.get(i).getAgent_access().equals("2")) {
                        more_assistantList.remove(i);
                        i--;
                    } else {
                        for (int j = 0; j < assistantList.size(); j++) {
                            if (more_assistantList.get(i).getAgent_id().equals(assistantList.get(j).getAgent_id())) {
                                more_assistantList.remove(i);
                                i--;
                                break;
                            }

                        }
                    }
                }
                adapter = new Shop_add_assistant_adapter(more_assistantList, checkBoxState);
                adapter.setOnItemClickListener(Shop_add_assistant_fragment.this::OnItemCheck);
                assistantView.setAdapter(adapter);

            }
        });
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemCheck(int position, String status) {
        fetch_shop_admin_response assistant = more_assistantList.get(position);
        //Toast.makeText(getActivity(), "mesba", Toast.LENGTH_SHORT).show();


        if (status.equals("yess")) {
            admin_access access = new admin_access();
            access.setId(assistant.getAgent_id());
            access_asssistantList.add(access);

        } else if (status.equals("no")) {
            for (int i = 0; i < access_asssistantList.size(); i++) {
                if (access_asssistantList.get(i).getId().equals(assistant.getAgent_id())) {
                    access_asssistantList.remove(i);
                    break;
                }
            }

        }

    }
}
