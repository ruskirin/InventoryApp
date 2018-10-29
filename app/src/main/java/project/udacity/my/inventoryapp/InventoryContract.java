package project.udacity.my.inventoryapp;

import android.net.Uri;
import android.provider.BaseColumns;

import butterknife.BindDrawable;

public final class InventoryContract {

    private InventoryContract() {}

    public static final String CONTENT_AUTH = "project.udacity.my.myinventoryapp.provider";
    public static final Uri CONTENT_URI_BASE = Uri.parse("content://" + CONTENT_AUTH);

    public static final int STOCK_EMPTY = 0;
    public static final int FOR_SALE_YES = 1;
    public static final int FOR_SALE_NO = 0;

    @BindDrawable(R.drawable.inventoryapp_noimage) static int defNoImage;

    public static final class CommonEntry {

        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String PRICE_BUY = "bought_at";
        public static final String PRICE_SELL = "sell_at";
        public static final String AMT_STOCK = "stock_total";
        public static final String AMT_SOLD = "sold_total";
        public static final String SELLER = "seller_name";
        public static final String SELLER_CONTACT = "seller_addr";
        public static final String FOR_SALE = "is_for_sale";
    }

    public static final class BookEntry {

        public static final String TABLE_NAME = "books";
    }

    public static final class MovieEntry {

        public static final String TABLE_NAME = "movies";
    }

    public static final class GameEntry {

        public static final String TABLE_NAME = "games";
    }
}
