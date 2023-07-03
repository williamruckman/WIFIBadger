package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WIFIBadgerScanBroadcastReceiver extends BroadcastReceiver {
    WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();
    WIFIBadgerWIFIPoller WIFIBadgerWIFIPoller = new WIFIBadgerWIFIPoller();
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        String[] applicationsettings = WIFIBadgerCommonCalls.GetApplicationSettingspoller(context);
        String visability = applicationsettings[0];

        if (visability.equals("NV")) {
            WIFIBadgerWIFIPoller.getWIFIInfo(context, 1);
        }
    }
}
