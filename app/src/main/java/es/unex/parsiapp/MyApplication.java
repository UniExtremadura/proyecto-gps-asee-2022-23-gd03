package es.unex.parsiapp;

import android.app.Application;

import es.unex.parsiapp.util.AppContainer;

public class MyApplication extends Application {
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(this);
    }
}
