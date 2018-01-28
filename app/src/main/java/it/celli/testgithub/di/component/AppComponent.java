package it.celli.testgithub.di.component;

import javax.inject.Singleton;

import dagger.Component;
import it.celli.testgithub.di.module.ContextModule;
import it.celli.testgithub.di.module.NetworkModule;
import it.celli.testgithub.di.module.PicassoModule;
import it.celli.testgithub.view.MainActivity;

@Component(modules = {NetworkModule.class, ContextModule.class, PicassoModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity activity);
}
