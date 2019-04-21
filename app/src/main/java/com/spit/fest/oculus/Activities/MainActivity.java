package com.spit.fest.oculus.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.EventFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.FriendsFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.GoogleAssistantFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.NotificationsFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.ProfileFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.Sponsors;
import com.spit.fest.oculus.Fragments.Sponsors.SponsorFragment;
import com.spit.fest.oculus.Fragments.BottomNavigationFragments.TimeTableFragment;
import com.spit.fest.oculus.Fragments.OtherFragments.AboutUsFragment;
import com.spit.fest.oculus.Fragments.OtherFragments.NGOFragment;
import com.spit.fest.oculus.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    public static BottomNavigationView navigation;
    public static Fragment fragment;
    private final EventFragment eventFragment = EventFragment.getEventFragment();
    private final Sponsors sponsorFragment = Sponsors.getFrag();
    private final FriendsFragment friendsFragment= FriendsFragment.getHomeFragment();
    //private TimeTableFragment timeTableFragment = TimeTableFragment.getTimeTableFragment();
    private NotificationsFragment timeTableFragment = NotificationsFragment.getNotificationsFragment();


    private final GoogleAssistantFragment googleAssistantFragment = GoogleAssistantFragment.getGoogleAssistantFragment();
    private final ProfileFragment profileFragment = ProfileFragment.getProfileFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    if (fragment != eventFragment)
                        replaceFragment(eventFragment);
                    return true;
                case R.id.navigation_sponsors:
                    if (fragment != friendsFragment)
                        replaceFragment(friendsFragment);
                    return true;
                case R.id.navigation_itinerary:
                    if (fragment != timeTableFragment)
                        replaceFragment(timeTableFragment);
                    return true;
                case R.id.navigation_google_assistant:
                    if (fragment != googleAssistantFragment)
                        replaceFragment(googleAssistantFragment);
                    return true;
                case R.id.navigation_others:
                    if (fragment != profileFragment)
                        replaceFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.cancel(15);
        }catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences shared= getSharedPreferences("userData",MODE_PRIVATE);

        boolean isLoggedIn = shared.getBoolean("loggedIn",false);
//        Toast.makeText(this, "log "+Boolean.toString(isLoggedIn), Toast.LENGTH_SHORT).show();
        if(!isLoggedIn){
            redirect();
            finish();
        }

        navigation = findViewById(R.id.navigation);

        if (!isNetworkAvailable())
            Toast.makeText(this, "You're offline", Toast.LENGTH_LONG).show();

        DatabaseReference.goOnline();

        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
        {
            Log.i("FCM","no bundle found creating new");
            bundle = new Bundle();
            bundle.putInt("Count",3);
        }
        Log.i("FCM","is from fcm "+Boolean.toString(bundle.containsKey("fromFcm")));

        for(String keys :bundle.keySet() )
        {
            Log.i("FCM","bundle details "+keys+" value "+String.valueOf(bundle.get(keys)));

        }

        fragmentManager = getSupportFragmentManager();
        fragment = eventFragment;
        fragment.setArguments(bundle);
        replaceFragment(fragment);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        Log.i("FCM TOKEN", FirebaseInstanceId.getInstance().getToken());

        FirebaseMessaging.getInstance().subscribeToTopic("developer").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(MainActivity.this, "Subscribed to developer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void replaceFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.navigation_fragment_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
        MainActivity.fragment = fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference.goOffline();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof AboutUsFragment || fragment instanceof NGOFragment)
            replaceFragment(ProfileFragment.getProfileFragment());
        else if (fragment instanceof GoogleAssistantFragment || fragment instanceof ProfileFragment || fragment instanceof SponsorFragment || fragment instanceof TimeTableFragment)
            navigation.setSelectedItemId(R.id.navigation_events);
        else
            super.onBackPressed();
    }

    private void redirect()
    {
        Intent intent=new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle bundle = intent.getExtras();
        if(bundle==null){
            return;
        }

        for(String keys :bundle.keySet() ){
            Log.i("FCM intent","bundle details "+keys+" value "+String.valueOf(bundle.get(keys)));

        }

    }
}
