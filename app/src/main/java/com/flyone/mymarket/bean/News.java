package com.flyone.mymarket.bean;

/**
 * Created by Jacky Fu on 2018/4/23.
 */

public class News {
    private String name;
    private int imageId;
    private String lastNews;
    private int thingImageId;
    public News(int imageId, String name, String lastNews, int thingImageId) {
        this.imageId = imageId;
        this.name = name;
        this.lastNews = lastNews;
        this.thingImageId = thingImageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getLastNews() {
        return lastNews;
    }

    public int getThingImageId() {
        return thingImageId;
    }
}
