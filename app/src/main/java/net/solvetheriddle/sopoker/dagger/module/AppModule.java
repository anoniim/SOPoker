package net.solvetheriddle.sopoker.dagger.module;


import android.app.Application;
import android.content.Context;

import net.solvetheriddle.sopoker.app.schedule.PokeScheduler;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides // could use @Bind, that returns injected parameter
    @Singleton
    @Named("application")
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    PokeScheduler providePokeScheduler(@Named("application") final Context context) {
        return new PokeScheduler(context);
    }
}
