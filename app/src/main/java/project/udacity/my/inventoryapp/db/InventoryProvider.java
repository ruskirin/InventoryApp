package project.udacity.my.inventoryapp.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import project.udacity.my.inventoryapp.db.InventoryContract.CommonEntry;
import project.udacity.my.inventoryapp.db.InventoryContract.BookEntry;
import project.udacity.my.inventoryapp.db.InventoryContract.MovieEntry;
import project.udacity.my.inventoryapp.db.InventoryContract.GameEntry;

//TODO: Register in Manifest

/*
 Reference:
  https://developer.android.com/guide/topics/providers/content-provider-creating#java
  https://developer.android.com/guide/topics/providers/content-provider-basics#java
 */

public class InventoryProvider extends ContentProvider {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private InventoryDbHelper dbHelper;
    private SQLiteDatabase db;

    private static final int BOOKS = 100;
    private static final int BOOKS_ID = 101;

    private static final int MOVIES = 200;
    private static final int MOVIES_ID = 201;

    private static final int GAMES = 300;
    private static final int GAMES_ID = 301;

    static {
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH, InventoryContract.URI_PATH_BOOKS, BOOKS);
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH,
                InventoryContract.URI_PATH_BOOKS + "/#", BOOKS_ID);

        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH, InventoryContract.URI_PATH_MOVIES, MOVIES);
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH,
                InventoryContract.URI_PATH_MOVIES + "/#", MOVIES_ID);

        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH, InventoryContract.URI_PATH_GAMES, GAMES);
        URI_MATCHER.addURI(InventoryContract.CONTENT_AUTH,
                InventoryContract.URI_PATH_GAMES + "/#", GAMES_ID);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new InventoryDbHelper(getContext());
        db = dbHelper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Cursor cursor = null;

        final int CODE = URI_MATCHER.match(uri);
        switch(CODE) {

            case BOOKS:
                cursor = db.query(BookEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIES:
                cursor = db.query(MovieEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
                break;
            case GAMES:
                cursor = db.query(GameEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                Log.e("InventoryProvider.query", "Bad URI = " + uri);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int CASE = URI_MATCHER.match(uri);
        switch(CASE) {

            case BOOKS:
                return BookEntry.CONTENT_LIST_TYPE;
            case BOOKS_ID:
                return BookEntry.CONTENT_ITEM_TYPE;
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;
            case MOVIES_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            case GAMES:
                return GameEntry.CONTENT_LIST_TYPE;
            case GAMES_ID:
                return GameEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("InvProvider.getType : Bad URI = " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri returnUri = null;
        long id;

        /*
         TODO: create feedback for good/bad insertion
         */
        final int code = URI_MATCHER.match(uri);
        switch(code) {

            case BOOKS:
                id = db.insert(BookEntry.TABLE_NAME, null, values);

                if(id == -1) {
                    throw new SQLException("InvProvider.insert : Bad insertion");
                }
                returnUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                break;

            case MOVIES:
                id = db.insert(MovieEntry.TABLE_NAME, null, values);

                if(id == -1) {
                    throw new SQLException("InvProvider.insert : Bad insertion");
                }
                returnUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);
                break;

            case GAMES:
                id = db.insert(GameEntry.TABLE_NAME, null, values);

                if(id == -1) {
                    throw new SQLException("InvProvider.insert : Bad insertion");
                }
                returnUri = ContentUris.withAppendedId(GameEntry.CONTENT_URI, id);
                break;

            default:
                throw new IllegalArgumentException("InvProvider.insert : Bad URI = " + uri);
        }
        //notify of added row if one was added
        if(returnUri != null)
            getContext().getContentResolver().notifyChange(returnUri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      String selection,
                      String[] selectionArgs) {

        int rows = 0;

        final int CODE = URI_MATCHER.match(uri);
        switch(CODE) {

            case BOOKS_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = db.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIES_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case GAMES_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = db.delete(GameEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("InvProvider.delete : Bad URI = " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {

        int rows = 0;

        final int CODE = URI_MATCHER.match(uri);
        switch(CODE) {

            case BOOKS_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = updateItems(BookEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case MOVIES_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = updateItems(MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case GAMES_ID:
                selection = CommonEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rows = updateItems(GameEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("InvProvider.update: Bad URI = " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }

    /*
     Verify the input and update
     */
    private int updateItems(String table,
                            ContentValues values,
                            String selection,
                            String[] selectionArgs) {

        if(values.containsKey(CommonEntry.NAME)) {
            final String name = values.getAsString(CommonEntry.NAME);

            if(name == null || name.length() < 1)
                throw new IllegalArgumentException(LOG_TAG + "item name is required");
        }

        if(values.containsKey(CommonEntry.PRICE_BUY)) {
            final double sell = values.getAsDouble(CommonEntry.PRICE_BUY);

            if(sell < 0.0)
                throw new IllegalArgumentException(LOG_TAG + "sell price cannot be negative");
        }

        if(values.containsKey(CommonEntry.AMT_STOCK)) {
            final int stock = values.getAsInteger(CommonEntry.AMT_STOCK);

            if(stock < 0)
                throw new IllegalArgumentException(LOG_TAG + "stock cannot be negative");
        }

        if(values.containsKey(CommonEntry.FOR_SALE)) {
            final Integer isForSale = values.getAsInteger(CommonEntry.FOR_SALE);

            if(isForSale != InventoryContract.FOR_SALE_NO && isForSale != InventoryContract.FOR_SALE_YES)
                throw new IllegalArgumentException(LOG_TAG + "(-1) is not for sale, (1) is for sale");
        }
        return db.update(table, values, selection, selectionArgs);
    }

    public void closeDatabase() {
        if(db.isOpen())
            db.close();
    }
}
