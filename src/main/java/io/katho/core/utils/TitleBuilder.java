package io.katho.core.utils;

public class TitleBuilder {

    private String title;
    private String subtitle;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;

    public TitleBuilder() {
        this.title = "";
        this.subtitle = "";
        this.fadeInTime = 1;
        this.stayTime = 1;
        this.fadeOutTime = 1;
    }

    public String getTitle() {
        return title;
    }

    public TitleBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public TitleBuilder setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public int getFadeInTime() {
        return fadeInTime;
    }

    public TitleBuilder setFadeInTime(int fadeInTime) {
        this.fadeInTime = fadeInTime;
        return this;
    }

    public int getStayTime() {
        return stayTime;
    }

    public TitleBuilder setStayTime(int stayTime) {
        this.stayTime = stayTime;
        return this;
    }

    public int getFadeOutTime() {
        return fadeOutTime;
    }

    public TitleBuilder setFadeOutTime(int fadeOutTime) {
        this.fadeOutTime = fadeOutTime;
        return this;
    }

    public Title build() {
        return new Title(this.title, this.subtitle, this.fadeInTime, this.stayTime, this.fadeOutTime);
    }

}
