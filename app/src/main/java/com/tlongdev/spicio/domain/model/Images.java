package com.tlongdev.spicio.domain.model;

/**
 * @author Long
 * @since 2016. 03. 03.
 */
public class Images {

    private Image fanArt;

    private Image poster;

    private Image logo;

    private Image clearArt;

    private Image banner;

    private Image thumb;

    private Image screenshot;

    public Image getFanArt() {
        return fanArt;
    }

    public void setFanArt(Image fanArt) {
        this.fanArt = fanArt;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Image getClearArt() {
        return clearArt;
    }

    public void setClearArt(Image clearArt) {
        this.clearArt = clearArt;
    }

    public Image getBanner() {
        return banner;
    }

    public void setBanner(Image banner) {
        this.banner = banner;
    }

    public Image getThumb() {
        return thumb;
    }

    public void setThumb(Image thumb) {
        this.thumb = thumb;
    }

    public Image getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(Image screenshot) {
        this.screenshot = screenshot;
    }
}
