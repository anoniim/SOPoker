package net.solvetheriddle.sopoker.data.component;


import retrofit2.Retrofit;

//@Singleton
//@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
