package com.spit.fest.oculus.HelperClass;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Likes.class, Comments.class, Post.class}, version = 1, exportSchema = false)
public abstract class FeedDatabase extends RoomDatabase
{
    private static FeedDatabase feedDatabase;
    public abstract PostDao postDao();
    public abstract LikesDao likesDao();
    public abstract CommentsDao commentsDao();

    public static FeedDatabase getInstance(Context context)
    {
        if (feedDatabase == null)
            feedDatabase = Room.databaseBuilder(context, FeedDatabase.class, "FeedDatabase").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        return feedDatabase;
    }
}
