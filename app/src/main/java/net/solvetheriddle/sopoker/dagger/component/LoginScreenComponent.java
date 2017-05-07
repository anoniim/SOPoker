package net.solvetheriddle.sopoker.dagger.component;

import net.solvetheriddle.sopoker.app.login.LoginActivity;
import net.solvetheriddle.sopoker.dagger.module.LoginScreenModule;
import net.solvetheriddle.sopoker.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {LoginScreenModule.class})
public interface LoginScreenComponent {
    void inject(LoginActivity activity);
}
