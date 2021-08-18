package com.parse.starter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myHolder>{
    List<Post> postList;
    public PostAdapter(List<Post> postList) {
        this.postList = postList;
        Log.i("here", "it got here to the post adaptor constructor");
    }
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("here", "it got here post adaptor");
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postitem,viewGroup,false);
        myHolder myHolder=new myHolder(view);
        return myHolder;

    }
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        Post data=postList.get(i);
        myHolder.name1.setText(data.getName());
        myHolder.profileimg.setImageBitmap(data.getBitmap1());
        myHolder.postimg.setImageBitmap(data.getBitmap2());

    }
    @Override
    public int getItemCount() {
        return postList.size();
    }

    class myHolder extends RecyclerView.ViewHolder

    {
        TextView name1;
        ImageView postimg,profileimg;

        public myHolder(@NonNull View itemView)
        {
            super(itemView);
            name1=itemView.findViewById(R.id.textView3);
            postimg=itemView.findViewById(R.id.imageView4);
            profileimg=itemView.findViewById(R.id.imageView2);

        }
    }
}
