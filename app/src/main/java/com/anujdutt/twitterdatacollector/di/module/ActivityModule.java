package com.anujdutt.twitterdatacollector.di.module;

import android.app.Activity;

import com.anujdutt.twitterdatacollector.ui.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;


@Module(subcomponents = { ActivityModule.MainActivitySubComponent.class })
public abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity> provideMainActivityInjectorFactory(MainActivitySubComponent.Builder builder);


    @Subcomponent(modules = {MainActivityModule.class})
    public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

        @Subcomponent.Builder
        public abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        }
    }
}
