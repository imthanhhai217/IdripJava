package com.jaroid.demoretrofit.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wishlist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "wishlist";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";

    public SqlHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL, " + TITLE_COLUMN + " TEXT, " + PRICE_COLUMN + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addWish(int id, String title, String price) {
        Log.d("TAG", "addWish: " + id);
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN, id);
        contentValues.put(TITLE_COLUMN, title);
        contentValues.put(PRICE_COLUMN, price);

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void removeWish(int id) {
        Log.d("TAG", "removeWish: " + id);
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, "id = ?", new String[]{id + ""});
        database.close();
    }


    public ArrayList<Integer> getAllWishId() {
        ArrayList<Integer> data = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int currentId = cursor.getInt(0);
                data.add(currentId);
            } while (cursor.moveToNext());
        }
        return data;
    }

}
