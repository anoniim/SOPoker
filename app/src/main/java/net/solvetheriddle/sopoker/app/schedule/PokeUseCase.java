package net.solvetheriddle.sopoker.app.schedule;

import android.util.Log;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.network.model.Attempt;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PokeUseCase {

    public static final String TAG = "StackOverflow Poker";

    private ProfileRepository mProfileRepository;

    @Inject
    public PokeUseCase(final ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    public Observable<Attempt> doPoke() {
        Log.wtf("Marcel", "doPoke");
        return mProfileRepository.getTodaysAttempts()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
//                .filter(this::noAttempts)
                .map(attempts -> mProfileRepository.getNotNeededAttempt())
                .switchIfEmpty(attempt -> mProfileRepository.getNewProfile(false))
                .doOnNext(attempt -> mProfileRepository.saveAttempt(attempt));
    }

    private boolean noAttempts(final List<Attempt> attempts) {
        final boolean needToPoke = attempts == null || attempts.isEmpty();
        Log.i(TAG, needToPoke
                   ? "- looks like it hasn't, will poke now"
                   : "- looks like it has, no need to do anything");
//                    if (!needToPoke) { pokeJobDisposable.dispose(); }
        return needToPoke;
    }

}
