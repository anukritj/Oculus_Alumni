package com.spit.fest.oculus.Fragments.BottomNavigationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.Fragments.Sponsors.SponsorFragment;
import com.spit.fest.oculus.Fragments.Sponsors.SponsorsNew;
import com.spit.fest.oculus.HelperClass.TabLayoutAdapter;
import com.spit.fest.oculus.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sponsors extends Fragment {

    View rootView;
    TabLayout tab;
    ViewPager pager;

    SponsorFragment oldSponsors;
    SponsorsNew newSponsors;

    private static Sponsors frag;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public Sponsors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        rootView =  inflater.inflate(R.layout.fragment_sponsors, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tab = (TabLayout)rootView.findViewById(R.id.sponsorsTab);
        pager = (ViewPager)rootView.findViewById(R.id.sponsorsViewPager);
//        Adapter adapter = new Adapter(getChildFragmentManager());
        TabLayoutAdapter adapter1 = new TabLayoutAdapter(getChildFragmentManager());
        adapter1.addFragment(new SponsorsNew(),"2019");
        adapter1.addFragment(new SponsorFragment(),"2018");

        pager.setAdapter(adapter1);
        tab.setupWithViewPager(pager);

    }



    public class Adapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
            fragments.add(new SponsorsNew());
            fragments.add(new SponsorFragment());

        }

        @Override
        public int getCount() {
            return 2;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "2K19";
                case 1:
                    return "2K18";


            }


            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int i) {
//            switch (i){
//                case 0:
//                    if(newSponsors == null){
//                        newSponsors = new SponsorsNew();
//                    }
//                    newSponsors = new SponsorsNew();
//                    return newSponsors;
//
//
//
//                case 1:
//                    if(oldSponsors == null){
//                        oldSponsors = new SponsorFragment();
//                    }
//                    oldSponsors = new SponsorFragment();
//
//
//                    return oldSponsors;
//
//
//            }
//
//
//            return null;
//        }

            return fragments.get(i);

        }
    }

    public static Sponsors getFrag(){
        if(frag == null){
            frag = new Sponsors();
        }

        return frag;
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
