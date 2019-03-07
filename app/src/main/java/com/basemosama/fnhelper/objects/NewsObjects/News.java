package com.basemosama.fnhelper.objects.NewsObjects;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class News  {
    private String type;
    private String typesm;
    private ArrayList<NewsEntries> entries;

    public News(String type, String typesm, ArrayList<NewsEntries> entries) {
        this.type = type;
        this.typesm = typesm;
        this.entries = entries;
    }

    public String getType() {
        return type;
    }

    public String getTypesm() {
        return typesm;
    }

    public ArrayList<NewsEntries> getEntries() {
        return entries;
    }
}
