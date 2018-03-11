package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.profile.data.ProfileDao;
import net.solvetheriddle.sopoker.app.schedule.PokeService;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;

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

    @Provides
    ProfileDao provideProfileDao(final Retrofit retrofit, final SoPokerPrefs prefs) {
        return new ProfileDao(retrofit, prefs);
    }

    @Provides
    @Named("service")
    Context provideContext() {
        return mPokeService;
    }
}
