package net.solvetheriddle.sopoker;

import android.app.Application;

import net.solvetheriddle.sopoker.dagger.component.AppComponent;
import net.solvetheriddle.sopoker.dagger.component.DaggerAppComponent;
import net.solvetheriddle.sopoker.dagger.module.NetworkModule;


public class SoPokerApp extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .application(this)
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
