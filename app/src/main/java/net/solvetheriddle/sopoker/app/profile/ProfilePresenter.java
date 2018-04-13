package net.solvetheriddle.sopoker.app.profile;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.domain.PokeController;
import net.solvetheriddle.sopoker.network.LoginApi;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.Attempt;
import net.solvetheriddle.sopoker.network.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends AndroidViewModel implements ProfileScreenContract.Presenter {

    private static final String TAG = ProfilePresenter.class.getCanonicalName();

    private ProfileRepository mProfileRepository;
    private LoginApi mLoginApi;
    private PokeScheduler mPokeScheduler;

    private LiveData<List<Attempt>> attemptsList;
    private PokeController mPokeController;

    @Inject
    ProfilePresenter(final Application application,
            final ProfileRepository profileRepository,
            final LoginApi loginApi,
            final PokeScheduler pokeScheduler,
            final PokeController pokeController) {
        super(application);
        mProfileRepository = profileRepository;
        mLoginApi = loginApi;
        mPokeScheduler = pokeScheduler;
        mPokeController = pokeController;

        attemptsList = mProfileRepository.getAllAttempt();
    }

    @Override
    public String getLoginUrl() {
        return mLoginApi.getLoginUri();
    }

    @Override
    public void authenticationSuccessful(final AccessToken accessToken) {
        mLoginApi.authenticated(accessToken);
    }

    @Override
    public void schedulePoking() {
        mPokeController.doPoke(true).subscribe();
//        mPokeScheduler.schedule();
    }

    @Override
    public Observable<Attempt> loadProfile() {
        return mProfileRepository.poke(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> getLatestProfile() {
        return mProfileRepository.getLatestSuccessfulAttempt()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Attempt::getUser)
                .unsubscribeOn(Schedulers.io());

    }

    public LiveData<List<Attempt>> getAllAttempts() {
        return attemptsList;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Application application;
        private ProfileRepository profileRepository;
        private LoginApi mLoginApi;
        private PokeScheduler pokeScheduler;
        private PokeController mPokeController;

        public Factory(final Application application,
                final ProfileRepository profileRepository,
                final LoginApi loginApi,
                final PokeScheduler pokeScheduler,
                final PokeController pokeController) {
            this.application = application;
            this.profileRepository = profileRepository;
            this.mLoginApi = loginApi;
            this.pokeScheduler = pokeScheduler;
            this.mPokeController = pokeController;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public ProfilePresenter create(@NonNull final Class modelClass) {
            return new ProfilePresenter(application, profileRepository, mLoginApi, pokeScheduler, mPokeController);
        }
    }
}
