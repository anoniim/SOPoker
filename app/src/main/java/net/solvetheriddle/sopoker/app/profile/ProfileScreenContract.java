package net.solvetheriddle.sopoker.app.profile;


import net.solvetheriddle.sopoker.network.model.AccessToken;
import net.solvetheriddle.sopoker.network.model.Attempt;
import net.solvetheriddle.sopoker.network.model.User;

import io.reactivex.Observable;

public interface ProfileScreenContract {

    interface Presenter {
        String getLoginUrl();
        void authenticationSuccessful(AccessToken accessToken);
        void schedulePoking();
        Observable<Attempt> loadProfile();
        Observable<User> getLatestProfile();
    }
}
