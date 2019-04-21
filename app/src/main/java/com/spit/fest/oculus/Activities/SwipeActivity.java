package com.spit.fest.oculus.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.spit.fest.oculus.HelperClass.OnSwipeTouchListener;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ShowcaseViewUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.TRUE;

public class SwipeActivity extends AppCompatActivity {

    int count;
    String textToShow[] = {"Literary", "Fun", "Technical", "Cultural", "Featured"};
    private TextSwitcher mSwitcher;
    ImageView dot1,dot2,dot3,dot4,dot5;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        SharedPreferences shared= getSharedPreferences("userData",MODE_PRIVATE);

        boolean isLoggedIn = shared.getBoolean("loggedIn",false);
//        Toast.makeText(this, "log "+Boolean.toString(isLoggedIn), Toast.LENGTH_SHORT).show();
        if(!isLoggedIn){
            redirect();
            finish();
        }


        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        dot4 = findViewById(R.id.dot4);
        dot5 = findViewById(R.id.dot5);

        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(SwipeActivity.this);
                t.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                Typeface tf = ResourcesCompat.getFont(SwipeActivity.this,R.font.fourhand);
                t.setGravity(Gravity.CENTER);
                t.setTextSize(36);
                t.setTypeface(tf);
                return t;
            }
        });
        //mSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        //mSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_out_right);

        final ImageView img = findViewById(R.id.img);

        SharedPreferences sharedPreferences = getSharedPreferences("com.spit.fest.oculus", Context.MODE_PRIVATE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.setMargins(32,0,0,32);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM , TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START , TRUE);

        ShowcaseViewUtils.getShowcaseViewUtils().setShowcaseViewWithHoloShowcase(sharedPreferences,
                "Category",
                this,
                img,
                2,
                2,
                "Event Category",
                "Tap it to select the category or swipe it to change it.",
                layoutParams,
                R.style.CustomShowcaseTheme);

        final AnimatedVectorDrawable anim1 = (AnimatedVectorDrawable) getDrawable(R.drawable.litfun);
        final AnimatedVectorDrawable anim2 = (AnimatedVectorDrawable) getDrawable(R.drawable.funlit);
        final AnimatedVectorDrawable anim3 = (AnimatedVectorDrawable) getDrawable(R.drawable.funtech);
        final AnimatedVectorDrawable anim4 = (AnimatedVectorDrawable) getDrawable(R.drawable.techfun);
        final AnimatedVectorDrawable anim5 = (AnimatedVectorDrawable) getDrawable(R.drawable.techcul);
        final AnimatedVectorDrawable anim6 = (AnimatedVectorDrawable) getDrawable(R.drawable.cultech);
        final AnimatedVectorDrawable anim7 = (AnimatedVectorDrawable) getDrawable(R.drawable.cul_fea);
        final AnimatedVectorDrawable anim8 = (AnimatedVectorDrawable) getDrawable(R.drawable.fea_cul);
        img.setImageDrawable(anim5);
        count = 3;
        mSwitcher.setText(textToShow[2]);
        dot3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        img.setOnTouchListener(new OnSwipeTouchListener(SwipeActivity.this) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                if (count==2) {
                    count=1;
                    img.setImageDrawable(anim2);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                        mSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                        mSwitcher.setText(textToShow[0]);
                        dot2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==3)
                {
                    count=2;
                    img.setImageDrawable(anim4);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                        mSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                        mSwitcher.setText(textToShow[1]);
                        dot3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==4)
                {
                    count=3;
                    img.setImageDrawable(anim6);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                        mSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                        mSwitcher.setText(textToShow[2]);
                        dot4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==5)
                {
                    count=4;
                    img.setImageDrawable(anim8);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                        mSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                        mSwitcher.setText(textToShow[3]);
                        dot5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }


            }
            public void onSwipeLeft() {

                if (count==1) {
                    count=2;
                    img.setImageDrawable(anim1);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        mSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        mSwitcher.setText(textToShow[1]);
                        dot1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==2)
                {
                    count=3;
                    img.setImageDrawable(anim3);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        mSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        mSwitcher.setText(textToShow[2]);
                        dot2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==3)
                {
                    count=4;
                    img.setImageDrawable(anim5);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        mSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        mSwitcher.setText(textToShow[3]);
                        dot3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
                else if(count==4)
                {
                    count=5;
                    img.setImageDrawable(anim7);
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)img.getDrawable();
                    Drawable drawable = animatedVectorDrawable.getCurrent();
                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                        mSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        mSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        mSwitcher.setText(textToShow[4]);
                        dot4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dot));
                        dot5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                    }
                }
            }
            public void onSwipeBottom() {
            }

            public void onClick() {
                super.onClick();
                Intent intent=new Intent(SwipeActivity.this, MainActivity.class);
                intent.putExtra("Count",count);
                startActivity(intent);
//                finish();
            }
        });

    }


    private void redirect()
    {
        Intent intent=new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }

}