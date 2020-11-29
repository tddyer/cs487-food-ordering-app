package com.example.orderquik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";

    private SQLiteDatabase database;
    private static final int DATABASE_VERSION = 1; // increment when DB Schema is changed

    public static final String DATABASE_NAME = "OrderQuikDB";

    // MenuItems table vars + creation string
    private static final String MENU_ITEMS_TABLE_NAME = "MenuItems";

    private static final String MENU_ITEM_NAME = "ItemName";
    private static final String MENU_ITEM_PRICE = "ItemPrice";
    private static final String MENU_ITEM_DESC = "ItemDescription";
    private static final String MENU_ITEM_CALS = "ItemCalories";

    private static final String SQL_CREATE_MENU_ITEMS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + MENU_ITEMS_TABLE_NAME + " (" +
                    MENU_ITEM_NAME + " TEXT PRIMARY KEY," +
                    MENU_ITEM_PRICE + " FLOAT," +
                    MENU_ITEM_DESC + " TEXT," +
                    MENU_ITEM_CALS + " INTEGER)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }




    /* SQLite method overrides */


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create any tables here
        sqLiteDatabase.execSQL(SQL_CREATE_MENU_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




    /* MenuItems table methods */


    // add menu item to db
    public void addItem(Item item) {

        ContentValues vals = new ContentValues();
        vals.put(MENU_ITEM_NAME, item.getName());
        vals.put(MENU_ITEM_PRICE, item.getPrice());
        vals.put(MENU_ITEM_DESC, item.getDescription());
        vals.put(MENU_ITEM_CALS, item.getCalories());

        database.insert(MENU_ITEMS_TABLE_NAME, null, vals);
    }

    // delete menu item from db
    public void deleteItem(String name) {
        int count = database.delete(MENU_ITEMS_TABLE_NAME, "ItemName = ?", new String[] {name});
    }

    public void flushItems() {
        database.execSQL(String.format("DELETE FROM %s;", MENU_ITEMS_TABLE_NAME));
    }

    // fetch all items from db
    public ArrayList<Item> loadMenuItems() {
        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor = database.query(
                MENU_ITEMS_TABLE_NAME,
                new String[] {MENU_ITEM_NAME, MENU_ITEM_PRICE, MENU_ITEM_DESC, MENU_ITEM_CALS},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(0);
                double price = cursor.getFloat(1);
                String desc = cursor.getString(2);
                int cals = cursor.getInt(3);
                Item temp = new Item(name, price, desc, cals);
                items.add(temp);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return items;
    }
}
