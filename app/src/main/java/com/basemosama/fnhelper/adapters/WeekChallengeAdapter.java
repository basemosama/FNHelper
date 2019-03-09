package com.basemosama.fnhelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.ChallengesObjects.WeekChallenges;

import java.util.List;

public class WeekChallengeAdapter extends RecyclerView.Adapter<WeekChallengeAdapter.challengesViewHolder> {
    private Context context;
    private List<WeekChallenges> challenges;
    private ChallengeClickListener challengeClickListener;

    public WeekChallengeAdapter(Context context, List<WeekChallenges> challenges, ChallengeClickListener challengeClickListener) {
        this.context = context;
        this.challenges = challenges;
        this.challengeClickListener = challengeClickListener;

    }

    public interface ChallengeClickListener {
        void onChallengeClickListener(int position);
    }

    @NonNull
    @Override
    public challengesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_challenges_item, viewGroup, false);
        return new challengesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull challengesViewHolder challengesViewHolder, int position) {
        challengesViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (challenges == null) {
            return 0;
        }
        return challenges.size();
    }

    public void updateAdapter(List<WeekChallenges> newChallenges) {
        if (challenges != null) {
            challenges.clear();
            challenges.addAll(newChallenges);
        }
        notifyDataSetChanged();
    }

    class challengesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView challenge;
        TextView challengeTotal;
        TextView challengeStars;

        private challengesViewHolder(@NonNull View itemView) {
            super(itemView);
            challenge = itemView.findViewById(R.id.challenge);
            challengeTotal = itemView.findViewById(R.id.challenge_total);
            challengeStars = itemView.findViewById(R.id.challenge_stars);
            itemView.setOnClickListener(this);
        }

        private void bind(int position) {
            challenge.setText(challenges.get(position).getChallenge());
            String totalNumber = "( " + String.valueOf(challenges.get(position).getTotal()) + " )";
            String stars = String.valueOf(challenges.get(position).getStars());
            challengeTotal.setText(totalNumber);
            challengeStars.setText(stars);

        }

        @Override
        public void onClick(View view) {
            challengeClickListener.onChallengeClickListener(getAdapterPosition());
        }
    }
}
