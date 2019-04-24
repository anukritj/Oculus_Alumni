package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface LikesDao
{
    @Query("select count(likeId) from Likes where postId=:postId")
    int fetchLikesCount(int postId);

    @Insert
    void insertLike(Likes likes);

    @Query("select * from Likes where postId=:postId and userId=:userId")
    Likes checkLike(int postId, String userId);

    @Query("delete from Likes where postId=:postId and userId=:userId")
    void removeLike(int postId, String userId);
}
