package net.solvetheriddle.sopoker.dagger.module;


import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(final Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @Named("application")
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    SoPokerPrefs provideSoPokerPrefs() {
        return new SoPokerPrefs(PreferenceManager.getDefaultSharedPreferences(mApplication));
    }
}
