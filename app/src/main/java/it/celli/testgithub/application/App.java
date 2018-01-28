package it.celli.testgithub.application;


import android.app.Application;
import android.content.Context;

import it.celli.testgithub.di.component.AppComponent;
import it.celli.testgithub.di.component.DaggerAppComponent;
import it.celli.testgithub.di.module.ContextModule;
import it.celli.testgithub.di.module.NetworkModule;
import it.celli.testgithub.di.module.PicassoModule;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .contextModule(new ContextModule(this))
                .picassoModule(new PicassoModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
