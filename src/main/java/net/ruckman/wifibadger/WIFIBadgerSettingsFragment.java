package net.ruckman.wifibadger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class WIFIBadgerSettingsFragment extends Fragment {

    private static String WBDATABASE = "WIFIBadgerDB.db";
    WIFIBadgerMainActivity WIFIBadgerMainActivityDynamic = new WIFIBadgerMainActivity();
    boolean firstrundetect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static WIFIBadgerSettingsFragment newInstance() {
        return new WIFIBadgerSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifibadger_settings, container, false);
        //Set home and icon display on actionbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

        //setup switch and listener
        Switch IntervalSwitch = getActivity().findViewById(R.id.inapprefreshswitch);

        //get settings from main application
        IntervalSwitch.setChecked(WIFIBadgerMainActivity.alarmboolean);

        IntervalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='ON' WHERE rowid='1';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.alarmboolean = true;
                    WIFIBadgerMainActivity.applicationsettings[0] = "ON";
                    getActivity().invalidateOptionsMenu();
                } else {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='OFF' WHERE rowid='1';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.alarmboolean = false;
                    WIFIBadgerMainActivity.applicationsettings[0] = "OFF";
                    getActivity().invalidateOptionsMenu();
                }
                WIFIBadgerMainActivityDynamic.onAlarmStatusChange(getActivity());
            }
        });
        //setup service switch and listener
        Switch ServiceSwitch = getActivity().findViewById(R.id.servicerefreshswitch);

        //get settings from main application
        ServiceSwitch.setChecked(WIFIBadgerMainActivity.servicealarmboolean);

        ServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='SON' WHERE rowid='7';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.applicationsettings[6]="SON";
                    WIFIBadgerMainActivity.servicealarmboolean=true;
                    getActivity().startService(new Intent(getContext(), WIFIBadgerService.class));
                    getActivity().invalidateOptionsMenu();
                } else {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='SOFF' WHERE rowid='7';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.applicationsettings[6]="SOFF";
                    WIFIBadgerMainActivity.servicealarmboolean=false;
                    getActivity().stopService(new Intent(getContext(), WIFIBadgerService.class));
                    getActivity().invalidateOptionsMenu();
                }
            }
        });
        //setup debug switch and listener
        Switch DebugSwitch = getActivity().findViewById(R.id.emaildebugsswitch);

        //get settings from main application
        DebugSwitch.setChecked(WIFIBadgerMainActivity.emaildebugboolean);

        DebugSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='DON' WHERE rowid='8';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.applicationsettings[7]="DON";
                    WIFIBadgerMainActivity.emaildebugboolean=true;
                    Intent i = getActivity().getPackageManager()
                            .getLaunchIntentForPackage( getActivity().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().finish();
                    startActivity(i);
                } else {
                    //save in-app alarm boolean switch setting
                    SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
                    Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                    db.execSQL("UPDATE wifibadgersettingstable SET setting='DOFF' WHERE rowid='8';");
                    c.close();
                    db.close();
                    WIFIBadgerMainActivity.applicationsettings[7]="DOFF";
                    WIFIBadgerMainActivity.emaildebugboolean=false;
                    Intent i = getActivity().getPackageManager()
                            .getLaunchIntentForPackage( getActivity().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().finish();
                    startActivity(i);
                }
            }
        });

        firstrundetect=false;

        //setup roaming profile spinner and listener
        Spinner RoamingProfilespinner = getActivity().findViewById(R.id.roamingprofilespinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> roamingprofileadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.roamingprofilespinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        roamingprofileadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        RoamingProfilespinner.setAdapter(roamingprofileadapter);

        //set spinner value by position
        int RoamingProfilespinnerPosition = roamingprofileadapter.getPosition(WIFIBadgerMainActivity.applicationsettings[2]);
        RoamingProfilespinner.setSelection(RoamingProfilespinnerPosition);

        RoamingProfilespinner.setOnItemSelectedListener(new MyOnItemRoamingProfileSelectedListener());


        //setup Interval spinner and listener
        Spinner Intervalspinner = getActivity().findViewById(R.id.inapprefreshspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> inapprefreshintervaladapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.inapprefreshspinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        inapprefreshintervaladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Intervalspinner.setAdapter(inapprefreshintervaladapter);

        //set spinner value by position
        int inapprefreshintervalspinnerPosition = inapprefreshintervaladapter.getPosition(WIFIBadgerMainActivity.applicationsettings[1]);
        Intervalspinner.setSelection(inapprefreshintervalspinnerPosition);

        Intervalspinner.setOnItemSelectedListener(new MyOnItemIntervalSelectedListener());

        //setup Interval spinner and listener
        Spinner ServiceIntervalspinner = getActivity().findViewById(R.id.serviceintervalspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> serviceintervaladapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.servicerefreshspinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        serviceintervaladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ServiceIntervalspinner.setAdapter(serviceintervaladapter);

        //set spinner value by position
        int servicerefreshintervalspinnerPosition = serviceintervaladapter.getPosition(WIFIBadgerMainActivity.applicationsettings[3]);
        ServiceIntervalspinner.setSelection(servicerefreshintervalspinnerPosition);
        ServiceIntervalspinner.setOnItemSelectedListener(new MyOnItemServiceSelectedListener());

        //Display warning if needed
        TextView profilewarningtext = getActivity().findViewById(R.id.roamingprofilewarning);
        if (WIFIBadgerMainActivity.applicationsettings[2].equals("Any SSID and Open")){
            ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
            params.height = 225;
            profilewarningtext.setLayoutParams(params);
            profilewarningtext.setVisibility(TextView.VISIBLE);
        } else {
            ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
            params.height = 0;
            profilewarningtext.setLayoutParams(params);
            profilewarningtext.setVisibility(TextView.INVISIBLE);
        }

        //setup RSSI profile spinner and listener
        Spinner RSSIspinner = getActivity().findViewById(R.id.RSSIspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> RSSIprofileadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.RSSIspinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        RSSIprofileadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        RSSIspinner.setAdapter(RSSIprofileadapter);

        //set spinner value by position
        int RSSIspinnerPosition = RSSIprofileadapter.getPosition(WIFIBadgerMainActivity.applicationsettings[9]);
        RSSIspinner.setSelection(RSSIspinnerPosition);

        RSSIspinner.setOnItemSelectedListener(new MyOnItemRSSIProfileSelectedListener());

    }

    public class MyOnItemIntervalSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {

            //Save poll interval
            SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            db.execSQL("UPDATE wifibadgersettingstable SET setting='" + parent.getSelectedItem().toString() + "' WHERE rowid='2';");
            c.close();
            db.close();

            if (!firstrundetect) {
                firstrundetect = true;
            } else {
                //inform of setting change
                WIFIBadgerMainActivityDynamic.onAlarmStatusChange(getActivity());
            }
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
        //nothing to do
        }
    }

    public class MyOnItemRoamingProfileSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {

            //Save roaming profile
            SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            db.execSQL("UPDATE wifibadgersettingstable SET setting='" + parent.getSelectedItem().toString()+"' WHERE rowid='3';");
            c.close();
            db.close();
            WIFIBadgerMainActivity.applicationsettings[2] = parent.getSelectedItem().toString();
            //Display warning if needed
            TextView profilewarningtext = getActivity().findViewById(R.id.roamingprofilewarning);
            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Any SSID and Open")){
                ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
                params.height = 225;
                profilewarningtext.setLayoutParams(params);
                profilewarningtext.setVisibility(TextView.VISIBLE);
            } else {
                ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
                params.height = 0;
                profilewarningtext.setLayoutParams(params);
                profilewarningtext.setVisibility(TextView.INVISIBLE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            //Display warning if needed
            TextView profilewarningtext = getActivity().findViewById(R.id.roamingprofilewarning);
            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Any SSID and Open")){
                ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
                params.height = 225;
                profilewarningtext.setLayoutParams(params);
                profilewarningtext.setVisibility(TextView.VISIBLE);
            } else {
                ViewGroup.LayoutParams params = profilewarningtext.getLayoutParams();
                params.height = 0;
                profilewarningtext.setLayoutParams(params);
                profilewarningtext.setVisibility(TextView.INVISIBLE);
            }
        }
    }

    public class MyOnItemServiceSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {

            //Save roaming profile
            SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            db.execSQL("UPDATE wifibadgersettingstable SET setting='" + parent.getSelectedItem().toString()+"' WHERE rowid='4';");
            c.close();
            db.close();
            WIFIBadgerMainActivity.applicationsettings[3] = parent.getSelectedItem().toString();

        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            //nothing to do
        }
    }

    public class MyOnItemRSSIProfileSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {

            //Save RSSI profile
            SQLiteDatabase db = getActivity().openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            db.execSQL("UPDATE wifibadgersettingstable SET setting='" + parent.getSelectedItem().toString()+"' WHERE rowid='10';");
            c.close();
            db.close();
            WIFIBadgerMainActivity.applicationsettings[9] = parent.getSelectedItem().toString();

        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            //nothing to do
        }
    }

}
