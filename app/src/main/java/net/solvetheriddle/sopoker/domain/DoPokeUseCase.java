package net.solvetheriddle.sopoker.domain;

import android.util.Log;

import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.network.model.Attempt;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DoPokeUseCase {

    private ProfileRepository mProfileRepository;

    @Inject
    public DoPokeUseCase(final ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    public Observable<Attempt> doPokeIfNeeded() {
        Log.d(SoPokerApp.TAG, "doPokeIfNeeded");
        return mProfileRepository.getAttemptsSinceLastMidnight()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<Attempt>, ObservableSource<Attempt>>) attempts -> {
                    if (attempts == null || attempts.isEmpty()) {
                        return mProfileRepository.poke(false);
                    } else {
                        return mProfileRepository.getNotNeededAttemptObservable();
                    }
                })
                .take(1)
                .onErrorResumeNext(mProfileRepository.getErrorAttempt());
    }

    public Observable<Attempt> doPoke() {
        Log.d(SoPokerApp.TAG, "doPoke");
        return mProfileRepository.poke(false)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(mProfileRepository.getErrorAttempt());
    }
}
