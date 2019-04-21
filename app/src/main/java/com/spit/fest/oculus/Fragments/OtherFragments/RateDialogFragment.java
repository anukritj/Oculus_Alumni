package com.spit.fest.oculus.Fragments.OtherFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.Objects;

public class RateDialogFragment extends BottomSheetDialogFragment
{

    private String eventEmail;
    private String uid;
    private String url;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public RateDialogFragment() {
    }

    public static RateDialogFragment newInstance(ArrayList<String> params)
    {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("paramsRating",params);
        rateDialogFragment.setArguments(bundle);
        return rateDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setShowsDialog(true);

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

        if (getArguments() != null)
        {
            eventEmail = Objects.requireNonNull(getArguments().getStringArrayList("paramsRating")).get(0);
            uid = Objects.requireNonNull(getArguments().getStringArrayList("paramsRating")).get(1);
            url = Objects.requireNonNull(getArguments().getStringArrayList("paramsRating")).get(2);
        }
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
////        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT < 23) {
//            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.rating_dialog_background);
////        dialog.setContentView(view);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
//        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawableResource(R.drawable.rating_dialog_background);
        if (eventEmail != null && uid != null && url != null)
        {
            final RatingBar ratingBar = view.findViewById(R.id.ratingRatingBar);
            ImageUtils.newInstance().setImage(view.getContext(), url, (ImageView) (view.findViewById(R.id.ratingImgPhoto)), null);
            Button submitBtn = view.findViewById(R.id.ratingSubmitButton);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Rating").child(eventEmail).child(uid).setValue(ratingBar.getRating());
                    dismiss();
                }
            });
        }
        return view;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        return dialog;
//    }

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
