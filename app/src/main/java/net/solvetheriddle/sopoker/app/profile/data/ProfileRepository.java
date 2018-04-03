package net.solvetheriddle.sopoker.app.profile.data;


import android.arch.lifecycle.LiveData;

import net.solvetheriddle.sopoker.app.profile.data.local.db.AppDatabase;
import net.solvetheriddle.sopoker.app.profile.data.remote.ProfileApi;
import net.solvetheriddle.sopoker.network.model.Attempt;
import net.solvetheriddle.sopoker.network.model.AttemptStatus;
import net.solvetheriddle.sopoker.network.model.User;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ProfileRepository {

    private ProfileApi mProfileApi;
    private AppDatabase mDb;
    private LiveData<List<Attempt>> mAllAttemptsLiveData;

    @Inject
    public ProfileRepository(final AppDatabase db, final ProfileApi profileApi) {
        mDb = db;
        mProfileApi = profileApi;
    }

    public Observable<User> getNewProfile() {
        return mProfileApi.getProfile()
                .doOnNext(this::newAttempt);
    }

    public Observable<Attempt> getLatestSuccessfulAttempt() {
        return mDb.attemptModel().loadLatestSuccessfulAttempt().toObservable();
    }

    private void newAttempt(User user) {
        Attempt attempt = new Attempt(new Date(), AttemptStatus.SUCCESS, user);
        mDb.attemptModel().insertAttempt(attempt);
//        final List<User> currentItems = mAllAttemptsLiveData.getValue();
//        if (currentItems != null) {
//            currentItems.add(attempt);
//        } else {
//            mAllAttemptsLiveData = getAllAttempt();
//        }
//        mAllAttemptsLiveData.setValue(currentItems);
    }

    public LiveData<List<Attempt>> getAllAttempt() {
        mAllAttemptsLiveData = mDb.attemptModel().loadAllAttempts();
        return mAllAttemptsLiveData;
    }
}
