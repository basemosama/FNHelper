package com.basemosama.fnhelper.objects;


public class CosmeticItem {
      private String  identifier;
      private String name;
      private String description;
      private int cost;
      private String type;
      private String rarity;
      private int upcoming;
      private CosmeticImages images;

    public CosmeticItem(String identifier, String name, String description, int cost, String type,
                        String rarity, int upcoming, CosmeticImages images, CosmeticRating ratings) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.rarity = rarity;
        this.upcoming = upcoming;
        this.images = images;
        this.ratings = ratings;
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

    private CosmeticRating ratings;



        }
