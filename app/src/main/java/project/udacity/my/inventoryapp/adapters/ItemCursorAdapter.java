package project.udacity.my.inventoryapp.adapters;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import project.udacity.my.inventoryapp.InventoryClickAsyncLoader;
import project.udacity.my.inventoryapp.R;
import project.udacity.my.inventoryapp.db.InventoryContract;
import project.udacity.my.inventoryapp.fragments.InventoryFragment;

public class ItemCursorAdapter extends CursorAdapter {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final InventoryClickAsyncLoader asyncLoader;

    private final InventoryFragment fragment;

    private final int CATEGORY;
    private final int UPDATE;

    //TODO: remove the fragment parameter instance
    public ItemCursorAdapter(Context context, Cursor c, int category, InventoryFragment fragment) {
        super(context, c, 0);

        this.fragment = fragment;

        this.asyncLoader = new InventoryClickAsyncLoader((Application)context.getApplicationContext());

        this.CATEGORY = category;
        this.UPDATE = context.getResources().getInteger(R.integer.operation_update);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context)
                .inflate(R.layout.inventory_list_item_layout, parent, false);
    }

    /**
     * TODO: Terribly packed, separate into fragments next time
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final LinearLayout foldedItemLayout = view.findViewById(R.id.inventory_list_item_layout_simple);
        final LinearLayout expandItemLayout = view.findViewById(R.id.inventory_list_item_layout_expand);
        final LinearLayout quantityItemLayout = view.findViewById(R.id.inventory_list_item_layout_quantity);
        final LinearLayout confirmationLayout = fragment.getView().findViewById(R.id.inventory_list_layout_confirmation);
        final LinearLayout editLayout = fragment.getView().findViewById(R.id.inventory_list_layout_edit);

        final Button contactButton = view.findViewById(R.id.inventory_list_item_button_contact);
        final Button sellButton = view.findViewById(R.id.inventory_list_item_button_sell);
        final Button plusButton = view.findViewById(R.id.inventory_list_item_button_incr);
        final Button minusButton = view.findViewById(R.id.inventory_list_item_button_decr);
        final Button deleteButton = view.findViewById(R.id.inventory_list_item_button_delete);
        final Button editButton = view.findViewById(R.id.inventory_list_item_button_edit);

        final EditText editName = fragment.getView().findViewById(R.id.inventory_list_edit_name);
        final EditText editPrice = fragment.getView().findViewById(R.id.inventory_list_edit_price);
        final EditText editSeller = fragment.getView().findViewById(R.id.inventory_list_edit_seller);

        final ImageView itemImage = view.findViewById(R.id.inventory_list_item_image);
        final TextView nameView = view.findViewById(R.id.inventory_list_item_title);
        final TextView priceBuyView = view.findViewById(R.id.inventory_list_item_priceB);
        final TextView sellerView = view.findViewById(R.id.inventory_list_item_seller);
        final TextView quantityView = view.findViewById(R.id.inventory_list_item_quantity);
        final TextView confirmText = fragment.getView().findViewById(R.id.inventory_list_confirmation_text);

        final String thumbnail = cursor.getString(cursor.getColumnIndex(InventoryContract.CommonEntry.IMAGE));
        final int id = cursor.getInt(cursor.getColumnIndex(InventoryContract.CommonEntry._ID));
        final String name = cursor.getString(cursor.getColumnIndex(InventoryContract.CommonEntry.NAME));
        final double priceBuy = cursor.getDouble(cursor.getColumnIndex(InventoryContract.CommonEntry.PRICE_BUY));
        final String seller = cursor.getString(cursor.getColumnIndex(InventoryContract.CommonEntry.SELLER));
        final String sellerPage = cursor.getString(cursor.getColumnIndex(InventoryContract.CommonEntry.SELLER_CONTACT));
        final int quantity = cursor.getInt(cursor.getColumnIndex(InventoryContract.CommonEntry.AMT_STOCK));

        final String priceText = "$ " + priceBuy;
        final String sellerText = "Seller: " + seller;
        final String quantityText = "Stock:\n" + quantity;

        Picasso.get().load(thumbnail).into(itemImage);
        nameView.setText(name);
        priceBuyView.setText(priceText);
        sellerView.setText(sellerText);
        quantityView.setText(quantityText);

        /**
         * Packed under one method because found no way to access the individual cell elements otherwise,
         *  and was fighting time
         */

        /*--------------------------------------Listeners----------------------------------------*/
        foldedItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandItemLayout.setVisibility(
                        expandItemLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                itemImage.setVisibility(expandItemLayout.getVisibility());
                quantityItemLayout.setVisibility(expandItemLayout.getVisibility());
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.CommonEntry.AMT_STOCK, quantity - 1);

                    asyncLoader.myLoadInBackground(CATEGORY, UPDATE,
                            id, values);

                    Toast.makeText(context, "Sold!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Can't have a negative stock... right?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(InventoryContract.CommonEntry.AMT_STOCK, quantity + 1);

                asyncLoader.myLoadInBackground(CATEGORY, UPDATE,
                        id, values);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity > 0) {
                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.CommonEntry.AMT_STOCK, quantity - 1);

                    asyncLoader.myLoadInBackground(CATEGORY, UPDATE,
                            id, values);

                } else {
                    Toast.makeText(context, "Can't have a negative stock... right?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmText.setTag(id);
                confirmationLayout.setVisibility(View.VISIBLE);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStore = new Intent(Intent.ACTION_VIEW, Uri.parse(sellerPage));
                context.startActivity(toStore);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editLayout.setVisibility(View.VISIBLE);

                editName.setText(name);
                editPrice.setText(String.valueOf(priceBuy));
                editSeller.setText(seller);

                editName.setTag(id);
            }
        });
    }
}
