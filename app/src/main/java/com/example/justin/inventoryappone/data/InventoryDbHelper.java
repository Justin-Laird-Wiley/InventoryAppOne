package com.example.justin.inventoryappone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "warehouse.db";

    private static final String COMMA = ",";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";
    private static final String DEFAULT_VALUE = " DEFAULT";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + "(" +
            InventoryContract.InventoryEntry._ID + INT_TYPE + PRIMARY_KEY + AUTO_INCREMENT + COMMA +
            InventoryContract.InventoryEntry.COLUMN_ITEM + TEXT_TYPE + NOT_NULL + COMMA +
            InventoryContract.InventoryEntry.COLUMN_PRICE + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            InventoryContract.InventoryEntry.COLUMN_QUANTITY + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0"+ COMMA +
            InventoryContract.InventoryEntry.COLUMN_SUPPLIER + TEXT_TYPE + NOT_NULL + COMMA +
            InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" +
            ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
