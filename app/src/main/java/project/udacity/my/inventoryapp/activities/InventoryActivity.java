package project.udacity.my.inventoryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import project.udacity.my.inventoryapp.R;
import project.udacity.my.inventoryapp.fragments.InventoryFragment;


public class InventoryActivity extends AppCompatActivity {

    //Bit crude, but addresses the issue of wrong category on navigating back from browser
    static int category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_activity_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra("category"))
            category = intent.getIntExtra("category", getResources().getInteger(R.integer.category_error));

        InventoryFragment inventoryFragment = InventoryFragment.newInstance(category);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.inventory_activity_coordinator_frame, inventoryFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.inventory_activity_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBrowser = new Intent(v.getContext(), BrowserActivity.class);
                toBrowser.putExtra("category", category);
                startActivity(toBrowser);
            }
        });
    }
}
