package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    GridView gridview;
    database db;
    ArrayList<MovieDetail> arrlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new database(this);
        gridview = findViewById(R.id.act_main_gridview);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cManager .getActiveNetworkInfo();
        if(activeNetwork != null){
            db.deleteAll();
            new Progressasyns().execute();

        }else {
            viewImageData();
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,Detail.class);
                intent.putExtra("Movie_detail",(MovieDetail) adapterView.getItemAtPosition(i));
                startActivity(intent);
            }
        });

    }
    private void viewImageData() {
        arrlist = db.getAll();
        MovieCustom customAdapter = new MovieCustom(MainActivity.this, arrlist);
        gridview.setAdapter(customAdapter);

    }
    public class Progressasyns extends AsyncTask<Void,Void,String> {

        URL url;
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... strings) {
            try {
                url= new URL("https://api.themoviedb.org/3/movie/popular?api_key=4c546b2537e42cba63c8392ca256696b");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s=bufferedReader.readLine();
                bufferedReader.close();
                return s ;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject reader = new JSONObject(s);

                ArrayList<MovieDetail> list = new ArrayList<>();

                JSONArray jsonArray = reader.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieDetail detail = new MovieDetail();
                    detail.setTitle(object.getString("original_title"));
                    detail.setDesc(object.getString("overview"));
                    detail.setImage(object.getString("poster_path"));

                    list.add(detail);
                    db.insertDetailMovies(detail);
                }


                MovieCustom customAdapter = new MovieCustom(MainActivity.this, list);
                gridview.setAdapter(customAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}

