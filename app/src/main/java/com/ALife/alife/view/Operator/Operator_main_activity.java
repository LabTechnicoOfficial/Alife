package com.ALife.alife.view.Operator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ALife.alife.BuildConfig;
import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.R;
import com.ALife.alife.model.get_shop_admin_information_response;
import com.ALife.alife.model.get_version_response;
import com.ALife.alife.view.Customer.Customer_homescreen_fragment;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.LoginActivity;
import com.ALife.alife.view.Shop.Shop_main_activity;
import com.ALife.alife.viewmodel.Get_shop_admin_information;
import com.ALife.alife.viewmodel.Get_version;
import com.ALife.alife.viewmodel.SessionManagment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Operator_main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String agent_name, agent_phone, agent_image, agent_status, agent_access;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    LinearLayout headerimage;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Dialog alertCustom;
    private String name, image, type;
    Get_shop_admin_information get_shop_admin_information;
    TextView profileName, yesButton, noButton, connectButton;
    private String agent_id, shop_id;
    Get_version get_version;
    private String version_name, version_code;
    Dialog dialog;

    protected void onStart() {

        super.onStart();


        get_version.getData().observe(Operator_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                if (!(get_version_response.getVersion_code().equals(version_code))) {


                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();
                    TextView updateButton = dialog.findViewById(R.id.updateButton);
                    TextView noButton = dialog.findViewById(R.id.noButton);

                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ALife.alife"));
                            //startActivity(intent);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ALife.alife"));
                            startActivity(intent);
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();

                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_version = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Get_version.class);
        version_code = String.valueOf(BuildConfig.VERSION_CODE);
        version_name = BuildConfig.VERSION_NAME;
        dialog = new Dialog(Operator_main_activity.this);
        dialog.setContentView(R.layout.update_alert);
        get_version.getData().observe(Operator_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                if (!(get_version_response.getVersion_code().equals(version_code))) {


                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();
                    TextView updateButton = dialog.findViewById(R.id.updateButton);
                    TextView noButton = dialog.findViewById(R.id.noButton);

                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ALife.alife"));
                            //startActivity(intent);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ALife.alife"));
                            startActivity(intent);
                        }
                    });                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();

                }
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Operator_home_fragment()).commit();

        }

        setContentView(R.layout.operator_activity);
        checkConnection();

        SessionManagment sessionManagment = new SessionManagment(Operator_main_activity.this);
        int userId = sessionManagment.getSession();
        agent_id = String.valueOf(userId);
        type = sessionManagment.getType();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        headerimage = (LinearLayout) findViewById(R.id.headerview);
        View view = navigationView.inflateHeaderView(R.layout.header);
        imageView = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_imageID);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        //ActionBar
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        alertCustom = new Dialog(this);
        alertCustom.setContentView(R.layout.loader);
        profileName = (TextView) view.findViewById(R.id.profile_name);

        get_operator_details();
    }

    private void get_operator_details() {
        get_shop_admin_information = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Get_shop_admin_information.class);
        get_shop_admin_information.getData(agent_id).observe(Operator_main_activity.this, new Observer<get_shop_admin_information_response>() {
            @Override
            public void onChanged(get_shop_admin_information_response get_shop_admin_information_response) {
                agent_image = get_shop_admin_information_response.getAgent_image();
                Picasso.get().load(agent_image).fit().centerInside().into(imageView);
                agent_name = get_shop_admin_information_response.getAgent_name();

                profileName.setText(agent_name);
                agent_access = get_shop_admin_information_response.getAgent_access();
                agent_status = get_shop_admin_information_response.getStatus();
                shop_id = get_shop_admin_information_response.getAgent_shop_id();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        alertControl();
        if (item.getItemId() == R.id.log_out) {
            SessionManagment sessionManagment = new SessionManagment(this);
            sessionManagment.removeSession();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (item.getItemId() == R.id.profile) {


        } else if (item.getItemId() == R.id.categoryListID) {
            get_operator_details();
            if (agent_status.equals("1")) {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Operator_category_list_fragment(shop_id, agent_id, agent_access, type)).addToBackStack(null).commit();
            } else {
                Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.sellProductsID) {
            List<ProductSell> sellList;
            sellList = new ArrayList<>();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Operator_sell_select_fragment(shop_id, agent_id, sellList)).addToBackStack(null).commit();
        } else if (item.getItemId() == R.id.productListID) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Operator_all_products_fragment(shop_id, agent_id)).addToBackStack(null).commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        Dialog networkAlert = new Dialog(Operator_main_activity.this);
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();
                    finish();
                    startActivity(getIntent());
                }
            });
        }
    }

    private void alertControl() {
        alertCustom.show();
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                alertCustom.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {

        Dialog alert = new Dialog(Operator_main_activity.this);
        alert.setContentView(R.layout.exit_alert);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);
        alert.show();

        TextView yesButton = (TextView) alert.findViewById(R.id.yesButtonID);
        TextView noButton = (TextView) alert.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }
}