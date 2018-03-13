package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.app.profile.ProfileScreenContract;
import net.solvetheriddle.sopoker.app.profile.data.ProfileDao;
import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ProfileScreenModule {

    private final ProfileActivity mProfileActivity;

    public ProfileScreenModule(ProfileActivity profileActivity) {
        mProfileActivity = profileActivity;
    }

    @Provides
    ProfileScreenContract.View provideLoginScreenContractView() {
        return mProfileActivity;
    }

    @Provides
    PokeScheduler providePokeScheduler() {
        return new PokeScheduler(mProfileActivity);
    }

    @Provides
    ProfileRepository provideProfileRepository(final ProfileDao profileDao) {
        return new ProfileRepository(profileDao);
    }

    @Provides
    ProfileDao provideProfileDao(final Retrofit retrofit,
            final SoPokerPrefs prefs,
            final ResponseParser responseParser) {
        return new ProfileDao(retrofit, prefs, responseParser);
    }

    @Provides
    @Named("activity")
    Context provideContext() {
        return mProfileActivity;
    }
}
