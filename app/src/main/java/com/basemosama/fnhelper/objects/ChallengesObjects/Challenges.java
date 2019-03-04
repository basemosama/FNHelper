package com.basemosama.fnhelper.objects.ChallengesObjects;

public class Challenges {
    private String language;
    private int season;
    private int currentweek;
    private String star;
    private SeasonChallenges challenges;

    public Challenges(String language, int season, int currentweek, String star, SeasonChallenges challenges) {
        this.language = language;
        this.season = season;
        this.currentweek = currentweek;
        this.star = star;
        this.challenges = challenges;
    }

    public String getLanguage() {
        return language;
    }

    public int getSeason() {
        return season;
    }

    public int getCurrentweek() {
        return currentweek;
    }

    public String getStar() {
        return star;
    }

    public SeasonChallenges getChallenges() {
        return challenges;
    }
}
