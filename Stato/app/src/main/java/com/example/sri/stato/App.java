package com.example.sri.stato;
import android.app.Application;
import android.graphics.Point;
import android.os.Handler;

/**
 * Created by sri on 30-07-2018.
 */

public class App extends Application {

    private static App Instance;
    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Instance=this;

        applicationHandler = new Handler(getInstance().getMainLooper());

        NativeLoader.initNativeLibs(App.getInstance());

    }

    public static App getInstance()
    {
        return Instance;
    }

    public App getResources(Point displaySize) {
        return Instance;
    }

    public AndroidUtilities getDisplayMetrics() {
        return null;
    }
}