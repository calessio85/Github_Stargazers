package it.celli.testgithub.di.module;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.celli.testgithub.di.PicassoInstance;

@Module
public class PicassoModule {

    public PicassoModule() {}

    @Provides
    @Singleton
    PicassoInstance providePicassoInstance(Context context) {
        return new PicassoInstance(context);
    }
}