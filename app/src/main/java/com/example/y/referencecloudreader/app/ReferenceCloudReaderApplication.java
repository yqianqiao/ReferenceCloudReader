package com.example.y.referencecloudreader.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by y on 2018/5/29.
 */

public class ReferenceCloudReaderApplication extends Application {
    private static ReferenceCloudReaderApplication referenceCloudReaderApplication;

    public static ReferenceCloudReaderApplication getInstance() {
        return referenceCloudReaderApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        referenceCloudReaderApplication = this;

        initTextSize();
    }

    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
