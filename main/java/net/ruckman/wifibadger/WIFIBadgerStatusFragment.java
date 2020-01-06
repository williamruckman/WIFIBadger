package net.ruckman.wifibadger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WIFIBadgerStatusFragment extends Fragment {

    private TextView mstatus_wifi_state_var;
    private TextView mwifi_state_var;
    private TextView mwifi_connected_var;
    private TextView mstatus_wifi_SSID;
    private TextView mstatus_wifi_BSSID;
    private TextView mstatus_wifi_MAC;
    private TextView mstatus_wifi_RSSI;
    private TextView mstatus_wifi_linkspeed;
    private ProgressBar mstatus_wifi_progressBarrssi;
    private TextView mstatus_wifi_percent;
    private TextView mstatus_wifi_linktype;
    private TextView mstatus_wifi_linkquality;
    private TextView mstatus_wifi_ipaddress;
    private TextView mstatus_wifi_netmask;
    private TextView mstatus_wifi_gateway;
    private TextView mstatus_wifi_dns1;
    private TextView mstatus_wifi_dns2;
    private TextView mstatus_wifi_dhcpserver;
    private TextView mstatus_wifi_dhcplease;
    private TextView mstatus_wifi_iptype;
    private TextView mstatus_wifi_savedaps;
    private TextView mstatus_wifi_freqval;
    private TextView mStatusCHANNELval;
    private TextView mstatus_wifi_CAPVal;

    private int percentpower;

    WIFIBadgerWIFIPoller WIFIBadgerWIFIPoller = new WIFIBadgerWIFIPoller();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //get wifi information

        WIFIBadgerWIFIPoller.getWIFIInfo(getActivity(),0);

    }

    public static WIFIBadgerStatusFragment newInstance() {
        return new WIFIBadgerStatusFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mOnUpdateWIFIStatus);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Setup Broadcast Intent Receiver for refreshing wifi data when alarm is triggered
        IntentFilter filter = new IntentFilter();
        filter.addAction(net.ruckman.wifibadger.WIFIBadgerWIFIPoller.ACTION_UPDATE_WIFI_STATUS);
        filter.addAction(net.ruckman.wifibadger.WIFIBadgerWIFIPoller.ACTION_CHANNEL_UPDATE);
        getActivity().registerReceiver(mOnUpdateWIFIStatus, filter, net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE, null);
        WIFIBadgerWIFIPoller.getWIFIInfo(getActivity(),0);
    }

    //Take action when broadcast is received with the proper auth
    private BroadcastReceiver mOnUpdateWIFIStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //refresh screen data after initial get
            if (intent.getAction().equals("net.ruckman.wifibadger.UPDATE_WIFI_STATUS")) {
                refreshscreendata();
            }
            //update channel after successful scan
            if (intent.getAction().equals("net.ruckman.wifibadger.ACTION_CHANNEL_UPDATE")) {
                int channel = intent.getExtras().getInt("gotchannel");
                int frequency = intent.getExtras().getInt("gotfrequency");
                String CAP = intent.getExtras().getString("gotCAP");

                mstatus_wifi_freqval.setText(String.valueOf(frequency));
                mStatusCHANNELval.setText(String.valueOf(channel));
                CAP = CAP.replace("[", "");
                CAP = CAP.replace("]", " ");
                mstatus_wifi_CAPVal.setText(CAP+"\n");

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifibadger_status, container, false);
        //Set home and icon display on actionbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Assign view by id to manipulated GUI variables
        mstatus_wifi_state_var = rootView.findViewById(R.id.status_wifi_state_var);
        mwifi_state_var = rootView.findViewById(R.id.wifi_state_var);
        mwifi_connected_var = rootView.findViewById(R.id.wifi_connected_var);
        mstatus_wifi_SSID = rootView.findViewById(R.id.status_wifi_SSIDVal);
        mstatus_wifi_BSSID = rootView.findViewById(R.id.status_wifi_BSSIDVal);
        mstatus_wifi_MAC = rootView.findViewById(R.id.status_wifi_MACVal);
        mstatus_wifi_RSSI = rootView.findViewById(R.id.status_wifi_RSSIVal);
        mstatus_wifi_linkspeed = rootView.findViewById(R.id.status_wifi_linkspeedval);
        mstatus_wifi_progressBarrssi = rootView.findViewById(R.id.status_wifi_progressBarrssi);
        mstatus_wifi_percent = rootView.findViewById(R.id.status_wifi_percentval);
        mstatus_wifi_linktype = rootView.findViewById(R.id.status_wifi_linktypeVal);
        mstatus_wifi_linkquality = rootView.findViewById(R.id.status_wifi_linkqualityVal);
        mstatus_wifi_ipaddress = rootView.findViewById(R.id.status_wifi_ipaddressval);
        mstatus_wifi_netmask = rootView.findViewById(R.id.status_wifi_netmaskval);
        mstatus_wifi_gateway = rootView.findViewById(R.id.status_wifi_gatewayval);
        mstatus_wifi_dns1 = rootView.findViewById(R.id.status_wifi_dns1val);
        mstatus_wifi_dns2 = rootView.findViewById(R.id.status_wifi_dns2val);
        mstatus_wifi_iptype = rootView.findViewById(R.id.status_wifi_iptypeval);
        mstatus_wifi_dhcpserver = rootView.findViewById(R.id.status_wifi_dhcpserverval);
        mstatus_wifi_dhcplease = rootView.findViewById(R.id.status_wifi_dhcpleaseval);
        mstatus_wifi_savedaps = rootView.findViewById(R.id.status_wifi_savedaplist);
        mstatus_wifi_freqval = rootView.findViewById(R.id.status_wifi_freqval);
        mStatusCHANNELval = rootView.findViewById(R.id.StatusCHANNELval);
        mstatus_wifi_CAPVal = rootView.findViewById(R.id.status_wifi_CAPVal);

        refreshscreendata();

        //return to where you came from
        return rootView;
    }

    public void refreshscreendata(){

        //Set initial displayed variables
        //WIFI state

        if (WIFIBadgerMainActivity.iswifienabled != null){
            if (WIFIBadgerMainActivity.iswifienabled){
                mstatus_wifi_state_var.setText(R.string.status_wifi_state_on);
            }
            if (!WIFIBadgerMainActivity.iswifienabled) {
                mstatus_wifi_state_var.setText(R.string.status_wifi_state_off);
            }
        }
        if (WIFIBadgerMainActivity.iswifienabled == null){
            mstatus_wifi_state_var.setText("-");
        }

        //get wifi state 0=disabling, 1=disabled, 2=enabling, 3=enabled, 4=unknown
        if (WIFIBadgerMainActivity.WIFIState == 0){
            mwifi_state_var.setText(R.string.status_wifi_state_0);
        }
        if (WIFIBadgerMainActivity.WIFIState == 1){
            mwifi_state_var.setText(R.string.status_wifi_state_1);
        }
        if (WIFIBadgerMainActivity.WIFIState == 2){
            mwifi_state_var.setText(R.string.status_wifi_state_2);
        }
        if (WIFIBadgerMainActivity.WIFIState == 3){
            mwifi_state_var.setText(R.string.status_wifi_state_3);
        }
        else {
            mwifi_state_var.setText(R.string.status_wifi_state_4);
        }

        //get wifi connection state aka is default route false=not connected, true=connected
        if (WIFIBadgerMainActivity.isconnected != null){
            if (!WIFIBadgerMainActivity.isconnected){
                mwifi_connected_var.setText(R.string.status_wifi_disconnected);
                mstatus_wifi_SSID.setText("-");
                mstatus_wifi_BSSID.setText("-");
                mstatus_wifi_MAC.setText("-");
                mstatus_wifi_RSSI.setText("-");
                mstatus_wifi_linkspeed.setText("-");
                mstatus_wifi_linktype.setText("-");
                mstatus_wifi_linkquality.setText("-");
                mstatus_wifi_percent.setText("-");
                mstatus_wifi_progressBarrssi.setProgress(0);
                mstatus_wifi_ipaddress.setText("-");
                mstatus_wifi_netmask.setText("-");
                mstatus_wifi_gateway.setText("-");
                mstatus_wifi_dns1.setText("-");
                mstatus_wifi_dns2.setText("-");
                mstatus_wifi_dhcpserver.setText("-");
                mstatus_wifi_dhcplease.setText("-");
                mstatus_wifi_iptype.setText("-");
                mstatus_wifi_freqval.setText("-");
                mStatusCHANNELval.setText("-");
                mstatus_wifi_CAPVal.setText("-\n");
            }
            if (WIFIBadgerMainActivity.isconnected){
                mwifi_connected_var.setText(R.string.status_wifi_connected);
                //get wifi information and display it
                mstatus_wifi_SSID.setText(WIFIBadgerMainActivity.ssid);
                mstatus_wifi_BSSID.setText(WIFIBadgerMainActivity.bssid);
                mstatus_wifi_MAC.setText(WIFIBadgerMainActivity.mac);
                mstatus_wifi_RSSI.setText(String.valueOf(WIFIBadgerMainActivity.rssi));
                mstatus_wifi_linkspeed.setText(String.valueOf(WIFIBadgerMainActivity.linkspeed));

                //calculate link type
                if (WIFIBadgerMainActivity.linkspeed <= 2) {
                    mstatus_wifi_linktype.setText(R.string.status_wifi_linktype80211);
                }
                if ((WIFIBadgerMainActivity.linkspeed > 2) && WIFIBadgerMainActivity.linkspeed <= 11) {
                    mstatus_wifi_linktype.setText(R.string.status_wifi_linktype80211b);
                }
                if ((WIFIBadgerMainActivity.linkspeed > 11) && WIFIBadgerMainActivity.linkspeed <= 54) {
                    mstatus_wifi_linktype.setText(R.string.status_wifi_linktype80211ag);
                }
                if ((WIFIBadgerMainActivity.linkspeed > 54) && WIFIBadgerMainActivity.linkspeed <= 600) {
                    mstatus_wifi_linktype.setText(R.string.status_wifi_linktype80211n);
                }
                if (WIFIBadgerMainActivity.linkspeed > 600) {
                    mstatus_wifi_linktype.setText(R.string.status_wifi_linktype80211ac);
                }

                //calculate link quality
                int rssipower = WIFIBadgerMainActivity.rssi;
                if (rssipower >= -50) {
                    mstatus_wifi_linkquality.setText(R.string.status_wifi_linkqualityexcellent);
                }
                if ((rssipower > -70) && (rssipower < -50)) {
                    mstatus_wifi_linkquality.setText(R.string.status_wifi_linkqualitygood);
                }
                if (rssipower <= -70) {
                    mstatus_wifi_linkquality.setText(R.string.status_wifi_linkqualitypoor);
                }

                //calculate percent power
                if (rssipower >= -20) {
                    percentpower = 100;
                }
                if (rssipower <= -100) {
                    percentpower = 0;
                }
                if ((rssipower < -20) && (rssipower > -100) ) {
                    int fudgefactor = (int)Math.round((rssipower +100)*.25);
                    percentpower = ((rssipower +100)+fudgefactor);
                }
                mstatus_wifi_percent.setText(String.valueOf(percentpower));
                mstatus_wifi_progressBarrssi.setProgress(percentpower);

                //get DHCP IP Info
                if (WIFIBadgerMainActivity.ipaddress.equals("0.0.0.0")) {
                    mstatus_wifi_ipaddress.setText("-");
                }
                else {
                    mstatus_wifi_ipaddress.setText(WIFIBadgerMainActivity.ipaddress);
                }

                if (WIFIBadgerMainActivity.netmask.equals("0.0.0.0")) {
                    mstatus_wifi_netmask.setText("-");
                }
                else {
                    mstatus_wifi_netmask.setText(WIFIBadgerMainActivity.netmask);
                }

                if (WIFIBadgerMainActivity.gateway.equals("0.0.0.0")) {
                    mstatus_wifi_gateway.setText("-");
                }
                else {
                    mstatus_wifi_gateway.setText(WIFIBadgerMainActivity.gateway);
                }

                //get dns info
                if (WIFIBadgerMainActivity.dns1.equals("0.0.0.0")) {
                    mstatus_wifi_dns1.setText("-");
                }
                else {
                    mstatus_wifi_dns1.setText(WIFIBadgerMainActivity.dns1);
                }

                if (WIFIBadgerMainActivity.dns2.equals("0.0.0.0")) {
                    mstatus_wifi_dns2.setText("-");
                }
                else {
                    mstatus_wifi_dns2.setText(WIFIBadgerMainActivity.dns2);
                }

                //set ip type info
                if (WIFIBadgerMainActivity.server.equals("0.0.0.0")) {
                    mstatus_wifi_iptype.setText(R.string.status_wifi_type_static);
                    mstatus_wifi_dhcpserver.setText("-");
                    mstatus_wifi_dhcplease.setText("-");
                }
                else {
                    mstatus_wifi_iptype.setText(R.string.status_wifi_type_dhcp);
                    mstatus_wifi_dhcpserver.setText(WIFIBadgerMainActivity.server);
                    if (WIFIBadgerMainActivity.leaseduration.equals("-1") ) {
                        mstatus_wifi_dhcplease.setText(R.string.notavailable);
                    }
                    else {
                        mstatus_wifi_dhcplease.setText(WIFIBadgerMainActivity.leaseduration);
                    }
                }

            }
        }
        if (WIFIBadgerMainActivity.isconnected == null){
            mwifi_connected_var.setText(R.string.status_wifi_disconnected);
            mstatus_wifi_SSID.setText("-");
            mstatus_wifi_BSSID.setText("-");
            mstatus_wifi_MAC.setText("-");
            mstatus_wifi_RSSI.setText("-");
            mstatus_wifi_linkspeed.setText("-");
            mstatus_wifi_linktype.setText("-");
            mstatus_wifi_linkquality.setText("-");
            mstatus_wifi_percent.setText("-");
            mstatus_wifi_progressBarrssi.setProgress(0);
            mstatus_wifi_ipaddress.setText("-");
            mstatus_wifi_netmask.setText("-");
            mstatus_wifi_gateway.setText("-");
            mstatus_wifi_dns1.setText("-");
            mstatus_wifi_dns2.setText("-");
            mstatus_wifi_dhcpserver.setText("-");
            mstatus_wifi_dhcplease.setText("-");
            mstatus_wifi_iptype.setText("-");
            mstatus_wifi_freqval.setText("-");
            mStatusCHANNELval.setText("-");
            mstatus_wifi_CAPVal.setText("-\n");
        }


        //set and display known access points

        //get saved ap count in database table
        int savedapcount;
        if (WIFIBadgerMainActivity.SavedSSIDs == null) {
            savedapcount = 0;
        } else {
            savedapcount = WIFIBadgerMainActivity.SavedSSIDs.length;
        }

        if (savedapcount == 0){
            mstatus_wifi_savedaps.setText("-");
        }
        else {

            //Build saved ap list and display
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < savedapcount; i++) {
                sb.append(WIFIBadgerMainActivity.SavedSSIDs[i]).append("\n");
            }
            sb = new StringBuilder(sb.toString().replace("\"", ""));
            mstatus_wifi_savedaps.setText(sb.toString());

        }

    }

}
