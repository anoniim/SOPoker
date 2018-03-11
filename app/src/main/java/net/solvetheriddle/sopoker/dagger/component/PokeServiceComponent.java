package net.solvetheriddle.sopoker.dagger.component;

import net.solvetheriddle.sopoker.app.schedule.PokeService;
import net.solvetheriddle.sopoker.dagger.module.PokeServiceModule;
import net.solvetheriddle.sopoker.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {PokeServiceModule.class})
public interface PokeServiceComponent {
    void inject(PokeService service);
}
