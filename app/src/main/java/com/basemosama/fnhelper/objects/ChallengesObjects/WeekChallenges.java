package com.basemosama.fnhelper.objects.ChallengesObjects;

public class WeekChallenges {
    private String identifier;
    private String challenge;
    private int total;
    private int stars;
    private String difficulty;

    public WeekChallenges(String identifier, String challenge, int total, int stars, String difficulty) {
        this.identifier = identifier;
        this.challenge = challenge;
        this.total = total;
        this.stars = stars;
        this.difficulty = difficulty;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getTotal() {
        return total;
    }

    public int getStars() {
        return stars;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
