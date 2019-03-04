package com.basemosama.fnhelper.objects.CosmeticItemsObjects;

public class CosmeticOccurrences {
    private String first;
    private String last;
    private int occurrences;

    public CosmeticOccurrences(String first, String last, int occurrences) {
        this.first = first;
        this.last = last;
        this.occurrences = occurrences;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public int getOccurrences() {
        return occurrences;
    }
}