package project.udacity.my.inventoryapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import project.udacity.my.inventoryapp.objects.EbayItem;


public class ItemBrowserViewModel extends AndroidViewModel {
    private ItemBrowserLiveData itemsLiveData;
    private final Application application;
    private final int category;
    private Utility utility;

    public ItemBrowserViewModel(@NonNull Application application, Utility utility, int category) {
        super(application);
        this.application = application;
        this.utility = utility;
        this.category = category;
    }

    public MutableLiveData<List<EbayItem>> getData() {

        if(itemsLiveData == null)
            this.itemsLiveData = new ItemBrowserLiveData(application, utility, category);
        return itemsLiveData;
    }
}
