package net.ruckman.wifibadger;

public class ListViewItem {
    public final String title;        // the text for the ListView item title
    final String BSSID;
    final String FREQ;
    final String RSSI;
    final String CAP;
    final int PERCENT;

    ListViewItem(String title, String BSSID, String FREQ, String RSSI, String CAP, int PERCENT) {
        this.title = title;
        this.BSSID = BSSID;
        this.FREQ = FREQ;
        this.RSSI = RSSI;
        this.CAP = CAP;
        this.PERCENT = PERCENT;
    }
}
