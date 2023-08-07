package com.alifew.alife.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import com.alifew.alife.R;

import com.alifew.alife.Utils.GPSLocationTurnOn;
import com.alifew.alife.session.SessionManagement;
import com.alifew.alife.view.Customer.Customer_main_activity;
import com.alifew.alife.view.Operator.Operator_main_activity;
import com.alifew.alife.view.Shop.Shop_main_activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements LocationListener {
    private int LocationPrmissionDenyCount = 1;
    private LocationManager locationManager;
    private Location AddressLocation;
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    SessionManagement sessionManagement;
    private static final int REQUEST_LOCATION = 12;
    TextView locationText;

    int userId;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();


        checkPermission();

    }

    private void checkPlayStoreVersion() {
    }


    private void initView() {
        sessionManagement = new SessionManagement(SplashActivity.this);
        locationText = findViewById(R.id.locationText);

        userId = sessionManagement.getSession();
        type = sessionManagement.getType();
    }


    private void checkSession() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId != -1) {
                    switch (type) {
                        case "shopkeeper":
                            startActivity(new Intent(SplashActivity.this, Shop_main_activity.class));
                            break;
                        case "customer":
                            startActivity(new Intent(SplashActivity.this, Customer_main_activity.class));
                            break;
                        case "admin":
                            startActivity(new Intent(SplashActivity.this, Operator_main_activity.class));
                            break;
                    }


                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkPermission() {


        locationText.setText("🚩 Fetching location");

        Dexter.withContext(this)
                .withPermissions(Arrays.asList(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                )).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        Log.e("onPermissionsChecked", "Called");
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Log.e("onPermissionsGranted", "Called");


                            LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                Log.e("GPS 1", "Called");
                                //GPS Permission not Granted
                                GPSLocationTurnOn.displayLocationSettingsRequest(getApplicationContext());
                            } else {
                                Log.e("GPS 2", "Called");
                                //GPS Permission Granted
                                checkLocationPermission();
                            }

                        } else {
                            //  checkPermission();
                            Log.e("===1", "Called");
                            LocationPrmissionDenyCount = LocationPrmissionDenyCount + 1;
                            if (LocationPrmissionDenyCount >= 2) {
//                                CustomInfoDialog1.showInfoDialog(getActivity(),
//                                        "Location permission required",
//                                        "You have already denied this permission. To enable, go to Settings and turn on Location, permit allow all the time",
//                                        new CustomInfoDialog1.OnResponseListners() {
//                                            @Override
//                                            public void onResponse() {
//
//                                            }
//                                        });
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 101);
                            }
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void checkLocationPermission() {

        Log.d("locationxx", "Check");
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("locationxx", "Not granted");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        Log.d("locationxx", "Granted");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 60000, (LocationListener) this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 60000, (LocationListener) this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("locationxx", "Called" + requestCode);
        switch (requestCode) {
            case 12:
                Log.d("locationxx", "Called 12");
                if (resultCode == RESULT_OK) {
                    LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Log.d("locationxx", "Called ok");
                        //GPS Permission not Granted
                        checkLocationPermission();

                    } else {
                        Log.d("locationxx", "Called granted");
                        //GPS Permission Granted
                        checkLocationPermission();
                    }
                } else {
                    Log.d("locationxx", "gps grated 2");
                    //GPS Permission Granted
                    checkLocationPermission();
                }


                break;
            case 101:
                checkLocationPermission();
                break;
        }


    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("onLocationChanged", "Called" + location.getLatitude());
        locationManager.removeUpdates((LocationListener) this);
        AddressLocation = location;
        setLocation();

    }

    private void setLocation() {


        if (AddressLocation != null) {
            String latitude = String.valueOf(new DecimalFormat("##.#####").format(AddressLocation.getLatitude()));
            String longitude = String.valueOf(new DecimalFormat("##.#####").format(AddressLocation.getLongitude()));

            LinkedHashMap<String, Object> body = new LinkedHashMap<>();
            body.put("latitude", String.valueOf(AddressLocation.getLatitude()));
            body.put("longitude", String.valueOf(AddressLocation.getLongitude()));

            //  Log.d("dataxx", body.toString());

            locationText.setText("Let's Go");

            sessionManagement.saveLocation(latitude, longitude);
            checkSession();
        }

    }

}