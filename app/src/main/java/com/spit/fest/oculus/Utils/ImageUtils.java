package com.spit.fest.oculus.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class ImageUtils
{

    private static ImageUtils imageUtils;

    public void setCircleCropImage(Context context, String url, ImageView imageView, final ProgressBar progressBar)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setImage(final Context context, String url, ImageView imageView, final ProgressBar progressBar)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
//                .apply(RequestOptions.placeholderOf())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setImageWithPlaceholder(final Context context, String url, ImageView imageView, int resPlaceholder)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
                .apply(RequestOptions.placeholderOf(resPlaceholder))
                .into(imageView);
    }

    public static ImageUtils newInstance()
    {
        if (imageUtils == null)
            imageUtils = new ImageUtils();
        return imageUtils;
    }

}
