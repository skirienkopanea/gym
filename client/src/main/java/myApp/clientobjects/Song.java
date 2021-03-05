package myApp.clientobjects;

public class Song {

    private String name;
    private String album;
    private int year;

    public Song(String name, String album, int year) {
        this.name = name;
        this.album = album;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " - " + album + " (" + year + ")";
    }
}
