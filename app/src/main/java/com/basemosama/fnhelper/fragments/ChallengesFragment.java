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
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ChallengesAdapter;
import com.basemosama.fnhelper.objects.ChallengesObjects.Challenges;
import com.basemosama.fnhelper.objects.ChallengesObjects.SeasonChallenges;
import com.basemosama.fnhelper.utility.CosmeticService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChallengesFragment extends Fragment implements ChallengesAdapter.WeekChallengeClickListener {
    private RecyclerView challengesRecyclerView;
    private ChallengesAdapter challengesAdapter;
    private Call<Challenges> getChallenges;
    private SeasonChallenges seasonChallenges;

    public ChallengesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.challenges_fragment,container,false);
        challengesRecyclerView =view.findViewById(R.id.challenges_rv);
        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getContext());
        challengesRecyclerView.setLayoutManager(gridLayoutManager);
        getChallenges();

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
                    challengesAdapter =new ChallengesAdapter(getContext(), seasonChallenges,ChallengesFragment.this);
                    challengesRecyclerView.setAdapter(challengesAdapter);
                }
            }

            @Override
            public void onFailure(Call<Challenges> call, Throwable t) {
                Log.i("itemShop", t.getLocalizedMessage());

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
    public void onWeekChallengeClickListener(int position) {

        Toast.makeText(getContext(),seasonChallenges.getWeekChallenges().get(position).get(position).getChallenge(),Toast.LENGTH_SHORT);
    }
}
