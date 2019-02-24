package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.CosmeticItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CosmeticAdapter extends RecyclerView.Adapter<CosmeticAdapter.CosmeticViewHolder> {
    private Context context;
    private List<CosmeticItem> cosmeticItems;

    public CosmeticAdapter(Context context,List<CosmeticItem> cosmeticItems) {
        this.context = context;
        this.cosmeticItems = cosmeticItems;
    }

    @NonNull
    @Override
    public CosmeticViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view = LayoutInflater.from(context).inflate(R.layout.cosmetic_grid_item,viewGroup,false);
        return new CosmeticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CosmeticViewHolder cosmeticViewHolder, int position) {
        cosmeticViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(cosmeticItems==null)
        return 0;

        return cosmeticItems.size();
    }

    public void updateAdapter(List<CosmeticItem> newCosmeticItems){
        cosmeticItems.clear();
        cosmeticItems.addAll(newCosmeticItems);
        notifyDataSetChanged();
    }

     class CosmeticViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cosmetic_grid_image)
        ImageView cosmeticImage;
        private CosmeticViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        private void bind(int position){
            String imageUrl=cosmeticItems.get(position).getImages().getInfo();
            Picasso.get().load(imageUrl)
                    .into(cosmeticImage);
        }
    }
}
