package project.udacity.my.inventoryapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import project.udacity.my.inventoryapp.R;

/**
 * TODO: list to get done:
 *   - make the seller textview clickable and redirect to the seller eBay page (if exists)
 *     otherwise redirect to eBay's homepage
 *   - receive the passed category identifier from bundle to determine what information gets loaded
 *   - upon receiving above, need to instantly access database and display any items in the inventory
 *     IF there are none, display a screen saying that there is no stock
 */

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list_layout);

        
    }
}
