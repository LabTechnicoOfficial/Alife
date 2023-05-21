package com.ALife.alife.view.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ALife.alife.BuildConfig;
import com.ALife.alife.R;
import com.ALife.alife.model.Customer_response;
import com.ALife.alife.model.getUser_deviceToken_response;
import com.ALife.alife.model.get_version_response;
import com.ALife.alife.view.LoginActivity;
import com.ALife.alife.view.Shop.Shop_homescreen_fragment;
import com.ALife.alife.view.Shop.Shop_main_activity;
import com.ALife.alife.viewmodel.Customer_details;
import com.ALife.alife.viewmodel.Get_version;
import com.ALife.alife.viewmodel.SessionManagment;
import com.ALife.alife.viewmodel.User;
import com.ALife.alife.viewmodel.User_deviceToken;
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

import java.util.Timer;
import java.util.TimerTask;

public class Customer_main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout headerimage;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Customer_details customer_details;
    TextView profileName;
    String type, name, image;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Dialog alertCustom;
    private String customer_id;
    Get_version get_version;
    private String version_name, version_code;
    Dialog dialog;
    private String deviceToken;
    User_deviceToken user_deviceToken;
    AdManagerAdView mAdManagerAdView;
    @SuppressLint("MissingPermission")
    protected void onStart() {
        // isInForeground = false;
        super.onStart();
        SessionManagment sessionManagment = new SessionManagment(Customer_main_activity.this);
        int userId = sessionManagment.getSession();
        if (userId == -1) {
            Intent intent = new Intent(Customer_main_activity.this, LoginActivity.class);
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
                            user_deviceToken.getToken(String.valueOf(userId), "customer").observe(Customer_main_activity.this, new Observer<getUser_deviceToken_response>() {
                                @Override
                                public void onChanged(getUser_deviceToken_response getUser_deviceToken_response) {
                                    if (!getUser_deviceToken_response.getToken().equals(deviceToken)) {
                                        SessionManagment sessionManagment = new SessionManagment(Customer_main_activity.this);
                                        sessionManagment.removeSession();
                                        startActivity(new Intent(Customer_main_activity.this, LoginActivity.class));

                                    }
                                }
                            });

                        } else {
                            SessionManagment sessionManagment = new SessionManagment(Customer_main_activity.this);
                            sessionManagment.removeSession();
                            startActivity(new Intent(Customer_main_activity.this, LoginActivity.class));
                            // Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // end check
        get_version.getData().observe(Customer_main_activity.this, new Observer<get_version_response>() {
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
        user_deviceToken=new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(User_deviceToken.class);

        version_code = String.valueOf(BuildConfig.VERSION_CODE);
        version_name = BuildConfig.VERSION_NAME;
        dialog = new Dialog(Customer_main_activity.this);
        dialog.setContentView(R.layout.update_alert);
        get_version.getData().observe(Customer_main_activity.this, new Observer<get_version_response>() {
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
                }
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_homescreen_fragment()).commit();

        }
        setContentView(R.layout.customer_main_activity);
        checkConnection();

        alertCustom = new Dialog(Customer_main_activity.this);
        alertCustom.setContentView(R.layout.loader);

        SessionManagment sessionManagment = new SessionManagment(Customer_main_activity.this);
        int userId = sessionManagment.getSession();
        customer_id = String.valueOf(userId);
        type = sessionManagment.getType();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        headerimage = (LinearLayout) findViewById(R.id.headerview);

        //banner add
        mAdManagerAdView = (AdManagerAdView) findViewById(R.id.adManagerAdView);

        //
        View view = navigationView.inflateHeaderView(R.layout.header);
        imageView = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_imageID);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        customer_details = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Customer_details.class);
        customer_details.getdata(String.valueOf(userId)).observe(Customer_main_activity.this, new Observer<Customer_response>() {
            @Override
            public void onChanged(Customer_response customer_response) {
                name = customer_response.getName();
                image = customer_response.getImage();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            alertControl();
            SessionManagment sessionManagment = new SessionManagment(Customer_main_activity.this);
            sessionManagment.removeSession();
            startActivity(new Intent(Customer_main_activity.this, LoginActivity.class));
        } else if (item.getItemId() == R.id.profile) {
            alertControl();
            getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_profile_fragments()).addToBackStack(null).commit();
        } else if (item.getItemId() == R.id.shopListID) {
            alertControl();
            getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_shopList_fragment(customer_id)).addToBackStack(null).commit();
        }


        //close drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
        }, 2500);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                //Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();

            } else {
                /*Dialog alert = new Dialog(Customer_main_activity.this);
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
                });*/
            }
        }
    }


    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(this);
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
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
}

