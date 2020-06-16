package com.example.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieCustom extends BaseAdapter {
    private Context context;
    private List<MovieDetail> list;

    public MovieCustom(Context context, List<MovieDetail> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contextview, ViewGroup viewGroup) {
        MovieDetail movieDetail = list.get(i);
        View view = LayoutInflater.from(context).inflate(R.layout.customlayout, viewGroup, false);
        ImageView imageMovie = view.findViewById(R.id.custom_image);
        Picasso.get().load("https://image.tmdb.org/t/p/w300/" + movieDetail.getImage()).into(imageMovie);
        return view;
    }
}
