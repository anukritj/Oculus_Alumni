package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Post
{
    @PrimaryKey(autoGenerate = true)
    private int postId;
    private String imageUrl;
    private String status;

    @Ignore
    public Post(String imageUrl, String status) {
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Post() {
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
