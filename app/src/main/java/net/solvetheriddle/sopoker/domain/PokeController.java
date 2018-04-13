package net.solvetheriddle.sopoker.domain;

import android.support.annotation.NonNull;

import net.solvetheriddle.sopoker.network.model.Attempt;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokeController {

    @NonNull private DoPokeUseCase mDoPokeUseCase;
    @NonNull private SavePokeUseCase mSavePokeUseCase;

    @Inject
    public PokeController(@NonNull final DoPokeUseCase doPokeUseCase, @NonNull final SavePokeUseCase savePokeUseCase) {
        mDoPokeUseCase = doPokeUseCase;
        mSavePokeUseCase = savePokeUseCase;
    }

    public Observable<Attempt> doPoke(final boolean manual) {
        return getPokeObservable(manual)
                .doOnNext(attempt -> mSavePokeUseCase.save(attempt));
    }

    private Observable<Attempt> getPokeObservable(final boolean manual) {
        if (manual) {
            return mDoPokeUseCase.doPoke();
        } else {
            return mDoPokeUseCase.doPokeIfNeeded();
        }
    }
}
