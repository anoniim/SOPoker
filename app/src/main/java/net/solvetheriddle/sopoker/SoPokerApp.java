package net.solvetheriddle.sopoker;

import net.solvetheriddle.sopoker.dagger.component.DaggerAppComponent;
import net.solvetheriddle.sopoker.dagger.module.NetworkModule;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;


public class SoPokerApp extends DaggerApplication implements HasActivityInjector {

//    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
    }

//    @Override
//    public AndroidInjector<Activity> activityInjector() {
//        return dispatchingActivityInjector;
//    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
    }
}
