package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.List;

class WIFIBadgerCommonCalls {

    private static String WBDATABASE = "WIFIBadgerDB.db";

    int CalculateChannel(int FREQ){
        int channel = 0;

        //2.4Ghz channels
        if (FREQ == 2412){
            channel = 1;
        }
        if (FREQ == 2417){
            channel = 2;
        }
        if (FREQ == 2422){
            channel = 3;
        }
        if (FREQ == 2427){
            channel = 4;
        }
        if (FREQ == 2432){
            channel = 5;
        }
        if (FREQ == 2437){
            channel = 6;
        }
        if (FREQ == 2442){
            channel = 7;
        }
        if (FREQ == 2447){
            channel = 8;
        }
        if (FREQ == 2452){
            channel = 9;
        }
        if (FREQ == 2457){
            channel = 10;
        }
        if (FREQ == 2462){
            channel = 11;
        }
        if (FREQ == 2467){
            channel = 12;
        }
        if (FREQ == 2472){
            channel = 13;
        }
        if (FREQ == 2484){
            channel = 14;
        }

        //5Ghz channels
        if (FREQ == 5035){
            channel = 7;
        }
        if (FREQ == 5040){
            channel = 8;
        }
        if (FREQ == 5045){
            channel = 9;
        }
        if (FREQ == 5055){
            channel = 11;
        }
        if (FREQ == 5060){
            channel = 12;
        }
        if (FREQ == 5080){
            channel = 16;
        }
        if (FREQ == 5170){
            channel = 34;
        }
        if (FREQ == 5180){
            channel = 36;
        }
        if (FREQ == 5190){
            channel = 38;
        }
        if (FREQ == 5200){
            channel = 40;
        }
        if (FREQ == 5210){
            channel = 42;
        }
        if (FREQ == 5220){
            channel = 44;
        }
        if (FREQ == 5230){
            channel = 46;
        }
        if (FREQ == 5240){
            channel = 48;
        }
        if (FREQ == 5260){
            channel = 52;
        }
        if (FREQ == 5280){
            channel = 56;
        }
        if (FREQ == 5300){
            channel = 60;
        }
        if (FREQ == 5320){
            channel = 64;
        }
        if (FREQ == 5500){
            channel = 100;
        }
        if (FREQ == 5520){
            channel = 104;
        }
        if (FREQ == 5540){
            channel = 108;
        }
        if (FREQ == 5560){
            channel = 112;
        }
        if (FREQ == 5580){
            channel = 116;
        }
        if (FREQ == 5600){
            channel = 120;
        }
        if (FREQ == 5620){
            channel = 124;
        }
        if (FREQ == 5640){
            channel = 128;
        }
        if (FREQ == 5660){
            channel = 132;
        }
        if (FREQ == 5680){
            channel = 136;
        }
        if (FREQ == 5700){
            channel = 140;
        }
        if (FREQ == 5720){
            channel = 144;
        }
        if (FREQ == 5745){
            channel = 149;
        }
        if (FREQ == 5765){
            channel = 153;
        }
        if (FREQ == 5785){
            channel = 157;
        }
        if (FREQ == 5805){
            channel = 161;
        }
        if (FREQ == 5825){
            channel = 165;
        }
        if (FREQ == 4915){
            channel = 183;
        }
        if (FREQ == 4920){
            channel = 184;
        }
        if (FREQ == 4925){
            channel = 185;
        }
        if (FREQ == 4935){
            channel = 187;
        }
        if (FREQ == 4940){
            channel = 188;
        }
        if (FREQ == 4945){
            channel = 189;
        }
        if (FREQ == 4960){
            channel = 192;
        }
        if (FREQ == 4980){
            channel = 196;
        }

        return channel;
    }

    int CalculateIntervalForSpinner(String interval){

        int miliseconds = 120000;
        if (interval.equals("15 Seconds")) { miliseconds=15000; }
        if (interval.equals("30 Seconds")) { miliseconds=30000; }
        if (interval.equals("45 Seconds")) { miliseconds=45000; }
        if (interval.equals("1 Minute")) { miliseconds=60000; }
        if (interval.equals("2 Minutes")) { miliseconds=120000; }
        if (interval.equals("5 Minutes")) { miliseconds=300000; }
        if (interval.equals("10 Minutes")) { miliseconds=600000; }
        if (interval.equals("15 Minutes")) { miliseconds=900000; }
        if (interval.equals("20 Minutes")) { miliseconds=1200000; }
        if (interval.equals("30 Minutes")) { miliseconds=1800000; }
        if (interval.equals("40 Minutes")) { miliseconds=2400000; }
        if (interval.equals("50 Minutes")) { miliseconds=3000000; }
        if (interval.equals("60 Minutes")) { miliseconds=3600000; }

        return miliseconds;
    }

    int CalculateIntervalForServiceSpinner(String interval){

        int miliseconds = 900000;
        if (interval.equals("15 Seconds")) { miliseconds=15000; }
        if (interval.equals("30 Seconds")) { miliseconds=30000; }
        if (interval.equals("45 Seconds")) { miliseconds=45000; }
        if (interval.equals("1 Minute")) { miliseconds=60000; }
        if (interval.equals("2 Minutes")) { miliseconds=120000; }
        if (interval.equals("5 Minutes")) { miliseconds=300000; }
        if (interval.equals("10 Minutes")) { miliseconds=600000; }
        if (interval.equals("15 Minutes")) { miliseconds=900000; }
        if (interval.equals("20 Minutes")) { miliseconds=1200000; }
        if (interval.equals("30 Minutes")) { miliseconds=1800000; }
        if (interval.equals("40 Minutes")) { miliseconds=2400000; }
        if (interval.equals("50 Minutes")) { miliseconds=3000000; }
        if (interval.equals("60 Minutes")) { miliseconds=3600000; }

        return miliseconds;
    }

