package project.udacity.my.inventoryapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.udacity.my.inventoryapp.EbayItem;
import project.udacity.my.inventoryapp.ItemBrowserAdapter;
import project.udacity.my.inventoryapp.ItemsViewModel;
import project.udacity.my.inventoryapp.R;

public class BrowserActivity extends AppCompatActivity {

    public final static String SETTINGS_ONE = "settings_one";

    //TODO: this activity is responsible for browsing through the eBay data, so must be passed some
    //       identifying variable to know which category was selected; for now going with SharedPrefs
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_list_layout);

        List<EbayItem> browseItems = new ArrayList<>();

        //TODO: pass the category selected as a bundle instead of sharedpreferences!
        Bundle bundle = new Bundle();

        ItemsViewModel itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        RecyclerView browseRecycler = new RecyclerView(this);
        ItemBrowserAdapter itemBrowserAdapter = new ItemBrowserAdapter(browseItems);

        browseRecycler.setLayoutManager(layoutManager);
        browseRecycler.setAdapter(itemBrowserAdapter);

        if(isNetworkAvailable()) {
            browseItems = itemsViewModel.getData().getValue();

        } else {
            Toast.makeText(this,
                    "Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
