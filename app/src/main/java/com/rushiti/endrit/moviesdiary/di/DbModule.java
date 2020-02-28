package com.rushiti.endrit.moviesdiary.di;

import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    private SQLiteDatabase database;

    public DbModule(SQLiteDatabase database) {
        this.database = database;
    }

    @Provides
    @Singleton
    public SQLiteDatabase provideDb() {
        return database;
    }
}