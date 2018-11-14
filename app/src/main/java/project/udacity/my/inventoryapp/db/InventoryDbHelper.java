package project.udacity.my.inventoryapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.udacity.my.inventoryapp.db.InventoryContract;
import project.udacity.my.inventoryapp.db.InventoryContract.*;

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_BOOKS = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + CommonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CommonEntry.NAME + " TEXT NOT NULL, "
                + CommonEntry.IMAGE + " TEXT, "
                + CommonEntry.PRICE_BUY + " FLOAT NOT NULL, "
                + CommonEntry.PRICE_SELL + " FLOAT, "
                + CommonEntry.AMT_STOCK + " INTEGER DEFAULT " + InventoryContract.STOCK_DEF + ", "
                + CommonEntry.SELLER + " TEXT NOT NULL, "
                + CommonEntry.SELLER_CONTACT + " TEXT NOT NULL, "
                + CommonEntry.FOR_SALE + " INTEGER NOT NULL DEFAULT " + InventoryContract.FOR_SALE_NO
                + ");";

        final String CREATE_TABLE_MOVIES = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
                + CommonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CommonEntry.NAME + " TEXT NOT NULL, "
                + CommonEntry.IMAGE + " TEXT, "
                + CommonEntry.PRICE_BUY + " FLOAT NOT NULL, "
                + CommonEntry.PRICE_SELL + " FLOAT, "
                + CommonEntry.AMT_STOCK + " INTEGER DEFAULT " + InventoryContract.STOCK_DEF + ", "
                + CommonEntry.SELLER + " TEXT NOT NULL, "
                + CommonEntry.SELLER_CONTACT + " TEXT NOT NULL, "
                + CommonEntry.FOR_SALE + " INTEGER NOT NULL DEFAULT " + InventoryContract.FOR_SALE_NO
                + ");";

        final String CREATE_TABLE_GAMES = "CREATE TABLE " + GameEntry.TABLE_NAME + " ("
                + CommonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CommonEntry.NAME + " TEXT NOT NULL, "
                + CommonEntry.IMAGE + " TEXT, "
                + CommonEntry.PRICE_BUY + " FLOAT NOT NULL, "
                + CommonEntry.PRICE_SELL + " FLOAT, "
                + CommonEntry.AMT_STOCK + " INTEGER DEFAULT " + InventoryContract.STOCK_DEF + ", "
                + CommonEntry.SELLER + " TEXT NOT NULL, "
                + CommonEntry.SELLER_CONTACT + " TEXT NOT NULL, "
                + CommonEntry.FOR_SALE + " INTEGER NOT NULL DEFAULT " + InventoryContract.FOR_SALE_NO
                + ");";

        db.execSQL(CREATE_TABLE_BOOKS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_GAMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        onCreate(db);
    }
}
