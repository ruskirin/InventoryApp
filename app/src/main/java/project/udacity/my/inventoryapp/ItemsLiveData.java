package project.udacity.my.inventoryapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


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
    }

    //TODO: create a progress bar in onPreExecute if the load is noticeable

    @SuppressLint("StaticFieldLeak")
    private void loadItems() {
        new AsyncTask<String, Void, List<EbayItem>>() {

            @Override
            protected List<EbayItem> doInBackground(String... jsonResponses) {
                List<EbayItem> items = new ArrayList<>();

                JsonParser.getEbayData(jsonResponses[0], items);

                return items;
            }

            //TODO: update the UI from here with the returned list of parsed items,
            //      not certain if 'setValue' is the proper way to go about that
            @Override
            protected void onPostExecute(List<EbayItem> ebayItems) {
                setValue(ebayItems);
            }
        }.execute();
    }
}
