package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class WIFIBadgerWIFIPoller extends IntentService {

    public WIFIBadgerWIFIPoller() {
        super(TAG);
    }

    private static final String TAG = "WIFIBadgerWIFIPoller";

    private static String WBDATABASE = "WIFIBadgerDB.db";

    WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();

    //broadcast action and permissions
    public static final String ACTION_UPDATE_WIFI_STATUS = "net.ruckman.wifibadger.UPDATE_WIFI_STATUS";
    public static final String PERM_WIFIPRIVATE = "net.ruckman.wifibadger.WIFIPRIVATE";
    public static final String ACTION_DELETE_AREA_SCAN = "net.ruckman.wifibadger.DELETE_AREA_SCAN";
    public static final String ACTION_CHANNEL_UPDATE = "net.ruckman.wifibadger.ACTION_CHANNEL_UPDATE";

    //Used Variables
    private int rowid;

    //Application Settings Global Variables for NV calls
    public static String[] applicationsettings = new String[11];
    public static Boolean alarmboolean;
    public static Integer poll_interval;

    //WIFI status Global Variables for NV calls
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

    //WIFI scan Global Variables for NV calls
    //create arrays for data
    public static List<ScanResult> wifiList;
    public static int[] rowidscan;
    public static String[] ssidscan;
    public static String[] bssidscan;
    public static int[] levelscan;
    public static int[] frequencyscan;
    public static String[] capabilitiesscan;

    //WIFI saved SSID lisy Global Variables for NV calls
    public static String[] SavedSSIDs;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //Set in-app polling interval alarm
    public void setServiceAlarm(Context context, boolean isOn) {

        Intent i = new Intent(context, WIFIBadgerWIFIPoller.class);
        PendingIntent wifipi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        final int SDK_INT = Build.VERSION.SDK_INT;

        if (isOn) {
            //Set in-App poll interval to 30 seconds due to android throttling
            long timeInMillis = (System.currentTimeMillis() + 30000);

            if (SDK_INT < 19) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
            } else if (19 <= SDK_INT && SDK_INT < 23) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
            } else if (SDK_INT >= 23) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
            }
        } else {
            alarmManager.cancel(wifipi);
            wifipi.cancel();
        }
    }

    //Set in-app polling interval alarm
    public void setServiceAlarmService(Context context, boolean isOn) {

        String ScreenState = "ScreenOn";
        //Open screen state database
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
        Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='5'", null);
        c.moveToFirst();
        ScreenState = c.getString(1);
        c.close();
        db.close();

        //get poll interval
        applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettingspoller(context);
        int POLL_INTERVAL;
        int POLL_INTERVAL_ON;
        String SONSOFF = applicationsettings[7];
        String SPOLL_INTERVAL = applicationsettings[4];
        String SPOLL_INTERVAL_ON = applicationsettings[2];
        POLL_INTERVAL = WIFIBadgerCommonCalls.CalculateIntervalForServiceSpinner(SPOLL_INTERVAL);
        POLL_INTERVAL_ON = WIFIBadgerCommonCalls.CalculateIntervalForSpinner(SPOLL_INTERVAL_ON);

        Intent i = new Intent(context, WIFIBadgerWIFIPoller.class);
        PendingIntent wifipi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        final int SDK_INT = Build.VERSION.SDK_INT;

        long timeInMillis;
        if (isOn) {
            if (ScreenState.equals("ScreenOn")){
                timeInMillis = (System.currentTimeMillis() + POLL_INTERVAL_ON);
            } else {
                timeInMillis = (System.currentTimeMillis() + POLL_INTERVAL);
            }

            if (SDK_INT < 19) {
                if (SONSOFF.equals("SON")) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
                }
            } else if (19 <= SDK_INT && SDK_INT < 23) {
                if (SONSOFF.equals("SON")) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
                }
            } else if (SDK_INT >= 23) {
                if (SONSOFF.equals("SON")) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, wifipi);
                }
            }
        } else {
            alarmManager.cancel(wifipi);
            wifipi.cancel();
        }
    }

    //Get wifi info on intent call from alarm and broadcast
    @Override
    public void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
        applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettingspoller(context);
        getWIFIInfo(context, 0);
        if (applicationsettings[0].equals("V")){
            sendBroadcast(new Intent(ACTION_UPDATE_WIFI_STATUS), PERM_WIFIPRIVATE);

            //set alarm again
            SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor cvis = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='1'", null);
            String alarmbool = "ON";
            if (cvis.moveToFirst()) {
                alarmbool = cvis.getString(1);
            }
            cvis.close();
            db.close();
            Boolean alarmboolean;
            alarmboolean = alarmbool.equals("ON");
            setServiceAlarm(getApplicationContext(), alarmboolean);
        } else {
//            Boolean salarmboolean = true;
            setServiceAlarmService(context, true);
        }

    }

    public void getWIFIInfo(Context context, int runmode) {

        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager.WifiLock wifiLock = wifi.createWifiLock("WakeLock");

        // Creating database and tables
        SQLiteDatabase db = context.openOrCreateDatabase(WBDATABASE, 0, null);

        // Check application visibility
        Cursor cvis = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='0'", null);
        String visability = "NV";
        if (cvis.moveToFirst()) {
            visability = cvis.getString(1);
        }
        cvis.close();

        //Begin main code

        //runmode is 0 and application is visible then run
        if (runmode == 0 && visability.equals("V")) {
            //wifi lock
            wifiLock.acquire();

            //Setup wifi and connection manager objects

            //check if wifi is enabled
            WIFIBadgerMainActivity.iswifienabled = wifi.isWifiEnabled();

            //get wifi state 0=disabling, 1=disabled, 2=enabling, 3=enabled, 4=unknown
            WIFIBadgerMainActivity.WIFIState = wifi.getWifiState();

            //see if wifi is connected as default route
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            WIFIBadgerMainActivity.isconnected = mWifi.isConnected();

            //get wifi info (SSID, BSSID, MAC, RSSI, Link Speed)
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (wifiInfo.getSSID() == null) {
                WIFIBadgerMainActivity.ssid = wifiInfo.getSSID();
            } else {
                WIFIBadgerMainActivity.ssid = wifiInfo.getSSID().replace("\"", "");
            }

            if (WIFIBadgerMainActivity.isconnected) {
                WIFIBadgerMainActivity.bssid = wifiInfo.getBSSID().toUpperCase();
            } else {
                WIFIBadgerMainActivity.bssid = wifiInfo.getBSSID();
            }
                //Get MAC address for android Marshmallow+
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                        for (NetworkInterface nif : all) {
                            if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                            byte[] macBytes = nif.getHardwareAddress();
                            if (macBytes == null) {
                                mac = "";
                                WIFIBadgerMainActivity.mac = mac;
                            }

                            StringBuilder res1 = new StringBuilder();
                            for (byte b : macBytes) {
                                res1.append(String.format("%02X:", b));
                            }

                            if (res1.length() > 0) {
                                res1.deleteCharAt(res1.length() - 1);
                            }
                            mac = res1.toString();
                            WIFIBadgerMainActivity.mac = mac.toUpperCase();
                        }
                    } catch (Exception ex) {
                        mac = "02:00:00:00:00:00";
                        WIFIBadgerMainActivity.mac = mac.toUpperCase();
                    }
                    //Get Mac Address in Android below marshmallow
                } else {
                    @SuppressLint("HardwareIds") String mac = wifiInfo.getMacAddress();
                    if (WIFIBadgerMainActivity.mac != null) {
                        if (mac == null) {
                            mac = "";
                            WIFIBadgerMainActivity.mac = mac;
                        } else {
                            WIFIBadgerMainActivity.mac = mac.toUpperCase();
                        }
                    }
                }

            WIFIBadgerMainActivity.rssi = wifiInfo.getRssi();
            WIFIBadgerMainActivity.linkspeed = wifiInfo.getLinkSpeed();

            //get dhcp ip information
            DhcpInfo dhcpInfo = wifi.getDhcpInfo();
            //Log.d("DHCP INFO", String.valueOf(dhcpInfo));
            WIFIBadgerMainActivity.dns1 = String.valueOf(Formatter.formatIpAddress(dhcpInfo.dns1));
            WIFIBadgerMainActivity.dns2 = String.valueOf(Formatter.formatIpAddress(dhcpInfo.dns2));
            WIFIBadgerMainActivity.gateway = String.valueOf(Formatter.formatIpAddress(dhcpInfo.gateway));
            WIFIBadgerMainActivity.ipaddress = String.valueOf(Formatter.formatIpAddress(dhcpInfo.ipAddress));
            WIFIBadgerMainActivity.leaseduration = String.valueOf(dhcpInfo.leaseDuration);
            WIFIBadgerMainActivity.netmask = String.valueOf(Formatter.formatIpAddress(dhcpInfo.netmask));
            WIFIBadgerMainActivity.server = String.valueOf(Formatter.formatIpAddress(dhcpInfo.serverAddress));


            //Get saved network list and save in stringset
            if (WIFIBadgerMainActivity.iswifienabled) {
                //get SSID data to array
                //2-21-17 made tweak for null pointer fix and added startscan when empty or null
                if (wifi.getConfiguredNetworks() == null || wifi.getConfiguredNetworks().isEmpty()) {
                    WIFIBadgerMainActivity.SavedSSIDs = new String[1];
                    WIFIBadgerMainActivity.SavedSSIDs[0] = "-";
                    wifi.startScan();
                } else {
                    int numberOfEntries = wifi.getConfiguredNetworks().size();
                    WIFIBadgerMainActivity.SavedSSIDs = new String[numberOfEntries];
                    for (int i = 0; i < numberOfEntries; i++) {
                        WifiConfiguration wificonf = wifi.getConfiguredNetworks().get(i);
                        if (wificonf.SSID != null) {
                            WIFIBadgerMainActivity.SavedSSIDs[i] = wificonf.SSID.replace("\"", "");
                        }
                    }
                    wifi.startScan();
                }
                db.close();
            }

            if (!WIFIBadgerMainActivity.iswifienabled) {
                //if wifi is disabled, send broadcast to clear area scan results
                context.sendBroadcast(new Intent(ACTION_DELETE_AREA_SCAN), PERM_WIFIPRIVATE);
                db.close();
            }

            //release wifi lock
            wifiLock.release();

            // return to where you came from.
            return;
        }

        //runmode is 1 and application is visible then run
        if (runmode == 1 && visability.equals("V")) {
            //wifi lock
            wifiLock.acquire();
            //get and send results
            WIFIBadgerMainActivity.wifiList = wifi.getScanResults();
            int wifilistsize = WIFIBadgerMainActivity.wifiList.size();

            //create arrays for data
            WIFIBadgerMainActivity.rowidscan = new int[wifilistsize];
            WIFIBadgerMainActivity.ssidscan = new String[wifilistsize];
            WIFIBadgerMainActivity.bssidscan = new String[wifilistsize];
            WIFIBadgerMainActivity.levelscan = new int[wifilistsize];
            WIFIBadgerMainActivity.frequencyscan = new int[wifilistsize];
            WIFIBadgerMainActivity.capabilitiesscan = new String[wifilistsize];

            //save list to arrays for manipulation
            for (int i = 0; i < wifilistsize; i++) {
                WIFIBadgerMainActivity.rowidscan[i] = i;

                WIFIBadgerMainActivity.ssidscan[i] = WIFIBadgerMainActivity.wifiList.get(i).SSID;
                //sanitize SSID input to prevent escape characters (added .replace 6-3-17)
                WIFIBadgerMainActivity.ssidscan[i] = WIFIBadgerMainActivity.ssidscan[i].replace("\"", "");

                WIFIBadgerMainActivity.bssidscan[i] = WIFIBadgerMainActivity.wifiList.get(i).BSSID;
                WIFIBadgerMainActivity.bssidscan[i] = WIFIBadgerMainActivity.bssidscan[i].toUpperCase();
                WIFIBadgerMainActivity.levelscan[i] = WIFIBadgerMainActivity.wifiList.get(i).level;
                WIFIBadgerMainActivity.frequencyscan[i] = WIFIBadgerMainActivity.wifiList.get(i).frequency;
                WIFIBadgerMainActivity.capabilitiesscan[i] = WIFIBadgerMainActivity.wifiList.get(i).capabilities;

            }

            db.close();

            //check candidate wifi for better signal automatically
            if (WIFIBadgerMainActivity.WIFIState == 3) {

                //check signal strength for better match
                String[] getcanidateinfo = WIFIBadgerCommonCalls.checkcanidatesignalwifi(context);

                //reassociate
                if (getcanidateinfo[0].equals("true")){
                    WIFIBadgerCommonCalls.reassociatewifi(context, getcanidateinfo[1], getcanidateinfo[2], getcanidateinfo[3]);
                } else {
                    if (WIFIBadgerMainActivity.applicationsettings[2].equals("Off")){
                    } else {
                    }
                }

            } else {
            }

            //release wifi lock
            wifiLock.release();
            return;
        }

        //runmode is 0 and application is not visible then run
        if (runmode == 0 && visability.equals("NV")) {

            //set initial values
            //get settings from database
            applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettingspoller(context);
            WIFIBadgerCommonCalls.LoadActiveDatabasepoller(context);

            //wifi lock
            wifiLock.acquire();

            //Setup wifi and connection manager objects

            //check if wifi is enabled
            iswifienabled = wifi.isWifiEnabled();

            //get wifi state 0=disabling, 1=disabled, 2=enabling, 3=enabled, 4=unknown
            WIFIState = wifi.getWifiState();

            //see if wifi is connected as default route
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            isconnected = mWifi.isConnected();

            //get wifi info (SSID, BSSID, MAC, RSSI, Link Speed)
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (wifiInfo.getSSID() == null) {
                ssid = wifiInfo.getSSID();
            } else {
                ssid = wifiInfo.getSSID().replace("\"", "");
            }

            if (isconnected) {
                bssid = wifiInfo.getBSSID().toUpperCase();
            } else {
                bssid = wifiInfo.getBSSID();
            }

            rssi = wifiInfo.getRssi();
            linkspeed = wifiInfo.getLinkSpeed();

            //get dhcp ip information
            DhcpInfo dhcpInfo = wifi.getDhcpInfo();
            dns1 = String.valueOf(Formatter.formatIpAddress(dhcpInfo.dns1));
            dns2 = String.valueOf(Formatter.formatIpAddress(dhcpInfo.dns2));
            gateway = String.valueOf(Formatter.formatIpAddress(dhcpInfo.gateway));
            ipaddress = String.valueOf(Formatter.formatIpAddress(dhcpInfo.ipAddress));
            leaseduration = String.valueOf(dhcpInfo.leaseDuration);
            netmask = String.valueOf(Formatter.formatIpAddress(dhcpInfo.netmask));
            server = String.valueOf(Formatter.formatIpAddress(dhcpInfo.serverAddress));


            //Get saved network list and save in stringset
            if (iswifienabled) {
                //get SSID data to array
                //2-21-17 made tweak for null pointer fix and added startscan when empty or null
                if (wifi.getConfiguredNetworks() == null || wifi.getConfiguredNetworks().isEmpty()) {
                    wifi.startScan();
                } else {
                    int numberOfEntries = wifi.getConfiguredNetworks().size();
                    SavedSSIDs = new String[numberOfEntries];
                    for (int i = 0; i < numberOfEntries; i++) {
                        WifiConfiguration wificonf = wifi.getConfiguredNetworks().get(i);

                        SavedSSIDs[i] = wificonf.SSID.replace("\"", "");
                    }
                    wifi.startScan();
                }
                db.close();
            }

            if (!iswifienabled) {
                //if wifi is disabled, send broadcast to clear area scan results
                context.sendBroadcast(new Intent(ACTION_DELETE_AREA_SCAN), PERM_WIFIPRIVATE);
                db.close();
            }

            //release wifi lock
            wifiLock.release();

            //WIFIBadgerCommonCalls.SaveActiveDatabasepoller(context);

            // return to where you came from.
            return;
        }

        //runmode is 1 and application is not visible then run
        if (runmode == 1 && visability.equals("NV")) {
            applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettingspoller(context);
            //WIFIBadgerCommonCalls.LoadActiveDatabasepoller(context);
            //wifi lock
            wifiLock.acquire();
            //get and send results
            wifiList = wifi.getScanResults();
            int wifilistsize = wifiList.size();

            //create arrays for data
            rowidscan = new int[wifilistsize];
            ssidscan = new String[wifilistsize];
            bssidscan = new String[wifilistsize];
            levelscan = new int[wifilistsize];
            frequencyscan = new int[wifilistsize];
            capabilitiesscan = new String[wifilistsize];

            //save list to arrays for manipulation
            for (int i = 0; i < wifilistsize; i++) {
                rowidscan[i] = i;

                ssidscan[i] = wifiList.get(i).SSID;
                //sanitize SSID input to prevent escape characters
                ssidscan[i] = ssidscan[i];

                bssidscan[i] = wifiList.get(i).BSSID;
                bssidscan[i] = bssidscan[i].toUpperCase();
                levelscan[i] = wifiList.get(i).level;
                frequencyscan[i] = wifiList.get(i).frequency;
                capabilitiesscan[i] = wifiList.get(i).capabilities;

            }

            db.close();

            //check candidate wifi for better signal automatically
            if (WIFIState == 3) {

                //check signal strength for better match
                String[] getcanidateinfo = WIFIBadgerCommonCalls.checkcanidatesignalwifi(context);

                //reassociate
                if (getcanidateinfo[0].equals("true")){
                    WIFIBadgerCommonCalls.reassociatewifi(context, getcanidateinfo[1], getcanidateinfo[2], getcanidateinfo[3]);
                } else {
                    if (applicationsettings[2].equals("Off")){
                    } else {
                    }
                }

            } else {
            }

            //release wifi lock
            wifiLock.release();
            WIFIBadgerCommonCalls.SaveActiveDatabasepoller(context);
            return;
        }

        //close database in-case of unknown error
        db.close();
    }

}
