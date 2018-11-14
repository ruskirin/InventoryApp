package project.udacity.my.inventoryapp;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Allows for customized creation of ViewModel.
 * Here specifically for passing the category selection variable to the ViewModel.
 *
 * found here: https://stackoverflow.com/a/46704702/8916812
 */

public class BrowserViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Utility utility;
    private int category;

    public BrowserViewModelFactory(@NonNull Application application, Utility utility, int category) {
        this.application = application;
        this.utility = utility;
        this.category = category;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ItemBrowserViewModel(application, utility, category);
    }
}
