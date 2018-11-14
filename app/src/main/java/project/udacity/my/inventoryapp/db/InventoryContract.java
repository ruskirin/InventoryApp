package project.udacity.my.inventoryapp.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import butterknife.BindDrawable;

public final class InventoryContract {

    private InventoryContract() {}

    public static final String CONTENT_AUTH = "project.udacity.my.inventoryapp.provider";

    public static final Uri CONTENT_URI_BASE = Uri.parse("content://" + CONTENT_AUTH);
    public static final String URI_PATH_BOOKS = "books";
    public static final String URI_PATH_MOVIES = "movies";
    public static final String URI_PATH_GAMES = "games";

    public static final int STOCK_EMPTY = 0;
    public static final int STOCK_DEF = 1;
    public static final int FOR_SALE_YES = 1;
    public static final int FOR_SALE_NO = -1;
    public static final String PHONE_DEF = "123";

    /*
     For variables shared between all tables
     */
    public static final class CommonEntry {

        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String PRICE_BUY = "bought_for";
        public static final String PRICE_SELL = "sell_for";
        public static final String AMT_STOCK = "stock_total";
        public static final String SELLER = "seller_name";
        public static final String SELLER_CONTACT = "seller_addr";
        public static final String FOR_SALE = "is_for_sale";
        public static final String PHONE = "phone";
    }

    public static final class BookEntry {

        public static final String TABLE_NAME = "books";
        public static final Uri CONTENT_URI
                = Uri.withAppendedPath(CONTENT_URI_BASE, URI_PATH_BOOKS);
        public static final String CONTENT_LIST_TYPE
                = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE
                = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
    }

    public static final class MovieEntry {

        public static final String TABLE_NAME = "movies";
        public static final Uri CONTENT_URI
                = Uri.withAppendedPath(CONTENT_URI_BASE, URI_PATH_MOVIES);
        public static final String CONTENT_LIST_TYPE
                = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE
                = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
    }

    public static final class GameEntry {

        public static final String TABLE_NAME = "games";
        public static final Uri CONTENT_URI
                = Uri.withAppendedPath(CONTENT_URI_BASE, URI_PATH_GAMES);
        public static final String CONTENT_LIST_TYPE
                = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE
                = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTH + "/" + TABLE_NAME;
    }
}
