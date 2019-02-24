package com.basemosama.fnhelper.utility;

import com.basemosama.fnhelper.objects.CosmeticItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CosmeticService {

    @GET("/store/get?language=en")
    Call<List<CosmeticItem>>getCosmeticItems();

}
