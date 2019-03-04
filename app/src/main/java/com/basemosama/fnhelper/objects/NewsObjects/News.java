package com.basemosama.fnhelper.objects.NewsObjects;

import java.util.List;

public class News {
    private String type;
    private String typesm;
    private List<NewsEntries> entries;

    public News(String type, String typesm, List<NewsEntries> entries) {
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

    public List<NewsEntries> getEntries() {
        return entries;
    }
}
