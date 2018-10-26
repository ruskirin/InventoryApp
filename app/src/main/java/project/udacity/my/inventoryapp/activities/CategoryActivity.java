package project.udacity.my.inventoryapp.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.udacity.my.inventoryapp.fragments.CategoryFragment;
import project.udacity.my.inventoryapp.R;

//Used the following article for help with CardView:
// https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/

public class CategoryActivity extends FragmentActivity {

    final static int NUM_CATEGORIES = 3;

    private ViewPager categoryPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryPager = findViewById(R.id.main_viewpager);
        CategoryPagerAdapter pagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        categoryPager.setAdapter(pagerAdapter);

        categoryPager.setOnClickListener(pagerClick);
    }

    static class CategoryPagerAdapter extends FragmentPagerAdapter {

        public CategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return CategoryFragment.newInstance();
        }

        @Override
        public int getCount() {
            return NUM_CATEGORIES;
        }
    }

    private View.OnClickListener pagerClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int category = categoryPager.getCurrentItem();
            Intent toInventory = new Intent(v.getContext(), InventoryActivity.class);

            switch(category) {
                case 0: //Books
                    toInventory.putExtra("category", 0);
                    startActivity(toInventory);
                    break;
                case 1: //Movies
                    toInventory.putExtra("category", 1);
                    startActivity(toInventory);
                    break;
                case 2: //Games
                    toInventory.putExtra("category", 2);
                    startActivity(toInventory);
                    break;
            }
        }
    };
}
