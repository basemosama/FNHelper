package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CosmeticAdapter extends RecyclerView.Adapter<CosmeticAdapter.CosmeticViewHolder> {
    private Context context;
    private List<MainItem> mainItems;
    private CosmeticItemClickListener cosmeticItemClickListener;

    public CosmeticAdapter(Context context, List<MainItem> mainItems, CosmeticItemClickListener cosmeticItemClickListener) {
        this.context = context;
        this.mainItems = mainItems;
        this.cosmeticItemClickListener = cosmeticItemClickListener;

    }

    public interface CosmeticItemClickListener {
        void onCosmeticItemClickListener(int position);
    }

    @NonNull
    @Override
    public CosmeticViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cosmetic_grid_item, viewGroup, false);
        return new CosmeticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CosmeticViewHolder cosmeticViewHolder, int position) {
        cosmeticViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mainItems == null) {
            return 0;
        }
        return mainItems.size();
    }

    public void updateAdapter(List<MainItem> newMainItems) {
        if (mainItems != null) {
            mainItems.clear();
            mainItems.addAll(newMainItems);
        }
        notifyDataSetChanged();
    }

    public void stopLoading() {
        Picasso.get()
                .cancelTag(CosmeticAdapter.class.getName());
    }

    class CosmeticViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView cosmeticImage;

        private CosmeticViewHolder(@NonNull View itemView) {
            super(itemView);
            cosmeticImage = itemView.findViewById(R.id.cosmetic_grid_image);
            cosmeticImage.setOnClickListener(this);
        }

        private void bind(int position) {

            String imageUrl = mainItems.get(position).getImages().getInfo();
            if (TextUtils.isEmpty(imageUrl)) {
                imageUrl = mainItems.get(position).getImages().getInformation();
            }
            Picasso.get().load(imageUrl)
                    .fit()
                    .tag(CosmeticAdapter.class.getName())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(cosmeticImage);
        }

        @Override
        public void onClick(View view) {
            cosmeticItemClickListener.onCosmeticItemClickListener(getAdapterPosition());
        }
    }


}
