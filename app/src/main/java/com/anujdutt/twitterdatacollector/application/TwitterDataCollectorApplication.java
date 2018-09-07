package com.anujdutt.twitterdatacollector.application;

import android.app.Activity;
import android.app.Application;

import com.anujdutt.twitterdatacollector.di.component.ApplicationComponent;
import com.anujdutt.twitterdatacollector.di.component.DaggerApplicationComponent;
import com.twitter.sdk.android.core.Twitter;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * A custom application class to initialise application level parameters.
 */
public class TwitterDataCollectorApplication extends Application  implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityAndroidInjector;

    ApplicationComponent applicationComponent;

    static TwitterDataCollectorApplication twitterDataCollectorApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
        twitterDataCollectorApplication = this;

        applicationComponent =
                DaggerApplicationComponent.builder().twitterDataCollectorApplication(this).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static TwitterDataCollectorApplication getInstance() {
        return twitterDataCollectorApplication;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityAndroidInjector;
    }
}
