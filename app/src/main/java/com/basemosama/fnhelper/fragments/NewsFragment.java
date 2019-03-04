package com.basemosama.fnhelper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.NewsActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.NewsAdapter;
import com.basemosama.fnhelper.objects.NewsObjects.News;
import com.basemosama.fnhelper.objects.NewsObjects.NewsEntries;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment implements NewsAdapter.NewsItemClickListener {
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsEntries> news =new ArrayList<>();
    private Call<News> getNews;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment,container,false);
        newsRecyclerView =view.findViewById(R.id.news_rv);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1);
        newsRecyclerView.setLayoutManager(gridLayoutManager);
        newsAdapter =new NewsAdapter(getContext(), news,this);
        newsRecyclerView.setAdapter(newsAdapter);
        getNewsItems();

        return view;
    }

    public void getNewsItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService=retrofit.create(CosmeticService.class);
        getNews = cosmeticService.getNews();
        getNews.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.body()!=null) {
                    news = response.body().getEntries();
                    newsAdapter.updateAdapter(news);
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.i("myretrofit",t.getLocalizedMessage());


            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getNews !=null){
            getNews.cancel();
        }
        newsRecyclerView.setAdapter(null);
    }

    @Override
    public void onNewsItemClickListener(int position) {
        Toast.makeText(getContext(), news.get(position).getTitle(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), NewsActivity.class);
        intent.putExtra(Constant.INTENT_news_KEY,news.get(position));
        startActivity(intent);
    }
}
