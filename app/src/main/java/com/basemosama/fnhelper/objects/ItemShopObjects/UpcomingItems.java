package com.basemosama.fnhelper.objects.ItemShopObjects;

import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;

import java.util.ArrayList;
import java.util.List;

public class UpcomingItems {
    private int rows;
    private String vbucks;
    private ArrayList<ItemShopItems> items;

    public UpcomingItems(int rows, String vbucks, ArrayList<ItemShopItems> items) {
        this.rows = rows;
        this.vbucks = vbucks;
        this.items = items;
    }

    public int getRows() {
        return rows;
    }

    public String getVbucks() {
        return vbucks;
    }

    public ArrayList<ItemShopItems> getItems() {
        return items;
    }
}
