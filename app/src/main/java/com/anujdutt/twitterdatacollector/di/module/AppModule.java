package com.anujdutt.twitterdatacollector.di.module;


import android.content.Context;

import com.anujdutt.twitterdatacollector.application.TwitterDataCollectorApplication;
import com.anujdutt.twitterdatacollector.db.TwitterDataCollectorDatabase;
import com.anujdutt.twitterdatacollector.di.scope.TwitterDataCollectorScope;
import com.anujdutt.twitterdatacollector.util.AppExecutor;

import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    TwitterDataCollectorDatabase twitterDataCollectorDatabase;

    @TwitterDataCollectorScope
    @Provides
    @Named("ApplicationContext")
    public Context getTwitterDataCollectorApplication(TwitterDataCollectorApplication twitterDataCollectorApplication) {
        return twitterDataCollectorApplication.getApplicationContext();
    }

    @TwitterDataCollectorScope
    @Provides
    TwitterDataCollectorDatabase twitterDataCollectorDatabase(final TwitterDataCollectorApplication twitterDataCollectorApplication, final AppExecutor appExecutor) {
        twitterDataCollectorDatabase = Room.databaseBuilder(twitterDataCollectorApplication, TwitterDataCollectorDatabase.class, "TwitterDataCollector.db").addCallback(new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                super.onCreate(db);


            }
        }).build();
        return twitterDataCollectorDatabase;
    }

    @TwitterDataCollectorScope
    @Provides
    AppExecutor appExecutor() {
        return new AppExecutor();
    }
}
