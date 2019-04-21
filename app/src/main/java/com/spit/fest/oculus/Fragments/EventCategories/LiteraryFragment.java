package com.spit.fest.oculus.Fragments.EventCategories;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.Event;
import com.spit.fest.oculus.HelperClass.EventViewHolder;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiteraryFragment extends Fragment {

    private FirebaseRecyclerAdapter<Event, EventViewHolder> firebaseRecyclerAdapter;

    int paramEventEmail=0;
    boolean isFromFcm = false;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public LiteraryFragment() {
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

        View view = inflater.inflate(R.layout.fragment_literary, container, false);

        ProgressBar progressBar = view.findViewById(R.id.literary_progress);
        progressBar.setIndeterminate(true);
        //    private static LiteraryFragment literaryFragment;
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        final RecyclerView recyclerView = view.findViewById(R.id.literary_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            paramEventEmail = bundle.getInt("paramEventEmail");
            isFromFcm = bundle.getBoolean("fromFcm",false);

            Log.e("LiteraryFragment", "Value of index: "+paramEventEmail);
            linearLayoutManager.scrollToPositionWithOffset(paramEventEmail, 0);
        }

        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Event").orderByChild("category").equalTo("Literary"), Event.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                Bundle bundle = getArguments();
                if (bundle != null)
                {
                    int paramEventEmail = bundle.getInt("paramEventEmail");
                    Log.e("CulturalFragment", "Value of index: "+paramEventEmail);
                    if (paramEventEmail >= 0)
                        linearLayoutManager.scrollToPositionWithOffset(paramEventEmail, 0);
                }
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new EventViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_event, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event model) {
                holder.setEvent(model);
                if(isFromFcm && position == paramEventEmail){
                    isFromFcm = false;
                    Log.i("FCM","Trying to expand");
                    try{
                        holder.foldingCell.unfold(false);
                    }catch (Exception e){
                        holder.foldingCell.unfold(true);
                    }

                }
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                Log.i("FCM",Integer.toString(itemCount)+"data range "+Integer.toString(positionStart));
                if(itemCount  == (firebaseRecyclerAdapter.getItemCount()-1) ){
                    linearLayoutManager.scrollToPositionWithOffset(paramEventEmail,0);

                }
            }
        });

        progressBar.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("LiteraryFragment","Entered LiteraryFragment");

    }

//    public static LiteraryFragment getLiteraryFragment() {
//        if (literaryFragment == null)
//            literaryFragment = new LiteraryFragment();
//        return literaryFragment;
//    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
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
