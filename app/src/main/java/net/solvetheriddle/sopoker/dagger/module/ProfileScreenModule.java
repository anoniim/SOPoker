package net.solvetheriddle.sopoker.dagger.module;

import android.app.Application;

import net.solvetheriddle.sopoker.app.profile.ProfilePresenter;
import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.app.schedule.PokeUseCase;
import net.solvetheriddle.sopoker.network.LoginApi;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ProfileScreenModule {

    @Provides
    static ProfilePresenter.Factory bindViewModelFactory(final Application application,
            final ProfileRepository profileRepository,
            final LoginApi loginApi,
            final PokeScheduler pokeScheduler,
            final PokeUseCase pokeUseCase) {
        return new ProfilePresenter.Factory(application, profileRepository, loginApi, pokeScheduler, pokeUseCase);
    }
}
