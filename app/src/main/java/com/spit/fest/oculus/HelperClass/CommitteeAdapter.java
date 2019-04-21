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

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.CommitteeViewHolder>
{

    private Context context;
    private ArrayList<Committee> commiteeList;
    private LayoutInflater layoutInflater;

    public CommitteeAdapter(Context context)
    {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        commiteeList = new ArrayList<>();
    }

    public void addCommitteeMember(Committee committee)
    {
        commiteeList.add(committee);
        notifyItemInserted(commiteeList.size()-1);
    }

    @NonNull
    @Override
    public CommitteeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.comittee_each,viewGroup,false);
        return new CommitteeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitteeViewHolder committeeViewHolder, int i) {
        Committee committee = commiteeList.get(i);
        committeeViewHolder.setImage(committee.getUrl());
        committeeViewHolder.setName(committee.getName());
        committeeViewHolder.setPost(committee.getPost());
    }

    @Override
    public int getItemCount() {
        return commiteeList.size();
    }

    class CommitteeViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView memberPhoto;
        private TextView name;
        private TextView post;

        CommitteeViewHolder(@NonNull View itemView) {
            super(itemView);
            memberPhoto = itemView.findViewById(R.id.imageMember);
            name = itemView.findViewById(R.id.txtMemberName);
            post = itemView.findViewById(R.id.txtMemberPost);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

        void setName(String memberName)
        {
            name.setText(memberName);
        }

        void setPost(String postText)
        {
            post.setText(postText);
        }

        void setImage(String imageUrl)
        {
//            memberPhoto.setImageDrawable(context.getDrawable(R.drawable.t_1));
            ImageUtils.newInstance().setImage(context, imageUrl, memberPhoto, null);
        }
    }
}
