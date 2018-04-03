package net.solvetheriddle.sopoker.dagger.module;

import android.app.Application;

import net.solvetheriddle.sopoker.app.profile.ProfilePresenter;
import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.LoginApi;
import net.solvetheriddle.sopoker.network.ResponseParser;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class ProfileScreenModule {

    @Provides
    static ProfilePresenter.Factory bindViewModelFactory(final Application application,
            final ProfileRepository profileRepository,
            final LoginApi loginApi,
            final PokeScheduler pokeScheduler) {
        return new ProfilePresenter.Factory(application, profileRepository, loginApi, pokeScheduler);
    }

    @Provides
    static ProfileRepository provideProfileRepository(AppDatabase appDatabase,
            final ProfileApi profileApi) {
        return new ProfileRepository(appDatabase, profileApi);
    }

    @Provides
    static ProfileApi provideProfileDao(final Retrofit retrofit,
            final SoPokerPrefs prefs,
            final ResponseParser responseParser) {
        return new ProfileApi(retrofit, prefs, responseParser);
    }

    @Provides
    static LoginApi provideLoginDao(final SoPokerPrefs prefs) {
        return new LoginApi(prefs);
    }
}
