package com.basemosama.fnhelper.objects;

public class CosmeticRating {
    private double avgStars;
    private int totalPoints;
    private int numberVotes;

    public CosmeticRating(double avgStars, int totalPoints, int numberVotes) {
        this.avgStars = avgStars;
        this.totalPoints = totalPoints;
        this.numberVotes = numberVotes;
    }

    public double getAvgStars() {
        return avgStars;
    }

    public void setAvgStars(double avgStars) {
        this.avgStars = avgStars;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getNumberVotes() {
        return numberVotes;
    }

    public void setNumberVotes(int numberVotes) {
        this.numberVotes = numberVotes;
    }
}
