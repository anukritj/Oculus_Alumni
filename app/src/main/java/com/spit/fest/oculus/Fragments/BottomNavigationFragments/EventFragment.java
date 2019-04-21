package com.spit.fest.oculus.Fragments.BottomNavigationFragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.Fragments.EventCategories.CulturalFragment;
import com.spit.fest.oculus.Fragments.EventCategories.FeaturedFragment;
import com.spit.fest.oculus.Fragments.EventCategories.FunFragment;
import com.spit.fest.oculus.Fragments.EventCategories.LiteraryFragment;
import com.spit.fest.oculus.Fragments.EventCategories.TechFragment;
import com.spit.fest.oculus.Fragments.OtherFragments.VotingFragment;
import com.spit.fest.oculus.HelperClass.CustomTabLayout;
import com.spit.fest.oculus.HelperClass.TabLayoutAdapter;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static EventFragment eventFragment;


    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        MainActivity.fragment = getEventFragment();
        MainActivity.navigation.setSelectedItemId(R.id.navigation_events);
        requestPermissions();
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));

        final View view = inflater.inflate(R.layout.fragment_event, container, false);

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Voting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                long value = (long) (Objects.requireNonNull(((Objects.requireNonNull(dataSnapshot.child("enabled"))).getValue())));
                if (value == 1 && !dataSnapshot.hasChild(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {

                    Snackbar.make(view.findViewById(R.id.viewPager), "WoB voting lines are open.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("VOTE NOW", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    handleVoting();
                                    if (dataSnapshot.hasChild(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {

                                        Toast.makeText(getContext(),"You have voted!",Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                                            return;

                                        client.getLastLocation().addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Location>() {
                                            @Override
                                            public void onSuccess(Location location) {
                                                if (location != null) {
                                                    double lat = location.getLatitude();
                                                    double lon = location.getLongitude();
                                                    double spitLat = (double)dataSnapshot.child("latitude").getValue();
                                                    double spitLong = (double)dataSnapshot.child("longitude").getValue();
                                                    if(canVote(lat, lon, spitLat, spitLong)){
                                                        ArrayList<String> params = new ArrayList<>();
                                                        params.add(mAuth.getCurrentUser().getUid());
                                                        params.add("https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/Events%2FApp%2Fwarofbranch.jpeg?alt=media&token=f8484b77-6c7d-4680-ba4d-b3db7440bab1");
                                                        VotingFragment votingFragment = VotingFragment.newInstance(params);
                                                        votingFragment.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), "vote_bottomsheet");
                                                    }
                                                    else{
                                                        Toast.makeText(getContext(),"You are away from S.P.I.T. Move closer to college and try again.",Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            }
                                        });
                                    }
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorAccent))
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        int count = 3;
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            count = bundle.getInt("Count", 1);
        }

        int index = count - 1;

        LiteraryFragment literaryFragment = new LiteraryFragment();
        FunFragment funFragment = new FunFragment();
        TechFragment techFragment = new TechFragment();
        CulturalFragment culturalFragment = new CulturalFragment();
        FeaturedFragment featuredFragment = new FeaturedFragment();

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> params = bundle.getStringArrayList("paramsTimeline");
            if (params != null) {
                String eventEmailParam = params.get(1);
                Bundle paramBundle = new Bundle();
                paramBundle.putInt("paramEventEmail", Integer.parseInt(eventEmailParam));
                paramBundle.putBoolean("fromFcm",bundle.getBoolean("fromFcm",false));
                switch (params.get(0)) {
                    case "Cultural":
                        index = 3;
                        culturalFragment.setArguments(paramBundle);
                        break;
                    case "Technical":
                        index = 2;
                        techFragment.setArguments(paramBundle);
                        break;
                    case "Literary":
                        index = 0;
                        literaryFragment.setArguments(paramBundle);
                        break;
                    case "Fun":
                        index = 1;
                        funFragment.setArguments(paramBundle);
                        break;
                    case "Featured":
                        index = 4;
                        featuredFragment.setArguments(paramBundle);
                        break;
                }
            }
        }

        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(Objects.requireNonNull(getChildFragmentManager()));
        CustomTabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        Log.e("EventFragment", "Entered EventFragment");
        tabLayoutAdapter.addFragment(literaryFragment, "Literary");
        tabLayoutAdapter.addFragment(funFragment, "Fun");
        tabLayoutAdapter.addFragment(techFragment, "Technical");
        tabLayoutAdapter.addFragment(culturalFragment, "Cultural");
        tabLayoutAdapter.addFragment(featuredFragment, "Featured");

        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        CustomTabLayout.Tab tab = tabLayout.getTabAt(index);
        assert tab != null;
        tab.select();
    }

    public static EventFragment getEventFragment() {
        if (eventFragment == null)
            eventFragment = new EventFragment();
        return eventFragment;
    }

    public void requestPermissions(){
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),new String[]{ACCESS_FINE_LOCATION},1);
    }

    private boolean canVote(double lat1, double long1, double lat2, double long2)
    {
        int radius = 6371;
        double diffLat = Math.toRadians(lat2 - lat1);
        double diffLong = Math.toRadians(long2 - long1);
        double temp1 = Math.pow(Math.sin(diffLat / 2), 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(diffLong / 2), 2);
        double temp2 = 2 * Math.atan2(Math.sqrt(temp1), Math.sqrt(1-temp1));
        double d = radius * temp2;
        Log.e("Distance is: ",String.valueOf(d));
        return d <= 1;
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