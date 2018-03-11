package net.solvetheriddle.sopoker.app.settings;


import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import net.solvetheriddle.sopoker.network.model.AccessToken;

public class SoPokerPrefs {

    private static final String AUTH_TOKEN = "EXTRA_AUTH_TOKEN";
    private static final String AUTH_TOKEN_EXPIRY = "AUTH_TOKEN_EXPIRY";

    private SharedPreferences mSharedPreferences;

    public SoPokerPrefs(final SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public void storeAccessToken(final AccessToken accessToken) {
        Log.i(SoPokerPrefs.class.getCanonicalName(), "Storing " + accessToken);
        mSharedPreferences.edit()
                .putString(AUTH_TOKEN, accessToken.getAccessToken())
                .putInt(AUTH_TOKEN_EXPIRY, accessToken.getExpiry()) // TODO System.currentTimeMillis() +
                .apply();
    }

    @Nullable
    public String getAccessToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
