package com.basemosama.fnhelper.utility;

import com.basemosama.fnhelper.objects.ChallengesObjects.Challenges;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.basemosama.fnhelper.objects.NewsObjects.News;
import com.basemosama.fnhelper.objects.ItemShopObjects.UpcomingItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CosmeticService {

    @GET("items/list")
    Call<List<MainItem>>getCosmeticItems();

    @GET("store/get?language=en")
    Call<ItemShop>getItemShop();

    @GET("upcoming/get")
    Call<UpcomingItems>getUpcomingItems();

    @GET("challenges/get?season=current")
    Call<Challenges>getChallenges();

    @GET("br_motd/get?language=en")
    Call<News>getNews();

    @GET("item/get")
    Call<MainItem>getMainItem(@Query("ids") String id);


}
