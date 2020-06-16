package com.example.movies;

import java.io.Serializable;

public class MovieDetail implements Serializable {

    public static final String TABLE_NAME = "text_table";
    public static final String MOVIES_ID = "movies_id";
    public static final String MOVIES_DESC = "movies_desc";
    public static final String MOVIES_TITLE = "movies_title";
    public static final String MOVIES_IMAGE = "movies_image";
    public static final String MOVIES_FAVOURITE= "movies_favourite";

    private String id;
    private String desc;
    private String title;
    private String image;
    private int favourite;


    public MovieDetail(String id, String desc, String title, String image, int favourite) {
        this.id = id;
        this.desc = desc;
        this.title = title;
        this.image = image;
        this.favourite=0;
    }

    public MovieDetail() {

    }

    public int getFavourite() { return favourite; }

    public int setFavourite(int favourite) { this.favourite = favourite;
        return favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

