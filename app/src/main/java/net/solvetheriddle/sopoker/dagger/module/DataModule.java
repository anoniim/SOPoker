package net.solvetheriddle.sopoker.dagger.module;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class, StorageModule.class})
public class DataModule {

    @Provides
    @Singleton
    static ProfileRepository provideProfileRepository(AppDatabase appDatabase,
            final ProfileApi profileApi) {
        return new ProfileRepository(appDatabase, profileApi);
    }
}
