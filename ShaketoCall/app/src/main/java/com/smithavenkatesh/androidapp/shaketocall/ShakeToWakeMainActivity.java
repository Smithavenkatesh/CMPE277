package com.smithavenkatesh.androidapp.shaketocall;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import android.location.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShakeToWakeMainActivity extends AppCompatActivity implements ShakeEventListener.ShakeListener, LocationListener {


    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private ShakeEventListener sd;
    public boolean permissionGranted=false;


    Location currentLocation;
    LocationManager locationManager;
    public double latitude;
    public double longitude;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_to_wake_main);

        //Check if sensor exits
        PackageManager PM = this.getPackageManager();
        boolean check_accelerometer = PM.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        System.out.println(check_accelerometer);

        if (check_accelerometer) {
            sd = new ShakeEventListener();
            sd.setListener(this);
            sd.init(this);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        sd.register();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //locationManager.removeUpdates(this);
        sd.deregister();
    }


    @Override
    public void onShake() {
        callWrapper();
    }


    public void callWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if ((!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) && (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION)))
            permissionsNeeded.add("GPS Location");
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("Phone Call");
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("Send Message");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(ShakeToWakeMainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(ShakeToWakeMainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        methodAfterPermission();
    }

    /**
     * Method which displays dialog box to user and asks permission to access Location, Contacts, and SMS
     *
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ShakeToWakeMainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<String, Integer>();

                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                // Check for all permissions
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    // All Permissions Granted
                    methodAfterPermission();
                } else {

                    // Permission Denied
                    Toast.makeText(ShakeToWakeMainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void methodAfterPermission() {

        Uri phoneNumber = Uri.parse("tel:3125363429");
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumber);


        getLocation();
        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();
        String locationName="";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(null!=listAddresses&&listAddresses.size()>0){
                locationName = listAddresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String locationUrl = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + locationName + ")";


        StringBuffer smsBody = new StringBuffer();
        smsBody.append(Uri.parse(locationUrl));
        String message = "Help Needed! My Location is  " + smsBody;


        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("3125363429", null, message, null, null);



        //Verify There is an App to Receive the Intent
        PackageManager packageManager = getPackageManager();
        List callactivities = packageManager.queryIntentActivities(callIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = callactivities.size() > 0;

        if (isIntentSafe) {
            startActivity(callIntent);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        //

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        System.out.println(latitude + " " + longitude);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /**
     * Method to check whether a location is requested from other apps.
     * If current location is not null, get the latitude and longitude. Else request for the location.
     */
    public void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {

            //Default Location
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                callWrapper();
            }

            currentLocation = locationManager.getLastKnownLocation(provider);
            System.out.println(currentLocation);
            if (currentLocation != null) {
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                //onLocationChanged(currentLocation);
            } else {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
            }
        } else {
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, this);
                Log.d("activity", "LOC Network Enabled");
                if (locationManager != null) {
                    currentLocation = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (currentLocation != null) {
                        Log.d("activity", "LOC by Network");
                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (currentLocation == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);
                    Log.d("activity", "RLOC: GPS Enabled");
                    if (locationManager != null) {
                        currentLocation = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (currentLocation != null) {
                            Log.d("activity", "RLOC: loc by GPS");

                            latitude = currentLocation.getLatitude();
                            longitude = currentLocation.getLongitude();
                        }
                    }

                }
            }
        }
    }
}

