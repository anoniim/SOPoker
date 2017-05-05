package net.solvetheriddle.sopoker.data.module;

import net.solvetheriddle.sopoker.PokeScreenContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PokeScreenModule {

    private final PokeScreenContract.View mView;


    public PokeScreenModule(PokeScreenContract.View view) {
        mView = view;
    }

    @Provides
    @Singleton
    PokeScreenContract.View providePokeScreenContractView() {
        return mView;
    }
}
