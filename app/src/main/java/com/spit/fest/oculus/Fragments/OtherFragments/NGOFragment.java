package com.spit.fest.oculus.Fragments.OtherFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NGOFragment extends Fragment {

    private static NGOFragment ngoFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public NGOFragment() {
        // Required empty public constructor
    }


    @SuppressLint("RestrictedApi")
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

        MainActivity.fragment = getNgoFragment();

        View view = inflater.inflate(R.layout.fragment_ngo, container, false);
        TextView title = view.findViewById(R.id.textView2);
        final ProgressBar progressBar = view.findViewById(R.id.ngo_progress);
        progressBar.setIndeterminate(true);
        ImageView iv = view.findViewById(R.id.ngoimg);
        new ImageUtils().setImage(getContext(),"https://firebasestorage.googleapis.com/v0/b/oculusapp-ae493.appspot.com/o/ngo%2Flogo%20in%20circle.png?alt=media&token=b4009da8-a14c-43d5-97e2-677593d201e8", iv , progressBar);
//        String linkText = "Visit the <a href='Website : https://ratnanidhi.org/'>Ratna Nidhi Foundation</a> website.";
//        tv.setText(Html.fromHtml(linkText));
//        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    public NGOFragment getNgoFragment() {
        if (ngoFragment == null)
            ngoFragment = new NGOFragment();
        return ngoFragment;
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
