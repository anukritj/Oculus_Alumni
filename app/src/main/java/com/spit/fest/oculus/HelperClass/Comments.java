package com.spit.fest.oculus.HelperClass;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Comments
{
    @PrimaryKey(autoGenerate = true)
    private int commentId;
    @ForeignKey(entity = Post.class, parentColumns = {"postId"}, childColumns = {"postId"}, onDelete = ForeignKey.CASCADE)
    private int postId;
    private String userId;
    private String comment;

    @Ignore
    public Comments(int postId, String userId, String comment) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
    }

    public Comments() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
