package net.solvetheriddle.sopoker.app.profile;


import android.support.annotation.NonNull;

import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.User;

public interface ProfileScreenContract {

    interface View {
        void showProfile(User profile);
        void showError(String message);
        void startAuthenticationActivity(@NonNull String loginUrl);
        void showAuthenticationError();
    }

    interface Presenter {
        void authenticate();
        void authenticationSuccessful(AccessToken accessToken);
        void schedulePoking();
        void loadProfile();
    }
}
