package com.ibm.techathon.dealmania;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

public final class DealManiaApplication extends Application {
    public static final int EDIT_ACTIVITY_RC = 1;
    private static final String APP_ID = "applicationID";
    private static final String APP_SECRET = "applicationSecret";
    private static final String APP_ROUTE = "applicationRoute";
    private static final String PROPS_FILE = "dealmania.properties";
    private static final String CLASS_NAME = DealManiaApplication.class.getSimpleName();
    List<Offers> offersList;

    public DealManiaApplication() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(CLASS_NAME,"Activity created: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(CLASS_NAME,"Activity saved instance state: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(CLASS_NAME, "Activity paused: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(CLASS_NAME, "Activity stopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(CLASS_NAME, "Activity destroyed: " + activity.getLocalClassName());
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Called here");
        offersList = new ArrayList<Offers>();
        // Read from properties file.
        Properties props = new java.util.Properties();
        Context context = getApplicationContext();
//        try {
//            AssetManager assetManager = context.getAssets();
//            props.load(assetManager.open(PROPS_FILE));
//            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
//        } catch (FileNotFoundException e) {
//            Log.e(CLASS_NAME, "The dealmania.properties file was not found.", e);
//        } catch (IOException e) {
//            Log.e(CLASS_NAME, "The dealmania.properties file could not be read properly.", e);
//        }
        // Initialize the IBM core backend-as-a-service.
//        IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
        IBMBluemix.initialize(this, "1ff21171-e53d-4bbd-978a-2c69ab56f955", "648c0da97ab130b9d7dba428c6be0940bed7ee2d", "DealsMania.mybluemix.net");
        // Initialize the IBM Data Service.
        IBMData.initializeService();
        // Register the Item Specialization.
        Offers.registerSpecialization(Offers.class);
        Subscribes.registerSpecialization(Subscribes.class);
        SharedCoupons.registerSpecialization(SharedCoupons.class);
    }

    /**
     * returns the itemList, an array of Item objects.
     *
     * @return offersList
     */
    public List<Offers> getOffersList() {
        return offersList;
    }
}