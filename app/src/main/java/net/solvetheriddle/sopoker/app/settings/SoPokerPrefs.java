package net.solvetheriddle.sopoker.app.settings;


import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import net.solvetheriddle.sopoker.network.model.AccessToken;

public class SoPokerPrefs {

    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private static final String AUTH_TOKEN_EXPIRY = "AUTH_TOKEN_EXPIRY";

    private SharedPreferences mSharedPreferences;

    public SoPokerPrefs(final SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public void storeAccessToken(final AccessToken accessToken) {
        mSharedPreferences.edit()
                .putString(AUTH_TOKEN, accessToken.getAccessToken())
                .putInt(AUTH_TOKEN_EXPIRY, accessToken.getExpiry())
                .apply();
    }

    @Nullable
    public String getAccessToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }
}
