package net.solvetheriddle.sopoker.app.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;
import net.solvetheriddle.sopoker.network.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PokeService extends JobService {

    private static final String TAG = PokeService.class.getCanonicalName();

    @Inject ProfileApi mProfileApi;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        Log.i(TAG, "PokeService created");

//        SoPokerApp application = (SoPokerApp) getApplication();
//        DaggerPokeServiceComponent.builder()
//                .appComponent(application.getAppComponent())
//                .pokeServiceModule(new PokeServiceModule(this))
//                .build()
//                .inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.wtf("Marcel", "onStartJob");
        mProfileApi.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onError(final Throwable e) {
                        Log.wtf("Marcel", "NOPE", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.wtf("Marcel", "AND THAT's IT");
                    }

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.wtf("Marcel", "OnSubscribe in PokeService");
                    }

                    @Override
                    public void onNext(final User userResponse) {
                        Log.wtf("Marcel", "YEAH");
                        final Date now = new Date();
                        final Calendar calendar = new GregorianCalendar();
                        calendar.setTime(now);
                        calendar.roll(Calendar.MINUTE, 30);

                        Log.i(TAG, "JOB TRIGGERED at " + now);
                        Log.i(TAG, ".. next one should come up at " + calendar.getTime());
                        jobFinished(params, false);
                    }
                });
        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.wtf("Marcel", "onStopJob");
        return false;
    }
}
