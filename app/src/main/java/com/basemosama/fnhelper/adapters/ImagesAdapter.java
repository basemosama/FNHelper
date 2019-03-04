package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemosama.fnhelper.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {
    private Context context;
    private List<String> images;

    public ImagesAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;

    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view = LayoutInflater.from(context).inflate(R.layout.cosmetic_grid_item,viewGroup,false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder imagesViewHolder, int position) {
        imagesViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(images==null){
        return 0;}
        return images.size();
    }

    public void updateAdapter(List<String> newImages){
        if(images!=null){
        images.clear();
        images.addAll(newImages);}
        notifyDataSetChanged();
    }

    public void stopLoading(){
        Picasso.get()
              .cancelTag("CosmeticAdapter");
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder  {
        private ImageView cosmeticImage;
        private ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
        cosmeticImage=itemView.findViewById(R.id.cosmetic_grid_image);
        }
        private void bind(int position){
            String imageUrl=images.get(position);
            //Log.i("itemShopImage",imageUrl);
            Picasso.get().load(imageUrl)
                    .resize(500,500)
                    .tag("CosmeticAdapter")
                    .placeholder(R.drawable.placeholder1)
                    .into(cosmeticImage);
        }


     }


}
