package com.spit.fest.oculus.Fragments.BottomNavigationFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spit.fest.oculus.Activities.MainActivity;
import com.spit.fest.oculus.Activities.SwipeActivity;
import com.spit.fest.oculus.HelperClass.ChatMessage;
import com.spit.fest.oculus.HelperClass.chat_rec;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ShowcaseViewUtils;

import java.util.Objects;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.TRUE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleAssistantFragment extends Fragment  {

    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView addBtn;
    private DatabaseReference ref;
    private FirebaseRecyclerAdapter<ChatMessage,chat_rec> adapter;
    private AIService aiService;
    private static GoogleAssistantFragment googleAssistantFragment;
    private String key;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public GoogleAssistantFragment() {
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

        MainActivity.fragment = getGoogleAssistantFragment();
        MainActivity.navigation.setSelectedItemId(R.id.navigation_google_assistant);

        View view = inflater.inflate(R.layout.fragment_google_assistant, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        editText = view.findViewById(R.id.editText);
        addBtn = view.findViewById(R.id.fab_img);
        addBtn.setTag("mic");

        final AIConfiguration config = new AIConfiguration("1cce7e5afdee41c8b71ae8133d20fa1a ", AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(Objects.requireNonNull(getContext()),config);
        //aiService.setListener(view.getContext());

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new    LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        key = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.keepSynced(true);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String val=charSequence.toString();
                mic_send(val);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.spit.fest.oculus", Context.MODE_PRIVATE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.setMargins(32,0,0,32);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM , TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START , TRUE);

        ShowcaseViewUtils.getShowcaseViewUtils().setShowcaseViewWithHoloShowcase(sharedPreferences,
                "Mic",
                getActivity(),
                addBtn,
                2,
                2,
                "Mic / Send",
                "Use it to send your queries or talk to us. Tap the mic and say \"Talk to Oculus Fest\".",
                layoutParams,
                R.style.CustomShowcaseTheme);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tagValue=addBtn.getTag().toString();

                if (tagValue.equals("mic"))
                {
                    startActivity(new Intent(Intent.ACTION_VOICE_COMMAND).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else {

                    String message = editText.getText().toString().trim();

                    if (!message.equals("")) {

                        ChatMessage chatMessage = new ChatMessage(message, "user");
                        ref.child("chat").child(key).push().setValue(chatMessage);

                        aiRequest.setQuery(message);
                        new AsyncTask<AIRequest, Void, AIResponse>() {

                            @Override
                            protected AIResponse doInBackground(AIRequest... aiRequests) {
                                final AIRequest request = aiRequests[0];
                                try {
                                    final AIResponse response = aiDataService.request(aiRequest);
                                    return response;
                                } catch (AIServiceException e) {
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(AIResponse response) {
                                if (response != null) {

                                    Result result = response.getResult();
                                    String reply = result.getFulfillment().getSpeech();
                                    ChatMessage chatMessage = new ChatMessage(reply, "bot");
                                    ref.child("chat").child(key).push().setValue(chatMessage);
                                }
                            }
                        }.execute(aiRequest);
                    } else {
                        aiService.startListening();
                    }

                    editText.setText("");
                }

            }
        });

        FirebaseRecyclerOptions<ChatMessage> options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(ref.child("chat").child(key), ChatMessage.class)
                .build();



        adapter = new FirebaseRecyclerAdapter<ChatMessage, chat_rec>(options) {

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (adapter.getItemCount() >= 1)
                    recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);

            }

            @NonNull
            @Override
            public chat_rec onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //return new chat_rec(LayoutInflater.from(getContext()).inflate(R.layout.msglist, viewGroup, false));
                View view = LayoutInflater.from(getContext()).inflate(R.layout.msglist, viewGroup, false);
                return new chat_rec(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull chat_rec viewHolder, int position, @NonNull ChatMessage model) {
                Log.d("google pos",Integer.toString(position));
                Log.d("google size",Integer.toString(getItemCount()));
                Log.d("google size",Integer.toString(adapter.getItemCount()));
                if (model.getMsgUser().equals("user")) {
                    viewHolder.rightText.setText(model.getMsgText());
                    viewHolder.rightText.setVisibility(View.VISIBLE);
                    viewHolder.leftText.setVisibility(View.GONE);

                }
                else {
                    viewHolder.leftText.setText(model.getMsgText());
                    viewHolder.rightText.setVisibility(View.GONE);
                    viewHolder.leftText.setVisibility(View.VISIBLE);

                }
                if(position == getItemCount()-1){
//                    Toast.makeText(getContext(), "Trying to scroll", Toast.LENGTH_SHORT).show();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
                }

            }
        };
        recyclerView.setAdapter(adapter);


        return view;

    }

    public void onResult(AIResponse result) {

    }

    public void onError(AIError error) {

    }

    public void onAudioLevel(float level) {

    }

    public void onListeningStarted() {

    }

    public void onListeningCanceled() {

    }

    public void onListeningFinished() {

    }

    public static GoogleAssistantFragment getGoogleAssistantFragment() {
        if (googleAssistantFragment == null)
            googleAssistantFragment = new GoogleAssistantFragment();
        return googleAssistantFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter ==null || recyclerView == null){
            return;
        }
        if(adapter.getItemCount() == 0){
            return;
        }

//        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
////        if(editText.hasFocus()){
//            manager.hideSoftInputFromWindow(editText.getWindowToken(),0);
////        }
        recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);


    }

    private void mic_send(String val){
        if (val.isEmpty())
        {
            addBtn.setImageResource(R.drawable.ic_mic_black_24dp);
            addBtn.setTag("mic");
        }
        else
        {
            addBtn.setImageResource(R.drawable.ic_send_black_24dp);
            addBtn.setTag("send");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("GoogleAssistantFragment","onPause()");
        InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null)
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}