package com.rushiti.endrit.moviesdiary;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.onesignal.OneSignal;
import com.rushiti.endrit.moviesdiary.db.FilmaDb;
import com.rushiti.endrit.moviesdiary.di.AppComponent;
import com.rushiti.endrit.moviesdiary.di.AppModule;
import com.rushiti.endrit.moviesdiary.di.DaggerAppComponent;
import com.rushiti.endrit.moviesdiary.di.DbModule;

import javax.inject.Inject;

import static com.rushiti.endrit.moviesdiary.ui.SettingsActivity.SHARED_PREFERENCES_DARK_MODE;

public class MoviesDiaryApp extends Application {

    @Inject
    SharedPreferences sharedPreferences;
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);

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

        getComponent().inject(this);
        initDarkMode();
    }

    private void initDarkMode() {
        boolean isDarkMode = sharedPreferences.getBoolean(SHARED_PREFERENCES_DARK_MODE, true);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public AppComponent getComponent() {
        return component;
    }
}
