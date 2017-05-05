package net.solvetheriddle.sopoker;


import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import net.solvetheriddle.sopoker.data.StackExchangeService;
import net.solvetheriddle.sopoker.data.model.Response;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class PokePresenter implements PokeScreenContract.Presenter {

    //    public static final String API_OAUTH_REDIRECT = "http://sopoker.solvetheriddle.net";
    public static final String API_OAUTH_REDIRECT = "https://stackexchange.com/oauth/login_success";
    public static final String EXPLICIT_AUTH_URL = "http://sopoker.solvetheriddle.net";
    public static final String CLIENT_ID = "client_id";
    public static final String SCOPE = "scope";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String SO_POKER_CLIENT_ID = "9347"; // TODO Move to build.gradle config
    public static final String SO_POKER_AUTH_SCOPE = "private_info";
    public static final String IMPLICIT_AUTH_URL = "https://stackexchange.com/oauth/dialog";

//    @Inject
    private Retrofit mRetrofit;
//    @Inject
    private PokeScreenContract.View mView;
//    @Inject // TODO Find out how to inject
    SharedPreferences mPrefs;

    private final Observer<Response> mUsersObserver = new UsersObserver();

    @Inject
    PokePresenter(Retrofit retrofit, SharedPreferences prefs, PokeScreenContract.View view) {
        mRetrofit = retrofit;
        mPrefs = prefs;
        mView = view;

        mPrefs.edit();
    }

    @Override
    public void loadUsers(final String user) {
        getStackExchangeService()
                .getUser("stackoverflow", user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(mUsersObserver);
    }

    private StackExchangeService getStackExchangeService() {
        return mRetrofit.create(StackExchangeService.class);
    }

    @Override
    public void poke() {
        Uri authUri = getImplicitUri();
        mView.openAuthDialog(authUri);
    }

    private Uri getExplicitUri() {
        return Uri.parse(EXPLICIT_AUTH_URL)
                .buildUpon()
                .appendQueryParameter(CLIENT_ID, SO_POKER_CLIENT_ID)
                .appendQueryParameter(SCOPE, SO_POKER_AUTH_SCOPE)
                .appendQueryParameter(REDIRECT_URI, API_OAUTH_REDIRECT)
                .build();
    }

    private Uri getImplicitUri() {
        return Uri.parse(IMPLICIT_AUTH_URL)
                .buildUpon()
                .appendQueryParameter(CLIENT_ID, SO_POKER_CLIENT_ID)
                .appendQueryParameter(SCOPE, SO_POKER_AUTH_SCOPE)
                .appendQueryParameter(REDIRECT_URI, API_OAUTH_REDIRECT)
                .build();
    }

    @Override
    public void handleAuthResponse(final Uri data) {
        if (data != null && data.toString().startsWith(API_OAUTH_REDIRECT)) {
            String code = data.getQueryParameter("code");
            if (code != null) {
                mView.showToken(code);
            } else {
                Log.wtf("Marcel", "handleAuthResponse: code null");
            }
        } else {
            Log.wtf("Marcel", "handleAuthResponse: no interesting data");
        }
    }

    @Override
    public void getAccessToken(final String url) { // TODO Rx from WebView to Presenter?
        if (url.contains("access_token=")) {
            String[] parameters = Uri.parse(url).getFragment().split("&");
            if (parameters.length > 0) {
                String accessToken = getParameterValue(parameters[0]);
                if (!TextUtils.isEmpty(accessToken)) {
                    int expiry = Integer.valueOf(getParameterValue(parameters[1]));
                    storeToken(accessToken, expiry);
                    mView.closeAuthDialog();
                }
            }
        }
    }

    private void storeToken(final String accessToken, final int expiry) { // TODO Injected Prefs class
        mPrefs.edit()
                .putString("AUTH_TOKEN", accessToken)
                .putInt("AUTH_TOKEN_EXPIRY", expiry)
                .apply();
    }

    private String getParameterValue(@NonNull final String keyValue) {
//        return keyValue.split("=")[1];
        return keyValue.substring(keyValue.indexOf("=") + 1);
    }

    private class UsersObserver implements Observer<Response> {
        @Override
        public void onCompleted() {
            mView.showComplete();
        }

        @Override
        public void onError(final Throwable e) {
            mView.showError(e.getMessage());
        }

        @Override
        public void onNext(final Response response) {
            mView.showUsers(response);
        }
    }
}
