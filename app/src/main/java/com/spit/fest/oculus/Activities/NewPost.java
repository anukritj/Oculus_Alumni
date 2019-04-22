package com.spit.fest.oculus.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.spit.fest.oculus.HelperClass.FeedDatabase;
import com.spit.fest.oculus.HelperClass.Post;
import com.spit.fest.oculus.R;
import com.spit.fest.oculus.Utils.ImageUtils;

import java.util.Objects;

public class NewPost extends AppCompatActivity {

    private String imageUrl;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Toolbar toolbar = findViewById(R.id.new_post_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Post");
        getSupportActionBar().setHomeButtonEnabled(true);

        imageView = findViewById(R.id.new_post_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

        final EditText editText = findViewById(R.id.new_post_status);

        Button button = findViewById(R.id.button_upload_post);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = editText.getText().toString();
                if (imageUrl != null || !status.isEmpty())
                {
                    Post post = new Post(imageUrl, status);
                    FeedDatabase.getInstance(NewPost.this).postDao().insertPost(post);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                imageUrl = Objects.requireNonNull(data.getData()).toString();
                findViewById(R.id.placeholderText).setVisibility(View.GONE);
                ImageUtils.newInstance().setImage(this, imageUrl, imageView, null);
            }
        }

    }
}
