package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WIFIBadgerStartupService extends BroadcastReceiver {
    WIFIBadgerWIFIPoller WIFIBadgerWIFIPoller = new WIFIBadgerWIFIPoller();
    WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    public void onReceive(Context arg0, Intent arg1)
    {
        //setup intent
        Intent intent = new Intent(arg0,WIFIBadgerWIFIPoller.class);

        //Set up database if it doesn't exist and build tables for defaults
        String WBDATABASE = "WIFIBadgerDB.db";
        SQLiteDatabase db = arg0.openOrCreateDatabase(WBDATABASE, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgersettingstable(rowid INTEGER,setting VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgerstatustable(rowid INTEGER,isenabled BOOLEAN,state INTEGER,isconnected BOOLEAN,ssid VARCHAR,bssid VARCHAR,mac VARCHAR,rssi INTEGER,linkspeed INTEGER,dns1 VARCHAR,dns2 VARCHAR,gateway VARCHAR,ipaddress VARCHAR,leaseduration VARCHAR,netmask VARCHAR,server VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgersavedaptable(rowid INTEGER,savedssid VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgerapscantable(rowidscan INTEGER,ssidscan VARCHAR,bssidscan VARCHAR,levelscan INTEGER,frequencyscan INTEGER,capabilitiesscan VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS wifibadgercanidatetable(rowid INTEGER,ssid VARCHAR,bssid VARCHAR,polls INTEGER);");
        Cursor c=db.rawQuery("SELECT * FROM wifibadgersettingstable", null);
        if(c.getCount()==0)
        {
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('0','NV');");
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
        }
        if(c.getCount()==10)
        {
            db.execSQL("INSERT INTO wifibadgersettingstable VALUES('10','15 RSSI');");
        }
        c.close();
        db.close();

        //start bootup services and polling
        arg0.startService(intent);

        String[] applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettings(arg0);
        //Start Screen Listen Service
        if (applicationsettings[6].equals("SON")){
            WIFIBadgerWIFIPoller.setServiceAlarmService(arg0, true);
            arg0.startService(new Intent(arg0, WIFIBadgerService.class));
        }
    }
}
