package com.basemosama.fnhelper.objects.ImagesObjects;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.CosmeticImages;

public class ItemImages {
       private String image;
       private CosmeticImages images;

    public ItemImages(String image, CosmeticImages images) {
        this.image = image;
        this.images = images;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImages(CosmeticImages images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public CosmeticImages getImages() {
        return images;
    }
}
