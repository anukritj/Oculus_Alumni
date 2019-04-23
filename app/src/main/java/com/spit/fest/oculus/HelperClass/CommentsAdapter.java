package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>
{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Comments> commentsArrayList;

    public CommentsAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        commentsArrayList = new ArrayList<>();
    }

    public void populateArrayList(int postId)
    {
        commentsArrayList.clear();
        commentsArrayList.addAll(FeedDatabase.getInstance(context).commentsDao().fetchComments(postId));
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentsViewHolder(layoutInflater.inflate(R.layout.item_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder commentsViewHolder, int i) {
        Comments comments = commentsArrayList.get(i);
        commentsViewHolder.setUsername(comments.getUsername());
        commentsViewHolder.setComment(comments.getComment());
        commentsViewHolder.setImageView(comments.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder
    {
        private TextView username;
        private TextView comment;
        private ImageView imageView;

        CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_username);
            comment = itemView.findViewById(R.id.comment_text);
            imageView = itemView.findViewById(R.id.comment_icon);
        }

        void setUsername(String username)
        {
            this.username.setText(username);
        }

        void setComment(String comment)
        {
            this.comment.setText(comment);
        }

        void setImageView(String imageUrl)
        {
            ImageUtils.newInstance().setImageWithPlaceholder(context,imageUrl, imageView, R.drawable.ic_person_black_24dp);
        }

    }
}
