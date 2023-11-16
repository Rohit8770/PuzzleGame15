package com.example.puzzlegame15.Table;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.puzzlegame15.Model.HighScoreModel;
import com.example.puzzlegame15.Model.PuzzleModel;

import java.util.ArrayList;


public class PuzzleHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Swipe_Puzzle";
    private static final String USER_TABLE = "Users";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_IMAGE = "User_Image";

    // highScore
    private static final String TABLE_SCORE = "Score_Table";
    private static final String KEY_SCORE_ID = "Id";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_MOVES = "Moves";
    private static final String KEY_TIME = "Time_Taken";
  //  private static final String KEY_USERNAME = "username";




    public PuzzleHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_IMAGE + " BLOB"
                + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE_SCORE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_SCORE_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_MOVES + " INTEGER,"
                + KEY_TIME + " INTEGER,"
                + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + USER_TABLE + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_TABLE_SCORE);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String Name, String Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, Name);
        values.put(COLUMN_IMAGE, Image);
        db.insert(USER_TABLE, null, values);
        db.close();
    }
    public void insertScore(int userId, int moves, int timeTaken,String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_MOVES, moves);
        values.put(KEY_TIME, timeTaken);
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }


    public ArrayList<PuzzleModel> getAllUsers() {
        ArrayList<PuzzleModel> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));

                PuzzleModel user = new PuzzleModel(id, name, image);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;

    }
    public ArrayList<HighScoreModel> getAllScores(int Limit) {
        ArrayList<HighScoreModel> scoreList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_MOVES + " ASC LIMIT ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(Limit)});


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int score_id = cursor.getInt(cursor.getColumnIndex(KEY_SCORE_ID));
                @SuppressLint("Range") int user_id = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                @SuppressLint("Range") int move = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range") int time = cursor.getInt(cursor.getColumnIndex(KEY_TIME));
         //       @SuppressLint("Range") String userName = String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_USERNAME)));

                HighScoreModel highScoreModel = new HighScoreModel(score_id, user_id, move, time);
                scoreList.add(highScoreModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return scoreList;
    }

    public ArrayList<HighScoreModel> getUserHistory(int userId) {
        ArrayList<HighScoreModel> userHistoryList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORE + " WHERE " + KEY_USER_ID + " = ? ORDER BY " + KEY_MOVES;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int score_id = cursor.getInt(cursor.getColumnIndex(KEY_SCORE_ID));
                @SuppressLint("Range") int user_id = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                @SuppressLint("Range") int move = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range") int time = cursor.getInt(cursor.getColumnIndex(KEY_TIME));
          //      @SuppressLint("Range") String userName = String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_USERNAME)));

                HighScoreModel highScoreModel = new HighScoreModel(score_id, user_id, move, time);
                userHistoryList.add(highScoreModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userHistoryList;
    }




    public void deleteUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};
        db.delete(USER_TABLE, whereClause, whereArgs);
        db.close();
    }

}
