package com.spit.fest.oculus.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;

import java.util.Objects;

public class ShowcaseViewUtils
{
    private static ShowcaseViewUtils showcaseViewUtils;

    public void setShowcaseViewWithMaterialShowcase(final SharedPreferences sharedPreferences, final String key, Activity activity, final View view, final int factorX, final int factorY, String contentTitle, String contentText, RelativeLayout.LayoutParams layoutParams, int styleResId)
    {
        int defaultValue = sharedPreferences.getInt(key, 0);
        if (defaultValue == 0)
        {
            final ShowcaseView showcaseView = new ShowcaseView.Builder(Objects.requireNonNull(activity))
                    .setTarget(new Target() {
                        @Override
                        public Point getPoint() {
                            int[] location = new int[2];
                            if (view != null)
                            {
                                view.getLocationOnScreen(location);
                                location[0] += view.getWidth() / factorX;
                                location[1] += view.getHeight() / factorY;
                            }
                            return new Point(location[0], location[1]);
                        }
                    })
                    .hideOnTouchOutside()
                    .withMaterialShowcase()
                    .setStyle(styleResId)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .doNotBlockTouches()
                    .build();
            showcaseView.overrideButtonClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setButtonPosition(layoutParams);
            showcaseView.setButtonText("Got it");
        }
    }

    public void setShowcaseViewWithHoloShowcase(final SharedPreferences sharedPreferences, final String key, Activity activity, final View view, final int factorX, final int factorY, String contentTitle, String contentText, RelativeLayout.LayoutParams layoutParams, int styleResId)
    {
        int defaultValue = sharedPreferences.getInt(key, 0);
        if (defaultValue == 0)
        {
            final ShowcaseView showcaseView = new ShowcaseView.Builder(Objects.requireNonNull(activity))
                    .setTarget(new Target() {
                        @Override
                        public Point getPoint() {
                            int[] location = new int[2];
                            if (view != null)
                            {
                                view.getLocationOnScreen(location);
                                location[0] += view.getWidth() / factorX;
                                location[1] += view.getHeight() / factorY;
                            }
                            return new Point(location[0], location[1]);
                        }
                    })
                    .hideOnTouchOutside()
                    .withHoloShowcase()
                    .setStyle(styleResId)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .doNotBlockTouches()
                    .build();
            showcaseView.overrideButtonClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setButtonPosition(layoutParams);
            showcaseView.setButtonText("Got it");
        }
    }

    public void setShowcaseViewWithNewStyleShowcase(final SharedPreferences sharedPreferences, final String key, Activity activity, final View view, final int factorX, final int factorY, String contentTitle, String contentText, RelativeLayout.LayoutParams layoutParams, int styleResId)
    {
        int defaultValue = sharedPreferences.getInt(key, 0);
        if (defaultValue == 0)
        {
            final ShowcaseView showcaseView = new ShowcaseView.Builder(Objects.requireNonNull(activity))
                    .setTarget(new Target() {
                        @Override
                        public Point getPoint() {
                            int[] location = new int[2];
                            if (view != null)
                            {
                                view.getLocationOnScreen(location);
                                location[0] += view.getWidth() / factorX;
                                location[1] += view.getHeight() / factorY;
                            }
                            return new Point(location[0], location[1]);
                        }
                    })
                    .hideOnTouchOutside()
                    .withNewStyleShowcase()
                    .setStyle(styleResId)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .doNotBlockTouches()
                    .build();
            showcaseView.overrideButtonClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putInt(key, 1).apply();
                    if (showcaseView.isShowing())
                        showcaseView.hide();
                }
            });
            showcaseView.setButtonPosition(layoutParams);
            showcaseView.setButtonText("Got it");
        }
    }

    public static ShowcaseViewUtils getShowcaseViewUtils() {
        if (showcaseViewUtils == null)
            showcaseViewUtils = new ShowcaseViewUtils();
        return showcaseViewUtils;
    }
}
