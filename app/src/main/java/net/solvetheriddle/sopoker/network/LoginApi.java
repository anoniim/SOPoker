package net.solvetheriddle.sopoker.network;


import android.net.Uri;

import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.model.AccessToken;

import javax.inject.Inject;

public class LoginApi {

    public static final String API_OAUTH_REDIRECT = "https://sopoker.solvetheriddle.net";
    //    private static final String API_OAUTH_REDIRECT = "https://stackexchange.com/oauth/login_success";
    public static final String EXPLICIT_AUTH_URL = "http://sopoker.solvetheriddle.net";
    public static final String SO_POKER_CLIENT_ID = "9347"; // TODO Move to build.gradle config
    public static final String SO_POKER_AUTH_SCOPE = "private_info no_expiry";
    public static final String IMPLICIT_AUTH_URL = "https://stackexchange.com/oauth/dialog";
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String REDIRECT_URI = "redirect_uri";

    private SoPokerPrefs mPrefs;

    @Inject
    public LoginApi(final SoPokerPrefs prefs) {
        mPrefs = prefs;
    }


    public String getLoginUri() {
        return getImplicitUri().toString();
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

    public void authenticated(final AccessToken accessToken) {
        mPrefs.storeAccessToken(accessToken);
    }
}
