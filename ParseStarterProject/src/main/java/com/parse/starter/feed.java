package com.parse.starter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class feed extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Post> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");

        setTitle(name+"'s feeds");

        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final PostAdapter notesAdapter=new PostAdapter(list);
        recyclerView.setAdapter(notesAdapter);

        final Bitmap[] bitmap = new Bitmap[1];
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("profilepicture");

        query2.whereEqualTo("username", name);

        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null && data != null) {
                                    bitmap[0] = BitmapFactory.decodeByteArray(data, 0,data.length);

                                }
                            }
                        });
                    }
                }
            }
        });
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", name);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null && data != null) {
                                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0,data.length);
                                    Post post=new Post();
                                    post.setName(name);
                                    post.setBitmap1(bitmap[0]);
                                    post.setBitmap2(bitmap1);
                                    list.add(post);
                                    Log.i("here", "it got here to the post add");
                                    notesAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                    notesAdapter.notifyDataSetChanged();
                    Log.i("here", "it got here to the post add, ends");
                }
            }
        });


    }
}