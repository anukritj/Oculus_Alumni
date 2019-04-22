package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>
{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Post> postArrayList;

    public DashboardAdapter(Context context)
    {
        this.context = context;
        postArrayList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void populateArrayList()
    {
        postArrayList.clear();
        postArrayList.addAll(FeedDatabase.getInstance(context).postDao().fetchPosts());
    }

//    public void addPost(Post post)
//    {
//        postArrayList.add(post);
//        notifyItemChanged(postArrayList.size() - 1);
//    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DashboardViewHolder(layoutInflater.inflate(R.layout.item_dashboard, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DashboardViewHolder dashboardViewHolder, int i) {
        final Post post = postArrayList.get(i);
        dashboardViewHolder.setImage(post.getImageUrl());
        dashboardViewHolder.setStatus(post.getStatus());
        dashboardViewHolder.setLikes(FeedDatabase.getInstance(context).likesDao().fetchLikesCount(post.getPostId()),
                                                FeedDatabase.getInstance(context).likesDao().checkLike(post.getPostId()
                                                , Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
        dashboardViewHolder.likeConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Likes likes = FeedDatabase.getInstance(context).likesDao().checkLike(post.getPostId(), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                if (likes == null)
                    FeedDatabase.getInstance(context).likesDao().insertLike(new Likes(post.getPostId(), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
                else
                    FeedDatabase.getInstance(context).likesDao().removeLike(post.getPostId(), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

                dashboardViewHolder.setLikes(FeedDatabase.getInstance(context).likesDao().fetchLikesCount(post.getPostId()),
                        FeedDatabase.getInstance(context).likesDao().checkLike(post.getPostId()
                                , Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView imageView;
        private TextView textView;
        ConstraintLayout likeConstraintLayout;
        private TextView likeTextView;
        private ConstraintLayout commentConstraintLayout;

        DashboardViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.dashboard_image);
            textView = itemView.findViewById(R.id.dashboard_status);
            likeConstraintLayout = itemView.findViewById(R.id.likeConstraintLayout);
            likeTextView = itemView.findViewById(R.id.likeTextView);
            commentConstraintLayout = itemView.findViewById(R.id.commentConstraintLayout);
        }

        void setImage(String imageUrl)
        {
            if (imageUrl == null)
                imageView.setVisibility(View.GONE);
            else
                ImageUtils.newInstance().setImage(context, imageUrl, imageView, null);
        }

        void setStatus(String status)
        {
            if (status == null || status.isEmpty())
                textView.setVisibility(View.GONE);
            else
                textView.setText(status);
        }

        void setLikes(int count, Likes likes)
        {
            likeTextView.setText(NumberFormat.getInstance(Locale.ENGLISH).format(count) + " likes");
            if (likes != null)
            {
                likeTextView.setTextColor(context.getResources().getColor(R.color.red));
                likeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_black_24dp, 0, 0, 0);
            }
            else
            {
                likeTextView.setTextColor(context.getResources().getColor(R.color.black));
                likeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_black_1_24dp, 0, 0, 0);
            }
        }

    }
}
