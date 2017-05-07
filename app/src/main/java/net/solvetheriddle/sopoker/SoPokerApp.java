package net.solvetheriddle.sopoker;

import android.app.Application;

import net.solvetheriddle.sopoker.dagger.component.AppComponent;
import net.solvetheriddle.sopoker.dagger.component.DaggerAppComponent;
import net.solvetheriddle.sopoker.dagger.module.AppModule;
import net.solvetheriddle.sopoker.dagger.module.NetModule;


public class SoPokerApp extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.stackexchange.com/2.2/"))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
