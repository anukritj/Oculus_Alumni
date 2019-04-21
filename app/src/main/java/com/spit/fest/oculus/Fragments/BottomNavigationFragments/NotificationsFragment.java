package com.spit.fest.oculus.Fragments.BottomNavigationFragments;

import android.support.v4.app.Fragment;

public class NotificationsFragment extends Fragment {

    private static NotificationsFragment homeFragment;

    public static NotificationsFragment getNotificationsFragment() {
        if (homeFragment == null)
            homeFragment = new NotificationsFragment();
        return homeFragment;
    }
}
