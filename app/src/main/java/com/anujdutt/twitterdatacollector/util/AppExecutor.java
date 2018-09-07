package com.anujdutt.twitterdatacollector.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import androidx.annotation.NonNull;

public class AppExecutor {

    public Executor networkIO;

    public Executor diskIO;

    public Executor workerThread;

    public Executor mainThread;


    public AppExecutor(Executor networkIO, Executor diskIO, Executor workerThread, Executor mainThread) {
        this.networkIO = networkIO;
        this.diskIO = diskIO;
        this.workerThread = workerThread;
        this.mainThread = mainThread;
    }

    public AppExecutor() {
        this(Executors.newFixedThreadPool(3), Executors.newSingleThreadExecutor(), Executors.newCachedThreadPool(new ThreadFactory() {

            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(r);
                t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        }), new MainThreadExecutor());
    }

    private static class MainThreadExecutor implements Executor {

        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
