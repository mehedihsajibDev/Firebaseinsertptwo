package com.example.sajib.firebaseinsertptwo;

/**
 * Created by Sajib on 03-Apr-18.
 */

public class Artist {
    String id;
    String name;
    String artistGenre;

    public Artist( String id,String name, String artistGenre) {
        this.id = id;
        this.name = name;
        this.artistGenre = artistGenre;
    }

    public Artist() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public void setArtistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
    }
}
