package project.udacity.my.inventoryapp.adapters;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.udacity.my.inventoryapp.InventoryClickAsyncLoader;
import project.udacity.my.inventoryapp.objects.EbayItem;
import project.udacity.my.inventoryapp.db.InventoryContract;
import project.udacity.my.inventoryapp.R;


public class ItemBrowserAdapter extends RecyclerView.Adapter<ItemBrowserAdapter.ItemViewHolder> {
    private List<EbayItem> items;
    private int category;
    private Context context;

    public ItemBrowserAdapter(Context context, int category) {
        this.context = context;
        this.category = category;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
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
        final EbayItem item = items.get(pos);
        String price = context.getResources().getString(R.string.text_price) + item.getPrice();

        Picasso.get().load(item.getThumbnail()).into(itemViewHolder.image);
        itemViewHolder.price.setText(price);
        itemViewHolder.price.setTextSize(context.getResources().getDimension(R.dimen.browser_list_item_button_text));
        itemViewHolder.title.setText(item.getName());
        itemViewHolder.seller.setText(item.getSellerName());

        itemViewHolder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                final int INSERT = context.getResources().getInteger(R.integer.operation_insert);

                if(item.getName() != null && item.getName().length() > 0
                        && item.getPrice() > 0.0
                        && item.getSellerName() != null && item.getSellerName().length() > 0
                        && item.getSellerContact() != null && item.getSellerContact().length() > 0) {

                    values.put(InventoryContract.CommonEntry.NAME, item.getName());
                    values.put(InventoryContract.CommonEntry.IMAGE, item.getThumbnail());
                    values.put(InventoryContract.CommonEntry.PRICE_BUY, item.getPrice());
                    values.put(InventoryContract.CommonEntry.AMT_STOCK, item.getQuantity());
                    values.put(InventoryContract.CommonEntry.SELLER, item.getSellerName());
                    values.put(InventoryContract.CommonEntry.SELLER_CONTACT, item.getSellerContact());

                    //Passing around application like this is a bad idea
                    InventoryClickAsyncLoader clickLoader = new InventoryClickAsyncLoader((Application) context.getApplicationContext());
                    clickLoader.myLoadInBackground(category, INSERT, -1, values);

                } else {
                    Toast.makeText(context, "Can't store. Bad item information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void updateData(List<EbayItem> items) {
        this.items = items;
        notifyDataSetChanged();
     }
}
