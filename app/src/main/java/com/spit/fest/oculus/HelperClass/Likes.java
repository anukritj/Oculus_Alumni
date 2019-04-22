package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Likes
{
    @PrimaryKey(autoGenerate = true)
    private int likeId;
    @ForeignKey(entity = Post.class, parentColumns = {"postId"}, childColumns = {"postId"}, onDelete = ForeignKey.CASCADE)
    private int postId;
    private String userId;

    @Ignore
    public Likes(int postId, String userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Likes() {
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
