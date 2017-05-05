package net.solvetheriddle.sopoker.data.component;

import net.solvetheriddle.sopoker.PokeActivity;
import net.solvetheriddle.sopoker.data.module.AppModule;
import net.solvetheriddle.sopoker.data.module.NetModule;
import net.solvetheriddle.sopoker.data.module.PokeScreenModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component (modules = {PokeScreenModule.class, AppModule.class, NetModule.class})
public interface PokeScreenComponent {
    void inject(PokeActivity activity);
    Retrofit retrofit();
}
