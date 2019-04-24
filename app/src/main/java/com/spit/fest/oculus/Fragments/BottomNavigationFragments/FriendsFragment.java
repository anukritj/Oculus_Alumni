package com.spit.fest.oculus.Fragments.BottomNavigationFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spit.fest.oculus.HelperClass.Friends;
import com.spit.fest.oculus.HelperClass.FriendsAdapter;
import com.spit.fest.oculus.R;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private List<Friends> details = new ArrayList<>();
    private RecyclerView recyclerView;
    private FriendsAdapter mAdapter;
    private static FriendsFragment homeFragment;

    public FriendsFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_recycler_view, container, false);




        /*getSupportActionBar().setTitle("Find a Group"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar*/

        recyclerView =  rootView.findViewById(R.id.recycler_view);

        mAdapter = new FriendsAdapter(details,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        /*ivBack = findViewById(R.id.ivback);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        prepareGroupData();
        //return rootView;
        return rootView;
    }
    //..
    private void prepareGroupData() {
        details.add(new Friends("Anukrit Jain","1", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Divya Kamath","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Azain Jaffer","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Shloka Sapru","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Urvi Bhanushali","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Mukund Vora","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Aashay Jain","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Urvee Chaudhari","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Atul Chitanvis","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Sumegh Dave","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Uzair Kadam","0", R.drawable.ic_account_box_black_24dp));
        details.add(new Friends("Gargi Agnihotri","0", R.drawable.ic_account_box_black_24dp));

        details.add(new Friends("Harsh Dave","0", R.drawable.ic_account_box_black_24dp));



        mAdapter.notifyDataSetChanged();
    }
    public static FriendsFragment getHomeFragment() {
        if (homeFragment == null)
            homeFragment = new FriendsFragment();
        return homeFragment;
    }
}

