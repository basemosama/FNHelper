package com.basemosama.fnhelper.objects.CosmeticItemsObjects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "favorites")
public class MainItem {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "cosmetic_identifier")
    private String identifier;
    @ColumnInfo(name = "cosmetic_name")
    private String name;
    private String description;
    private int cost;
    private String type;
    private String rarity;
    private int upcoming;
    private long lastupdate;
    private String obtained;
    private String obtained_type;
    @ColumnInfo(name = "today_store")
    private int todaystore;
    @Embedded
    private CosmeticImages images;
    @Embedded
    private CosmeticRating ratings;
    @Ignore
    private CosmeticSet set;
    @Embedded(prefix = "cosmetic_occurrences")
    private CosmeticOccurrences occurrences;

    public MainItem(int id, String identifier, String name, String description, int cost, String type,
                    String rarity, int upcoming, long lastupdate, String obtained, String obtained_type,
                    int todaystore, CosmeticImages images, CosmeticRating ratings, CosmeticOccurrences occurrences) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.rarity = rarity;
        this.upcoming = upcoming;
        this.lastupdate = lastupdate;
        this.obtained = obtained;
        this.obtained_type = obtained_type;
        this.todaystore = todaystore;
        this.images = images;
        this.ratings = ratings;
        this.occurrences = occurrences;
    }

    @Ignore
    public MainItem(String identifier, String name, String description, int cost, String type,
                    String rarity, int upcoming, long lastupdate, String obtained, String obtained_type,
                    int todaystore, CosmeticImages images, CosmeticRating ratings, CosmeticSet set
                    , CosmeticOccurrences occurrences) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.rarity = rarity;
        this.upcoming = upcoming;
        this.lastupdate = lastupdate;
        this.obtained = obtained;
        this.obtained_type = obtained_type;
        this.todaystore = todaystore;
        this.images = images;
        this.ratings = ratings;
        this.set = set;
        this.occurrences = occurrences;
    }

    //Setters and Getters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(int upcoming) {
        this.upcoming = upcoming;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getObtained() {
        return obtained;
    }

    public void setObtained(String obtained) {
        this.obtained = obtained;
    }

    public String getObtained_type() {
        return obtained_type;
    }

    public void setObtained_type(String obtained_type) {
        this.obtained_type = obtained_type;
    }

    public int getTodaystore() {
        return todaystore;
    }

    public void setTodaystore(int todaystore) {
        this.todaystore = todaystore;
    }

    public CosmeticImages getImages() {
        return images;
    }

    public void setImages(CosmeticImages images) {
        this.images = images;
    }

    public CosmeticRating getRatings() {
        return ratings;
    }

    public void setRatings(CosmeticRating ratings) {
        this.ratings = ratings;
    }

    public CosmeticSet getSet() {
        return set;
    }

    public void setSet(CosmeticSet set) {
        this.set = set;
    }

    public CosmeticOccurrences getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(CosmeticOccurrences occurrences) {
        this.occurrences = occurrences;
    }





}
