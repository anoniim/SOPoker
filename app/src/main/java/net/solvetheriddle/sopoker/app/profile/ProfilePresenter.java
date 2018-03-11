package net.solvetheriddle.sopoker.app.profile;


import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;
import net.solvetheriddle.sopoker.app.settings.SoPokerPrefs;

import javax.inject.Inject;

public class ProfilePresenter implements ProfileScreenContract.Presenter {

    private ProfileScreenContract.View mView;
    private PokeScheduler mPokeScheduler;
    private SoPokerPrefs mPrefs;

    @Inject
    ProfilePresenter(final SoPokerPrefs prefs,
            PokeScheduler pokeScheduler, ProfileScreenContract.View view) {
        mPrefs = prefs;
        mView = view;
        mPokeScheduler = pokeScheduler;
    }

    @Override
    public void schedulePoking() {
        mPokeScheduler.schedule();
    }
}
