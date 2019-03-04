package com.basemosama.fnhelper.objects.ChallengesObjects;

import java.util.ArrayList;
import java.util.List;

public class SeasonChallenges {
    private List<WeekChallenges> week1 ;
    private List<WeekChallenges> week2 ;
    private List<WeekChallenges> week3 ;
    private List<WeekChallenges> week4 ;
    private List<WeekChallenges> week5 ;
    private List<WeekChallenges> week6 ;
    private List<WeekChallenges> week7 ;
    private List<WeekChallenges> week8 ;
    private List<WeekChallenges> week9 ;
    private List<WeekChallenges> week10;


    public SeasonChallenges(List<WeekChallenges> week1, List<WeekChallenges> week2, List<WeekChallenges> week3, List<WeekChallenges> week4, List<WeekChallenges> week5,
                            List<WeekChallenges> week6, List<WeekChallenges> week7, List<WeekChallenges> week8, List<WeekChallenges> week9, List<WeekChallenges> week10) {
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.week5 = week5;
        this.week6 = week6;
        this.week7 = week7;
        this.week8 = week8;
        this.week9 = week9;
        this.week10 = week10;
    }


    public List<WeekChallenges> getWeek1() {
        return week1;
    }

    public List<WeekChallenges> getWeek2() {
        return week2;
    }

    public List<WeekChallenges> getWeek3() {
        return week3;
    }

    public List<WeekChallenges> getWeek4() {
        return week4;
    }

    public List<WeekChallenges> getWeek5() {
        return week5;
    }

    public List<WeekChallenges> getWeek6() {
        return week6;
    }

    public List<WeekChallenges> getWeek7() {
        return week7;
    }

    public List<WeekChallenges> getWeek8() {
        return week8;
    }

    public List<WeekChallenges> getWeek9() {
        return week9;
    }

    public List<WeekChallenges> getWeek10() {
        return week10;
    }

    public List<List<WeekChallenges>> getWeekChallenges() {
         List<List<WeekChallenges>> weekChallenges=new ArrayList<>();
        weekChallenges.add(week1);
        weekChallenges.add(week2);
        weekChallenges.add(week3);
        weekChallenges.add(week4);
        weekChallenges.add(week5);
        weekChallenges.add(week6);
        weekChallenges.add(week7);
        weekChallenges.add(week8);
        weekChallenges.add(week9);
        weekChallenges.add(week10);
        return weekChallenges;
    }

    public SeasonChallenges() {
    }
}