    private int CalculateRSSIforSettingsSpinner(String SRSSI){

        int RSSI = -15;
        if (SRSSI.equals("5 RSSI")) { RSSI=-5; }
        if (SRSSI.equals("10 RSSI")) { RSSI=-10; }
        if (SRSSI.equals("15 RSSI")) { RSSI=-15; }
        if (SRSSI.equals("20 RSSI")) { RSSI=-20; }
        if (SRSSI.equals("25 RSSI")) { RSSI=-25; }

        return RSSI;
    }

    void SetApplicationVisibility(String visibility, Context context) {

        //set application visibility flag in the database
        if (visibility.equals("NV")){
            SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='NV' WHERE rowid='0';");
            cv.close();
            dbv.close();
        }
        if (visibility.equals("V")){
            SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='V' WHERE rowid='0';");
            cv.close();
            dbv.close();
        }
    }

    void SetApplicationScreenState(String visibility, Context context) {

        //set application visibility flag in the database
        if (visibility.equals("ScreenOff")){
            SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='ScreenOff' WHERE rowid='5';");
            cv.close();
            dbv.close();
        }
        if (visibility.equals("ScreenOn")){
            SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='ScreenOn' WHERE rowid='5';");
            cv.close();
            dbv.close();
        }
    }

    String[] GetApplicationSettings(Context context) {
        String[] settings = new String[10];
        //get settings from database
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);

