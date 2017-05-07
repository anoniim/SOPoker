package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.app.profile.ProfileScreenContract;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

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
    @Named("activity")
    Context provideContext() {
        return mProfileActivity;
    }
}
