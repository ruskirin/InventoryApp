package project.udacity.my.inventoryapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import project.udacity.my.inventoryapp.http.JsonParser;


/***
 * Used the following article as a guide:
 *  https://medium.com/androiddevelopers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4
 *
 * Contains:
 *  TODO: FILL
 */
public class ItemsLiveData extends LiveData<List<EbayItem>> {
    private Context context;

    public ItemsLiveData(Context context) {
        this.context = context;
        loadItems();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadItems() {
        new AsyncTask<Void, Void, List<EbayItem>>() {

            int category = 0;

            @Override
            protected void onPreExecute() {
                Bundle bundle = new Bundle();
                category = bundle.getInt("category");
            }

            @Override
            protected List<EbayItem> doInBackground(Void... voids) {
                List<EbayItem> items = new ArrayList<>();

                if(category == 0)
                    return null;

                String url = JsonParser.buildUri(context, category);
                if(url != null)
                    JsonParser.getEbayData(url, items);
                else
                    return null;

                return items;
            }
        }.execute();
    }
}