        for (int i = 1; i < 11; i++) {
            Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='" + i + "'", null);
            if (c.moveToFirst()) {
                settings[i-1] = c.getString(1);
            }
            c.close();
        }
        db.close();
        return settings;
    }

    void SaveActiveDatabase(Context context) {

        //store sql data in status table
        int rowid;
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        //store sql data in status table
        Cursor c=db.rawQuery("SELECT * FROM wifibadgerstatustable", null);
        if(c.getCount()==0)
        {
            rowid = 1;
            db.execSQL("INSERT INTO wifibadgerstatustable VALUES('" + rowid + "','" + WIFIBadgerMainActivity.iswifienabled + "','" + WIFIBadgerMainActivity.WIFIState + "','" + WIFIBadgerMainActivity.isconnected + "','" + WIFIBadgerMainActivity.ssid + "','" + WIFIBadgerMainActivity.bssid + "','" + WIFIBadgerMainActivity.mac + "','" + WIFIBadgerMainActivity.rssi + "','" + WIFIBadgerMainActivity.linkspeed + "','" + WIFIBadgerMainActivity.dns1 + "','" + WIFIBadgerMainActivity.dns2 + "','" + WIFIBadgerMainActivity.gateway + "','" + WIFIBadgerMainActivity.ipaddress + "','" + WIFIBadgerMainActivity.leaseduration + "','" + WIFIBadgerMainActivity.netmask + "','" + WIFIBadgerMainActivity.server+"');");
        }
        else {
            rowid = 1;
            if (WIFIBadgerMainActivity.ssid != null){
                db.execSQL("UPDATE wifibadgerstatustable SET isenabled='" + WIFIBadgerMainActivity.iswifienabled + "',state='" + WIFIBadgerMainActivity.WIFIState + "',isconnected='" + WIFIBadgerMainActivity.isconnected + "',ssid='" + WIFIBadgerMainActivity.ssid.replace("\'", "\'\'") + "',bssid='" + WIFIBadgerMainActivity.bssid + "',mac='" + WIFIBadgerMainActivity.mac + "',rssi='" + WIFIBadgerMainActivity.rssi + "',linkspeed='" + WIFIBadgerMainActivity.linkspeed + "',dns1='" + WIFIBadgerMainActivity.dns1 + "',dns2='" + WIFIBadgerMainActivity.dns2 + "',gateway='" + WIFIBadgerMainActivity.gateway + "',ipaddress='" + WIFIBadgerMainActivity.ipaddress + "',leaseduration='" + WIFIBadgerMainActivity.leaseduration + "',netmask='" + WIFIBadgerMainActivity.netmask + "',server='" + WIFIBadgerMainActivity.server +"' WHERE rowid='"+rowid+"'");
            }
        }
        c.close();

        //store sql data in savedap table
        int numberOfEntries;
        if (WIFIBadgerMainActivity.SavedSSIDs == null) {
            numberOfEntries = 0;
        } else {
            numberOfEntries = WIFIBadgerMainActivity.SavedSSIDs.length;
        }

        Cursor csavedap=db.rawQuery("SELECT * FROM wifibadgersavedaptable", null);
        db.execSQL("delete from wifibadgersavedaptable");
        for (int i = 0; i < numberOfEntries; i++) {
            rowid = i;
            if (WIFIBadgerMainActivity.SavedSSIDs[i] != null){
                db.execSQL("INSERT INTO wifibadgersavedaptable VALUES('" + rowid + "','" + WIFIBadgerMainActivity.SavedSSIDs[i].replace("\'", "\'\'") + "');");
            }
        }
        csavedap.close();

        //save scanned ap list to sql lite database
        int wifilistsize;
        if (WIFIBadgerMainActivity.wifiList == null) {
            wifilistsize = 0;
        } else {
            wifilistsize = WIFIBadgerMainActivity.wifiList.size();
            Cursor cscannedap = db.rawQuery("SELECT * FROM wifibadgerapscantable", null);
            db.execSQL("delete from wifibadgerapscantable");
            for(int i = 0; i < wifilistsize; i++){
                try {
                    if (WIFIBadgerMainActivity.ssidscan[i] !=null) {
                        db.execSQL("INSERT INTO wifibadgerapscantable VALUES('" + WIFIBadgerMainActivity.rowidscan[i] + "','" + WIFIBadgerMainActivity.ssidscan[i].replace("\'", "\'\'") + "','" + WIFIBadgerMainActivity.bssidscan[i] + "','" + WIFIBadgerMainActivity.levelscan[i] + "','" + WIFIBadgerMainActivity.frequencyscan[i] + "','" + WIFIBadgerMainActivity.capabilitiesscan[i] + "');");
                    }
                } catch (ArrayIndexOutOfBoundsException e){break;}
            }
            cscannedap.close();
        }

        db.close();
    }

    void LoadActiveDatabase(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        //load sql data into status table
        Cursor cload=db.rawQuery("SELECT * FROM wifibadgerstatustable WHERE rowid='1'", null);
        if(cload.moveToFirst())
        {
            WIFIBadgerMainActivity.iswifienabled = cload.getInt(1)>0;
            WIFIBadgerMainActivity.WIFIState = cload.getInt(2);
            WIFIBadgerMainActivity.isconnected = cload.getInt(3)>0;
            WIFIBadgerMainActivity.ssid = cload.getString(4);
            WIFIBadgerMainActivity.bssid = cload.getString(5);
            WIFIBadgerMainActivity.mac = cload.getString(6);
            WIFIBadgerMainActivity.rssi = cload.getInt(7);
            WIFIBadgerMainActivity.linkspeed = cload.getInt(8);
            WIFIBadgerMainActivity.dns1 = cload.getString(9);
            WIFIBadgerMainActivity.dns2 = cload.getString(10);
            WIFIBadgerMainActivity.gateway = cload.getString(11);
            WIFIBadgerMainActivity.ipaddress = cload.getString(12);
            WIFIBadgerMainActivity.leaseduration = cload.getString(13);
            WIFIBadgerMainActivity.netmask = cload.getString(14);
            WIFIBadgerMainActivity.server = cload.getString(15);
        }
        cload.close();

        //load sql data in savedap table
        int numberOfEntries;
        @SuppressLint("Recycle") Cursor cnum=db.rawQuery("SELECT * FROM wifibadgersavedaptable", null);
        numberOfEntries = cnum.getCount();
        WIFIBadgerMainActivity.SavedSSIDs = new String[numberOfEntries];

        for (int i = 0; i < numberOfEntries; i++) {
            Cursor csavedap=db.rawQuery("SELECT * FROM wifibadgersavedaptable WHERE rowid='"+i+"'", null);
            if(csavedap.moveToFirst())
            {
                WIFIBadgerMainActivity.SavedSSIDs[i]=csavedap.getString(1);
            }
            csavedap.close();
        }

        //store sql data in savedap table
        int numberOfAPs;
        @SuppressLint("Recycle") Cursor caps=db.rawQuery("SELECT * FROM wifibadgerapscantable", null);
        numberOfAPs = caps.getCount();
        WIFIBadgerMainActivity.rowidscan = new int[numberOfAPs];
        WIFIBadgerMainActivity.ssidscan = new String[numberOfAPs];
        WIFIBadgerMainActivity.bssidscan = new String[numberOfAPs];
        WIFIBadgerMainActivity.levelscan = new int[numberOfAPs];
        WIFIBadgerMainActivity.frequencyscan = new int[numberOfAPs];
        WIFIBadgerMainActivity.capabilitiesscan = new String[numberOfAPs];

        for (int i = 0; i <= numberOfAPs; i++) {
            Cursor csavedaps=db.rawQuery("SELECT * FROM wifibadgerapscantable WHERE rowid='"+i+"'", null);
            if(csavedaps.moveToFirst())
            {
                WIFIBadgerMainActivity.rowidscan[i-1]=csavedaps.getInt(0);
                WIFIBadgerMainActivity.ssidscan[i-1]=csavedaps.getString(1);
                WIFIBadgerMainActivity.bssidscan[i-1]=csavedaps.getString(2);
                WIFIBadgerMainActivity.levelscan[i-1]=csavedaps.getInt(3);
                WIFIBadgerMainActivity.frequencyscan[i-1]=csavedaps.getInt(4);
                WIFIBadgerMainActivity.capabilitiesscan[i-1]=csavedaps.getString(5);
            }
            csavedaps.close();
        }

        //close database
        db.close();

    }

    void reassociatewifi(Context context, String ssid, String ssidopen, String bssid) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiManager.WifiLock wifiLock=wifiManager.createWifiLock("WakeLock");
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();


        if (ssid.equals(null) || ssid.equals("")){
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE){

        } else {

        }

        //get settings from database
        String[] settings=GetApplicationSettings(context);

        //get last BSSID reassociated from database
        String lastbssid = settings[5];

        if (lastbssid.equals(bssid) || System.currentTimeMillis()<=Long.parseLong(settings[8])+60000) {

        } else {

        }

        //reassociate if Saved SSID
        if (settings[2].equals("Any Saved SSID") && (!lastbssid.equals(bssid) || System.currentTimeMillis()>Long.parseLong(settings[8])+60000)){
            if (list != null && (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE)){
                for( WifiConfiguration i : list ) {
                    if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                        wifiLock.acquire();
                        wifiManager.disconnect();

                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();
                        wifiLock.release();

                        SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
                        Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='"+bssid+"' WHERE rowid='6';");

                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + System.currentTimeMillis() +"' WHERE rowid='9';");
                        cv.close();
                        dbv.close();
                        break;
                    }
                }
            }
        }

        //reassociate if SAME SSID
        if (settings[2].equals("Same SSID") && (!lastbssid.equals(bssid) || System.currentTimeMillis()>Long.parseLong(settings[8])+60000)){
            if (list != null && (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE)){
                for( WifiConfiguration i : list ) {
                    if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                        wifiLock.acquire();
                        wifiManager.disconnect();

                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();
                        wifiLock.release();

                        SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
                        Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + bssid + "' WHERE rowid='6';");

                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + System.currentTimeMillis() +"' WHERE rowid='9';");
                        cv.close();
                        dbv.close();
                        break;
                    }
                }
            }

        }

        //reassociate if Any better SSID
        if (settings[2].equals("Any SSID and Open") && (!lastbssid.equals(bssid) || System.currentTimeMillis()>Long.parseLong(settings[8]))){
            if (list != null && (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE)){
                if (ssidopen.equals("false")){
                    for( WifiConfiguration i : list ) {
                        if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                            wifiLock.acquire();
                            wifiManager.disconnect();

                            wifiManager.enableNetwork(i.networkId, true);
                            wifiManager.reconnect();
                            wifiLock.release();

                            SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
                            Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + bssid + "' WHERE rowid='6';");

                            dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + System.currentTimeMillis() +"' WHERE rowid='9';");
                            cv.close();
                            dbv.close();
                            break;
                        }
                    }
                }
                if (ssidopen.equals("true")){
                    if (!ssid.equals(null)&&!ssid.equals("")){

                        wifiLock.acquire();
                        wifiManager.disconnect();
                        WifiConfiguration wc = new WifiConfiguration();
                        wc.SSID = "\"" + ssid + "\"";
                        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        int res = wifiManager.addNetwork(wc);
                        wifiManager.saveConfiguration();
                        wifiManager.enableNetwork(res, true);
                        wifiManager.reconnect();
                        wifiLock.release();

                        SQLiteDatabase dbv = context.openOrCreateDatabase(WBDATABASE, 0, null);
                        Cursor cv = dbv.rawQuery("SELECT * FROM wifibadgersettingstable", null);
                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + bssid + "' WHERE rowid='6';");

                        dbv.execSQL("UPDATE wifibadgersettingstable SET setting='" + System.currentTimeMillis() +"' WHERE rowid='9';");
                        cv.close();
                        dbv.close();
                    }
                }
            }
        }

        if (settings[2].equals("Off")) {

        }

    }

    String[] checkcanidatesignalwifi(Context context) {

        // Creating database and tables
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);

        // create local variables
        String visability="NV";
        String bestssid="";
        String bestbssid="";
        Boolean ssidupdate=false;
        Boolean openssid=false;
        String boolreturnstr="false";
        String boolopenstr="false";
        String[] returningarray = new String[4];
        int lastknownbetterrssi = -100;

        // Check application visibility
        Cursor cvis=db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='0'", null);
        if(cvis.moveToFirst())
        {
            visability=cvis.getString(1);
        }
        cvis.close();

        //check visibility and execute proper wifi checks
        if (visability.equals("V")) {

            //check profile type
            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Off")){

            }

            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Same SSID")){

                //get ap scan results and size
                int scannedapcount = WIFIBadgerMainActivity.wifiList.size();
                if (scannedapcount == 0){

                }
                else {
                    //look through the list
                    for (int i = 0; i < scannedapcount; i++) {

                        //null checker
                        String savedssid = WIFIBadgerMainActivity.ssid;
                        String ssidscanned = WIFIBadgerMainActivity.ssidscan[i];
                        String connectedbssid = WIFIBadgerMainActivity.bssid;
                        String bssidscanned= WIFIBadgerMainActivity.bssidscan[i];
                        if (savedssid == null) {
                            savedssid = "";
                        }
                        if (ssidscanned == null){
                            ssidscanned = "";
                        }
                        if (connectedbssid == null){
                            connectedbssid = "";
                        }
                        if (bssidscanned == null){
                            bssidscanned = "";
                        }

                        //if SSID is same and BSSID is different
                        if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                            //check levels and change if difference is more than 15

                            if ((WIFIBadgerMainActivity.rssi - WIFIBadgerMainActivity.levelscan[i])<(CalculateRSSIforSettingsSpinner(WIFIBadgerMainActivity.applicationsettings[9])) && (WIFIBadgerMainActivity.levelscan[i]>lastknownbetterrssi )) {

                                bestssid = WIFIBadgerMainActivity.ssidscan[i];
                                bestbssid = WIFIBadgerMainActivity.bssidscan[i];
                                ssidupdate = true;
                                lastknownbetterrssi=WIFIBadgerMainActivity.levelscan[i];
                            }
                        }
                    }
                }
            }

            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Any Saved SSID")) {

                //get ap scan results and size
                int scannedapcount = WIFIBadgerMainActivity.wifiList.size();
                if (scannedapcount == 0) {

                } else {
                    int savedapcount = WIFIBadgerMainActivity.SavedSSIDs.length;
                    if (savedapcount == 0) {

                    } else {
                        //look through the list
                        for (int i = 0; i < scannedapcount; i++) {
                            //if saved SSID is same and BSSID is different
                            for (int s = 0; s < savedapcount; s++) {

                                //null checker
                                String savedssid = WIFIBadgerMainActivity.SavedSSIDs[s];
                                String ssidscanned = WIFIBadgerMainActivity.ssidscan[i];
                                String connectedbssid = WIFIBadgerMainActivity.bssid;
                                String bssidscanned= WIFIBadgerMainActivity.bssidscan[i];
                                if (savedssid == null) {
                                    savedssid = "";
                                }
                                if (ssidscanned == null){
                                    ssidscanned = "";
                                }
                                if (connectedbssid == null){
                                    connectedbssid = "";
                                }
                                if (bssidscanned == null){
                                    bssidscanned = "";
                                }

                                if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                                    //check levels and change if difference is more than 15

                                    if ((WIFIBadgerMainActivity.rssi - WIFIBadgerMainActivity.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerMainActivity.applicationsettings[9])) && (WIFIBadgerMainActivity.levelscan[i] > lastknownbetterrssi)) {

                                        bestssid = WIFIBadgerMainActivity.ssidscan[i];
                                        bestbssid = WIFIBadgerMainActivity.bssidscan[i];
                                        ssidupdate = true;
                                        lastknownbetterrssi = WIFIBadgerMainActivity.levelscan[i];
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (WIFIBadgerMainActivity.applicationsettings[2].equals("Any SSID and Open")) {

                //get ap scan results and size
                int scannedapcount = WIFIBadgerMainActivity.wifiList.size();
                if (scannedapcount == 0) {

                } else {
                    int savedapcount = WIFIBadgerMainActivity.SavedSSIDs.length;
                    if (savedapcount == 0) {

                    } else {
                        //look through the list
                        for (int i = 0; i < scannedapcount; i++) {
                            //if saved SSID is same and BSSID is different
                            for (int s = 0; s < savedapcount; s++) {
                                //null checker
                                String savedssid = WIFIBadgerMainActivity.SavedSSIDs[s];
                                String ssidscanned = WIFIBadgerMainActivity.ssidscan[i];
                                String connectedbssid = WIFIBadgerMainActivity.bssid;
                                String bssidscanned= WIFIBadgerMainActivity.bssidscan[i];
                                String bssidcapability=WIFIBadgerMainActivity.capabilitiesscan[i];
                                if (savedssid == null) {
                                    savedssid = "";
                                }
                                if (ssidscanned == null){
                                    ssidscanned = "";
                                }
                                if (connectedbssid == null){
                                    connectedbssid = "";
                                }
                                if (bssidscanned == null){
                                    bssidscanned = "";
                                }
                                if (bssidcapability == null){
                                    bssidscanned = "";
                                }

                                if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                                    //check levels and change if difference is more than 15
                                    if (openssid){
                                        openssid=false;
                                    }
                                    if ((WIFIBadgerMainActivity.rssi - WIFIBadgerMainActivity.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerMainActivity.applicationsettings[9])) && (WIFIBadgerMainActivity.levelscan[i] > lastknownbetterrssi)) {

                                        bestssid = WIFIBadgerMainActivity.ssidscan[i];
                                        bestbssid = WIFIBadgerMainActivity.bssidscan[i];
                                        ssidupdate = true;
                                        openssid = false;
                                        lastknownbetterrssi = WIFIBadgerMainActivity.levelscan[i];
                                    }
                                } else {
                                    //check for open wifi
                                    if ((bssidcapability.equals("[ESS]") || bssidcapability.equals("[IBSS]")) && (!connectedbssid.equals(bssidscanned)) && (!savedssid.equals(ssidscanned))){
                                        if ((WIFIBadgerMainActivity.rssi - WIFIBadgerMainActivity.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerMainActivity.applicationsettings[9])) && (WIFIBadgerMainActivity.levelscan[i] > lastknownbetterrssi)) {

                                            bestssid = WIFIBadgerMainActivity.ssidscan[i];
                                            bestbssid = WIFIBadgerMainActivity.bssidscan[i];
                                            ssidupdate = true;
                                            openssid = true;
                                            lastknownbetterrssi = WIFIBadgerMainActivity.levelscan[i];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!WIFIBadgerMainActivity.applicationsettings[2].equals("Off") && !WIFIBadgerMainActivity.applicationsettings[2].equals("Same SSID") && !WIFIBadgerMainActivity.applicationsettings[2].equals("Any Saved SSID") && !WIFIBadgerMainActivity.applicationsettings[2].equals("Any SSID and Open")) {

            }

        } else {

            //check profile type
            if (WIFIBadgerWIFIPoller.applicationsettings[3].equals("Off")){

            }

            if (WIFIBadgerWIFIPoller.applicationsettings[3].equals("Same SSID")){

                //get ap scan results and size
                int scannedapcount = WIFIBadgerWIFIPoller.wifiList.size();
                if (scannedapcount == 0){

                }
                else {
                    //look through the list
                    for (int i = 0; i < scannedapcount; i++) {
                        //null checker
                        String savedssid = WIFIBadgerWIFIPoller.ssid;
                        String ssidscanned = WIFIBadgerWIFIPoller.ssidscan[i];
                        String connectedbssid = WIFIBadgerWIFIPoller.bssid;
                        String bssidscanned= WIFIBadgerWIFIPoller.bssidscan[i];
                        if (savedssid == null) {
                            savedssid = "";
                        }
                        if (ssidscanned == null){
                            ssidscanned = "";
                        }
                        if (connectedbssid == null){
                            connectedbssid = "";
                        }
                        if (bssidscanned == null){
                            bssidscanned = "";
                        }

                        //if SSID is same and BSSID is different
                        if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                            //check levels and change if difference is more than 15
                            if ((WIFIBadgerWIFIPoller.rssi - WIFIBadgerWIFIPoller.levelscan[i])<(CalculateRSSIforSettingsSpinner(WIFIBadgerWIFIPoller.applicationsettings[10])) && (WIFIBadgerWIFIPoller.levelscan[i]>lastknownbetterrssi )) {

                                bestssid = WIFIBadgerWIFIPoller.ssidscan[i];
                                bestbssid = WIFIBadgerWIFIPoller.bssidscan[i];
                                ssidupdate = true;
                                lastknownbetterrssi=WIFIBadgerWIFIPoller.levelscan[i];
                            }
                        }
                    }
                }
            }

            if (WIFIBadgerWIFIPoller.applicationsettings[3].equals("Any Saved SSID")) {

                //get ap scan results and size
                int scannedapcount = WIFIBadgerWIFIPoller.wifiList.size();
                if (scannedapcount == 0) {

                } else {
                    int savedapcount = WIFIBadgerWIFIPoller.SavedSSIDs.length;
                    if (savedapcount == 0) {

                    } else {
                        //look through the list
                        for (int i = 0; i < scannedapcount; i++) {
                            //if saved SSID is same and BSSID is different
                            for (int s = 0; s < savedapcount; s++) {
                                //null checker
                                String savedssid = WIFIBadgerWIFIPoller.SavedSSIDs[s];
                                String ssidscanned = WIFIBadgerWIFIPoller.ssidscan[i];
                                String connectedbssid = WIFIBadgerWIFIPoller.bssid;
                                String bssidscanned= WIFIBadgerWIFIPoller.bssidscan[i];
                                if (savedssid == null) {
                                    savedssid = "";
                                }
                                if (ssidscanned == null){
                                    ssidscanned = "";
                                }
                                if (connectedbssid == null){
                                    connectedbssid = "";
                                }
                                if (bssidscanned == null){
                                    bssidscanned = "";
                                }

                                if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                                    //check levels and change if difference is more than 15
                                    if ((WIFIBadgerWIFIPoller.rssi - WIFIBadgerWIFIPoller.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerWIFIPoller.applicationsettings[10])) && (WIFIBadgerWIFIPoller.levelscan[i] > lastknownbetterrssi)) {

                                        bestssid = WIFIBadgerWIFIPoller.ssidscan[i];
                                        bestbssid = WIFIBadgerWIFIPoller.bssidscan[i];
                                        ssidupdate = true;
                                        lastknownbetterrssi = WIFIBadgerWIFIPoller.levelscan[i];
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (WIFIBadgerWIFIPoller.applicationsettings[3].equals("Any SSID and Open")) {
                //get ap scan results and size
                int scannedapcount = WIFIBadgerWIFIPoller.wifiList.size();
                if (scannedapcount == 0) {
                } else {
                    int savedapcount = WIFIBadgerWIFIPoller.SavedSSIDs.length;
                    if (savedapcount == 0) {
                    } else {
                        //look through the list
                        for (int i = 0; i < scannedapcount; i++) {
                            //if saved SSID is same and BSSID is different
                            for (int s = 0; s < savedapcount; s++) {
                                //null checker
                                String savedssid = WIFIBadgerWIFIPoller.SavedSSIDs[s];
                                String ssidscanned = WIFIBadgerWIFIPoller.ssidscan[i];
                                String connectedbssid = WIFIBadgerWIFIPoller.bssid;
                                String bssidscanned= WIFIBadgerWIFIPoller.bssidscan[i];
                                String bssidcapability=WIFIBadgerWIFIPoller.capabilitiesscan[i];
                                if (savedssid == null) {
                                    savedssid = "";
                                }
                                if (ssidscanned == null){
                                    ssidscanned = "";
                                }
                                if (connectedbssid == null){
                                    connectedbssid = "";
                                }
                                if (bssidscanned == null){
                                    bssidscanned = "";
                                }
                                if (bssidcapability == null){
                                    bssidscanned = "";
                                }

                                if ((savedssid.equals(ssidscanned)) && (!connectedbssid.equals(bssidscanned))) {
                                    //check levels and change if difference is more than 15
                                    if (openssid){
                                        openssid=false;
                                    }
                                    if ((WIFIBadgerWIFIPoller.rssi - WIFIBadgerWIFIPoller.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerWIFIPoller.applicationsettings[10])) && (WIFIBadgerWIFIPoller.levelscan[i] > lastknownbetterrssi)) {

                                        bestssid = WIFIBadgerWIFIPoller.ssidscan[i];
                                        bestbssid = WIFIBadgerWIFIPoller.bssidscan[i];
                                        ssidupdate = true;
                                        openssid = false;
                                        lastknownbetterrssi = WIFIBadgerWIFIPoller.levelscan[i];
                                    }
                                } else {
                                    //check for open wifi
                                    if ((bssidcapability.equals("[ESS]") || bssidcapability.equals("[IBSS]")) && (!connectedbssid.equals(bssidscanned)) && (!savedssid.equals(ssidscanned))){
                                        if ((WIFIBadgerWIFIPoller.rssi - WIFIBadgerWIFIPoller.levelscan[i]) < (CalculateRSSIforSettingsSpinner(WIFIBadgerWIFIPoller.applicationsettings[10])) && (WIFIBadgerWIFIPoller.levelscan[i] > lastknownbetterrssi)) {

                                            bestssid = WIFIBadgerWIFIPoller.ssidscan[i];
                                            bestbssid = WIFIBadgerWIFIPoller.bssidscan[i];
                                            ssidupdate = true;
                                            openssid = true;
                                            lastknownbetterrssi = WIFIBadgerWIFIPoller.levelscan[i];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!WIFIBadgerWIFIPoller.applicationsettings[3].equals("Off") && !WIFIBadgerWIFIPoller.applicationsettings[3].equals("Same SSID") && !WIFIBadgerWIFIPoller.applicationsettings[3].equals("Any Saved SSID") && !WIFIBadgerWIFIPoller.applicationsettings[3].equals("Any SSID and Open")) {
            }

        }

        if (ssidupdate) {
            boolreturnstr="true";
        }
        if (openssid) {
            boolopenstr="true";
        }


        returningarray[0]=boolreturnstr;
        returningarray[1]=bestssid;
        returningarray[2]=boolopenstr;
        returningarray[3]=bestbssid;

        db.close();
        return returningarray;
        //end checkcanidatesignalwifi
    }

    String[] GetApplicationSettingspoller(Context context) {
        String[] settings = new String[11];
        //get settings from database
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);

        for (int i = 0; i < 11; i++) {
            Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='" + i + "'", null);
            if (c.moveToFirst()) {
                settings[i] = c.getString(1);
            }
            c.close();
        }
        db.close();
        return settings;
    }

    void SaveActiveDatabasepoller(Context context) {

        //store sql data in status table
        int rowid;
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        //store sql data in status table
        Cursor c=db.rawQuery("SELECT * FROM wifibadgerstatustable", null);
        if (WIFIBadgerWIFIPoller.ssid != null){
            if(c.getCount()==0)
            {
                rowid = 1;
                db.execSQL("INSERT INTO wifibadgerstatustable VALUES('" + rowid + "','" + WIFIBadgerWIFIPoller.iswifienabled + "','" + WIFIBadgerWIFIPoller.WIFIState + "','" + WIFIBadgerWIFIPoller.isconnected + "','" + WIFIBadgerWIFIPoller.ssid + "','" + WIFIBadgerWIFIPoller.bssid + "','" + WIFIBadgerWIFIPoller.mac + "','" + WIFIBadgerWIFIPoller.rssi + "','" + WIFIBadgerWIFIPoller.linkspeed + "','" + WIFIBadgerWIFIPoller.dns1 + "','" + WIFIBadgerWIFIPoller.dns2 + "','" + WIFIBadgerWIFIPoller.gateway + "','" + WIFIBadgerWIFIPoller.ipaddress + "','" + WIFIBadgerWIFIPoller.leaseduration + "','" + WIFIBadgerWIFIPoller.netmask + "','" + WIFIBadgerWIFIPoller.server + "');");
            }
            else {
                rowid = 1;
                if (WIFIBadgerWIFIPoller.ssid != null) {
                    db.execSQL("UPDATE wifibadgerstatustable SET isenabled='" + WIFIBadgerWIFIPoller.iswifienabled + "',state='" + WIFIBadgerWIFIPoller.WIFIState + "',isconnected='" + WIFIBadgerWIFIPoller.isconnected + "',ssid='" + WIFIBadgerWIFIPoller.ssid.replace("\'", "\'\'") + "',bssid='" + WIFIBadgerWIFIPoller.bssid + "',mac='" + WIFIBadgerWIFIPoller.mac + "',rssi='" + WIFIBadgerWIFIPoller.rssi + "',linkspeed='" + WIFIBadgerWIFIPoller.linkspeed + "',dns1='" + WIFIBadgerWIFIPoller.dns1 + "',dns2='" + WIFIBadgerWIFIPoller.dns2 + "',gateway='" + WIFIBadgerWIFIPoller.gateway + "',ipaddress='" + WIFIBadgerWIFIPoller.ipaddress + "',leaseduration='" + WIFIBadgerWIFIPoller.leaseduration + "',netmask='" + WIFIBadgerWIFIPoller.netmask + "',server='" + WIFIBadgerWIFIPoller.server + "' WHERE rowid='" + rowid + "'");
                }
            }
        }
        c.close();

        //store sql data in savedap table
        int numberOfEntries;
        if (WIFIBadgerWIFIPoller.SavedSSIDs == null) {
            numberOfEntries = 0;
        } else {
            numberOfEntries = WIFIBadgerWIFIPoller.SavedSSIDs.length;
        }

        Cursor csavedap=db.rawQuery("SELECT * FROM wifibadgersavedaptable", null);
        db.execSQL("delete from wifibadgersavedaptable");
        //fix from email remove if issues
        try {
            for (int i = 0; i < numberOfEntries; i++) {
                rowid = i;
                if (WIFIBadgerWIFIPoller.SavedSSIDs[i] !=null){
                    db.execSQL("INSERT INTO wifibadgersavedaptable VALUES('" + rowid + "','" + WIFIBadgerWIFIPoller.SavedSSIDs[i].replace("\'", "\'\'")+"');");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        csavedap.close();

        //save scanned ap list to sql lite database
        int wifilistsize;
        if (WIFIBadgerWIFIPoller.wifiList == null) {
            wifilistsize = 0;
        } else {
            wifilistsize = WIFIBadgerWIFIPoller.wifiList.size();
            Cursor cscannedap = db.rawQuery("SELECT * FROM wifibadgerapscantable", null);
            db.execSQL("delete from wifibadgerapscantable");
            //fix from email remove if issues
            try {
                for(int i = 0; i < wifilistsize; i++){
                    if (WIFIBadgerWIFIPoller.ssidscan[i]!=null) {
                        db.execSQL("INSERT INTO wifibadgerapscantable VALUES('" + WIFIBadgerWIFIPoller.rowidscan[i] + "','" + WIFIBadgerWIFIPoller.ssidscan[i].replace("\'", "\'\'") + "','" + WIFIBadgerWIFIPoller.bssidscan[i] + "','" + WIFIBadgerWIFIPoller.levelscan[i] + "','" + WIFIBadgerWIFIPoller.frequencyscan[i] + "','" + WIFIBadgerWIFIPoller.capabilitiesscan[i] + "');");
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){}
            cscannedap.close();
        }
        db.close();
    }

    void LoadActiveDatabasepoller(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        //load sql data into status table
        Cursor cload=db.rawQuery("SELECT * FROM wifibadgerstatustable WHERE rowid='1'", null);
        if(cload.moveToFirst())
        {
            WIFIBadgerWIFIPoller.iswifienabled = cload.getInt(1)>0;
            WIFIBadgerWIFIPoller.WIFIState = cload.getInt(2);
            WIFIBadgerWIFIPoller.isconnected = cload.getInt(3)>0;
            WIFIBadgerWIFIPoller.ssid = cload.getString(4);
            WIFIBadgerWIFIPoller.bssid = cload.getString(5);
            WIFIBadgerWIFIPoller.mac = cload.getString(6);
            WIFIBadgerWIFIPoller.rssi = cload.getInt(7);
            WIFIBadgerWIFIPoller.linkspeed = cload.getInt(8);
            WIFIBadgerWIFIPoller.dns1 = cload.getString(9);
            WIFIBadgerWIFIPoller.dns2 = cload.getString(10);
            WIFIBadgerWIFIPoller.gateway = cload.getString(11);
            WIFIBadgerWIFIPoller.ipaddress = cload.getString(12);
            WIFIBadgerWIFIPoller.leaseduration = cload.getString(13);
            WIFIBadgerWIFIPoller.netmask = cload.getString(14);
            WIFIBadgerWIFIPoller.server = cload.getString(15);
        }
        cload.close();

        //load sql data in savedap table
        int numberOfEntries;
        @SuppressLint("Recycle") Cursor cnum=db.rawQuery("SELECT * FROM wifibadgersavedaptable", null);
        numberOfEntries = cnum.getCount();
        WIFIBadgerWIFIPoller.SavedSSIDs = new String[numberOfEntries];

        for (int i = 0; i < numberOfEntries; i++) {
            Cursor csavedap=db.rawQuery("SELECT * FROM wifibadgersavedaptable WHERE rowid='"+i+"'", null);
            if(csavedap.moveToFirst())
            {
                WIFIBadgerWIFIPoller.SavedSSIDs[i]=csavedap.getString(1);
            }
            csavedap.close();
        }

        //store sql data in savedap table
        int numberOfAPs;
        @SuppressLint("Recycle") Cursor caps=db.rawQuery("SELECT * FROM wifibadgerapscantable", null);
        numberOfAPs = caps.getCount();
        WIFIBadgerWIFIPoller.rowidscan = new int[numberOfAPs];
        WIFIBadgerWIFIPoller.ssidscan = new String[numberOfAPs];
        WIFIBadgerWIFIPoller.bssidscan = new String[numberOfAPs];
        WIFIBadgerWIFIPoller.levelscan = new int[numberOfAPs];
        WIFIBadgerWIFIPoller.frequencyscan = new int[numberOfAPs];
        WIFIBadgerWIFIPoller.capabilitiesscan = new String[numberOfAPs];

        for (int i = 0; i <= numberOfAPs; i++) {
            try {
                Cursor csavedaps=db.rawQuery("SELECT * FROM wifibadgerapscantable WHERE rowid='"+i+"'", null);
                if(csavedaps.moveToFirst())
                {
                    WIFIBadgerWIFIPoller.rowidscan[i-1]=csavedaps.getInt(0);
                    WIFIBadgerWIFIPoller.ssidscan[i-1]=csavedaps.getString(1);
                    WIFIBadgerWIFIPoller.bssidscan[i-1]=csavedaps.getString(2);
                    WIFIBadgerWIFIPoller.levelscan[i-1]=csavedaps.getInt(3);
                    WIFIBadgerWIFIPoller.frequencyscan[i-1]=csavedaps.getInt(4);
                    WIFIBadgerWIFIPoller.capabilitiesscan[i-1]=csavedaps.getString(5);
                }
                csavedaps.close();
            } catch (ArrayIndexOutOfBoundsException e){break;}
        }

        //close database
        db.close();

    }

    void CheckIfLocationIsEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            final Context mycontext = context;
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
                dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mycontext.startActivity(myIntent);
                        //get gps
                    }
                });
                dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
        }
    }
}
