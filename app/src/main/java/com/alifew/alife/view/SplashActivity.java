package com.alifew.alife.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;


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
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements LocationListener {
    private int LocationPrmissionDenyCount = 1;
    private LocationManager locationManager;
    private Location AddressLocation;
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    SessionManagement sessionManagement;
    private static final int REQUEST_LOCATION = 12;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        initView();


        //checkSession();

        checkPermission();


    }


    private void initView() {
        sessionManagement = new SessionManagement(SplashActivity.this);
    }


    private void checkSession() {

        int userId = sessionManagement.getSession();
        String type = sessionManagement.getType();
        String phone = sessionManagement.getPhone();

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

  /*  private void checkPermission() {
        Log.d("dataxx", "checkPermission: ");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_SHORT).show();

                checkSession();
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    private void checkPermission() {
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


        if(AddressLocation != null){
            String latitude = String.valueOf(new DecimalFormat("##.#####").format(AddressLocation.getLatitude()));
            String longitude = String.valueOf(new DecimalFormat("##.#####").format(AddressLocation.getLongitude()));
            Log.d("locationxx", " lat: " + String.valueOf(AddressLocation.getLatitude()) + " lang " + String.valueOf(AddressLocation.getLongitude()));

            sessionManagement.saveLocation(latitude, longitude);
            checkSession();
        }

    }
}