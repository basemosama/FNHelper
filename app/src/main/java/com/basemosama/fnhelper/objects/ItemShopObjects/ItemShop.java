package com.basemosama.fnhelper.objects.ItemShopObjects;

import java.util.ArrayList;
import java.util.List;

public class ItemShop {
    private String date_layout;
    private long lastupdate;
    private String language;
    private String date;
    private int rows;
    private String vbucks;
    private ArrayList<ItemShopItems> items;

    public ItemShop(String date_layout, long lastupdate, String language, String date,
                    int rows, String vbucks, ArrayList<ItemShopItems> items) {
        this.date_layout = date_layout;
        this.lastupdate = lastupdate;
        this.language = language;
        this.date = date;
        this.rows = rows;
        this.vbucks = vbucks;
        this.items = items;
    }

    public String getDate_layout() {
        return date_layout;
    }

    public void setDate_layout(String date_layout) {
        this.date_layout = date_layout;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getVbucks() {
        return vbucks;
    }

    public void setVbucks(String vbucks) {
        this.vbucks = vbucks;
    }

    public ArrayList<ItemShopItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemShopItems> items) {
        this.items = items;
    }
}

