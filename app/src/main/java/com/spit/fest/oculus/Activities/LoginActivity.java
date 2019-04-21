package com.spit.fest.oculus.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.spit.fest.oculus.Fragments.OtherFragments.UpdateProfile;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.DatabaseUtils;

import java.util.Collections;
import java.util.List;

import static com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING;
import static com.google.android.exoplayer2.Player.STATE_ENDED;

public cpwlass LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 200;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private GoogleSignInButton googleSignInButton;
    private PlayerView playerView;
    private SimpleExoPlayer player;

    private SharedPreferences shared ;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        playerView = findViewById(R.id.intro_video);
//        Window window = getWindow();
//
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//        window.setStatusBarColor(getResources().getColor(R.color.my_statusbar_color));

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        shared= getSharedPreferences("userData",MODE_PRIVATE);

        isLoggedIn = shared.getBoolean("loggedIn",false);
//        Toast.makeText(this, "log "+Boolean.toString(isLoggedIn), Toast.LENGTH_SHORT).show();

        googleSignInButton=findViewById(R.id.button_google_login);

//        mAuth=FirebaseAuth.getInstance();
//        mAuthListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
////                updateUI(firebaseAuth.getCurrentUser());
//            }
//        };

        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInButton.setEnabled(false);

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setShutterBackgroundColor(Color.WHITE);
        playerView.setUseController(false);
        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player.setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.splash));
        try {
            rawResourceDataSource.open(dataSpec);

            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };
            MediaSource videoSource = new ExtractorMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());
            player.prepare(videoSource);
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
            player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    if (playbackState == STATE_ENDED) {
                        googleSignInButton.setVisibility(View.VISIBLE);
                        if(isLoggedIn){
                            FirebaseUser currentUser  = mAuth.getCurrentUser();
                            if(currentUser != null)
                                updateUI(currentUser);
                            else
                                shared.edit().putBoolean("loggedIn",false).apply();
                        }
                    }

                    else
                        googleSignInButton.setVisibility(View.GONE);

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void updateUI(FirebaseUser firebaseUser)
    {

        dismissProgressDialog();

        if (firebaseUser!=null)
        {

            if(!isLoggedIn){

                checkProfileExists();
                return;
            }

            if (Build.VERSION.SDK_INT >= 23)
            {
                Intent intent=new Intent(this,SwipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Logging in");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        if (requestCode == RC_SIGN_IN) {

//            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                checkProfileExists();
                dismissProgressDialog();
            }
            else
            {
                googleSignInButton.setEnabled(true);
                dismissProgressDialog();
                Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();
            }

        }
    }

    private void dismissProgressDialog()
    {
        if (progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }


    void checkProfileExists(){
        if (mAuth.getCurrentUser() != null)
        {
            String path = "Users/"+mAuth.getCurrentUser().getUid()+"/";
            DatabaseReference self = DatabaseUtils.getDatabaseReference().getDatabaseInstance().child(path);
            self.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("user stat",dataSnapshot.toString());
                    if(dataSnapshot.exists()){
                        Log.i("user stat","user exists");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        shared.edit().putBoolean("loggedIn",true).apply();
                        isLoggedIn = true;
                        updateUI(user);

                    }else{

                        UpdateProfile profile = new UpdateProfile();
                        profile.setUpdateForceFully(true);

                        profile.show(getSupportFragmentManager(),"User");
                        //show profile update dialog

                        googleSignInButton.setEnabled(true);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

}
