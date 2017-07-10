package net.solvetheriddle.sopoker.app.login.data;


import android.net.Uri;
import android.text.TextUtils;

import net.solvetheriddle.sopoker.network.StackExchangeService;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginDao {

    //    private static final String API_OAUTH_REDIRECT = "http://sopoker.solvetheriddle.net";
    private static final String API_OAUTH_REDIRECT = "https://stackexchange.com/oauth/login_success";
    private static final String EXPLICIT_AUTH_URL = "http://sopoker.solvetheriddle.net";
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String SO_POKER_CLIENT_ID = "9347"; // TODO Move to build.gradle config
    private static final String SO_POKER_AUTH_SCOPE = "private_info no_expiry";
    private static final String IMPLICIT_AUTH_URL = "https://stackexchange.com/oauth/dialog";
    private static final String SITE = "stackoverflow";
    private static final String KEY = "2Aiw5tBFMD)xb2TVgS))cg((";

    private Retrofit mRetrofit;

    public LoginDao(final Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public void getProfile(final String accessToken, final Observer<? super UserResponse> profileObserver) {
        if (!TextUtils.isEmpty(accessToken)) {
            getStackExchangeService()
                    .getProfile(accessToken, KEY, SITE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(profileObserver);
        } else {
            profileObserver.onError(new IllegalAccessException("Invalid access token"));
        }
    }

    private StackExchangeService getStackExchangeService() {
        return mRetrofit.create(StackExchangeService.class);
    }

    public Uri getLoginUri() {
        return getImplicitUri();
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
}
