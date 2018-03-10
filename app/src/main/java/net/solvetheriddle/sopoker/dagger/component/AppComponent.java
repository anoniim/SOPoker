package net.solvetheriddle.sopoker.dagger.component;


import android.app.Application;
import android.content.Context;

import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.dagger.module.AppModule;
import net.solvetheriddle.sopoker.dagger.module.NetworkModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(SoPokerApp application);

    @Named("application")
    Context getContext();

    SoPokerPrefs getSoPokerPrefs();

    Retrofit getRetrofit();

    @Component.Builder
    interface Builder {
        AppComponent build();
        Builder networkModule(NetworkModule networkModule);
        @BindsInstance
        Builder application(Application application);
    }
}
