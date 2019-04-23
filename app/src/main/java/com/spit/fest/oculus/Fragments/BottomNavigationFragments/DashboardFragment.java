package com.spit.fest.oculus.Fragments.BottomNavigationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.NewPost;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.DashboardAdapter;
import com.spit.fest.oculus.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private static DashboardFragment dashboardFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DashboardAdapter dashboardAdapter;
    private RecyclerView recyclerView;

    public DashboardFragment() {
        // Required empty public constructor
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

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dashboardAdapter = new DashboardAdapter(view.getContext());

        MainActivity.fragment = getDashboardFragment();
        MainActivity.navigation.setSelectedItemId(R.id.navigation_feed);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        ImageView profileImage = view.findViewById(R.id.show_profile);
//        ImageUtils.newInstance().setCircleCropImage(view.getContext(), Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl()).toString(), profileImage, null);
//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                replaceFragment(ProfileFragment.getProfileFragment());
//            }
//        });

        Toolbar toolbar = view.findViewById(R.id.dashboard_toolbar);
        ((AppCompatActivity)view.getContext()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) view.getContext()).getSupportActionBar()).setTitle("Dashboard");
//        ((AppCompatActivity)view.getContext()).setActionBar(toolbar);
        toolbar.inflateMenu(R.menu.dashboard_menu);

        recyclerView = view.findViewById(R.id.dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
//        dashboardAdapter.populateArrayList();
//        recyclerView.setAdapter(dashboardAdapter);

    }

    public static DashboardFragment getDashboardFragment()
    {
        if (dashboardFragment == null)
            dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = ((AppCompatActivity)Objects.requireNonNull(getContext())).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.navigation_fragment_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dashboard_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add)
        {
            Intent intent = new Intent(Objects.requireNonNull(getView()).getContext(), NewPost.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.action_profile)
            replaceFragment(ProfileFragment.getProfileFragment());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Function", "onResume");
        dashboardAdapter.populateArrayList();
        recyclerView.setAdapter(dashboardAdapter);
    }
}
