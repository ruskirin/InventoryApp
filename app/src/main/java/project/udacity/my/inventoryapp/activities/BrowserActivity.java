package project.udacity.my.inventoryapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import project.udacity.my.inventoryapp.BrowserViewModelFactory;
import project.udacity.my.inventoryapp.objects.EbayItem;
import project.udacity.my.inventoryapp.ItemBrowserViewModel;
import project.udacity.my.inventoryapp.R;
import project.udacity.my.inventoryapp.Utility;
import project.udacity.my.inventoryapp.adapters.ItemBrowserAdapter;

public class BrowserActivity extends AppCompatActivity implements Utility {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private ItemBrowserAdapter itemBrowserAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_list_layout);

        final int category = getIntent()
                .getIntExtra("category", getResources().getInteger(R.integer.category_error));

        //Create a class that implements ViewModelProvider.Factory to customize ViewModel (this case:
        //  take in more parameters)
        final ItemBrowserViewModel viewModel = ViewModelProviders
                .of(this, new BrowserViewModelFactory(this.getApplication(), this, category))
                .get(ItemBrowserViewModel.class);

        LinearLayout errorLayout = findViewById(R.id.browse_list_error);
        if(isNetworkAvailable()) {
            errorLayout.setVisibility(View.GONE);

        } else
            errorLayout.setVisibility(View.VISIBLE);

        RecyclerView browseRecycler = findViewById(R.id.browse_list_recycler);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        itemBrowserAdapter = new ItemBrowserAdapter(this, category);

        browseRecycler.setHasFixedSize(true); //Less overhead, set true if cell-size won't change dynamically
        browseRecycler.setLayoutManager(layoutManager);

        viewModel.getData().observe(this, new Observer<List<EbayItem>>() {
            @Override
            public void onChanged(@Nullable List<EbayItem> ebayItems) {
                if(ebayItems != null)
                    itemBrowserAdapter.updateData(ebayItems);
            }
        });

        browseRecycler.setAdapter(itemBrowserAdapter);
    }

    //---------------Helper methods----------------//
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm;

        if((cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)) != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    @Override
    public void printItems(List<EbayItem> items, String tag) {

        if(items != null) {
            Log.i(tag, "Passed list size = " + items.size());

            for (EbayItem i : items) {
                Log.i(tag, "Retrieved Item name = " + i.getName() + ", "
                        + "price = " + i.getPrice() + ", "
                        + "seller = " + i.getSellerName() + ", "
                        + "thumbnail = " + i.getThumbnail() + ", "
                        + "quantity = " + i.getQuantity());
            }
        }
    }
}
