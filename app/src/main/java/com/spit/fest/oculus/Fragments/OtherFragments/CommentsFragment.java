package com.spit.fest.oculus.Fragments.OtherFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.Comments;
import com.spit.fest.oculus.HelperClass.CommentsAdapter;
import com.spit.fest.oculus.HelperClass.FeedDatabase;
import com.spit.fest.oculus.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends BottomSheetDialogFragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int postId;
    private RecyclerView recyclerView;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(int param)
    {
        CommentsFragment commentsFragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("paramsComment",param);
        commentsFragment.setArguments(bundle);
        return commentsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setShowsDialog(true);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(getContext(), SwipeActivity.class);
                    startActivity(intent);
                }
            }
        };

        if (getArguments() != null)
            postId = getArguments().getInt("paramsComment");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final CommentsAdapter commentsAdapter = new CommentsAdapter(getContext());
        recyclerView = view.findViewById(R.id.comment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        commentsAdapter.populateArrayList(postId);
        recyclerView.setAdapter(commentsAdapter);

        final EditText editText = view.findViewById(R.id.comment_editText);
        ImageView imageView = view.findViewById(R.id.comment_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editText.getText().toString();
                if (!comment.isEmpty())
                {
                    String username = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
                    Comments comments = new Comments(postId
                                                    , FirebaseAuth.getInstance().getCurrentUser().getUid()
                                                    , username
                                                    , Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).toString()
                                                    , comment);
                    FeedDatabase.getInstance(view.getContext()).commentsDao().insertComment(comments);
                    editText.setText("");
                    commentsAdapter.populateArrayList(postId);
                    recyclerView.setAdapter(commentsAdapter);
                }
                else
                    Toast.makeText(view.getContext(), "Blank comment", Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onResume() {
        super.onResume();
    }
}
