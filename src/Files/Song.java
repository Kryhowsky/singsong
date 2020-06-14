package Files;

import java.io.Serializable;

public class Song implements Serializable {

    private String title, author;
    SongGenre songGenre;
    private int duration, releaseYear;

    public Song() {
        title = "";
        author = "";
        songGenre = SongGenre.ROCK;
        duration = 0;
        releaseYear = 0;
    }

    public Song(String title, String author, SongGenre songGenre, int duration, int releaseYear) {
        this.title = title;
        this.author = author;
        this.songGenre = songGenre;
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setSongGenre(SongGenre songGenre) {
        this.songGenre = songGenre;
    }

    public SongGenre getSongGenre() {
        return songGenre;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String toString() {
        return "Tytuł: " + title + "; Autor: " + author + "; Gatunek: " + songGenre;
    }

}
