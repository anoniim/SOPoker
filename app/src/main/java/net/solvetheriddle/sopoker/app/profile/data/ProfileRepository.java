package net.solvetheriddle.sopoker.app.profile.data;


import net.solvetheriddle.sopoker.network.model.User;

import javax.inject.Inject;

import rx.Observable;

public class ProfileRepository {

    private ProfileDao mProfileDao;

    @Inject
    public ProfileRepository(final ProfileDao profileDao) {
        mProfileDao = profileDao;
    }

    public Observable<User> getProfile() {
        return mProfileDao.getProfile();
    }
}
