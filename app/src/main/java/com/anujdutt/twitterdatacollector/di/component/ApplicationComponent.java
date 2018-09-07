package com.anujdutt.twitterdatacollector.di.component;

import com.anujdutt.twitterdatacollector.application.TwitterDataCollectorApplication;
import com.anujdutt.twitterdatacollector.di.module.ActivityModule;
import com.anujdutt.twitterdatacollector.di.module.ApiServiceModule;
import com.anujdutt.twitterdatacollector.di.module.AppModule;
import com.anujdutt.twitterdatacollector.di.module.DaoModule;
import com.anujdutt.twitterdatacollector.di.module.FragmentModule;
import com.anujdutt.twitterdatacollector.di.module.NetworkModule;
import com.anujdutt.twitterdatacollector.di.module.RepositoryModule;
import com.anujdutt.twitterdatacollector.di.module.ViewModelProviderModule;
import com.anujdutt.twitterdatacollector.di.scope.TwitterDataCollectorScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@TwitterDataCollectorScope
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        AppModule.class,
        ApiServiceModule.class,
        ViewModelProviderModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        DaoModule.class
})
public interface ApplicationComponent {

    void inject(TwitterDataCollectorApplication application);

    @Component.Builder
    public interface Builder {

        @BindsInstance
        Builder twitterDataCollectorApplication(TwitterDataCollectorApplication application);

        ApplicationComponent build();
    }
}
