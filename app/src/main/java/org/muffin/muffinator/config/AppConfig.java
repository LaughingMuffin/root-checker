package org.muffin.muffinator.config;


import java.io.Serializable;

import dreamspace.ads.sdk.data.AdNetworkType;

public class AppConfig implements Serializable {

    public static final AppConfig.Ads ads = new AppConfig.Ads();

    public static class Ads {

        public static final boolean AD_ENABLED = true;
        public static final boolean AD_ENABLE_GDPR = true;
        public static final boolean AD_GLOBAL_OPEN_APP = true;
        /* use real ids when publishing */
        public static final String AD_ADMOB_PUBLISHER_ID = "pub-3940256099942544";
        public static final String AD_ADMOB_OPEN_APP_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";
        public final AdNetworkType[] adNetworkTypes = {
                AdNetworkType.ADMOB,
        };
        public final Integer retryFromStartMax = 2;
    }

}
