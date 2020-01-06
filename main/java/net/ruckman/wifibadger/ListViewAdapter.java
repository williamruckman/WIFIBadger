package net.ruckman.wifibadger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

    private WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();

    ListViewAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.apTitle = convertView.findViewById(R.id.apTitle);
            viewHolder.apBSSID = convertView.findViewById(R.id.apBSSID);
            viewHolder.apFREQ = convertView.findViewById(R.id.apFREQ);
            viewHolder.apCHANNEL = convertView.findViewById(R.id.apCHANNEL);
            viewHolder.apRSSI = convertView.findViewById(R.id.apRSSI);
            viewHolder.apPERCENT = convertView.findViewById(R.id.area_wifi_percentval);
            viewHolder.apPERCENTBAR = convertView.findViewById(R.id.area_wifi_progressBarrssi);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);

        //blank or null frequency check added from crash report
        assert item != null;
        int frequency;
        if (item.FREQ !="" && item.FREQ != null){
            frequency = Integer.valueOf(item.FREQ);
        } else {
            frequency = 0;
        }
        int channel = WIFIBadgerCommonCalls.CalculateChannel(frequency);

        viewHolder.apTitle.setText(item.title);
        viewHolder.apBSSID.setText(item.BSSID);
        viewHolder.apFREQ.setText(item.FREQ);
        viewHolder.apCHANNEL.setText(String.valueOf(channel));
        viewHolder.apRSSI.setText(item.RSSI);
        viewHolder.apPERCENT.setText(String.valueOf(item.PERCENT));
        viewHolder.apPERCENTBAR.setProgress(item.PERCENT);

        //conditional formatting
        if (item.CAP.equals("[ESS]") || item.CAP.equals("[IBSS]")){
            //If open wifi
            viewHolder.apTitle.setTextColor(Color.RED);
            viewHolder.apBSSID.setTextColor(Color.RED);
        } else {
            viewHolder.apTitle.setTextColor(Color.parseColor("#FF019688"));
            viewHolder.apBSSID.setTextColor(Color.parseColor("#FF019688"));
        }

        if (item.BSSID.equals(WIFIBadgerMainActivity.bssid)) {
            //If connected
            viewHolder.apTitle.setTextColor(Color.GREEN);
            viewHolder.apBSSID.setTextColor(Color.GREEN);

            Intent channelintent = new Intent(WIFIBadgerWIFIPoller.ACTION_CHANNEL_UPDATE);
            channelintent.putExtra("gotchannel", channel);
            channelintent.putExtra("gotfrequency", frequency);
            channelintent.putExtra("gotCAP", item.CAP);
            getContext().sendBroadcast(channelintent, WIFIBadgerWIFIPoller.PERM_WIFIPRIVATE);
        }

        if (item.title.equals(WIFIBadgerMainActivity.ssid) && !item.BSSID.equals(WIFIBadgerMainActivity.bssid)) {
            //If connection canidate
            viewHolder.apTitle.setTextColor(Color.YELLOW);
            viewHolder.apBSSID.setTextColor(Color.YELLOW);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView apTitle;
        TextView apBSSID;
        TextView apFREQ;
        TextView apCHANNEL;
        TextView apRSSI;
        TextView apPERCENT;
        ProgressBar apPERCENTBAR;
    }
}
