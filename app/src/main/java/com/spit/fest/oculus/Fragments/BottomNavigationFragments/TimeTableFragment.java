package com.spit.fest.oculus.Fragments.BottomNavigationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.Event;
import com.spit.fest.oculus.HelperClass.EventTimeline;
import com.spit.fest.oculus.HelperClass.EventTimelineAdapter;
import com.spit.fest.oculus.HelperClass.Timing;
import com.spit.fest.oculus.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {

    private static TimeTableFragment timeTableFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    public static TimeTableFragment getTimeTableFragment() {
        if (timeTableFragment == null)
            timeTableFragment = new TimeTableFragment();
        return timeTableFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    Intent intent = new Intent(getContext(), SwipeActivity.class);
                    startActivity(intent);
                }
            }
        };

        MainActivity.fragment = getTimeTableFragment();
        MainActivity.navigation.setSelectedItemId(R.id.navigation_itinerary);

        final View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        final ProgressBar progressBar = view.findViewById(R.id.time_table_progress);
//        progressBar.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = view.findViewById(R.id.timeline_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        final EventTimelineAdapter eventTimelineAdapter = new EventTimelineAdapter(getContext());

        final HashSet<String> dateSet = new HashSet<>();
        final HashMap<String, ArrayList<Timing>> dateMap = new HashMap<>();
        final ArrayList<Event> eventList = new ArrayList<>();

        com.spit.fest.oculus.Utils.DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    Event event = d.getValue(Event.class);
                    if (event != null)
                    {
                        dateSet.addAll(event.getDate());
                        eventList.add(event);
                    }
                }

                for (String date : dateSet)
                    dateMap.put(date,new ArrayList<Timing>());

                ArrayList<String> dateList = new ArrayList<>(dateSet);

                Collections.sort(dateList, new Comparator<String>() {
                    DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                    @Override
                    public int compare(String o1, String o2) {
                        try {
                            return f.parse(o1).compareTo(f.parse(o2));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });

                for(String date : dateList)
                {
                    for (Event e : eventList)
                    {
                        if (e.getDate().contains(date))
                            Objects.requireNonNull(dateMap.get(date)).add(new Timing(e.getTitle(), e.getId(), e.getTiming(), e.getCategory()));
                    }
                }

                for (String date : dateList)
                    eventTimelineAdapter.addEventsToTimeline(new EventTimeline(date, dateMap.get(date)));
                recyclerView.setAdapter(eventTimelineAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
