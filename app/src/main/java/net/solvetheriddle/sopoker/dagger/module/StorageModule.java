package net.solvetheriddle.sopoker.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.preference.PreferenceManager;

import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

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
    SoPokerPrefs provideSoPokerPrefs(Application application) {
        return new SoPokerPrefs(PreferenceManager.getDefaultSharedPreferences(application));
    }
}
