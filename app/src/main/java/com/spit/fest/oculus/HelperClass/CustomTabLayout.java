package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import com.spit.fest.oculus.Utils.TabLayoutUtils;

import java.lang.reflect.Field;

public class CustomTabLayout extends TabLayout {

    private static final int WIDTH_INDEX = 0;
    private static final int DIVIDER_FACTOR = 4;
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth";

    public CustomTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }

    private void initTabMinWidth() {
        int[] wh = TabLayoutUtils.getScreenSize(getContext());
        int tabMinWidth = wh[WIDTH_INDEX] / DIVIDER_FACTOR;

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
