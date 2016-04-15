package com.geili.bean;

/**
 * Created by Dong on 3/30/2016.
 */
public class GameGift {
    private String gameImg;
    private String gameTitle;
    private String gameInfo;
    private int progress;
    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getGameImg() {
        return gameImg;
    }

    public void setGameImg(String gameImg) {
        this.gameImg = gameImg;
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(String gameInfo) {
        this.gameInfo = gameInfo;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
