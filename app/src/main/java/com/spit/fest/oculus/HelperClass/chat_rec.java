package com.spit.fest.oculus.HelperClass;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.spit.fest.oculus.R;

public class chat_rec extends RecyclerView.ViewHolder {

    public TextView leftText;
    public TextView rightText;

    public chat_rec(View itemView){
        super(itemView);

        leftText = itemView.findViewById(R.id.leftText);
        rightText = itemView.findViewById(R.id.rightText);

    }
}
