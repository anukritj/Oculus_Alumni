package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CommentsDao
{
    @Query("select * from Comments where postId=:postId")
    List<Comments> fetchComments(int postId);

    @Insert
    void insertComment(Comments comments);
}
