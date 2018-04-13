package net.solvetheriddle.sopoker.dagger.module;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.domain.DoPokeUseCase;
import net.solvetheriddle.sopoker.domain.PokeController;
import net.solvetheriddle.sopoker.domain.SavePokeUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class PokeModule {

    @Provides
    PokeController providePokeController(final DoPokeUseCase doPokeUseCase, final SavePokeUseCase savePokeUseCase) {
        return new PokeController(doPokeUseCase, savePokeUseCase);
    }

    @Provides
    SavePokeUseCase provideSavePokeUseCase(
            final ProfileRepository profileRepository) {
        return new SavePokeUseCase(profileRepository);
    }

    @Provides
    DoPokeUseCase providePokeUseCase(final ProfileRepository profileRepository) {
        return new DoPokeUseCase(profileRepository);
    }
}
