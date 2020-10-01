package ghanbari.sadegh.newshop;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.Parse;

import java.util.Locale;

public class MyApplication extends Application {
//Clone App
    @Override
    public void onCreate() {
        super.onCreate();
        // "getPackageName"

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z")
                .clientKey("rsrJSXUykAB1mK82l3AKfhCzzrL4M2GjbsTjwH4V")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
