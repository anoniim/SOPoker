package net.solvetheriddle.sopoker.dagger.module;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.schedule.PokeUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    PokeUseCase providePokeUseCase(final ProfileRepository profileRepository) {
        return new PokeUseCase(profileRepository);
    }
}
