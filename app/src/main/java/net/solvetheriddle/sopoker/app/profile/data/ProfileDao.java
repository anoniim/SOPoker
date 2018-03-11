package net.solvetheriddle.sopoker.app.profile.data;


import android.text.TextUtils;

import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.StackExchangeService;
import net.solvetheriddle.sopoker.network.model.UserResponse;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileDao {

    public static final String API_KEY = "2Aiw5tBFMD)xb2TVgS))cg((";
    private static final String SITE = "stackoverflow";

    private Retrofit mRetrofit;
    private SoPokerPrefs mPrefs;

    public ProfileDao(final Retrofit retrofit,
            final SoPokerPrefs prefs) {
        mRetrofit = retrofit;
        mPrefs = prefs;
    }

    public void getProfile(final Observer<? super UserResponse> profileObserver) {
        String accessToken = mPrefs.getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            getStackExchangeService()
                    .getProfile(accessToken, API_KEY, SITE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(profileObserver);
        } else {
            profileObserver.onError(new IllegalAccessException("Missing access token"));
        }
    }

    private StackExchangeService getStackExchangeService() {
        return mRetrofit.create(StackExchangeService.class);
    }
}
