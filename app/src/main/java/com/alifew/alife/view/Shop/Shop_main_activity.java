package com.alifew.alife.view.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alifew.alife.BuildConfig;
import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.R;
import com.alifew.alife.Utils.Constants;
import com.alifew.alife.adapter.Instruction_adapter;
import com.alifew.alife.model.Shop_response;
import com.alifew.alife.model.getUser_deviceToken_response;
import com.alifew.alife.model.get_version_response;
import com.alifew.alife.model.shop_status_response;
import com.alifew.alife.model.user_instruction_response;
import com.alifew.alife.view.LoginActivity;
import com.alifew.alife.viewmodel.Get_version;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.Shop_details;
import com.alifew.alife.viewmodel.Shop_status;
import com.alifew.alife.viewmodel.User_deviceToken;
import com.alifew.alife.viewmodel.User_instruction;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Shop_main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Instruction_adapter.OnItemClickListener {
    public static String SHOP_NAME = "";
    public static String SHOP_NUMBER = "";
    private int timeLimit_UseriNSTRUCTION = 0;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String type, image, name;
    LinearLayout headerimage;
    TextView profileName, yesButton, noButton, connectButton;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Shop_details shop_details;
    Shop_status shop_status;
    ActionBarDrawerToggle actionBarDrawerToggle;
    int ppppp;
    Dialog alertCustom;
    String shop_id;
    Get_version get_version;
    private String version_name, version_code;
    int camera_permission = 1;
    int read_storage_permission = 1;
    int write_external_permission = 1;
    int internet_permission = 1;
    int access_network_permission = 1;
    int call_phone_permission = 1;
    Dialog  statusdialog;
    User_instruction userInstruction;
    User_deviceToken user_deviceToken;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    public int leng;
    String date1 = "", date2 = "";

    String deviceToken;

    int user;

    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("MissingPermission")
    protected void onStart() {

        super.onStart();

        SessionManagement session = new SessionManagement(Shop_main_activity.this);
        user = session.getSession();


        if (user == -1) {
            Intent intent = new Intent(Shop_main_activity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }


        checkMultipleDeviceLogIN();

        instruction_func();
        shop_status.getStatus(shop_id).observe(Shop_main_activity.this, new Observer<shop_status_response>() {
            @Override
            public void onChanged(shop_status_response shop_status_response) {
                //Toast.makeText(Shop_main_activity.this,shop_status_response.getStatus(),Toast.LENGTH_SHORT).show();
                if (shop_status_response.getStatus().equals("1")) {
                    statusdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    statusdialog.setCancelable(false);
                    statusdialog.show();

                    Toast.makeText(Shop_main_activity.this, "InActive", Toast.LENGTH_SHORT).show();
                } else {
                    statusdialog.dismiss();
                }
            }
        });

        //checkVersion();

    }

    private void checkVersion() {
        Dialog dialog = new Dialog(Shop_main_activity.this);
        dialog.setContentView(R.layout.update_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        get_version.getData().observe(Shop_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                // Log.d("dataxx", "onChanged: "+get_version_response.getVersion_code().toString()+" "+version_code);
                if (!(get_version_response.getVersion_code().equals(version_code))) {


                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();
                    TextView updateButton = dialog.findViewById(R.id.updateButton);
                    TextView noButton = dialog.findViewById(R.id.noButton);
                    //updateButton.setMovementMethod(LinkMovementMethod.getInstance());
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

    private void checkMultipleDeviceLogIN() {

        user_deviceToken.getToken(String.valueOf(user), "shop").observe(Shop_main_activity.this, new Observer<getUser_deviceToken_response>() {
            @Override
            public void onChanged(getUser_deviceToken_response getUser_deviceToken_response) {
                if (!getUser_deviceToken_response.getToken().equals(deviceToken)) {

                    SessionManagement sessionManagement = new SessionManagement(Shop_main_activity.this);
                    sessionManagement.removeSession();
                    startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));

                }
            }
        });
    }


    private void instruction_func() {
        // if (timeLimit_UseriNSTRUCTION % 3600000 == 0){

        if (date1.equals("")) {
            date1 = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new java.util.Date());
            userInstruction = new ViewModelProvider(this).get(User_instruction.class);
            userInstruction.getInstruction("user").observe(Shop_main_activity.this, new Observer<List<user_instruction_response>>() {
                @Override
                public void onChanged(List<user_instruction_response> user_instruction_responses) {
                    int leng = user_instruction_responses.size();
                    instructionList = new ArrayList<>();

                    instructionList = user_instruction_responses;
                    instructionAdapter = new Instruction_adapter(instructionList);

                    if (leng > 0) {
                        Dialog instructionAlert = new Dialog(Shop_main_activity.this);
                        instructionAlert.setContentView(R.layout.user_instruction_alert);
                        instructionAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        instructionAlert.setCancelable(true);
                        instructionAlert.show();

                        Window window = instructionAlert.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();

                        wlp.gravity = Gravity.BOTTOM;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        wlp.windowAnimations = R.style.DialogAnimation;
                        //wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                        // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(wlp);
                        date1 = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());

                        ImageView closeButton = instructionAlert.findViewById(R.id.closeID);
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                instructionAlert.dismiss();
                                date1 = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());

                            }
                        });

                        RecyclerView instructionView = (RecyclerView) instructionAlert.findViewById(R.id.instructionViewID);
                        instructionView.setHasFixedSize(true);
                        instructionView.setLayoutManager(new LinearLayoutManager(Shop_main_activity.this, LinearLayoutManager.HORIZONTAL, false));
                        instructionAdapter.setOnClickListener(Shop_main_activity.this::OnItemClick);
                        instructionView.setAdapter(instructionAdapter);
                    }
                }
            });
        } else {
            SimpleDateFormat objSDF = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

            date2 = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = objSDF.parse(date1);
                d2 = objSDF.parse(date2);
            } catch (Exception e) {

            }
