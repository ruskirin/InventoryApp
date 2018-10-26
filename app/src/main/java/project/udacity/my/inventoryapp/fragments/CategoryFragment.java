package project.udacity.my.inventoryapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.udacity.my.inventoryapp.R;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {}

    //TODO: will need to enter a parameter required to display the appropriate category info
    //       not yet sure how that will work with getting data from SQLite.
    //       Leave empty till database established

    //TODO: will also pass the category opened from here into SharedPreferences

    public static CategoryFragment newInstance() {
        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater
                .inflate(R.layout.category_front_fragment, container, false);

        ImageView frontImage = rootView.findViewById(R.id.category_front_image);
        TextView frontItemsTotal = rootView.findViewById(R.id.category_front_items_total);
        TextView frontItemsSold = rootView.findViewById(R.id.category_front_items_sold);
        TextView frontItemsValue = rootView.findViewById(R.id.category_front_items_soldvalue);
        TextView frontItemsStock = rootView.findViewById(R.id.category_front_items_stock);

        return rootView;
    }
}
