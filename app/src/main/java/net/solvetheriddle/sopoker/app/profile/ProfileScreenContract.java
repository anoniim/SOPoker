package net.solvetheriddle.sopoker.app.profile;


import net.solvetheriddle.sopoker.network.model.User;

public interface ProfileScreenContract {

    interface View {
        void showProfile(User profile);
    }

    interface Presenter {
        void schedulePoking();
    }
}
