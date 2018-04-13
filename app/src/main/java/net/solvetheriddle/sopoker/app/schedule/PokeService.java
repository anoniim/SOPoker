package net.solvetheriddle.sopoker.app.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import net.solvetheriddle.sopoker.domain.PokeController;
import net.solvetheriddle.sopoker.network.model.Attempt;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static net.solvetheriddle.sopoker.SoPokerApp.TAG;

public class PokeService extends JobService {

    @Inject PokeController mPokeController;
    private Disposable pokeJobDisposable;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        Log.i(TAG, "PokeService job triggered");
    }

    @Override
    public boolean onStartJob(final JobParameters params) {

        Log.i(TAG, "Checking if the site has been accessed today");
        pokeJobDisposable = mPokeController.doPoke(false)
                .map(Attempt::getStatus)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseStatus -> {
                            switch (responseStatus) {
                                case Attempt.Status.POKE_SUCCESS:
                                    Log.i(TAG, "One poke closer to Fanatic badge! Nice one!");
                                    finish(params, false);
                                    break;
                                case Attempt.Status.POKE_ERROR:
                                    reportAndFinish(params, null);
                                    break;
                                default:
                                case Attempt.Status.POKE_NOT_NEEDED:
                                    Log.d(TAG, "Poke not needed");
                                    finish(params, false);
                                    break;
                            }
                        },
                        throwable -> reportAndFinish(params, throwable));
        return true;
    }

    void reportAndFinish(final JobParameters params, @Nullable final Throwable throwable) {
        Log.w(TAG, "Poke failed, will try again", throwable);
        onError();
        finish(params, true);
    }

    void finish(final JobParameters params, final boolean reschedule) {
        pokeJobDisposable.dispose();
        jobFinished(params, reschedule);
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.w(TAG, "Poke stopped before it finished, will try again");
        onError();
        pokeJobDisposable.dispose();
        // Reschedule
        return true;
    }

    private void onError() {
        // TODO show notification
    }
}