//            long diff = d2.getTime() - d1.getTime();
            long diff = 0;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= 120) {
                date1 = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new java.util.Date());
                userInstruction = new ViewModelProvider(this).get(User_instruction.class);
                userInstruction.getInstruction("user").observe(Shop_main_activity.this, new Observer<List<user_instruction_response>>() {
                    @Override
                    public void onChanged(List<user_instruction_response> user_instruction_responses) {
                        int leng = user_instruction_responses.size();
                        instructionList = new ArrayList<>();

                        instructionList = user_instruction_responses;
                        instructionAdapter = new Instruction_adapter(instructionList);

                        if (leng > 0) {
                            Dialog instructionAlert = new Dialog(Shop_main_activity.this);
                            instructionAlert.setContentView(R.layout.user_instruction_alert);
                            instructionAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            instructionAlert.setCancelable(false);
                            instructionAlert.show();

                            ImageView closeButton = instructionAlert.findViewById(R.id.closeID);
                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    instructionAlert.dismiss();
                                    date1 = (String) android.text.format.DateFormat.format("yy/MM/dd HH:mm:ss", new java.util.Date());

                                }
                            });

                            RecyclerView instructionView = (RecyclerView) instructionAlert.findViewById(R.id.instructionViewID);
                            instructionView.setHasFixedSize(true);
                            instructionView.setLayoutManager(new LinearLayoutManager(Shop_main_activity.this));
                            instructionAdapter.setOnClickListener(Shop_main_activity.this::OnItemClick);
                            instructionView.setAdapter(instructionAdapter);
                        }
                    }
                });
            }

        }


    }

    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(Shop_main_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE}, 1);
        //instruction_func();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        statusdialog = new Dialog(Shop_main_activity.this);
        statusdialog.setContentView(R.layout.inactive_status_alert);
        user_deviceToken = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(User_deviceToken.class);
        get_version = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Get_version.class);
        shop_status = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_status.class);

        version_code = String.valueOf(BuildConfig.VERSION_CODE);
        version_name = BuildConfig.VERSION_NAME;


        OneSignal.initWithContext(this);
        OneSignal.setAppId(Constants.ONESIGNAL_APP_ID);
        deviceToken = OneSignal.getDeviceState().getUserId();
        Log.d("dataxx", "checkMultipleDeviceLogIN: " + deviceToken);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, deviceToken);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

//        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
//
//        crashlytics.setCustomKey("current_level", 3);K
//        crashlytics.setCustomKey("last_UI_action", "logged_in");
        // Log.d("version: ",version_code);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();
        }

        setContentView(R.layout.shop_main_activity);
        checkConnection();

        SessionManagement sessionManagement = new SessionManagement(Shop_main_activity.this);
        int userId = sessionManagement.getSession();
        shop_id = String.valueOf(userId);
        type = sessionManagement.getType();
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

        alertCustom = new Dialog(Shop_main_activity.this);
        alertCustom.setContentView(R.layout.loader);


        shop_details = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_details.class);
        shop_details.getdata(String.valueOf(userId)).observe(Shop_main_activity.this, new Observer<Shop_response>() {
            @Override
            public void onChanged(Shop_response shop_response) {
                name = shop_response.getName();
                SHOP_NAME = name;
                SHOP_NUMBER = shop_response.getPhone();
                image = shop_response.getImage();

                Glide.with(getApplicationContext())
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.loader)
                        .into(imageView);

                profileName = (TextView) view.findViewById(R.id.profile_name);
                profileName.setText(name);

                sessionManagement.saveShopName(name);
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //alertControl();
        switch (item.getItemId()) {
            case R.id.log_out:

                SessionManagement sessionManagement = new SessionManagement(Shop_main_activity.this);
                sessionManagement.removeSession();
                startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));

                break;
            case R.id.categoryListID:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_categories_fragment(shop_id)).addToBackStack(null).commit();

                break;
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_profile_fragments()).addToBackStack(null).commit();

                break;
            case R.id.customerListID:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_customer_list_fragments(shop_id)).addToBackStack(null).commit();

                break;
            case R.id.productsID:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_all_products_fragment(shop_id)).addToBackStack(null).commit();

                break;
            case R.id.sellProductsID:

                List<ProductSell> sellList;
                sellList = new ArrayList<>();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, sellList)).addToBackStack(null).commit();

                break;
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();

                break;
            case R.id.addSlider:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ShopSliderFragment()).addToBackStack(null).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alertControl() {
        alertCustom.show();
        alertCustom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertCustom.setCancelable(false);
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


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                //Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();
                //getFragmentManager().popBackStack();

            } else {
                /*Dialog alert = new Dialog(Shop_main_activity.this);
                alert.setContentView(R.layout.exit_alert);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                alert.show();

                yesButton = (TextView) alert.findViewById(R.id.yesButtonID);
                noButton = (TextView) alert.findViewById(R.id.noButtonID);

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
                });*/

            }

        }
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        Dialog networkAlert = new Dialog(Shop_main_activity.this);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = .9f;
        applyOverrideConfiguration(override);
    }

    @Override
    public void OnItemClick(int position) {
        user_instruction_response response = instructionList.get(position);
        String link = response.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }
}


