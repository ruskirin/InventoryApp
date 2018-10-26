package project.udacity.my.inventoryapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/***
 * Contains:
 *  TODO: FILL
 */

public class ItemsViewModel extends AndroidViewModel {
    private ItemsLiveData itemsLiveData;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
        itemsLiveData = new ItemsLiveData(application);
    }

    public LiveData<List<EbayItem>> getData() {
        return itemsLiveData;
    }


}
