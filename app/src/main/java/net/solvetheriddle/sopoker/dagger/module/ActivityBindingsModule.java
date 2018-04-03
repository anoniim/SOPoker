package net.solvetheriddle.sopoker.dagger.module;

import net.solvetheriddle.sopoker.app.profile.ProfileActivity;
import net.solvetheriddle.sopoker.app.schedule.PokeService;
import net.solvetheriddle.sopoker.dagger.scope.PerActivity;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
public abstract class ActivityBindingsModule {

    @PerActivity
    @ContributesAndroidInjector(modules = ProfileScreenModule.class)
    abstract ProfileActivity contributeProfileActivityInjector();

    @ContributesAndroidInjector
    abstract PokeService contributePokeServiceInjector();
}
