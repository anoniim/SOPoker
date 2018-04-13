package net.solvetheriddle.sopoker.app.profile.data;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
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

import static net.solvetheriddle.sopoker.SoPokerApp.TAG;

public class ProfileRepository {

    private ProfileApi mProfileApi;
    private AppDatabase mDb;

    @Inject
    public ProfileRepository(final AppDatabase db, final ProfileApi profileApi) {
        mDb = db;
        mProfileApi = profileApi;
    }

    public Observable<Attempt> poke(boolean manual) {
        Log.i(TAG, "Poking! (Making request to the server)");
        return mProfileApi.getNewProfile(new Attempt(new Date(), manual));
    }

    public void saveAttempt(Attempt attempt) {
        Log.i(TAG, "Saving poke attempt: " + attempt);
        mDb.attemptModel().insertAttempt(attempt);
    }

    public Observable<Attempt> getLatestSuccessfulAttempt() {
        return mDb.attemptModel().loadLatestSuccessfulAttempt().toObservable();
    }

    public LiveData<List<Attempt>> getAllAttempt() {
        return mDb.attemptModel().loadAllAttempts();
    }

    public Observable<List<Attempt>> getAttemptsSinceLastMidnight() {
        final Calendar lastMidnight = new GregorianCalendar();
        lastMidnight.set(Calendar.HOUR_OF_DAY, 0);
        lastMidnight.set(Calendar.MINUTE, 0);
        lastMidnight.set(Calendar.SECOND, 0);
        return mDb.attemptModel().loadAttemptsFrom(lastMidnight.getTimeInMillis()).toObservable();
    }

    public Attempt getNotNeededAttempt() {
        return new Attempt(now(), Attempt.Status.POKE_NOT_NEEDED, null);
    }

    public Observable<Attempt> getNotNeededAttemptObservable() {
        return Observable.just(new Attempt(now(), Attempt.Status.POKE_NOT_NEEDED, null));
    }

    public Observable<Attempt> getErrorAttempt() {
        return Observable.just(new Attempt(now(), Attempt.Status.POKE_ERROR, null));
    }

    @NonNull
    private Date now() {
        return new Date();
    }
}
