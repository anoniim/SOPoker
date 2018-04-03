package net.solvetheriddle.sopoker.app.profile.data.remote;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;
import net.solvetheriddle.sopoker.network.StackExchangeService;
import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class ProfileApi {

    private static final String API_KEY = "2Aiw5tBFMD)xb2TVgS))cg((";
    private static final String SITE = "stackoverflow";

    private Retrofit mRetrofit;
    private SoPokerPrefs mPrefs;
    private ResponseParser mResponseParser;

    @Inject
    public ProfileApi(final Retrofit retrofit,
            final SoPokerPrefs prefs,
            final ResponseParser responseParser) {
        mRetrofit = retrofit;
        mPrefs = prefs;
        mResponseParser = responseParser;
    }

    @NonNull
    public Observable<User> getProfile() {
        String accessToken = mPrefs.getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            return getStackExchangeService()
                    .getProfile(accessToken, API_KEY, SITE)
                    .map(userResponse -> mResponseParser.parseUser(userResponse));
        } else {
            return Observable.error(new IllegalAccessException("Missing access token"));
        }
    }

    private StackExchangeService getStackExchangeService() {
        return mRetrofit.create(StackExchangeService.class);
    }
}
