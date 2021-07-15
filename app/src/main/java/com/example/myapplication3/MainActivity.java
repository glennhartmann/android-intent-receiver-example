package com.example.myapplication3;

import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = 31)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DomainVerificationManager manager =
                this.getSystemService(DomainVerificationManager.class);
        DomainVerificationUserState userState = null;
        try {
            userState = manager.getDomainVerificationUserState(this.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Integer> hostToStateMap = userState.getHostToStateMap();
        TextView verifiedDomains = findViewById(R.id.textView);
        TextView selectedDomains = findViewById(R.id.textView4);
        TextView unapprovedDomains = findViewById(R.id.textView6);
        for (String key : hostToStateMap.keySet()) {
            Integer stateValue = hostToStateMap.get(key);
            if (stateValue == DomainVerificationUserState.DOMAIN_STATE_VERIFIED) {
                // Domain has passed Android App Links verification.
                verifiedDomains.append(key + "   ");
            } else if (stateValue == DomainVerificationUserState.DOMAIN_STATE_SELECTED) {
                // Domain hasn't passed Android App Links verification, but the user has
                // associated it with an app.
                selectedDomains.append(key + "   ");
            } else {
                // All other domains.
                unapprovedDomains.append(key + "   ");
            }
        }
    }
}