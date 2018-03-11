package net.solvetheriddle.sopoker.app.login;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.app.login.data.LoginDao;
import net.solvetheriddle.sopoker.app.profile.data.ProfileDao;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import javax.inject.Inject;

import rx.Observer;

public class LoginPresenter implements LoginScreenContract.Presenter {

    private static final String TAG = LoginPresenter.class.getCanonicalName();

    @NonNull private final LoginScreenContract.View mView;
    @NonNull private final SoPokerPrefs mPrefs;
    @NonNull private final LoginDao mLoginDao;
    @NonNull private final ProfileDao mProfileDao;
    @NonNull private final ResponseParser mResponseParser;

    @Inject
    LoginPresenter(@NonNull LoginDao loginDao, @NonNull ResponseParser responseParser,
            @NonNull SoPokerPrefs prefs,
            @NonNull final ProfileDao profileDao,
            @NonNull LoginScreenContract.View view) {
        mLoginDao = loginDao;
        mResponseParser = responseParser;
        mPrefs = prefs;
        mProfileDao = profileDao;
        mView = view;
    }

    @Override
    public void login() {
        String loginUrl = mLoginDao.getLoginUri().toString();
        mView.openAuthenticationActivity(loginUrl);
    }

    @Override
    public void autoLogin() {
        Log.i(TAG, "Attempting auto login...");
        if (isLoggedIn()) {
            mView.log(R.string.login_log_already_logged_in);
            Log.i(TAG, "... access token available");
            getProfile();
        } else {
            Log.i(TAG, "... access token not available");
        }
    }

    private boolean isLoggedIn() {
        return !TextUtils.isEmpty(mPrefs.getAccessToken());
    }

    @Override
    public void getProfile() {
        mView.log(R.string.login_log_getting_profile);
        mProfileDao.getProfile(new Observer<UserResponse>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(final Throwable e) {
                mView.showError(e.getMessage());
            }

            @Override
            public void onNext(final UserResponse response) {
                User profile = mResponseParser.parseUser(response);
                mView.logProfile(profile);
                mView.redirectToProfile(profile);
            }
        });
    }

    @Override
    public void authenticationSuccessful(final AccessToken accessToken) {
        mPrefs.storeAccessToken(accessToken);
    }

    @Override
    public void logout() {
        mPrefs.clear();
        mView.clearBackStack();
    }
}
