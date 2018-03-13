package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.profile.data.ProfileDao;
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
//    LoginDao provideLoginDao(final SoPokerPrefs prefs) {
//        return new LoginDao(prefs);
//    }

    @Provides
    ProfileDao provideProfileDao(final Retrofit retrofit, final SoPokerPrefs prefs,
            final ResponseParser responseParser) {
        return new ProfileDao(retrofit, prefs, responseParser);
    }

    @Provides
    @Named("service")
    Context provideContext() {
        return mPokeService;
    }
}
