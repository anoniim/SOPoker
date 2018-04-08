package net.solvetheriddle.sopoker.app.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import net.solvetheriddle.sopoker.network.model.Attempt;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static net.solvetheriddle.sopoker.app.schedule.PokeUseCase.TAG;

public class PokeService extends JobService {

    @Inject PokeUseCase mPokeUseCase;
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
        pokeJobDisposable = mPokeUseCase.doPoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attempt -> {
                            final int responseStatus = attempt.getStatus();
                            switch (responseStatus) {
                                case Attempt.Status.POKE_SUCCESS:
                                    Log.i(TAG, "One poke closer to Fanatic badge! Nice one!");
                                    finish(params, false);
                                    break;
                                case Attempt.Status.POKE_ERROR:
                                    reportAndFinish(params, new Throwable("Poke request failed"));
                                    break;
                                default:
                                case Attempt.Status.POKE_NOT_NEEDED:
                                    Log.d(TAG, "Poke not needed");
//                                    mPokeUseCase.saveAttempt(attempt);
                                    finish(params, false);
                                    break;
                            }
                        },
                        throwable -> reportAndFinish(params, throwable));
        return true;
    }

    void reportAndFinish(final JobParameters params, final Throwable e) {
        Log.w(TAG, "Failed to connect to the server, will try again soon", e);
        finish(params, true);
    }

    void finish(final JobParameters params, final boolean reschedule) {
        pokeJobDisposable.dispose();
        jobFinished(params, reschedule);
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.wtf("Marcel", "onStopJob");
        pokeJobDisposable.dispose();
        return false;
    }
}
