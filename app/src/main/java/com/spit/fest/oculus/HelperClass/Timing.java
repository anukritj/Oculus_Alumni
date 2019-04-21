package com.spit.fest.oculus.HelperClass;

public class Timing
{

    private String title;
    private int id;
    private String timing;
    private String category;

    public Timing(String title, int id, String timing, String category) {
        this.title = title;
        this.id = id;
        this.timing = timing;
        this.category = category;
    }

    public Timing() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
