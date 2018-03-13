package net.solvetheriddle.sopoker.dagger.module;

import android.content.Context;

import net.solvetheriddle.sopoker.app.login.LoginActivity;
import net.solvetheriddle.sopoker.app.login.LoginScreenContract;
import net.solvetheriddle.sopoker.app.login.data.LoginDao;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;
import net.solvetheriddle.sopoker.network.ResponseParser;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginScreenModule {

    private final LoginActivity mLoginActivity;


    public LoginScreenModule(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    @Provides
    LoginScreenContract.View provideLoginScreenContractView() {
        return mLoginActivity;
    }

    @Provides
    @Named("activity")
    Context provideContext() {
        return mLoginActivity;
    }

    @Provides
    LoginDao provideLoginDao(final SoPokerPrefs prefs) {
        return new LoginDao(prefs);
    }

    @Provides
    ResponseParser provideAccessTokenParser() {
        return new ResponseParser();
    }
}
