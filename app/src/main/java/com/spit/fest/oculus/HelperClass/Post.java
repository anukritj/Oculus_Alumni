package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Post
{
    @PrimaryKey(autoGenerate = true)
    private int postId;
    private String username;
    private String profilePicture;
    private String imageUrl;
    private String status;

    @Ignore
    public Post(String username, String profilePicture, String imageUrl, String status) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Post() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
