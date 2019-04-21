package com.spit.fest.oculus.HelperClass;

public class Committee
{
    private String url;
    private String name;
    private String post;

    public Committee(String url, String name, String post) {
        this.url = url;
        this.name = name;
        this.post = post;
    }

    public Committee() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
