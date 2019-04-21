package com.spit.fest.oculus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    private List<Friends> list;
    private Context context;
    private boolean flag;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName, members;
        public ImageView groupImg;
        public TextView join;
        private ImageView check;
        private ProgressBar mProgressBar;

        public MyViewHolder(View view) {
            super(view);
            groupName= view.findViewById(R.id.group_name);
            members = (TextView) view.findViewById(R.id.members);
            groupImg = (ImageView) view.findViewById(R.id.profile_view);
            join=(TextView) view.findViewById(R.id.join);
            check= (ImageView) view.findViewById(R.id.check);
            mProgressBar= (ProgressBar) view.findViewById(R.id.indeterminateBar);
        }
    }


    public FriendsAdapter(List<Friends> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friends, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Friends groupDetailsModel = list.get(position);
        holder.groupName.setText(groupDetailsModel.getGroupName());
        holder.members.setText(groupDetailsModel.getMembers());
        holder.groupImg.setImageResource((groupDetailsModel.getImageResourceId()));
        //ImageUtils.loadImg(context, groupDetailsModel.getImageResourceId(), holder.groupImg);


        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.join.setVisibility(View.GONE);


                final android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 2s = 2000ms
                        holder.mProgressBar.setVisibility(View.GONE);
                        holder.check.setVisibility(View.VISIBLE);
                        holder.members.setText("2");
                        //GeneralUtils.toast(context,"Connected to the "+holder.groupName.getText());
                        flag=true;
                    }
                }, 2000);


            }
        });

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.check.setVisibility(View.GONE);


                final android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        holder.mProgressBar.setVisibility(View.GONE);
                        holder.join.setVisibility(View.VISIBLE);
                        holder.members.setText("1");
                        flag=false;
                    }
                }, 1000);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
