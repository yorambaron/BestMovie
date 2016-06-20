package com.yboweb.bestmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 05/05/16.
 */
/**
 * This class helps open, create, and upgrade the database file containing the
 * projects and their row counters.
 */



public class DBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "imdb4.db";
    public static final String IMDB_TABLE = "imdb6";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MAP = "map";
    public static final String COLUMN_IMAGES = "images";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_VOTING = "voting";
    public static final String COLUMN_IMAGE1 = "image1";


    private static final String TAG = "DBHandler";
    private static Context context = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Action", "DB created");

        String query = "CREATE TABLE " + IMDB_TABLE + "(" +
                COLUMN_ID + "  String PRIMARY KEY, " +
                COLUMN_MAP + " TEXT, " +
                COLUMN_IMAGES + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_RATING + " TEXT, " +
                COLUMN_IMAGE1 + " TEXT, " +
                COLUMN_VOTING + " TEXT " +
                ");";
        db.execSQL(query);
    }


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + IMDB_TABLE );
        onCreate(db);
    }

    //Add a new row to the DB
    public void addMovie(MovieRow mRow) {

        // Sending side


        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, mRow.id);

        try {
            byte[] data = mRow.map.getBytes("UTF-8");
            String map64String = Base64.encodeToString(data, Base64.DEFAULT);

            values.put(COLUMN_MAP, map64String);

            data = mRow.images.getBytes("UTF-8");
            String images64String = Base64.encodeToString(data, Base64.DEFAULT);
            values.put(COLUMN_IMAGES, images64String);

        } catch (java.io.UnsupportedEncodingException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }
        values.put(COLUMN_TITLE, mRow.title);
        values.put(COLUMN_RATING, mRow.rating);
        values.put(COLUMN_VOTING, mRow.voting);
        values.put(COLUMN_IMAGE1, mRow.imageUrl);


        SQLiteDatabase db = getWritableDatabase();
        db.insert(IMDB_TABLE, null, values);
        db.close();
    }

    public void updateMovie(MovieRow mRow) {
        ContentValues values = new ContentValues();
        try {
            byte[] data = mRow.map.getBytes("UTF-8");
            String map64String = Base64.encodeToString(data, Base64.DEFAULT);

            values.put(COLUMN_MAP, map64String);

            data = mRow.images.getBytes("UTF-8");
            String images64String = Base64.encodeToString(data, Base64.DEFAULT);
            values.put(COLUMN_IMAGES, images64String);

        } catch (java.io.UnsupportedEncodingException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }

        values.put(COLUMN_TITLE, mRow.title);
        values.put(COLUMN_RATING, mRow.rating);
        values.put(COLUMN_VOTING, mRow.voting);
        values.put(COLUMN_IMAGE1, mRow.imageUrl);


        SQLiteDatabase db = getWritableDatabase();
        db.update(IMDB_TABLE, values, COLUMN_ID + "=" + mRow.id, null);
        db.close();
    }

    public MovieRow searchMoviebyId(String id) {

        SQLiteDatabase db = getReadableDatabase();
        String[] tableColumns = new String[]{


                COLUMN_ID,
                COLUMN_MAP,
                COLUMN_IMAGES,
                COLUMN_TITLE,
                COLUMN_RATING,
                COLUMN_VOTING,
                COLUMN_IMAGE1


        };

        String whereClause = COLUMN_ID + "=" + " ?";


        String[] whereArgs = new String[]{
                id
        };

        String orderBy = COLUMN_ID;


            Cursor cursor = db.query(IMDB_TABLE, tableColumns, whereClause, whereArgs,
                    null, null, orderBy);


            if (cursor.moveToFirst()) {
                String mapString = null;
                String imageString = null;

                try {
                    String map64 = cursor.getString(cursor.getColumnIndex(COLUMN_MAP));
                    byte[] data = Base64.decode(map64, Base64.DEFAULT);
                    mapString = new String(data, "UTF-8");

                    String image64 = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES));
                    data = Base64.decode(image64, Base64.DEFAULT);
                    imageString = new String(data, "UTF-8");
                } catch (java.io.UnsupportedEncodingException e) {
                    Log.e("Base64 ", e.getMessage(), e);
                    e.printStackTrace();
                }


                MovieRow movieRow =
                        new MovieRow(
                                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_RATING)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_VOTING)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE1)),
                                mapString,
                                imageString

                        );


                return (movieRow);
            }


        return(null);
    }



    public List<MovieRow>  readAll() {

        SQLiteDatabase db = getReadableDatabase();
        String[] tableColumns = new String[]{
                COLUMN_ID,
                COLUMN_MAP,
                COLUMN_IMAGES,
                COLUMN_TITLE,
                COLUMN_IMAGE1,
                COLUMN_RATING,
                COLUMN_VOTING
        };

        String whereClause = null;
        String[] whereArgs = null;
        String orderBy = COLUMN_ID;

         List<MovieRow> movieArray = new ArrayList<MovieRow>();

        Cursor cursor = db.query(IMDB_TABLE, tableColumns, whereClause, whereArgs,
                null, null, orderBy);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String mapString = null;
                String imageString = null;
                try {
                    String map64 = cursor.getString(cursor.getColumnIndex(COLUMN_MAP));
                    byte[] data = Base64.decode(map64, Base64.DEFAULT);
                    mapString = new String(data, "UTF-8");

                    String image64 = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES));
                    data = Base64.decode(image64, Base64.DEFAULT);
                    imageString = new String(data, "UTF-8");
                } catch (java.io.UnsupportedEncodingException e) {
                    Log.e("Base64 ", e.getMessage(), e);
                    e.printStackTrace();
                }
                MovieRow movieRow =
                        new MovieRow(
                                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_RATING)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_VOTING)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE1)),
                                mapString,
                                imageString);



                cursor.moveToNext();
                movieArray.add(movieRow);
            }
            return(movieArray);

        }
        return (null);
    }
}
