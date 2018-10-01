package com.meridian.carins.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandlerUpload extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerUpload.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_carins";

    // Image table name
    private static final String TABLE_IMAGE = "s_image";

    // Image Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PATH = "path";
    private static final String KEY_USER_ID = "user_id";

    public SQLiteHandlerUpload(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PATH + " TEXT," + KEY_USER_ID + " TEXT" + ")";
        db.execSQL(CREATE_IMAGE_TABLE);

        Log.d(TAG, "Database table created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        // Create table again
        onCreate(db);
    }

    /**
     * Storing image details in database
     * */
    public void addImage(String name, String path, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PATH, path);
        values.put(KEY_USER_ID, uid);

        // Inserting row
        long id = db.insert(TABLE_IMAGE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New image inserted into SQLite: " + id);
    }

    /**
     * Getting image data from database
     * */
    public HashMap<String, String> getImageDetails() {
        HashMap<String, String> image = new HashMap<>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            image.put("name", cursor.getString(1));
            image.put("path", cursor.getString(2));
            image.put("user_id", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from SQLite: " + image.toString());

        return image;
    }

    /**
     * Re crate database Delete table and create it again
     * */
    public void deleteImage() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete All Rows
        db.delete(TABLE_IMAGE, null, null);
        db.close();

        Log.d(TAG, "Deleted all image info from SQLite");
    }
}
