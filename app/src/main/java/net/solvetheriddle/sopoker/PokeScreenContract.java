package net.solvetheriddle.sopoker;


import android.net.Uri;

import net.solvetheriddle.sopoker.data.model.Response;

public interface PokeScreenContract {

    interface View {
        void showUsers(Response response);
        void showToken(String token);
        void showError(String message);
        void showComplete();
        void openAuthDialog(Uri authUri);
        void closeAuthDialog();
    }

    interface Presenter {
        void loadUsers(final String user);
        void poke();
        void handleAuthResponse(Uri data);
        void getAccessToken(String url);
    }
}
