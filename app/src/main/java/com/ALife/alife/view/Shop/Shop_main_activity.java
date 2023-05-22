package com.ALife.alife.view.Shop;

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


import com.ALife.alife.BuildConfig;
import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Instruction_adapter;
import com.ALife.alife.model.Shop_response;
import com.ALife.alife.model.getUser_deviceToken_response;
import com.ALife.alife.model.get_version_response;
import com.ALife.alife.model.shop_status_response;
import com.ALife.alife.model.token_update_response;
import com.ALife.alife.model.user_instruction_response;
import com.ALife.alife.view.Customer.Customer_main_activity;
import com.ALife.alife.view.LoginActivity;
import com.ALife.alife.viewmodel.Get_version;
import com.ALife.alife.viewmodel.SessionManagment;
import com.ALife.alife.viewmodel.Shop_details;
import com.ALife.alife.viewmodel.Shop_status;
import com.ALife.alife.viewmodel.User;
import com.ALife.alife.viewmodel.User_deviceToken;
import com.ALife.alife.viewmodel.User_instruction;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

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
    Dialog dialog, statusdialog;
    User_instruction userInstruction;
    User_deviceToken user_deviceToken;
    private List<user_instruction_response> instructionList;
    Instruction_adapter instructionAdapter;
    public int leng;
    String date1 = "", date2 = "";
    private String deviceToken;


    @SuppressLint("MissingPermission")
    protected void onStart() {

        super.onStart();

        SessionManagment session = new SessionManagment(Shop_main_activity.this);
        int user = session.getSession();


        if (user == -1) {
            Intent intent = new Intent(Shop_main_activity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);


        }
        // check either another device loggedin or not
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            deviceToken = task.getResult().getToken();
                            user_deviceToken.getToken(String.valueOf(user), "shop").observe(Shop_main_activity.this, new Observer<getUser_deviceToken_response>() {
                                @Override
                                public void onChanged(getUser_deviceToken_response getUser_deviceToken_response) {
                                    if (!getUser_deviceToken_response.getToken().equals(deviceToken)) {
                                        //Log.d("token1",deviceToken);
                                        //Log.d("token2",getUser_deviceToken_response.getToken());
                                        Toast.makeText(Shop_main_activity.this, String.valueOf(user), Toast.LENGTH_SHORT).show();
                                        SessionManagment sessionManagment = new SessionManagment(Shop_main_activity.this);
                                        sessionManagment.removeSession();
                                        startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));

                                    }
                                }
                            });
                            /*user_deviceToken.getMessage(String.valueOf(user),"shop",deviceToken).observe(Shop_main_activity.this, new Observer<getUser_deviceToken_response>() {
                                @Override
                                public void onChanged(getUser_deviceToken_response getUser_deviceToken_response) {
                                    if(getUser_deviceToken_response.getToken().equals("no"))
                                    {
                                        Log.d("token1",deviceToken);
                                        Log.d("token2",getUser_deviceToken_response.getToken());
                                        Toast.makeText(Shop_main_activity.this,String.valueOf(user),Toast.LENGTH_SHORT).show();
                                        SessionManagment sessionManagment = new SessionManagment(Shop_main_activity.this);
                                        sessionManagment.removeSession();
                                        startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));
                                    }
                                }
                            });*/

                        } else {
                            SessionManagment sessionManagment = new SessionManagment(Shop_main_activity.this);
                            sessionManagment.removeSession();
                            startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));
                            // Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // end check


        // instruction_func();
        get_version.getData().observe(Shop_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                Log.d("dataxx", "onChanged: "+get_version_response.getVersion_code().toString()+" "+version_code);
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

        dialog = new Dialog(Shop_main_activity.this);
        dialog.setContentView(R.layout.update_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        statusdialog = new Dialog(Shop_main_activity.this);
        statusdialog.setContentView(R.layout.inactive_status_alert);
        user_deviceToken = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(User_deviceToken.class);
        get_version = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Get_version.class);
        shop_status = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_status.class);
        shop_status.getStatus(shop_id).observe(Shop_main_activity.this, new Observer<shop_status_response>() {
            @Override
            public void onChanged(shop_status_response shop_status_response) {
                //Toast.makeText(Shop_main_activity.this,shop_status_response.getStatus(),Toast.LENGTH_SHORT).show();
                if (shop_status_response.getStatus().equals("1")) {
                    statusdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    statusdialog.setCancelable(false);
                    statusdialog.show();

                } else {
                    statusdialog.dismiss();
                }
            }
        });
        version_code = String.valueOf(BuildConfig.VERSION_CODE);
        version_name = BuildConfig.VERSION_NAME;
        // Log.d("version: ",version_code);
        get_version.getData().observe(Shop_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                if (!(get_version_response.getVersion_code().equals(version_code))) {
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

                }
            }
        });
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();
        }

        setContentView(R.layout.shop_main_activity);
        checkConnection();

        SessionManagment sessionManagment = new SessionManagment(Shop_main_activity.this);
        int userId = sessionManagment.getSession();
        shop_id = String.valueOf(userId);
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

        alertCustom = new Dialog(Shop_main_activity.this);
        alertCustom.setContentView(R.layout.loader);


        shop_details = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Shop_details.class);
        shop_details.getdata(String.valueOf(userId)).observe(Shop_main_activity.this, new Observer<Shop_response>() {
            @Override
            public void onChanged(Shop_response shop_response) {
                name = shop_response.getName();
                SHOP_NAME = name;
                SHOP_NUMBER = shop_response.getPhone();
                //Log.d("number:", shop_response.getPhone());
                image = shop_response.getImage();
                //Toast.makeText(homeScreen.this,name,Toast.LENGTH_SHORT).show();
                Picasso.get().load(image).fit().centerInside().into(imageView);
                profileName = (TextView) view.findViewById(R.id.profile_name);
                profileName.setText(name);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        alertControl();
        if (item.getItemId() == R.id.log_out) {

            SessionManagment sessionManagment = new SessionManagment(Shop_main_activity.this);
            sessionManagment.removeSession();
            startActivity(new Intent(Shop_main_activity.this, LoginActivity.class));

        } else if (item.getItemId() == R.id.categoryListID) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_categories_fragment(shop_id)).addToBackStack(null).commit();

        } else if (item.getItemId() == R.id.profile) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_profile_fragments()).addToBackStack(null).commit();

        } else if (item.getItemId() == R.id.customerListID) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_customer_list_fragments(shop_id)).addToBackStack(null).commit();

        }/* else if (item.getItemId() == R.id.sub_shopID) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Sub_shop_fragment()).addToBackStack(null).commit();

        } else if (item.getItemId() == R.id.adminListID) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_admin_fragments(shop_id)).addToBackStack(null).commit();

        } */ else if (item.getItemId() == R.id.productsID) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_all_products_fragment(shop_id)).addToBackStack(null).commit();

        } else if (item.getItemId() == R.id.sellProductsID) {

            List<ProductSell> sellList;
            sellList = new ArrayList<>();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_sellcategoriesORproducts_fragment(shop_id, sellList)).addToBackStack(null).commit();

        } else if (item.getItemId() == R.id.nav_home) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Shop_homescreen_fragment()).commit();

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


