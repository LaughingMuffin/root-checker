package org.muffin.muffinator.advertise;

import android.app.Activity;

import org.muffin.muffinator.config.AppConfig;

import dreamspace.ads.sdk.AdConfig;
import dreamspace.ads.sdk.AdNetwork;
import dreamspace.ads.sdk.gdpr.GDPR;
import dreamspace.ads.sdk.listener.AdOpenListener;

public class AdNetworkHelper {

    private final AdNetwork adNetwork;
    private final GDPR gdpr;

    public AdNetworkHelper(Activity activity) {
        adNetwork = new AdNetwork(activity);
        gdpr = new GDPR(activity);
    }

    public static void initConfig() {
        AdConfig.ad_networks = AppConfig.ads.adNetworkTypes;
        AdConfig.retry_from_start_max = AppConfig.ads.retryFromStartMax;
        AdConfig.ad_admob_publisher_id = AppConfig.Ads.AD_ADMOB_PUBLISHER_ID;
        AdConfig.ad_admob_open_app_unit_id = AppConfig.Ads.AD_ADMOB_OPEN_APP_UNIT_ID;

    }

    public void updateConsentStatus() {
        if (!AppConfig.Ads.AD_ENABLED || !AppConfig.Ads.AD_ENABLE_GDPR) return;
        gdpr.updateGDPRConsentStatus();
    }

    public void init() {
        AdNetworkHelper.initConfig();
        adNetwork.init();
    }

    public void loadAndShowOpenAppAd(Activity activity, boolean enable, AdOpenListener listener) {
        adNetwork.loadAndShowOpenAppAd(activity, enable, listener);
    }

}
