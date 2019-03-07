package com.basemosama.fnhelper.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.Constants.Functions;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ChallengesAdapter;
import com.basemosama.fnhelper.objects.ChallengesObjects.Challenges;
import com.basemosama.fnhelper.objects.ChallengesObjects.SeasonChallenges;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChallengesFragment extends Fragment {
    private RecyclerView challengesRecyclerView;
    private ChallengesAdapter challengesAdapter;
    private Call<Challenges> getChallenges;
    private SeasonChallenges seasonChallenges=new SeasonChallenges();

    public ChallengesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.challenges_fragment,container,false);
        challengesRecyclerView =view.findViewById(R.id.challenges_rv);
        if(savedInstanceState!=null){
            seasonChallenges=savedInstanceState.getParcelable(Constant.CHALLENGES_BUNDLE_KEY);
        }

        challengesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        challengesAdapter =new ChallengesAdapter(getContext(), seasonChallenges);

        challengesRecyclerView.setAdapter(challengesAdapter);

        if(savedInstanceState == null && getContext()!=null) {
            if (Functions.isNetworkAvailable(getContext())) {
                getChallenges();
            } else {
                Toast.makeText(getContext(), getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }

    public void getChallenges(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService=retrofit.create(CosmeticService.class);
        getChallenges= cosmeticService.getChallenges();
        getChallenges.enqueue(new Callback<Challenges>() {
            @Override
            public void onResponse(Call<Challenges> call, Response<Challenges> response) {
                if (response.body() != null) {

                     seasonChallenges =response.body().getChallenges();
                     challengesAdapter.updateAdapter(seasonChallenges);
                }
            }

            @Override
            public void onFailure(Call<Challenges> call, Throwable t) {
                Log.i(getClass().getName(),t.getLocalizedMessage());
                Toast.makeText(getContext(), R.string.retrofit_error_message,Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getChallenges !=null){
            getChallenges.cancel();
        }
        challengesRecyclerView.setAdapter(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(Constant.CHALLENGES_BUNDLE_KEY,seasonChallenges);
    }
}
