package com.example.mytestweatherapp.app;

import android.app.Application;
import androidx.annotation.NonNull;



public class TestWeatherApp extends Application {

    private static TestWeatherApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @NonNull
    public static TestWeatherApp getAppContext() {
        return sInstance;
    }
}
