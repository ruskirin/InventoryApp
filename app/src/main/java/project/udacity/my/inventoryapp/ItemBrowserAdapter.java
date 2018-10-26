package project.udacity.my.inventoryapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemBrowserAdapter extends RecyclerView.Adapter<ItemBrowserAdapter.ItemViewHolder> {
    private List<EbayItem> items = null;

    public ItemBrowserAdapter(List<EbayItem> items) {
        this.items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_list_card_image)        ImageView image;
        @BindView(R.id.category_list_card_buybutton)    Button price;
        @BindView(R.id.category_list_card_title)        TextView title;
        @BindView(R.id.category_list_card_seller)       TextView seller;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemHolder = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.browser_list_item_layout, viewGroup, false);

        return new ItemViewHolder(itemHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int pos) {
        EbayItem item = items.get(pos);

        Picasso.get().load(item.getThumbnail()).into(itemViewHolder.image);
        itemViewHolder.price.setText(item.getPrice());
        itemViewHolder.title.setText(item.getName());
        itemViewHolder.seller.setText(item.getSellerName());

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO: update database with info from this item
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        itemViewHolder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {

        if(items == null)
            return 0;
        else
            return items.size();
    }
}
