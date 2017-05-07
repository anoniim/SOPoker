package net.solvetheriddle.sopoker.app.login;


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

    private final Observer<UserResponse> mProfileObserver = new ProfileObserver();
    private LoginScreenContract.View mView;
    private SoPokerPrefs mPrefs;
    private LoginDao mLoginDao;
    private ResponseParser mResponseParser;

    @Inject
    LoginPresenter(LoginDao loginDao, ResponseParser responseParser, SoPokerPrefs prefs,
            LoginScreenContract.View view) {
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

    private void getProfile() {
        mView.log(R.string.login_log_getting_profile);
        String accessToken = mPrefs.getAccessToken();
        mLoginDao.getProfile(accessToken, mProfileObserver);
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
