package com.pranata1adi.sqlcipher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "try_db";
    public static final String DATABASE_PASSWORD = "123456";
    public static final String TABLE_TODO = "todo_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_NAME = "name";
    public static final String COLUMN_TODO_PRIORITY = "priority";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " +
                TABLE_TODO + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TODO_NAME + " TEXT, " +
                COLUMN_TODO_PRIORITY + " INTEGER" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_NAME, item.getName());
        values.put(COLUMN_TODO_PRIORITY, item.getPriority());
        database.insert(TABLE_TODO, null, values);
    }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery("select * from " + TABLE_TODO, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int priority = cursor.getInt(2);
                items.add(new Item(id, name, priority));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    public void updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_NAME, item.getName());
        values.put(COLUMN_TODO_PRIORITY, item.getPriority());
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.update(TABLE_TODO, values, COLUMN_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
