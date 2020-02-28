package com.rushiti.endrit.moviesdiary;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.Stetho;
import com.onesignal.OneSignal;
import com.rushiti.endrit.moviesdiary.db.FilmaDb;
import com.rushiti.endrit.moviesdiary.di.AppComponent;
import com.rushiti.endrit.moviesdiary.di.AppModule;
import com.rushiti.endrit.moviesdiary.di.DaggerAppComponent;
import com.rushiti.endrit.moviesdiary.di.DbModule;

public class ApplicationClass extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        SQLiteDatabase db = new FilmaDb(this, "Filma_db", null, 2)
                .getWritableDatabase();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule(db))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
