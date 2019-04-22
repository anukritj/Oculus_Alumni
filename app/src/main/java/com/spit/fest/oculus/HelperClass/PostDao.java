package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PostDao
{
    @Insert
    void insertPost(Post post);

    @Query("select * from Post")
    List<Post> fetchPosts();
}
