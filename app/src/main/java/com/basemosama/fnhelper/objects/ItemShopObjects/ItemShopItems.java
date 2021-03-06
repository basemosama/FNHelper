package com.basemosama.fnhelper.objects.ItemShopObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.CosmeticRating;
import com.basemosama.fnhelper.objects.ImagesObjects.ItemImages;

public class ItemShopItems implements Parcelable {
    private String itemid;
    private String name;
    private String cost;
    private int featured;
    private int refundable;
    private long lastupdate;
    private String youtube;
    private ItemImages item;
    private String captial;
    private String type;
    private String rarity;
    private String obtained_type;
    private CosmeticRating ratings;

    public ItemShopItems(String itemid, String name, String cost, int featured, int refundable,
                         long lastupdate, String youtube, ItemImages item, String captial,
                         String type, String rarity, String obtained_type, CosmeticRating ratings) {
        this.itemid = itemid;
        this.name = name;
        this.cost = cost;
        this.featured = featured;
        this.refundable = refundable;
        this.lastupdate = lastupdate;
        this.youtube = youtube;
        this.item = item;
        this.captial = captial;
        this.type = type;
        this.rarity = rarity;
        this.obtained_type = obtained_type;
        this.ratings = ratings;
    }

    protected ItemShopItems(Parcel in) {
        itemid = in.readString();
        name = in.readString();
        cost = in.readString();
        featured = in.readInt();
        refundable = in.readInt();
        lastupdate = in.readLong();
        youtube = in.readString();
        captial = in.readString();
        type = in.readString();
        rarity = in.readString();
        obtained_type = in.readString();
    }

    public static final Creator<ItemShopItems> CREATOR = new Creator<ItemShopItems>() {
        @Override
        public ItemShopItems createFromParcel(Parcel in) {
            return new ItemShopItems(in);
        }

        @Override
        public ItemShopItems[] newArray(int size) {
            return new ItemShopItems[size];
        }
    };

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public void setRefundable(int refundable) {
        this.refundable = refundable;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public void setItem(ItemImages item) {
        this.item = item;
    }

    public void setCaptial(String captial) {
        this.captial = captial;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setObtained_type(String obtained_type) {
        this.obtained_type = obtained_type;
    }

    public void setRatings(CosmeticRating ratings) {
        this.ratings = ratings;
    }

    public String getCaptial() {
        return captial;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public String getObtained_type() {
        return obtained_type;
    }

    public CosmeticRating getRatings() {
        return ratings;
    }


    public String getItemid() {
        return itemid;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public int getFeatured() {
        return featured;
    }

    public int getRefundable() {
        return refundable;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public String getYoutube() {
        return youtube;
    }

    public ItemImages getItem() {
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemid);
        parcel.writeString(name);
        parcel.writeString(cost);
        parcel.writeInt(featured);
        parcel.writeInt(refundable);
        parcel.writeLong(lastupdate);
        parcel.writeString(youtube);
        parcel.writeString(captial);
        parcel.writeString(type);
        parcel.writeString(rarity);
        parcel.writeString(obtained_type);
    }
}
