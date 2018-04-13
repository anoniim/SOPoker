package net.solvetheriddle.sopoker.domain;

import android.support.annotation.NonNull;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.network.model.Attempt;

import javax.inject.Inject;

public class SavePokeUseCase {

    @NonNull private ProfileRepository mProfileRepository;

    @Inject
    public SavePokeUseCase(@NonNull final ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    public void save(final Attempt attempt) {
        mProfileRepository.saveAttempt(attempt);
    }
}
