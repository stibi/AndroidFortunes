package com.stibi.android.unixfortunewidget.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FortunesDbHelper extends SQLiteOpenHelper {

    public static final String DB_TABLE = "fortunes";
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "fortuneType";
    public static final String TEXT_COLUMN = "fortuneText";
    public static final String DB_NAME = "fortunes.db";

    private static final int DB_VERSION = 6;
    private static final String CREATE_DB =  "create table " + DB_TABLE + " ("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TYPE_COLUMN + " text not null,"
            + TEXT_COLUMN + " text not null);";
    private static final String DROP_DB = "DROP TABLE IF EXISTS " + DB_TABLE;

    public FortunesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("com.stibi.android.unixfortunewidget.database", "weeeee vytvarim DB!!!");
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(FortunesDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL(DROP_DB);
        onCreate(sqLiteDatabase);
    }


}
