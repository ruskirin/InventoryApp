package project.udacity.my.inventoryapp;

import android.app.Application;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import project.udacity.my.inventoryapp.db.InventoryContract;

public class InventoryClickAsyncLoader extends AsyncTaskLoader<Void> {
    private final Application application;

    private final int INSERT;
    private final int DELETE;
    private final int UPDATE;

    private ContentValues values;
    private Uri contentUri;
    private int operation;

    /**
     * I feel passing Application is a TERRIBLE practice and must never be done, but time is a worse enemy
     */
    public InventoryClickAsyncLoader(@NonNull Application application) {
        super(application);

        this.application = application;

        INSERT = application.getResources().getInteger(R.integer.operation_insert);
        DELETE = application.getResources().getInteger(R.integer.operation_delete);
        UPDATE = application.getResources().getInteger(R.integer.operation_update);
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        Uri returnUri;

        if(operation == INSERT) {
            application.getContentResolver().insert(contentUri, values);
            Toast.makeText(application, "Bought!", Toast.LENGTH_SHORT).show();

        } else if(operation == DELETE) {
            application.getContentResolver().delete(contentUri, null, null);

        } else if(operation == UPDATE) {
            application.getContentResolver().update(contentUri, values, null, null);

        } else
            Toast.makeText(application, application.getString(R.string.asyncloader_error_operation), Toast.LENGTH_LONG).show();

        return null;
    }

    /**
     * Filter method for the CRUD operations, allows for reuse of this single class.
     *
     * @param category = table to perform operation on
     * @param operation = CRUD operation
     * @param row = row to perform on
     * @param values = row value for insert/update
     *
     * @return was operation successful?
     */
    public boolean myLoadInBackground(int category, int operation, int row,
                                      @Nullable ContentValues values) {

        if(category == application.getResources().getInteger(R.integer.category_books))
            contentUri = InventoryContract.BookEntry.CONTENT_URI;
        else if(category == application.getResources().getInteger(R.integer.category_movies))
            contentUri = InventoryContract.MovieEntry.CONTENT_URI;
        else if(category == application.getResources().getInteger(R.integer.category_games))
            contentUri = InventoryContract.GameEntry.CONTENT_URI;
        else {
            contentUri = null;
            Toast.makeText(application, application.getString(R.string.asyncloader_error_category), Toast.LENGTH_LONG).show();
            return false;
        }

        if(operation == DELETE || operation == UPDATE) {

            if(row < 0) {
                Toast.makeText(application, application.getString(R.string.asyncloader_error_args_empty), Toast.LENGTH_LONG).show();
                return false;
            }
            contentUri = ContentUris.withAppendedId(contentUri, row);
        }

        if((operation == INSERT || operation == UPDATE) && values == null) {

            Toast.makeText(application, application.getString(R.string.asyncloader_error_contentvalues), Toast.LENGTH_LONG).show();
            return false;
        }

        this.operation = operation;
        this.values = values;

        loadInBackground();

        return true;
    }
}
