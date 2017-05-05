package net.solvetheriddle.sopoker;

import android.app.Application;

import net.solvetheriddle.sopoker.data.component.DaggerPokeScreenComponent;
import net.solvetheriddle.sopoker.data.component.NetComponent;
import net.solvetheriddle.sopoker.data.component.PokeScreenComponent;
import net.solvetheriddle.sopoker.data.module.AppModule;
import net.solvetheriddle.sopoker.data.module.NetModule;
import net.solvetheriddle.sopoker.data.module.PokeScreenModule;


public class SoPokerApp extends Application {

    PokeScreenComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerPokeScreenComponent.builder()
                .appModule(new AppModule(this))
                .pokeScreenModule(new PokeScreenModule())
                .netModule(new NetModule("https://api.stackexchange.com/2.2/"))
                .build();
    }

    public PokeScreenComponent getAppComponent() {
        return mAppComponent;
    }
}
