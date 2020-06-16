package com.example.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.midi.MidiOutputPort;


import java.util.ArrayList;

public class database extends SQLiteOpenHelper {


    private static final String databaseName = "movies_tvs";
    private static final int databaseVersionNumber = 1;

    public database(Context context) {
        super(context, databaseName, null, databaseVersionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MovieDetail.TABLE_NAME + " (" +
                MovieDetail.MOVIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDetail.MOVIES_TITLE + " TEXT, " +
                MovieDetail.MOVIES_DESC + " TEXT, " +
                MovieDetail.MOVIES_IMAGE + " TEXT, " +
                MovieDetail.MOVIES_FAVOURITE + " BOOLEAN "
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean updateFavourite(MovieDetail movie, int favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(String.valueOf(movie.MOVIES_FAVOURITE), favourite);
        db.update(movie.TABLE_NAME, values, movie.getId() + " = ?", new String[]{String.valueOf(movie.setFavourite(favourite))});
        db.close();
        return true;
    }

    public boolean insertDetailMovies(MovieDetail movie) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(movie.MOVIES_TITLE, movie.getTitle());
        values.put(movie.MOVIES_DESC, movie.getDesc());
        values.put(movie.MOVIES_IMAGE, movie.getImage());
        //values.put(String.valueOf(movie.MOVIES_FAVOURITE),movie.getFavourite());
        long result = db.insert(movie.TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieDetail.TABLE_NAME, null, null);
        db.close();
    }

    public ArrayList<MovieDetail> getAll() {
        ArrayList<MovieDetail> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + MovieDetail.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                MovieDetail data = new MovieDetail(cursor.getString(cursor.getColumnIndex(MovieDetail.MOVIES_ID)),
                        cursor.getString(cursor.getColumnIndex(MovieDetail.MOVIES_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieDetail.MOVIES_DESC)),
                        cursor.getString(cursor.getColumnIndex(MovieDetail.MOVIES_IMAGE)),
                        cursor.getInt(cursor.getColumnIndex(MovieDetail.MOVIES_FAVOURITE))
                );
                list.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public int selectFavourite(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT movies_favourite FROM " + MovieDetail.TABLE_NAME
                + " WHERE movies_id =" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int favourite = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieDetail.MOVIES_FAVOURITE)));
        return favourite;
    }

}
