package com.geili.bean;

import java.io.Serializable;

/**
 * Created by Dong on 4/4/2016.
 */
public class Game implements Serializable {

    private String id;
    private String image;
    private String title;
    private String size;
    private String downloadNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownloadNumber() {
        return downloadNumber;
    }

    public void setDownloadNumber(String downloadNumber) {
        this.downloadNumber = downloadNumber;
    }
}
