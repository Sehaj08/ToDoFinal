package com.example.sehajgulati08.todofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sehaj.gulati08 on 07-07-2017.
 */

public class ToDoOpenHelper extends SQLiteOpenHelper {

    public static final String TODO_TABLE_NAME = "ToDo";
    public static final String SPINNER__TABLE_NAME = "spinner_table";
    public static final String TODO_ID = "id";
    public static final String TODO_TITLE = "toDoTitle";
    public static final String TODO_CATEGORY = "toDoCategory";
    public static final String TODO_DATE = "toDoDate";
    public static final String TODO_TIME = "toDoTime";
    public static final String SPINNER_CATEGORY = "spinner_category";
    public static ToDoOpenHelper toDoOpenHelper;

    public static ToDoOpenHelper getToDoOpenHelperInstance(Context context){
        if(toDoOpenHelper == null){
            toDoOpenHelper = new ToDoOpenHelper(context);
        }
        return toDoOpenHelper;
    }

    public ToDoOpenHelper(Context context) {
        super(context,"ToDo.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TODO_TABLE_NAME +" ( " + TODO_ID +
                " integer primary key autoincrement, " + TODO_TITLE +" text, "
                + TODO_DATE + " real, "
                + TODO_CATEGORY + " text, " + TODO_TIME + " text);";

        String query2 = "create table" + SPINNER__TABLE_NAME + " ( " + SPINNER_CATEGORY + " text);";
        db.execSQL(query);
//        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
