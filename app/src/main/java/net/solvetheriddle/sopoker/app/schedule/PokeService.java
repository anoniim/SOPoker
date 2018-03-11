package net.solvetheriddle.sopoker.app.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import net.solvetheriddle.sopoker.SoPokerApp;
import net.solvetheriddle.sopoker.app.profile.data.ProfileDao;
import net.solvetheriddle.sopoker.dagger.component.DaggerPokeServiceComponent;
import net.solvetheriddle.sopoker.dagger.module.PokeServiceModule;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import javax.inject.Inject;

import rx.Observer;


public class PokeService extends JobService {

    private static final String TAG = PokeService.class.getCanonicalName();

    @Inject ProfileDao mProfileDao;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "PokeService created");

        SoPokerApp application = (SoPokerApp) getApplication();
        DaggerPokeServiceComponent.builder()
                .appComponent(application.getAppComponent())
                .pokeServiceModule(new PokeServiceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.wtf("Marcel", "onStartJob");
        mProfileDao.getProfile(new Observer<UserResponse>() {
            @Override
            public void onCompleted() {
                Log.wtf("Marcel", "AND THAT's IT");
            }

            @Override
            public void onError(final Throwable e) {
                Log.wtf("Marcel", "NOPE", e);
            }

            @Override
            public void onNext(final UserResponse userResponse) {
                Log.wtf("Marcel", "YEAH");
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
