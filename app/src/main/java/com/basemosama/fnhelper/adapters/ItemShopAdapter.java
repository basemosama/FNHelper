package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.ItemShopViewHolder> {
    private Context context;
    private List<ItemShopItems> itemShopItems;
    private ItemShopClickListener itemShopClickListener;

    public ItemShopAdapter(Context context, List<ItemShopItems> itemShopItems, ItemShopClickListener itemShopClickListener) {
        this.context = context;
        this.itemShopItems = itemShopItems;
        this.itemShopClickListener = itemShopClickListener;
    }

    public interface ItemShopClickListener {
        void onItemShopClickListener(int position);
    }

    @NonNull
    @Override
    public ItemShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cosmetic_grid_item, viewGroup, false);
        return new ItemShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemShopViewHolder itemShopViewHolder, int position) {
        itemShopViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (itemShopItems == null) {
            return 0;
        }
        return itemShopItems.size();
    }

    public void updateAdapter(List<ItemShopItems> newCosmeticItems) {
        itemShopItems.clear();
        itemShopItems.addAll(newCosmeticItems);
        notifyDataSetChanged();
    }

    public void stopLoading() {
        Picasso.get()
                .cancelTag(ItemShopAdapter.class.getName());
    }

    class ItemShopViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView cosmeticImage;

        private ItemShopViewHolder(@NonNull View itemView) {
            super(itemView);
            cosmeticImage = itemView.findViewById(R.id.cosmetic_grid_image);
            cosmeticImage.setOnClickListener(this);

        }

        private void bind(int position) {
            if (itemShopItems != null) {
                String imageUrl = itemShopItems.get(position).getItem().getImages().getInformation();
                Picasso.get().load(imageUrl)
                        .fit()
                        .tag(ItemShopAdapter.class.getName())
                        .placeholder(R.drawable.placeholder)
                        .into(cosmeticImage);
            }

        }

        @Override
        public void onClick(View view) {
            itemShopClickListener.onItemShopClickListener(getAdapterPosition());
        }
    }


}
