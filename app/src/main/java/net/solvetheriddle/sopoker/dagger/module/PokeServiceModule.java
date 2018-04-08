package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;
import net.solvetheriddle.sopoker.app.schedule.PokeService;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PokeServiceModule {

    private final PokeService mPokeService;

    public PokeServiceModule(PokeService pokeService) {
        mPokeService = pokeService;
    }

//    @Provides
//    LoginApi provideLoginApi(final SoPokerPrefs prefs) {
//        return new LoginApi(prefs);
//    }

    @Provides
    ProfileApi provideProfileDao(final Retrofit retrofit, final SoPokerPrefs prefs,
            final ResponseParser responseParser) {
        return new ProfileApi(retrofit, prefs, responseParser);
    }

    @Provides
    @Named("service")
    Context provideContext() {
        return mPokeService;
    }
}
