package project.udacity.my.inventoryapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/***
 * Contains:
 *  TODO: FILL
 */
public class ItemsViewModel extends AndroidViewModel {

    private ItemsLiveData itemsLiveData;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
    }


}
