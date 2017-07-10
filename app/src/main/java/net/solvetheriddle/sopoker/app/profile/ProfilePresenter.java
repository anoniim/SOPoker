package net.solvetheriddle.sopoker.app.profile;


import javax.inject.Inject;

public class ProfilePresenter implements ProfileScreenContract.Presenter {

    private ProfileScreenContract.View mView;
    private PokeScheduler mPokeScheduler;

    @Inject
    ProfilePresenter(ProfileScreenContract.View view, PokeScheduler pokeScheduler) {
        mView = view;
        mPokeScheduler = pokeScheduler;
    }

    @Override
    public void schedulePoking() {
        mPokeScheduler.schedule();
    }
}
