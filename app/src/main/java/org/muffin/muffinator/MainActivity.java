package org.muffin.muffinator;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.muffin.muffinator.advertise.AdNetworkHelper;
import org.muffin.muffinator.config.AppConfig;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String SUPER_USER_APK = "/system/app/Superuser.apk";

    public static boolean isRooted() {

        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        try {
            File file = new File(SUPER_USER_APK);
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }
        return executedSuccesfully;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // init ads
        AdNetworkHelper adNetworkHelper = new AdNetworkHelper(this);
        adNetworkHelper.init();
        adNetworkHelper.updateConsentStatus();
        // init open ads for admob
        adNetworkHelper.loadAndShowOpenAppAd(this, AppConfig.Ads.AD_GLOBAL_OPEN_APP, null);

        TextView rootStatus = findViewById(R.id.root_status);
        if (rootStatus != null) {
            rootStatus.setAllCaps(true);
            if (isRooted()) {
                rootStatus.setText(R.string.rooted);
                rootStatus.setTextColor(Color.RED);
            } else {
                rootStatus.setText(R.string.not_rooted);
                rootStatus.setTextColor(Color.GREEN);
            }
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}