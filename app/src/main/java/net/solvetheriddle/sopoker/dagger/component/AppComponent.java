package net.solvetheriddle.sopoker.dagger.component;


import android.app.Application;

import net.solvetheriddle.sopoker.dagger.module.ActivityBindingsModule;
import net.solvetheriddle.sopoker.dagger.module.AppModule;
import net.solvetheriddle.sopoker.dagger.module.DataModule;
import net.solvetheriddle.sopoker.dagger.module.NetworkModule;
import net.solvetheriddle.sopoker.dagger.module.UseCaseModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, DataModule.class, UseCaseModule.class,
        ActivityBindingsModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Override
    void inject(DaggerApplication application);

//    @Named("application")
//    Context getContext();
//
//    SoPokerPrefs getSoPokerPrefs();
//
//    Retrofit getRetrofit();
//
//    AppDatabase getAppDatabase();
//
//    PokeScheduler getPokeScheduler();

    @Component.Builder
    interface Builder {
        Builder networkModule(NetworkModule networkModule);
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
