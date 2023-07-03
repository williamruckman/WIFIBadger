package net.ruckman.wifibadger;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WIFIBadgerAboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static WIFIBadgerAboutFragment newInstance() {
        return new WIFIBadgerAboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifibadger_about, container, false);
        //Set home and icon display on actionbar
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Set up listener for the website button
        Button mAboutSupportButton = rootView.findViewById(R.id.about_support_button);
        mAboutSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String uriString = "https://ruckman.net/wifibadger.html";
                    Uri intentUri = Uri.parse(uriString);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(intentUri);

                    startActivity(intent);

                }
        });

        //Set up listener for the privacy policy button
        Button mAboutPrivacyButton = rootView.findViewById(R.id.about_privacy_button);
        mAboutPrivacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uriString = "https://ruckman.net/privacy.html";
                Uri intentUri = Uri.parse(uriString);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(intentUri);

                startActivity(intent);

            }
        });

        //Return the fragments view
        return rootView;
    }

}
