package com.example.sajib.firebaseinsertptwo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sajib on 04-Apr-18.
 */

public class CustomAdapter extends BaseAdapter{
    Context context;
    private ArrayList<Artist> artists;

    public CustomAdapter(Context context, ArrayList<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);
        }
        TextView textViewname=(TextView)view.findViewById(R.id.textViewname);
        TextView textViewgenre=(TextView)view.findViewById(R.id.textViewgenre);


        Artist artist=(Artist)this.getItem(position);
        textViewname.setText(artist.getName());
        textViewgenre.setText(artist.getArtistGenre());
        return view;
    }
}
