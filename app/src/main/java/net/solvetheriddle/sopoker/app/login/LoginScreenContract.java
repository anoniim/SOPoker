package net.solvetheriddle.sopoker.app.login;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.solvetheriddle.sopoker.network.model.AccessToken;

public interface LoginScreenContract {

    interface View {
        void openAuthenticationActivity(@NonNull String loginUrl);
        void showError(@Nullable String message);
        void log(@StringRes int log);
        void redirectToProfile();
        void clearBackStack();
    }

    interface Presenter {
        void login();
        void autoLogin();
        void authenticationSuccessful(AccessToken accessToken);
        void logout();
    }
}
