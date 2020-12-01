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


    // ActiveOrders table vars + creation string
    private static final String ACTIVE_ORDERS_TABLE_NAME = "ActiveOrders";

    private static final String ACTIVE_ORDER_ID = "ActiveOrderID";
    private static final String ACTIVE_ORDER_ITEM = "ItemName";
    private static final String ACTIVE_ORDER_ITEM_COUNT = "ItemCount";

    private static final String SQL_CREATE_ACTIVE_ORDERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ACTIVE_ORDERS_TABLE_NAME + " (" +
                    ACTIVE_ORDER_ID + " INTEGER," +
                    ACTIVE_ORDER_ITEM + " TEXT," +
                    ACTIVE_ORDER_ITEM_COUNT + " INTEGER, PRIMARY KEY (ActiveOrderID, ItemName))";


    // Users table vars + creation string
    private static final String USERS_TABLE_NAME = "Users";

    private static final String USER_EMAIL = "Email";
    private static final String USER_PASSWORD = "Password";
    private static final String USER_ADDRESS = "Address";
    private static final String USER_CARD_NO = "CardNumber";
    private static final String USER_REWARD_POINTS = "RewardPoints";
    private static final String USER_LAST_ORDER = "LastOrder";

    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " (" +
                    USER_EMAIL + " TEXT," +
                    USER_PASSWORD + " TEXT," +
                    USER_ADDRESS + " TEXT," +
                    USER_CARD_NO + " TEXT," +
                    USER_REWARD_POINTS + " INTEGER," +
                    USER_LAST_ORDER + " TEXT, PRIMARY KEY (Email))";


    // Staff table vars + creation string
    private static final String STAFF_TABLE_NAME = "Staff";

    private static final String STAFF_ID = "StaffID";
    private static final String STAFF_PASSWORD = "StaffPassword";

    private static final String SQL_CREATE_STAFF_TABLE =
            "CREATE TABLE IF NOT EXISTS " + STAFF_TABLE_NAME + " (" +
                    STAFF_ID + " INTEGER," +
                    STAFF_PASSWORD + " TEXT, PRIMARY KEY (StaffID, StaffPassword))";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }




    /* SQLite method overrides */


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create any tables here
        sqLiteDatabase.execSQL(SQL_CREATE_MENU_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ACTIVE_ORDERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STAFF_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }





    /* Staff table methods */


    // add staff member to db
    public void addStaff(StaffMember staffMember) {

        ContentValues vals = new ContentValues();
        vals.put(STAFF_ID, staffMember.getStaffID());
        vals.put(STAFF_PASSWORD, staffMember.getPassword());

        database.insert(STAFF_TABLE_NAME, null, vals);
    }


    // delete staff member from db
    public void deleteStaff(int id) {
        int count = database.delete(STAFF_TABLE_NAME, "StaffID = ?", new String[] {String.valueOf(id)});
    }


    // fetch staff member info
    public StaffMember loadStaff(int id, String password) {

        Cursor cursor = database.query(
                USERS_TABLE_NAME,
                new String[] {STAFF_ID, STAFF_PASSWORD},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                // finding desired staff member in the staff table
                int staffID = cursor.getInt(0);
                if (staffID == id) {

                    // ensure the password is correct
                    String p = cursor.getString(1);
                    if (password.equals(p)) {
                        return new StaffMember(staffID, p);
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return new StaffMember(-1, "");
    }



    /* Users table methods */


    // add user to db
    public void addUser(User user) {

        ContentValues vals = new ContentValues();
        vals.put(USER_EMAIL, user.getEmail());
        vals.put(USER_PASSWORD, user.getPassword());
        vals.put(USER_ADDRESS, user.getDeliveryAddress());
        vals.put(USER_CARD_NO, user.getCreditCardInfo());
        vals.put(USER_REWARD_POINTS, user.getRewardPoints());
        vals.put(USER_LAST_ORDER, user.getOrderHistory());

        database.insert(USERS_TABLE_NAME, null, vals);
    }

    // delete user from db
    public void deleteUser(String email) {
        int count = database.delete(USERS_TABLE_NAME, "Email = ?", new String[] {email});
    }

    // delete all users from db
    public void flushUsers() {
        database.execSQL(String.format("DELETE FROM %s;", USERS_TABLE_NAME));
    }

    // fetch user info
    public User loadUser(String email, String password) {

        Cursor cursor = database.query(
                USERS_TABLE_NAME,
                new String[] {USER_EMAIL, USER_PASSWORD, USER_ADDRESS, USER_CARD_NO, USER_REWARD_POINTS, USER_LAST_ORDER},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                // finding desired user in the users table
                String e = cursor.getString(0);
                if (e.equals(email)) {

                    // ensure the password is correct
                    String p = cursor.getString(1);
                    if (password.equals(p)) {
                        String addr = cursor.getString(2);
                        String card = cursor.getString(3);
                        int points = cursor.getInt(4);
                        String lastOrder = cursor.getString(5);
                        return new User(e, p, addr, card, points, lastOrder);
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return new User();
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

    // delete all items from db
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



    /* ActiveOrders table methods */


    // generates unique active order id
    public int generateActiveOrderID() {

        Cursor cursor = database.query(
                ACTIVE_ORDERS_TABLE_NAME,
                new String[] {ACTIVE_ORDER_ID, ACTIVE_ORDER_ITEM, ACTIVE_ORDER_ITEM_COUNT},
                null,
                null,
                null,
                null,
                null
        );

        int id = cursor.getCount() + 1;
        cursor.close();

        return id;
    }

    // add active order to db
    public void addActiveOrder(ArrayList<CheckoutItem> items) {

        int orderID = generateActiveOrderID();

        for (CheckoutItem item : items) {
            ContentValues vals = new ContentValues();
            vals.put(ACTIVE_ORDER_ID, orderID);
            vals.put(ACTIVE_ORDER_ITEM, item.getCoName());
            vals.put(ACTIVE_ORDER_ITEM_COUNT, item.getItemTotal());

            database.insert(ACTIVE_ORDERS_TABLE_NAME, null, vals);
        }
    }

    // delete active order from db
    public void deleteItem(int id) {
        int count = database.delete(ACTIVE_ORDERS_TABLE_NAME, "ActiveOrderID = ?", new String[] {String.valueOf(id)});
    }


    // fetch all active orders from db
    public ArrayList<Order> loadActiveOrders() {

        Cursor cursor = database.query(
                ACTIVE_ORDERS_TABLE_NAME,
                new String[] {ACTIVE_ORDER_ID, ACTIVE_ORDER_ITEM, ACTIVE_ORDER_ITEM_COUNT},
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<CheckoutItem> currOrder = new ArrayList<>();

        int currID = -1;

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int count = cursor.getInt(2);

                if (currID < 0) {
                    currID = id;
                } else if (id != currID) {
                    // save old order
                    orders.add(new Order(currID, currOrder));

                    // update to new order id and empty old order
                    currID = id;
                    currOrder = new ArrayList<>();
                } else if (i == cursor.getCount() - 1)
                    orders.add(new Order(currID, currOrder));

                CheckoutItem item = new CheckoutItem(name, 0.0, count);
                currOrder.add(item);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return orders;
    }

    public boolean tableExists(String name) {
        Cursor cursor = database.rawQuery(
                "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", name}
        );
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
}
