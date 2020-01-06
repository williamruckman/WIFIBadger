package net.ruckman.wifibadger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstRunActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_firstrunactivity);
            Button b1 = findViewById(R.id.button);
            b1.setOnClickListener(myhandler1);
            if (Build.VERSION.SDK_INT >= 23) {
                TextView m1 = findViewById(R.id.textView);
                m1.setVisibility(TextView.VISIBLE);
                m1.setText(R.string.app_permission_marshmallow_first_run);
            }
    }
    View.OnClickListener myhandler1 = new View.OnClickListener() {
        @SuppressLint("ApplySharedPref")
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            prefs.edit().putBoolean("firstrun", false).commit();
            if (Build.VERSION.SDK_INT >= 23) {
                Button b1 = findViewById(R.id.button);
                b1.setText(R.string.app_loading);
                b1.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), FirstRunPermissions.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Button b1 = findViewById(R.id.button);
                b1.setText(R.string.app_loading);
                b1.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), WIFIBadgerMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        }
    };
}
