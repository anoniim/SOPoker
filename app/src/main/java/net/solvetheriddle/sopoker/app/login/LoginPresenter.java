package net.solvetheriddle.sopoker.app.login;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import net.solvetheriddle.sopoker.app.login.data.LoginDao;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.model.AccessToken;

import javax.inject.Inject;

public class LoginPresenter implements LoginScreenContract.Presenter {

    private static final String TAG = LoginPresenter.class.getCanonicalName();

    @NonNull private final LoginScreenContract.View mView;
    @NonNull private final SoPokerPrefs mPrefs;
    @NonNull private final LoginDao mLoginDao;

    @Inject
    LoginPresenter(@NonNull LoginDao loginDao,
            @NonNull SoPokerPrefs prefs,
            @NonNull LoginScreenContract.View view) {
        mLoginDao = loginDao;
        mPrefs = prefs;
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
            Log.i(TAG, "... access token available");
            mView.redirectToProfile();
        } else {
            Log.i(TAG, "... access token not available");
        }
    }

    private boolean isLoggedIn() {
        return !TextUtils.isEmpty(mPrefs.getAccessToken());
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
