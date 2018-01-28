package it.celli.testgithub.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.celli.testgithub.di.RetrofitInstance;

@Module
public class NetworkModule {

    public NetworkModule() {}

    @Provides
    @Singleton
    RetrofitInstance provideRetrofitInstance() {
        return new RetrofitInstance();
    }
}
