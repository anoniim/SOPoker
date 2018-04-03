package net.solvetheriddle.sopoker.dagger.module;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.preference.PreferenceManager;

import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

//    private final Application mApplication;
//
//    public AppModule(final Application application) {
//        mApplication = application;
//    }

    @Provides // could use @Bind, that returns injected parameter
    @Singleton
    @Named("application")
    Context provideContext(Application application) {
        return application;
    }

//
//    @Provides
//    @Singleton
//    Application provideApplication() {
//        return mApplication;
//    }


    @Provides
    @Singleton
    SoPokerPrefs provideSoPokerPrefs(Application application) {
        return new SoPokerPrefs(PreferenceManager.getDefaultSharedPreferences(application));
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "SOPoker.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    PokeScheduler providePokeScheduler(@Named("application") final Context context) {
        return new PokeScheduler(context);
    }
}
