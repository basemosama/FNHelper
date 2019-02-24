package com.basemosama.fnhelper.objects;

public class CosmeticImages {
    private String transparent;
    private String background;
    private String info;

    public CosmeticImages(String transparent, String background, String info) {
        this.transparent = transparent;
        this.background = background;
        this.info = info;
    }

    public String getTransparent() {
        return transparent;
    }

    public void setTransparent(String transparent) {
        this.transparent = transparent;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
