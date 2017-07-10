package net.solvetheriddle.sopoker.app.login;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import net.solvetheriddle.sopoker.R;
import net.solvetheriddle.sopoker.app.login.data.LoginDao;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;
import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import javax.inject.Inject;

import rx.Observer;

public class LoginPresenter implements LoginScreenContract.Presenter {

    @NonNull private final Observer<UserResponse> mProfileObserver = new ProfileObserver();
    @NonNull private final LoginScreenContract.View mView;
    @NonNull private final SoPokerPrefs mPrefs;
    @NonNull private final LoginDao mLoginDao;
    @NonNull private final ResponseParser mResponseParser;

    @Inject
    LoginPresenter(@NonNull LoginDao loginDao, @NonNull ResponseParser responseParser, @NonNull SoPokerPrefs prefs,
            @NonNull LoginScreenContract.View view) {
        mLoginDao = loginDao;
        mResponseParser = responseParser;
        mPrefs = prefs;
        mView = view;
    }

    @Override
    public void login() {
        String loginUrl = mLoginDao.getLoginUri().toString();
        mView.openAuthDialog(loginUrl);
    }

    @Override
    public void parseAccessToken(final String url) { // TODO Rx from WebView to Presenter?
        if (url != null) {
            AccessToken accessToken = mResponseParser.parseAccessToken(url);
            if (accessToken != null) {
                mPrefs.storeAccessToken(accessToken);
                mView.closeAuthDialog();

                // TODO Rx to chain events?
                getProfile();
            } else {
                // URL does not contain access token
                mView.log(R.string.login_log_in_progress);
            }
        } else {
            mView.showError("Failed to parse access token from URL");
        }
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(mPrefs.getAccessToken());
    }

    @Override
    public void getProfile() {
        mView.log(R.string.login_log_getting_profile);
        String accessToken = mPrefs.getAccessToken();
        mLoginDao.getProfile(accessToken, mProfileObserver);
    }

    @Override
    public void autoLogin() {
        if (isLoggedIn()) {
            mView.log(R.string.login_log_already_logged_in);
            getProfile();
        }
    }

    private class ProfileObserver implements Observer<UserResponse> {
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
    }
}
