package com.basemosama.fnhelper.fragments;

import android.app.ActivityOptions;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.Constants.Functions;
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
    private ArrayList<NewsEntries> news =new ArrayList<>();
    private Call<News> getNews;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment,container,false);
        newsRecyclerView =view.findViewById(R.id.news_rv);
        int noOfColumns=1;
        if(getContext()!=null){
            noOfColumns=getContext().getResources().getInteger(R.integer.no_of_news_columns);
        }
        if(savedInstanceState!=null){
            news=savedInstanceState.getParcelableArrayList(Constant.NEWS_BUNDLE_KEY);
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),noOfColumns);
        newsRecyclerView.setLayoutManager(gridLayoutManager);
        newsAdapter =new NewsAdapter(getContext(), news,this);
        newsRecyclerView.setAdapter(newsAdapter);
        if(savedInstanceState == null && getContext()!=null) {
            if (Functions.isNetworkAvailable(getContext())) {
                getNewsItems();
            } else {
                Toast.makeText(getContext(), getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        }
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
                Log.i(getClass().getName(),t.getLocalizedMessage());
                if(getContext() !=null)
                    Toast.makeText(getContext(), R.string.retrofit_error_message,Toast.LENGTH_SHORT).show();


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
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constant.NEWS_BUNDLE_KEY,news);
    }

    @Override
    public void onNewsItemClickListener(int position, ImageView sharedImageView) {
        Toast.makeText(getContext(), news.get(position).getTitle(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), NewsActivity.class);
        intent.putExtra(Constant.INTENT_news_KEY,news.get(position));
        Bundle bundle= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.
                    makeSceneTransitionAnimation(
                            getActivity()
                            ,sharedImageView
                            ,sharedImageView.getTransitionName()
                    ).toBundle();
        }
        startActivity(intent,bundle);
    }
}
