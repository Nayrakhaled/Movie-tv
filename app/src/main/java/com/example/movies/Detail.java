package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {
    private ImageView image;
    private TextView title , desc ;
    database db;
    ImageView favouriteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.detail_image);
        title = findViewById(R.id.detail_text_title);
        desc = findViewById(R.id.detail_text_desc);
        MovieDetail detail = (MovieDetail) getIntent().getExtras().getSerializable("Movie_detail");
        Picasso.get().load("https://image.tmdb.org/t/p/w300/" + detail.getImage()).into(image);
        title.setText(detail.getTitle());
        desc.setText(detail.getDesc());

        favouriteImage = findViewById(R.id.detail_favourite_image);
        db = new database(this);
        String id = detail.getId();
        int favourite = db.selectFavourite(id);
        if(favourite == 1){
            favouriteImage.setImageResource(R.drawable.ic_stat_name);
        }else{
            favouriteImage.setImageResource(R.drawable.ic_stat_name_off);
        }
        favouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDetail detail = new MovieDetail();
                if(detail.getFavourite() == 0){
                    favouriteImage.setImageResource(R.drawable.ic_stat_name);
                    int favourite = 1;
                    db.updateFavourite(detail , favourite);
                    detail.setFavourite(favourite);
                }else{
                    favouriteImage.setImageResource(R.drawable.ic_stat_name_off);
                    int favourite = 0;
                    db.updateFavourite(detail , favourite);
                    detail.setFavourite(favourite);
                }
            }
        });
    }
}
