package net.ruckman.wifibadger;

import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class WIFIBadgerMainActivity extends AppCompatActivity implements ActionBar.TabListener {

    WIFIBadgerWIFIPoller WIFIBadgerWIFIPoller = new WIFIBadgerWIFIPoller();
    WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();
    WIFIBadgerStatusFragment WIFIBadgerStatusFragment = new WIFIBadgerStatusFragment();

    //Application Settings Global Variables
    public static String[] applicationsettings=new String[10];
    public static Boolean alarmboolean;
    public static Boolean servicealarmboolean;
    public static Boolean emaildebugboolean;

    //WIFI status Global Variables
    public static Boolean iswifienabled;
    public static int WIFIState;
    public static Boolean isconnected;
    public static String ssid;
    public static String bssid;
    public static String mac;
    public static int rssi;
    public static int linkspeed;
    public static String dns1;
    public static String dns2;
    public static String gateway;
    public static String ipaddress;
    public static String leaseduration;
    public static String netmask;
    public static String server;

    //WIFI scan Global Variables
    //create arrays for data
    public static List<ScanResult> wifiList;
    public static int[] rowidscan;
    public static String[] ssidscan;
    public static String[] bssidscan;
    public static int[] levelscan;
    public static int[] frequencyscan;
    public static String[] capabilitiesscan;

    //WIFI saved SSID lisy Global Variables
    public static String[] SavedSSIDs;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            Intent intent = new Intent(getApplicationContext(), FirstRunActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_wifibadger_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        Context context = getApplicationContext();

        //Set up database if it doesn't exist and build tables for defaults
        String WBDATABASE = "WIFIBadgerDB.db";
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgersettingstable(rowid INTEGER,setting VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgerstatustable(rowid INTEGER,isenabled BOOLEAN,state INTEGER,isconnected BOOLEAN,ssid VARCHAR,bssid VARCHAR,mac VARCHAR,rssi INTEGER,linkspeed INTEGER,dns1 VARCHAR,dns2 VARCHAR,gateway VARCHAR,ipaddress VARCHAR,leaseduration VARCHAR,netmask VARCHAR,server VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgersavedaptable(rowid INTEGER,savedssid VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgerapscantable(rowidscan INTEGER,ssidscan VARCHAR,bssidscan VARCHAR,levelscan INTEGER,frequencyscan INTEGER,capabilitiesscan VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgercanidatetable(rowid INTEGER,ssid VARCHAR,bssid VARCHAR,polls INTEGER);");
        Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);

        if(c.getCount()==0)
        {
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('0','V');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('1','ON');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('2','2 Minutes');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('3','Same SSID');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('4','15 Minutes');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('5','ScreenOff');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('6','00:00:00:00:00:00');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('7','SON');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('8','DOFF');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('9','0');");
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('10','15 RSSI');");
            applicationsettings[0]="ON";
            applicationsettings[1]="2 Minutes";
            applicationsettings[2]="Same SSID";
            applicationsettings[3]="15 Minutes";
            applicationsettings[4]="ScreenOff";
            applicationsettings[5]="00:00:00:00:00:00";
            applicationsettings[6]="SON";
            applicationsettings[7]="DOFF";
            applicationsettings[8]="0";
            applicationsettings[9]="15 RSSI";
        }
        if(c.getCount()==10)
        {
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('10','15 RSSI');");
            applicationsettings[9]="15 RSSI";
        }
        c.close();
        db.close();

        //set initial values
        //get settings from database
        applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettings(getApplication());

        //convert string to boolean
        alarmboolean = true;
        if (applicationsettings[0].equals("ON")) { alarmboolean=true; }
        if (applicationsettings[0].equals("OFF")) { alarmboolean=false; }

        //convert string to boolean
        servicealarmboolean = true;
        if (applicationsettings[6].equals("SON")) { servicealarmboolean=true; }
        if (applicationsettings[6].equals("SOFF")) { servicealarmboolean=false; }

        //convert string to boolean
        emaildebugboolean = false;
//        if (applicationsettings[7].equals("DON")) { emaildebugboolean=true; }
//        if (applicationsettings[7].equals("DOFF")) { emaildebugboolean=false; }

//        if (emaildebugboolean) {
            //Catch and e-mail exceptions for debugging
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionMailer(this));
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wifibadger_main, menu);
        if (alarmboolean) {
            MenuItem item = menu.findItem(R.id.action_refresh);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.action_refresh);
            item.setVisible(true);
        }

        if (servicealarmboolean) {
            MenuItem item = menu.findItem(R.id.action_exitAll);
            item.setVisible(true);
        } else {
            MenuItem item = menu.findItem(R.id.action_exitAll);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        //exit if selected in action menu
        if (id == R.id.action_exit) {
            onMainAppExit(getApplicationContext());
            finish();
            System.exit(0);
        }
        if (id == R.id.action_exitAll) {
            onMainAppExitAll(getApplicationContext());
            finish();
            System.exit(0);
        }
        //refresh data
        if (id == R.id.action_refresh) {
            Context context = getApplicationContext();
            Toast.makeText(context, R.string.action_refreshing, Toast.LENGTH_LONG).show();

            WIFIBadgerWIFIPoller.getWIFIInfo(context, 0);
            sendBroadcast(new Intent(net.ruckman.wifibadger.WIFIBadgerWIFIPoller.ACTION_UPDATE_WIFI_STATUS), net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a Fragment
            if (position == 0){
                return net.ruckman.wifibadger.WIFIBadgerStatusFragment.newInstance();
            }
            if (position == 1){
                return WIFIBadgerAreaFragment.newInstance();
            }
            if (position == 2){
                return WIFIBadgerSettingsFragment.newInstance();
            }
            if (position == 3){
                return WIFIBadgerAboutFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //save data
        WIFIBadgerCommonCalls.SaveActiveDatabase(getApplication());
        alarmboolean = false;

        WIFIBadgerWIFIPoller.setServiceAlarm(getApplicationContext(), alarmboolean);
//       Boolean salarmboolean = true;
        WIFIBadgerWIFIPoller.setServiceAlarmService(getApplicationContext(), true);

        //unregister listener for connectivity changes
        unregisterReceiver(mOnUpdateConnStatus);

        //set app not visible
        WIFIBadgerCommonCalls.SetApplicationVisibility("NV", getApplication());

    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            WIFIBadgerCommonCalls.CheckIfLocationIsEnabled(WIFIBadgerMainActivity.this);
        }
        //Start Poller
        Intent intent = new Intent(getApplicationContext(),WIFIBadgerWIFIPoller.class);
        startService(intent);

        //Start Screen Listen Service
        if (applicationsettings[6].equals("SON")){
            startService(new Intent(this, WIFIBadgerService.class));
        } else {
            stopService(new Intent(this, WIFIBadgerService.class));
        }

//        Boolean salarmboolean = false;
        WIFIBadgerWIFIPoller.setServiceAlarmService(getApplicationContext(), false);

        //get settings from database
        applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettings(getApplication());

        //convert string to boolean
        alarmboolean = true;
        if (applicationsettings[0].equals("ON")) { alarmboolean=true; }
        if (applicationsettings[0].equals("OFF")) { alarmboolean=false; }

        //set alarm for polling
        WIFIBadgerWIFIPoller.setServiceAlarm(getApplicationContext(), alarmboolean);

        //register listener for connectivity changes
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mOnUpdateConnStatus, filter, net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE, null);

        //set app visible
        WIFIBadgerCommonCalls.SetApplicationVisibility("V", getApplication());
        WIFIBadgerCommonCalls.LoadActiveDatabase(getApplication());
    }

    //Take action when broadcast is received with the proper auth for connectivity changes
    private BroadcastReceiver mOnUpdateConnStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //refresh screen data after initial get
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                WIFIBadgerWIFIPoller.getWIFIInfo(context, 0);
                sendBroadcast(new Intent(net.ruckman.wifibadger.WIFIBadgerWIFIPoller.ACTION_UPDATE_WIFI_STATUS), net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE);
            }
        }
    };

    public void onMainAppExit(Context context) {

//        alarmboolean = false;
        WIFIBadgerWIFIPoller.setServiceAlarm(context, false);
//        Boolean salarmboolean = true;
        WIFIBadgerWIFIPoller.setServiceAlarmService(context, true);

        //register listener for connectivity changes
        unregisterReceiver(mOnUpdateConnStatus);

        //set app not visible
        WIFIBadgerCommonCalls.SetApplicationVisibility("NV", context);
        //save data
        WIFIBadgerCommonCalls.SaveActiveDatabase(getApplication());

    }

    public void onMainAppExitAll(Context context) {

//        alarmboolean = false;
        WIFIBadgerWIFIPoller.setServiceAlarm(context, false);
//        Boolean salarmboolean = false;

        WIFIBadgerWIFIPoller.setServiceAlarmService(context, false);
        //register listener for connectivity changes
        unregisterReceiver(mOnUpdateConnStatus);

        //set app not visible
        WIFIBadgerCommonCalls.SetApplicationVisibility("NV", context);
        //save data
        WIFIBadgerCommonCalls.SaveActiveDatabase(getApplication());
        stopService(new Intent(this, WIFIBadgerService.class));

    }

    public void onAlarmStatusChange(Context context) {
        WIFIBadgerWIFIPoller.setServiceAlarm(context, alarmboolean);
    }

}


