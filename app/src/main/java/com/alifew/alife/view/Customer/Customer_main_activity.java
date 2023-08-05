package com.alifew.alife.view.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.BuildConfig;
import com.alifew.alife.R;
import com.alifew.alife.Utils.Constants;
import com.alifew.alife.model.Customer_response;
import com.alifew.alife.model.getUser_deviceToken_response;
import com.alifew.alife.model.get_version_response;
import com.alifew.alife.view.LoginActivity;
import com.alifew.alife.view.Shop.Shop_main_activity;
import com.alifew.alife.viewmodel.Customer_details;
import com.alifew.alife.viewmodel.Get_version;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.viewmodel.SessionManagment_registration;
import com.alifew.alife.viewmodel.User_deviceToken;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class Customer_main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Customer_details customer_details;
    TextView profileName;
    String type, name, image;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Dialog alertCustom;
    private String customer_id;
    Get_version get_version;
    private String version_name, version_code;
    private String deviceToken;
    User_deviceToken user_deviceToken;
    AdManagerAdView mAdManagerAdView;

    int userId;
    SessionManagement sessionManagement;

    @SuppressLint("MissingPermission")
    protected void onStart() {
        // isInForeground = false;
        super.onStart();

        userId = sessionManagement.getSession();

        checkForAppUpdate();

        //checkVersion();
    }

    private void checkVersion() {

        get_version = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Get_version.class);

        version_code = String.valueOf(BuildConfig.VERSION_CODE);
        version_name = BuildConfig.VERSION_NAME;
        Dialog dialog = new Dialog(Customer_main_activity.this);
        dialog.setContentView(R.layout.update_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        get_version.getData().observe(Customer_main_activity.this, new Observer<get_version_response>() {
            @Override
            public void onChanged(get_version_response get_version_response) {
                if (!(get_version_response.getVersion_code().equals(version_code))) {

                    dialog.show();
                    TextView updateButton = dialog.findViewById(R.id.updateButton);
                    TextView noButton = dialog.findViewById(R.id.noButton);


                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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


        user_deviceToken = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(User_deviceToken.class);


        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.cus_frame_container, new Customer_homescreen_fragment()).commit();

        }
        setContentView(R.layout.customer_main_activity);
        checkConnection();

        sessionManagement = new SessionManagement(Customer_main_activity.this);
        deviceToken = sessionManagement.getDeviceToken();

        //   Log.d("dataxx", "checkMultipleDeviceLogIN: "+deviceToken);

        alertCustom = new Dialog(Customer_main_activity.this);
        alertCustom.setContentView(R.layout.loader);

        SessionManagement sessionManagement = new SessionManagement(Customer_main_activity.this);
        int userId = sessionManagement.getSession();
        customer_id = String.valueOf(userId);
        type = sessionManagement.getType();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //banner add
        //mAdManagerAdView = (AdManagerAdView) findViewById(R.id.adManagerAdView);

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
                Glide.with(getApplicationContext())
                        .load(customer_response.getImage())
                        .centerCrop()
                        .placeholder(R.drawable.loader)
                        .into(imageView);
//                Picasso.get().load(image).fit().centerInside().into(imageView);
                profileName = (TextView) view.findViewById(R.id.profile_name);
                profileName.setText(name);
            }
        });

        checkMultipleDeviceLogIN();
    }

    private void checkMultipleDeviceLogIN() {

        user_deviceToken.getToken(String.valueOf(userId), "customer").observe(Customer_main_activity.this, new Observer<getUser_deviceToken_response>() {
            @Override
            public void onChanged(getUser_deviceToken_response getUser_deviceToken_response) {
                if (!getUser_deviceToken_response.getToken().equals(deviceToken)) {

                    Dialog sessionOutAlert = new Dialog(Customer_main_activity.this);
                    sessionOutAlert.setContentView(R.layout.session_out_alert);
                    sessionOutAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    sessionOutAlert.setCancelable(false);
                    sessionOutAlert.show();

                    Window window = sessionOutAlert.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                    wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(wlp);

                    TextView okButton = sessionOutAlert.findViewById(R.id.okButton);

                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(Shop_main_activity.this, "ok", Toast.LENGTH_SHORT).show();
                            sessionManagement = new SessionManagement(Customer_main_activity.this);
                            sessionManagement.removeSession();
                            startActivity(new Intent(Customer_main_activity.this, LoginActivity.class));
                            finish();
                        }
                    });

                }
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
            SessionManagement sessionManagement = new SessionManagement(Customer_main_activity.this);
            sessionManagement.removeSession();
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

    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case REQ_CODE_VERSION_UPDATE:
                if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                    // Log.d("Update flow failed! Result code: " + resultCode);
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    unregisterInstallStateUpdListener();
                }

                break;

        }
    }


    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    //startAppUpdateFlexible(appUpdateInfo);

                    force_app_update(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Start an update.
                    //startAppUpdateImmediate(appUpdateInfo);

                    force_app_update(appUpdateInfo);
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar =
                Snackbar.make(drawerLayout, "Updating", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.restart, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                                //Toast.makeText(this, "update available", Toast.LENGTH_SHORT).show();
                            }
                        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }


    //force app update

    @SuppressLint("SetTextI18n")
    public void force_app_update(AppUpdateInfo appUpdateInfo) {


        Dialog alertDialog = new Dialog(this);
        alertDialog.setContentView(R.layout.update_app_alert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView titleText = alertDialog.findViewById(R.id.titleText);
        TextView messageText = alertDialog.findViewById(R.id.messageText);

        titleText.setText("Update " + getString(R.string.app_name));
        messageText.setText(getString(R.string.app_name) + " recommends that you update to the latest version. You aren't authorized to access features of this without upgrading to the latest version.");

        AppCompatButton updateButton = alertDialog.findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
    }
}

