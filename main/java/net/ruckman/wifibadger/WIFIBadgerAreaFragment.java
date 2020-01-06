package net.ruckman.wifibadger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WIFIBadgerAreaFragment extends ListFragment {

    public List<ListViewItem> mItems;        // ListView items list
    private ArrayAdapter adapter;
    WIFIBadgerWIFIPoller WIFIBadgerWIFIPoller = new WIFIBadgerWIFIPoller();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // initialize the items list
        mItems = new ArrayList<>();

        adapter = new ListViewAdapter(getActivity(), mItems);

    }

    public static WIFIBadgerAreaFragment newInstance() {
        return new WIFIBadgerAreaFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);

        // initialize and set the list adapter
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), "CAPABILITIES\n"+item.CAP, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mOnUpdateWIFIStatus);
        getActivity().unregisterReceiver(mOnUpdateWIFIStatusDelete);
    }

    @Override
    public void onResume() {
        super.onResume();

        //receiver for adding items on wifi scan results
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getActivity().registerReceiver(mOnUpdateWIFIStatus, filter, net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE, null);

        //receiver for erasing items on poller wifi not enabled action
        IntentFilter filterdelscan = new IntentFilter(net.ruckman.wifibadger.WIFIBadgerWIFIPoller.ACTION_DELETE_AREA_SCAN);
        getActivity().registerReceiver(mOnUpdateWIFIStatusDelete, filterdelscan, net.ruckman.wifibadger.WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Take action when broadcast is received with the proper auth
    private BroadcastReceiver mOnUpdateWIFIStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WIFIBadgerWIFIPoller.getWIFIInfo(getActivity(), 1);

            int scannedapcount = WIFIBadgerMainActivity.wifiList.size();

            //get sql data and place into arrays
            if (scannedapcount == 0){
                mItems.clear();
            }
            else {
                mItems.clear();
                for (int i = 0; i < scannedapcount; i++) {

                    int percentpower = 0;
                    //calculate percent power
                    if (WIFIBadgerMainActivity.levelscan[i] >= -20) {
                        percentpower = 100;
                    }
                    if (WIFIBadgerMainActivity.levelscan[i] <= -100) {
                        percentpower = 0;
                    }
                    if ((WIFIBadgerMainActivity.levelscan[i] < -20) && (WIFIBadgerMainActivity.levelscan[i] > -100) ) {
                        int fudgefactor = (int)Math.round((WIFIBadgerMainActivity.levelscan[i]+100)*.25);
                        percentpower = ((WIFIBadgerMainActivity.levelscan[i]+100)+fudgefactor);
                    }

                    mItems.add(new ListViewItem(WIFIBadgerMainActivity.ssidscan[i], WIFIBadgerMainActivity.bssidscan[i], String.valueOf(WIFIBadgerMainActivity.frequencyscan[i]), String.valueOf(WIFIBadgerMainActivity.levelscan[i]), WIFIBadgerMainActivity.capabilitiesscan[i], percentpower));
                }

            }

            adapter.notifyDataSetChanged();

        }

    };

    //Take action when broadcast is received with the proper auth
    private BroadcastReceiver mOnUpdateWIFIStatusDelete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mItems.clear();
            adapter.notifyDataSetChanged();
        }
    };

}
