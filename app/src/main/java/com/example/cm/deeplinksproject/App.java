package com.example.cm.deeplinksproject;

import android.app.Application;
import android.util.Log;

/**
 * Created by cm on 12/7/2017.
 */

import com.example.cm.deeplinksproject.Utils.DeviceUuidFactory;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumDeviceIdMode;
import com.leanplum.LeanplumPushService;
import com.leanplum.annotations.Parser;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.StartCallback;
import com.leanplum.callbacks.VariablesChangedCallback;

public class App extends Application{
    public void onCreate() {
        super.onCreate();
        DeviceUuidFactory deviceUuidFactory= new DeviceUuidFactory(getApplicationContext());
        Leanplum.setApplicationContext(this);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        Leanplum.setApplicationContext(this);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);


        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode("app_e0RMov57XDGmvwe5rK3rvuNmZvP0XNaoxC7Xhj9nzOA", "dev_ldvc7O3eyagv9tPKtMbCk92RHrp15dAD3XjcDAbH4XE");
        } else {
            Leanplum.setAppIdForProductionMode("app_e0RMov57XDGmvwe5rK3rvuNmZvP0XNaoxC7Xhj9nzOA", "prod_A9wuqFiOHes1zWBIcmTLLRZYsaEEPGSfDRAJZq8NyZQ");
        }

        // Registering for Push with Leanplum
        // Here is where the SenderID is passed. In this case I'm using the Leanplum bundle SenderID,
        // no need in this case to specify any specific Google API key in the Settings in the Leanplum Dashboard.
        LeanplumPushService.setGcmSenderId(LeanplumPushService.LEANPLUM_SENDER_ID);

        Leanplum.addVariablesChangedHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                Log.i("### ", "Welcome message is: "+ "welcome");
                Leanplum.track("Launch");

            }
        });
        Leanplum.setDeviceId(deviceUuidFactory.getDeviceUuid().toString());
        Log.i("Leanplum ","UUID :" + deviceUuidFactory.getDeviceUuid().toString());
        Leanplum.start(this,"Guest");
        Leanplum.addStartResponseHandler(new StartCallback() {
            @Override
            public void onResponse(boolean b) {
                Log.i("### ", "Leanplum is started - Variants are: " + Leanplum.variants());

            }
        });

    }

}
