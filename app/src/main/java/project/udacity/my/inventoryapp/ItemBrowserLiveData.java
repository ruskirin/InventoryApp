package project.udacity.my.inventoryapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import project.udacity.my.inventoryapp.objects.EbayItem;
import project.udacity.my.inventoryapp.http.HttpHandler;
import project.udacity.my.inventoryapp.http.JsonParser;


/***
 * Used the following article as a guide:
 *  https://medium.com/androiddevelopers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4
 *
 * Contains:
 *  TODO: FILL
 */
public class ItemBrowserLiveData extends MutableLiveData<List<EbayItem>> {
    private final Context context;
    private Utility utility;
    private int category;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public ItemBrowserLiveData(Context context, Utility utility, int category) {
        this.context = context;
        this.utility = utility;
        this.category = category;
        loadItems();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadItems() {
        new AsyncTask<Void, Void, List<EbayItem>>() {

            String url;
            boolean isBadCategory = false;

            @Override
            protected void onPreExecute() {
                Log.i(LOG_TAG, "loadItems.onPreExecute: category = " + category);
            }

            @Override
            protected List<EbayItem> doInBackground(Void... voids) {
                if(utility.isNetworkAvailable()) {
                    List<EbayItem> items;
                    HttpHandler handler = new HttpHandler();

                    url = JsonParser.buildUri(context, category);

                    if (category == context.getResources().getInteger(R.integer.category_error) || url == null) {
                        isBadCategory = true;
                        return null;
                    }

                    String jsonResponse = handler.getJsonResponse(url);
                    items = JsonParser.getEbayData(jsonResponse);

                    if (items != null)
                        Log.i(LOG_TAG, "doInBackground: item size = " + items.size());

                    return items;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<EbayItem> ebayItems) {

                if(isBadCategory)
                    Toast.makeText(context, "Oops! Something went wrong! Bad category id.", Toast.LENGTH_LONG).show();
                setValue(ebayItems);
            }
        }.execute();
    }
}
