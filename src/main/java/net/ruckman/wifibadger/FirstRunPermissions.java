package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.Manifest;

public class FirstRunPermissions extends AppCompatActivity {

    //Marshmallow permission constants
    final int READ_PHONE_STATE=1000;
    final int LOCATION=2000;

    Boolean OK1=false;
    Boolean OK2=false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstrunpermissions);
        Button b2 = findViewById(R.id.button2);
        Button bquit = findViewById(R.id.buttonquit);
        Button blocation = findViewById(R.id.locationbutton);
        Button bphone = findViewById(R.id.phonebutton);
        //OK Button
        b2.setOnClickListener(myhandler2);
        b2.setEnabled(false);

        //QUIT BUTTON
        bquit.setOnClickListener(myhandlerQuit);

        //REQUEST LOCATION BUTTON
        blocation.setOnClickListener(myhandlerLocation);

        //REQUEST READ_PHONE_STATE BUTTON
        bphone.setOnClickListener(myhandlerPhone);

        if (Build.VERSION.SDK_INT >= 23) {
            // Check for LOCATION permission
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //SET BUTTON TRUE and SET OK FALSE
                blocation.setEnabled(true);
                b2.setEnabled(false);
                OK1=false;
            }else{
                blocation.setEnabled(false);
                b2.setEnabled(true);
                OK1=true;
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            // Check for READ_PHONE_STATE permission
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                //SET BUTTON TRUE and SET OK FALSE
                bphone.setEnabled(true);
                b2.setEnabled(false);
                OK2=false;
            }else{
                bphone.setEnabled(false);
                b2.setEnabled(true);
                OK2=true;
            }
        }

        //SET OK BUTTON FINAL LOAD
        WIFIBadgerRequestPermissionsCheckOK();
    }


    View.OnClickListener myhandler2 = new View.OnClickListener() {
        public void onClick(View v) {
            Button b2 = findViewById(R.id.button2);
            b2.setText(R.string.app_loading);
            b2.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), WIFIBadgerMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener myhandlerQuit = new View.OnClickListener() {
        @SuppressLint("ApplySharedPref")
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            prefs.edit().putBoolean("firstrun", true).commit();
            finish();
            System.exit(0);
        }
    };

    View.OnClickListener myhandlerLocation = new View.OnClickListener() {
        public void onClick(View v) {
            Button blocation = findViewById(R.id.locationbutton);
            WIFIBadgerRequestPermissionsLocation();
            blocation.setEnabled(false);
        }
    };

    View.OnClickListener myhandlerPhone = new View.OnClickListener() {
        public void onClick(View v) {
            Button bphone = findViewById(R.id.phonebutton);
            WIFIBadgerRequestPermissionsReadPhoneState();
            bphone.setEnabled(false);
        }
    };

    //MARSHMALLOW REQUEST RESULTS OVERRIDE
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Button bphone = findViewById(R.id.phonebutton);
                    bphone.setEnabled(false);
                    OK2=true;
                    WIFIBadgerRequestPermissionsCheckOK();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Button bphone = findViewById(R.id.phonebutton);
                    bphone.setEnabled(true);
                    OK2=false;
                    WIFIBadgerRequestPermissionsCheckOK();
                }
            }
            break;
            case LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Button blocation = findViewById(R.id.locationbutton);
                    blocation.setEnabled(false);
                    OK1=true;
                    WIFIBadgerRequestPermissionsCheckOK();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Button blocation = findViewById(R.id.locationbutton);
                    blocation.setEnabled(true);
                    OK1=false;
                    WIFIBadgerRequestPermissionsCheckOK();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void WIFIBadgerRequestPermissionsLocation () {
        if (Build.VERSION.SDK_INT >= 23) {
            // Check for LOCATION permission
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION);
            }

        }

    }

    private void WIFIBadgerRequestPermissionsReadPhoneState () {
        if (Build.VERSION.SDK_INT >= 23) {
            // Check for READ_PHONE_STATE permission
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE);
            }

        }

    }

    private void WIFIBadgerRequestPermissionsCheckOK () {
        //SET OK BUTTON FINAL LOAD
        if (OK1 && OK2) {
            Button b2 = findViewById(R.id.button2);
            b2.setEnabled(true);
        } else {
            Button b2 = findViewById(R.id.button2);
            b2.setEnabled(false);
        }
    }

}
