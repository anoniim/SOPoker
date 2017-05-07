package net.solvetheriddle.sopoker.dagger.component;


import android.content.Context;

import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.dagger.module.AppModule;
import net.solvetheriddle.sopoker.dagger.module.NetModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(SoPokerApp application);

    @Named("application")
    Context getContext();

    SoPokerPrefs getSoPokerPrefs();

    Retrofit getRetrofit();
}
