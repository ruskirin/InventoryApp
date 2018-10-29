package project.udacity.my.inventoryapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

//TODO: NOTE this will get populated from SQLite storage

public class ItemInventoryAdapter extends RecyclerView.Adapter<ItemInventoryAdapter.ItemViewHolder> {

    /*
     Quite a few things TODO: this is the NEXT STEP, must be done FIRST
         1. Because the inventory screen is going to be getting its data directly from the database,
         it seems like I require a custom implementation of a CursorAdapter, albeit I am not too sure.
          > check following: https://medium.com/@emuneee/cursors-recyclerviews-and-itemanimators-b3f08cfbd370
          > and: https://gist.github.com/Shywim/127f207e7248fe48400b
         although I think I can get away without doing that by simply using a cursor to directly read data...
         But once again I do not know how exactly and that will have to be explored further.
     */

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
