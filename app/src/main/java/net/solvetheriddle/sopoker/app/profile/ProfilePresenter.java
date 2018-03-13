package net.solvetheriddle.sopoker.app.profile;


import android.util.Log;

import net.solvetheriddle.sopoker.app.login.data.LoginDao;
import net.solvetheriddle.sopoker.app.profile.data.ProfileRepository;
import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfilePresenter implements ProfileScreenContract.Presenter {

    private static final String TAG = ProfilePresenter.class.getCanonicalName();
    private final ProfileRepository mProfileRepository;
    private LoginDao mLoginDao;
    private ProfileScreenContract.View mView;
    private PokeScheduler mPokeScheduler;

    @Inject
    ProfilePresenter(
            LoginDao loginDao,
            final ProfileRepository profileRepository,
            PokeScheduler pokeScheduler, ProfileScreenContract.View view) {
        mLoginDao = loginDao;
        mProfileRepository = profileRepository;
        mView = view;
        mPokeScheduler = pokeScheduler;
    }

    @Override
    public void authenticate() {
        mView.startAuthenticationActivity(mLoginDao.getLoginUri());
    }

    @Override
    public void authenticationSuccessful(final AccessToken accessToken) {
        mLoginDao.authenticated(accessToken);
    }

    @Override
    public void schedulePoking() {
        mPokeScheduler.schedule();
    }

    @Override
    public void loadProfile() {
        Log.d(TAG, "Loading profile...");
        mProfileRepository.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.i(TAG, "Profile load failed");
                        if (e instanceof IllegalAccessException) {
                            mView.showAuthenticationError();
                        } else {
                            mView.showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(final User response) {
                        Log.i(TAG, "Profile loaded");
                        mView.showProfile(response);
                    }
                });
    }
}
