package com.basemosama.fnhelper.objects.ItemShopObjects;

import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;

import java.util.List;

public class UpcomingItems {
    private int rows;
    private String vbucks;
    private List<ItemShopItems> items;

    public UpcomingItems(int rows, String vbucks, List<ItemShopItems> items) {
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

    public List<ItemShopItems> getItems() {
        return items;
    }
}
