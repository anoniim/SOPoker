package net.solvetheriddle.sopoker.app.profile.data;


import android.arch.lifecycle.LiveData;
import android.util.Log;

import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;
import net.solvetheriddle.sopoker.network.model.Attempt;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ProfileRepository {

    private ProfileApi mProfileApi;
    private AppDatabase mDb;

    @Inject
    public ProfileRepository(final AppDatabase db, final ProfileApi profileApi) {
        mDb = db;
        mProfileApi = profileApi;
    }

    public Attempt getNotNeededAttempt() {
        Log.wtf("Marcel", "getNotNeededAttempt");
        return new Attempt(new Date(), Attempt.Status.POKE_NOT_NEEDED, null);
    }

    public Observable<Attempt> getNewProfile(boolean manual) {
        Log.wtf("Marcel", "getNewProfile");
        return mProfileApi.poke(manual);
//                .doOnNext(this::saveAttempt);
    }

    public Observable<Attempt> getLatestSuccessfulAttempt() {
        return mDb.attemptModel().loadLatestSuccessfulAttempt().toObservable();
    }

    public void saveAttempt(Attempt attempt) {
        Log.wtf("Marcel", "saveAttempt: " + attempt);
        mDb.attemptModel().insertAttempt(attempt);
    }

    public LiveData<List<Attempt>> getAllAttempt() {
        return mDb.attemptModel().loadAllAttempts();
    }

    public Observable<List<Attempt>> getTodaysAttempts() {
        final Calendar now = new GregorianCalendar();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return mDb.attemptModel().loadAttemptsFrom(now.getTimeInMillis()).toObservable();
    }

    public Attempt newAttempt(final boolean manual) {
        return new Attempt(new Date(), manual);
    }
}
