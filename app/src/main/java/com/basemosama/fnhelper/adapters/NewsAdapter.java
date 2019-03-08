package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.NewsObjects.NewsEntries;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<NewsEntries> newsEntries;
    private NewsItemClickListener newsItemClickListener;

    public NewsAdapter(Context context, List<NewsEntries> newsEntries, NewsItemClickListener newsItemClickListener) {
        this.context = context;
        this.newsEntries = newsEntries;
        this.newsItemClickListener = newsItemClickListener;

    }
    public interface NewsItemClickListener {
        void onNewsItemClickListener(int position , ImageView sharedImageView);
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view = LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(newsEntries ==null){
        return 0;}
        return newsEntries.size();
    }

    public void updateAdapter(List<NewsEntries> newNewsEntries){
        if(newsEntries !=null){
        newsEntries.clear();
        newsEntries.addAll(newNewsEntries);}
        notifyDataSetChanged();
    }

     class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView newsImage;
        TextView newsTitle;
        private NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        newsImage=itemView.findViewById(R.id.news_image);
        newsTitle=itemView.findViewById(R.id.news_title);
        itemView.setOnClickListener(this);
        }
        private void bind(int position){
            newsTitle.setText(newsEntries.get(position).getTitle());
            String imageUrl= newsEntries.get(position).getImage();
            Picasso.get().load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(newsImage);
        }

         @Override
         public void onClick(View view) {
             newsItemClickListener.onNewsItemClickListener(getAdapterPosition(),newsImage);
         }
     }
}
