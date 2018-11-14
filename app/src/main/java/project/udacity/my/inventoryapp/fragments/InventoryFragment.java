package project.udacity.my.inventoryapp.fragments;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import project.udacity.my.inventoryapp.InventoryClickAsyncLoader;
import project.udacity.my.inventoryapp.db.InventoryContract;
import project.udacity.my.inventoryapp.R;
import project.udacity.my.inventoryapp.adapters.ItemCursorAdapter;


public class InventoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private int category;

    private static final int CURSOR_LOADER = 1;

    private ItemCursorAdapter cursorAdapter;
    private InventoryClickAsyncLoader asyncLoader;

    private LinearLayout editLayout;
    private LinearLayout confirmationLayout;

    private EditText editName;
    private EditText editPrice;
    private EditText editSeller;

    private TextView confirmationText;

    public static InventoryFragment newInstance(final int CATEGORY) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();

        args.putInt("category", CATEGORY);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater
                .inflate(R.layout.inventory_list_layout, container, false);

        Bundle bundle = getArguments();
        category = bundle.getInt("category");

        getLoaderManager().initLoader(CURSOR_LOADER, bundle, this);

        confirmationLayout = rootView.findViewById(R.id.inventory_list_layout_confirmation);
        LinearLayout emptyLayout = rootView.findViewById(R.id.inventory_activity_empty_layout);
        editLayout = rootView.findViewById(R.id.inventory_list_layout_edit);

        Button yesDeleteButton = rootView.findViewById(R.id.inventory_list_confirmation_button_yes);
        Button noDeleteButton = rootView.findViewById(R.id.inventory_list_confirmation_button_no);
        Button yesEditButton = rootView.findViewById(R.id.inventory_list_edit_button_yes);
        Button noEditButton = rootView.findViewById(R.id.inventory_list_edit_button_no);

        editName = rootView.findViewById(R.id.inventory_list_edit_name);
        editPrice = rootView.findViewById(R.id.inventory_list_edit_price);
        editSeller = rootView.findViewById(R.id.inventory_list_edit_seller);

        yesEditButton.setOnClickListener(editConfirmListener);
        noEditButton.setOnClickListener(editConfirmListener);
        yesDeleteButton.setOnClickListener(deleteConfirmListener);
        noDeleteButton.setOnClickListener(deleteConfirmListener);

        confirmationText = rootView.findViewById(R.id.inventory_list_confirmation_text);

        cursorAdapter = new ItemCursorAdapter(getContext(), null, category, this);
        asyncLoader = new InventoryClickAsyncLoader((Application)rootView.getContext().getApplicationContext());

        ListView itemList = rootView.findViewById(R.id.inventory_list_listview);
        itemList.setAdapter(cursorAdapter);

        itemList.setEmptyView(emptyLayout);

        return rootView;
    }

    /*--------------------------------------Loader Callbacks----------------------------------------*/
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(final int loader, @Nullable Bundle bundle) {
        final String[] PROJECTION = {
                InventoryContract.CommonEntry._ID,
                InventoryContract.CommonEntry.IMAGE,
                InventoryContract.CommonEntry.NAME,
                InventoryContract.CommonEntry.PRICE_BUY,
                InventoryContract.CommonEntry.AMT_STOCK,
                InventoryContract.CommonEntry.SELLER,
                InventoryContract.CommonEntry.SELLER_CONTACT
        };
        Uri uri = InventoryContract.BookEntry.CONTENT_URI;

        if(bundle.getInt("category") == getResources().getInteger(R.integer.category_movies))
            uri = InventoryContract.MovieEntry.CONTENT_URI;
        else if(bundle.getInt("category") == getResources().getInteger(R.integer.category_games))
            uri = InventoryContract.GameEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                uri,
                PROJECTION,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null)
            cursorAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.changeCursor(null);
    }


    /*--------------------------------------Listeners----------------------------------------*/
    private View.OnClickListener deleteConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int row = (int)confirmationText.getTag();

            final int DELETE = v.getResources().getInteger(R.integer.operation_delete);

            if(id == R.id.inventory_list_confirmation_button_yes)
                asyncLoader.myLoadInBackground(category, DELETE, row, null);

            confirmationLayout.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener editConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues values = new ContentValues();
            int id = v.getId();
            int row = (int)editName.getTag();

            final int UPDATE = v.getResources().getInteger(R.integer.operation_update);

            if(id == R.id.inventory_list_edit_button_yes) {

                if(editName.getText() != null && editName.getText().length() > 0
                        && editPrice.getText() != null && editPrice.getText().length() > 0
                        && editSeller.getText() != null && editSeller.getText().length() > 0) {

                    values.put(InventoryContract.CommonEntry.NAME, editName.getText().toString());
                    values.put(InventoryContract.CommonEntry.PRICE_BUY, Double.parseDouble(editPrice.getText().toString()));
                    values.put(InventoryContract.CommonEntry.SELLER, editSeller.getText().toString());

                    asyncLoader.myLoadInBackground(category, UPDATE, row, values);

                    editLayout.setVisibility(View.GONE);
                }

                if(editName.getText() == null || editName.getText().length() < 1)
                    Toast.makeText(v.getContext(), "Please enter a valid name!", Toast.LENGTH_LONG).show();
                if(editPrice.getText() == null || editPrice.getText().length() < 1)
                    Toast.makeText(v.getContext(), "Please enter a valid price!", Toast.LENGTH_LONG).show();
                if(editSeller.getText() == null || editSeller.getText().length() < 1)
                    Toast.makeText(v.getContext(), "Please enter a valid seller name!", Toast.LENGTH_LONG).show();
            }

            editLayout.setVisibility(View.GONE);
        }
    };
}
