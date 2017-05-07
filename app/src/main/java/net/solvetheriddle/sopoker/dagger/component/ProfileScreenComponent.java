package net.solvetheriddle.sopoker.dagger.component;

import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.dagger.module.ProfileScreenModule;
import net.solvetheriddle.sopoker.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {ProfileScreenModule.class})
public interface ProfileScreenComponent {
    void inject(ProfileActivity activity);
}
