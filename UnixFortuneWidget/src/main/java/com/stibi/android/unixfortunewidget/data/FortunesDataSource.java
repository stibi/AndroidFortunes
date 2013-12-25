package com.stibi.android.unixfortunewidget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stibi.android.unixfortunewidget.api.Constants;
import com.stibi.android.unixfortunewidget.api.IFortunesProvider;
import com.stibi.android.unixfortunewidget.database.FortunesDbHelper;

import java.util.ArrayList;
import java.util.List;

public class FortunesDataSource implements IFortunesProvider {

    private Context context;
    private SQLiteDatabase database;
    private FortunesDbHelper dbHelper;
    private String[] allColumns = {FortunesDbHelper.ID_COLUMN, FortunesDbHelper.TYPE_COLUMN, FortunesDbHelper.TEXT_COLUMN};

    public FortunesDataSource(Context context) {
        dbHelper = new FortunesDbHelper(context);
        this.context = context;
    }

    public String getDbName() {
        return dbHelper.DB_NAME;
    }

    public void open() {
        Log.d("com.stibi.android.unixfortunewidget.data", "Oteviram db");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        Log.d("com.stibi.android.unixfortunewidget.data", "Zaviram db");
        dbHelper.close();
    }

    public boolean createDb() {
        return false;
    }

    public boolean dropDb() {
        return context.deleteDatabase(dbHelper.DB_NAME);
    }

    public long getFortunesCount() {
//        String sql = String.format("select count(*) from %s", FortunesDbHelper.DB_TABLE);
//        Log.d(Constants.LOG, "Statement pro pocet fortunek: " + sql);
        // TODO ?? co je SQLiteStatement?
//        SQLiteStatement statement = database.compileStatement(sql);
//        long fortunesCount =  statement.simpleQueryForLong();
//        Log.d(Constants.LOG, "Kolik tam mame fortunek: " + fortunesCount);
//        return fortunesCount;
        Log.d(Constants.LOG, "Je db open? " + database.isOpen());
        long fortunesCount = DatabaseUtils.queryNumEntries(database, FortunesDbHelper.DB_TABLE);
        Log.d(Constants.LOG, "getFortunesCount() fortunesCount=" + fortunesCount);
        return fortunesCount;
    }

    @Override
    public Fortune getRandomFortune() {
        Cursor cursor = database.query("Fortunes ORDER BY RANDOM() LIMIT 1", new String[]{"*"}, null, null, null, null, null);
        cursor.moveToFirst();
        String fortuneText = cursor.getString(2);
        return new Fortune(0, "dafuqtype", fortuneText);
    }

    @Override
    public Fortune getRandomFortuneFromCollection(FortuneCollection fortuneCollection) {
        return null;
    }

    @Override
    public int allFortunesCount() {
        return 0;
    }

    @Override
    public int allFortunesInCollectionCount(FortuneCollection fortuneCollection) {
        return 0;
    }

    public Fortune saveFortune(String type, String text) {
        ContentValues values = new ContentValues();
        values.put(FortunesDbHelper.TYPE_COLUMN, type);
        values.put(FortunesDbHelper.TEXT_COLUMN, text);
        long insertId = database.insert(FortunesDbHelper.DB_TABLE, null, values);
        Cursor cursor = database.query(FortunesDbHelper.DB_TABLE, allColumns, FortunesDbHelper.ID_COLUMN + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Fortune savedFortune = cursorToComment(cursor);
        cursor.close();
        return savedFortune;
    }

    public void deleteFortune(Fortune comment) {
        long id = comment.getId();
        database.delete(FortunesDbHelper.DB_TABLE, FortunesDbHelper.ID_COLUMN + " = " + id, null);
    }

    public List<Fortune> getAllFortunes() {
        List<Fortune> allFortunesList = new ArrayList<Fortune>();
        Cursor cursor = database.query(FortunesDbHelper.DB_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Fortune comment = cursorToComment(cursor);
            allFortunesList.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return allFortunesList;
    }

    private Fortune cursorToComment(Cursor cursor) {
        Long id = cursor.getLong(0);
        String type = cursor.getString(1);
        String text = cursor.getString(2);
        return new Fortune(id, type, text);
    }
}
