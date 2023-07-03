package net.ruckman.wifibadger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WIFIBadgerScreenStateReceiver extends BroadcastReceiver {

        private boolean screenOn;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                screenOn = false;

            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                screenOn = true;

            }
            Intent i = new Intent(context, WIFIBadgerService.class);
            i.putExtra("screen_state", screenOn);
            context.startService(i);
        }
}
