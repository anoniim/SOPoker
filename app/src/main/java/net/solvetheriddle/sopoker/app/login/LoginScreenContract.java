package net.solvetheriddle.sopoker.app.login;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.solvetheriddle.sopoker.network.model.User;

public interface LoginScreenContract {

    interface View {
        void openAuthDialog(@NonNull String loginUrl);
        void closeAuthDialog();
        void showError(@Nullable String message);
        void logProfile(@Nullable User response);
        void log(@StringRes int log);
        void redirectToProfile(final User profile);
    }

    interface Presenter {
        void login();
        void parseAccessToken(@Nullable String url);
    }
}
