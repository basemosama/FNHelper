package com.basemosama.fnhelper.objects.CosmeticItemsObjects;

import android.arch.persistence.room.Embedded;
import android.text.TextUtils;

import com.basemosama.fnhelper.objects.ImagesObjects.FeaturedImages;

import java.util.ArrayList;
import java.util.List;

public class CosmeticImages {
    private String transparent;
    private String background;
    private String info;
    private String information;
    @Embedded(prefix = "featured")
    private FeaturedImages featured;

    public CosmeticImages(String transparent, String background, String info, String information, FeaturedImages featured) {
        this.transparent = transparent;
        this.background = background;
        this.info = info;
        this.information = information;
        this.featured = featured;
    }

    public String getTransparent() {
        return transparent;
    }

    public String getBackground() {
        return background;
    }

    public String getInfo() {
        return info;
    }

    public String getInformation() {
        return information;
    }

    public FeaturedImages getFeatured() {
        return featured;
    }

    public static List<String> getImages(CosmeticImages cosmeticImages){
        List<String> images=new ArrayList<>();
        if(!TextUtils.isEmpty(cosmeticImages.getFeatured().getTransparent()))
            images.add(cosmeticImages.getFeatured().getTransparent());
        if(!TextUtils.isEmpty(cosmeticImages.getTransparent()))
        images.add(cosmeticImages.getTransparent());
        if(!TextUtils.isEmpty(cosmeticImages.getBackground()))
            images.add(cosmeticImages.getBackground());
        if(!TextUtils.isEmpty(cosmeticImages.getInfo()))
            images.add(cosmeticImages.getInfo());
        return images;

    }
}
