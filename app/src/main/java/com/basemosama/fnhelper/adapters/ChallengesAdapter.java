package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.ChallengesObjects.SeasonChallenges;
import com.basemosama.fnhelper.objects.ChallengesObjects.WeekChallenges;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.ChallengesViewHolder> {
    private Context context;
    private SeasonChallenges challenges;
    private boolean isVisible=false;
    public ChallengesAdapter(Context context, SeasonChallenges challenges) {
        this.context = context;
        this.challenges = challenges;

    }
    @NonNull
    @Override
    public ChallengesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view = LayoutInflater.from(context).inflate(R.layout.challenges_item,viewGroup,false);
        return new ChallengesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesViewHolder challengesViewHolder, int position) {
        challengesViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(challenges==null){
        return 0;}
        return challenges.getWeekChallenges().size();
    }

    public void updateAdapter(SeasonChallenges newChallenges){
        if(challenges!=null){
            challenges=newChallenges;
        notifyDataSetChanged();
    }
    }

     class ChallengesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , WeekChallengeAdapter.ChallengeClickListener {
        private TextView challengeName;
        private TextView notAvailableChallenge;
        private RecyclerView weekChallengeRecyclerView;
        ImageView arrowImage;
        private ChallengesViewHolder(@NonNull View itemView) {
            super(itemView);

        challengeName=itemView.findViewById(R.id.challenge_name);
        weekChallengeRecyclerView=itemView.findViewById(R.id.week_rv);
            notAvailableChallenge=itemView.findViewById(R.id.challenge_not_available);
             arrowImage = itemView.findViewById(R.id.challenge_scroll_image);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            weekChallengeRecyclerView.setLayoutManager(linearLayoutManager);
        itemView.setOnClickListener(this);

        }
        private void bind(int position){

            int weekNumber=position+1;
            String name =context.getString(R.string.week)+weekNumber;
            challengeName.setText(name);
           List<WeekChallenges> weekChallenges=challenges.getWeekChallenges().get(position);
            WeekChallengeAdapter weekChallengeAdapter=new WeekChallengeAdapter(context,weekChallenges,this);
            weekChallengeRecyclerView.setAdapter(weekChallengeAdapter);

        }


         @Override
         public void onClick(View view) {

             if(isVisible){
                 weekChallengeRecyclerView.setVisibility(View.GONE);
                 notAvailableChallenge.setVisibility(View.GONE);
                 arrowImage.setImageResource(R.drawable.arrow_down);
                 arrowImage.setContentDescription(context.getString(R.string.expand_challenges));
                 isVisible=false;
             }else {
                 weekChallengeRecyclerView.setVisibility(View.VISIBLE);
                 arrowImage.setImageResource(R.drawable.arrow_up);
                 arrowImage.setContentDescription(context.getString(R.string.collapse_challenges));
                 if(challenges.getWeekChallenges().get(getAdapterPosition()).size()==0)
                     notAvailableChallenge.setVisibility(View.VISIBLE);

                 isVisible=true;
             }
         }

         @Override
         public void onChallengeClickListener(int position) {
            String challengeText=challenges.getWeekChallenges().get(getAdapterPosition()).get(position).getChallenge()+" "
                    +challenges.getWeekChallenges().get(getAdapterPosition()).get(position).getTotal()+" times";
             Toast.makeText(context,challengeText,Toast.LENGTH_SHORT).show();
         }
     }
}
