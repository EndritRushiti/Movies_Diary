package com.rushiti.endrit.moviesdiary.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class AppModule {
    public static final String SHARED_PREFERENCES_NAME = "movies_diary_shared_preferences";
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }
}
