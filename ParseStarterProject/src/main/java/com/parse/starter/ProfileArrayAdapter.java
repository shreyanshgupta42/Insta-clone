package com.parse.starter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileArrayAdapter extends ArrayAdapter<String> {

    private Context mcontext;
    int mresource;
    ArrayList<String> profileListItems;

    public ProfileArrayAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        profileListItems=objects;
        Log.i("got","started here");
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        Log.i("got","upto here");
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresource,parent,false);

        TextView tvname=convertView.findViewById(R.id.textview1);
        ImageView tvicon=convertView.findViewById(R.id.imageView10);

        String name=profileListItems.get(position);

        tvname.setText(name);

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
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,data.length);

                                    tvicon.setImageBitmap(bitmap);
                                }
                            }
                        });
                    }
                }else{
                    tvicon.setImageResource(R.drawable.blankprofile);
                }
            }
        });

        Log.i("got","upto here");
        return convertView;
    }
}
