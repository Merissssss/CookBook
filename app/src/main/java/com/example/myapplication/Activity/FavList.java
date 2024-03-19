package com.example.myapplication.Activity;

/////FavDB

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavList extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1; // Set the database version to 1
    private static final String DATABASE_NAME = "Foods";
    private static final String TABLE_NAME = "favoriteTable";
    public static final String KEY_ID = "id";
    public static final String ITEM_TITTLE = "itemTittle";
    public static final String ITEM_IMAGE = "itemImage";
    public static final String FAVORITE_STATUS = "fStatus";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " TEXT, " + ITEM_TITTLE + " TEXT," + ITEM_IMAGE + " TEXT)";

    public FavList(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //empty table

    public void insertEmpty() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int x = 1; x < 11; x++) {
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME, null, cv);
        }
    }

    public void insertIntoTheDatabase(String item_tittle, int itemImage, String id, String fav_status ) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITEM_TITTLE, item_tittle);
        cv.put(ITEM_IMAGE, itemImage);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB Status", item_tittle + ", favstatus - " + fav_status + " - ." + cv);
    }

    public Cursor read_all_data(String id) {
        SQLiteDatabase  db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET " + FAVORITE_STATUS + " ='0' WHERE" + KEY_ID + "=" + id + "";
        db.execSQL(sql);
        Log.d("remove", id.toString());
    }

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_STATUS + " = '1'";
        return db.rawQuery(sql, null);
    }

}
