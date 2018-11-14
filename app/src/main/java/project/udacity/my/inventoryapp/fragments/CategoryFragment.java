package project.udacity.my.inventoryapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.udacity.my.inventoryapp.R;
import project.udacity.my.inventoryapp.activities.InventoryActivity;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {}

    public static CategoryFragment newInstance(int position) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();

        args.putInt("category", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater
                .inflate(R.layout.category_front_fragment_layout, container, false);

        final int CATEGORY_BOOKS = rootView.getResources().getInteger(R.integer.category_books);
        final int CATEGORY_MOVIES = rootView.getResources().getInteger(R.integer.category_movies);
        final int CATEGORY_GAMES = rootView.getResources().getInteger(R.integer.category_games);

        final int category = getArguments().getInt("category");

        ImageView frontImage = rootView.findViewById(R.id.category_front_image);

        if(category == CATEGORY_BOOKS) {
            frontImage.setBackground(getResources().getDrawable(R.drawable.inventoryapp_category_books));

        } else if(category == CATEGORY_MOVIES) {
            frontImage.setBackground(getResources().getDrawable(R.drawable.inventoryapp_category_movies));

        } else if(category == CATEGORY_GAMES) {
            frontImage.setBackground(getResources().getDrawable(R.drawable.inventoryapp_category_games));
        }

        frontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInventory = new Intent(v.getContext(), InventoryActivity.class);

                if(category == CATEGORY_BOOKS) {
                    toInventory.putExtra("category", CATEGORY_BOOKS);
                    startActivity(toInventory);

                } else if(category == CATEGORY_MOVIES) {
                    toInventory.putExtra("category", CATEGORY_MOVIES);
                    startActivity(toInventory);

                } else if(category == CATEGORY_GAMES) {
                    toInventory.putExtra("category", CATEGORY_GAMES);
                    startActivity(toInventory);
                }
            }
        });

        return rootView;
    }
}
