package com.spit.fest.oculus.Fragments.OtherFragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.Objects;

public class VotingFragment extends BottomSheetDialogFragment{

    private String uid;
    private String url;
    private String branch;
    private View view;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public VotingFragment(){}

    public static VotingFragment newInstance(ArrayList<String> params)
    {
        VotingFragment votingFragment = new VotingFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("paramsVoting",params);
        votingFragment.setArguments(bundle);
        return votingFragment;
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
            uid = Objects.requireNonNull(getArguments().getStringArrayList("paramsVoting")).get(0);
            url = Objects.requireNonNull(getArguments().getStringArrayList("paramsVoting")).get(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_voting, container, false);

        view.findViewById(R.id.comps_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.comps_radio_button);
                branch = "Comps";
            }
        });

        ((RadioButton)view.findViewById(R.id.comps_radio_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.comps_radio_button);
                branch = "Comps";
            }
        });

        view.findViewById(R.id.it_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.it_radio_button);
                branch = "IT";
            }
        });

        ((RadioButton)view.findViewById(R.id.it_radio_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.it_radio_button);
                branch = "IT";
            }
        });

        view.findViewById(R.id.extc_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.extc_radio_button);
                branch = "Extc";
            }
        });

        ((RadioButton)view.findViewById(R.id.extc_radio_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.extc_radio_button);
                branch = "Extc";
            }
        });

        view.findViewById(R.id.etrx_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.etrx_radio_button);
                branch = "Etrx";
            }
        });

        ((RadioButton)view.findViewById(R.id.etrx_radio_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.etrx_radio_button);
                branch = "Etrx";
            }
        });

        view.findViewById(R.id.mca_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.mca_radio_button);
                branch = "MCA";
            }
        });

        ((RadioButton)view.findViewById(R.id.mca_radio_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRadioButton(R.id.mca_radio_button);
                branch = "MCA";
            }
        });

        if (uid != null && url != null)
        {
            ImageUtils.newInstance().setImage(view.getContext(), url, (ImageView)view.findViewById(R.id.votingImgPhoto), null);

            view.findViewById(R.id.votingSubmitButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (branch != null && !branch.isEmpty())
                    {
                        Log.e("VotingFragment", branch);
                        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Voting").child(uid).setValue(branch);
                        Toast.makeText(getContext(), "You've successfully voted", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                    else
                        Toast.makeText(getContext(), "Select a branch to vote", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return view;
    }

    public void selectRadioButton(int checkedId)
    {
        ArrayList<Integer> idRadioButtons = new ArrayList<>();
        idRadioButtons.add(R.id.comps_radio_button);
        idRadioButtons.add(R.id.it_radio_button);
        idRadioButtons.add(R.id.extc_radio_button);
        idRadioButtons.add(R.id.etrx_radio_button);
        idRadioButtons.add(R.id.mca_radio_button);

        RadioButton radioButton = view.findViewById(checkedId);
        radioButton.setChecked(true);

        for (int i = 0; i < idRadioButtons.size(); i++)
        {
            if (idRadioButtons.get(i) != checkedId)
            {
                ((RadioButton)view.findViewById(idRadioButtons.get(i))).setChecked(false);
                ((CardView)view.findViewById(getCardId(idRadioButtons.get(i)))).setCardBackgroundColor(Color.TRANSPARENT);
            }
            else
                ((CardView)view.findViewById(getCardId(idRadioButtons.get(i)))).setCardBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private int getCardId(int radioButtonId)
    {
        int checkedCard = 0;
        switch (radioButtonId)
        {
            case R.id.comps_radio_button:
                checkedCard = R.id.comps_cardView;
                break;
            case R.id.it_radio_button:
                checkedCard = R.id.it_cardView;
                break;
            case R.id.extc_radio_button:
                checkedCard = R.id.extc_cardView;
                break;
            case R.id.etrx_radio_button:
                checkedCard = R.id.etrx_cardView;
                break;
            case R.id.mca_radio_button:
                checkedCard = R.id.mca_cardView;
        }
        return checkedCard;
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
