package com.rushiti.endrit.moviesdiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FilmaDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Filma_db";
    private static final Integer DATABASE_VERSION=2;

    public static final String TABLE1="CREATE TABLE Filmi(Id INTEGER PRIMARY KEY AUTOINCREMENT,Emri TEXT)";
    public static final String WatchedMovie="CREATE TABLE WatchedMovie(ID INTEGER PRIMARY KEY AUTOINCREMENT,Emri TEXT,Rate DOUBLE,Description TEXT)";

    public FilmaDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(TABLE1);
      db.execSQL(WatchedMovie);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS TABLE1");
      db.execSQL("DROP TABLE IF EXISTS WatchedMovie");
    }
}
