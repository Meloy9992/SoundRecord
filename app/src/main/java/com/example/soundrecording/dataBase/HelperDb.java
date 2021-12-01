package com.example.soundrecording.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDb extends SQLiteOpenHelper {
    public HelperDb(@Nullable Context context) {
        super(context, ConstantsDb.DB_NAME, null, ConstantsDb.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantsDb.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConstantsDb.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
