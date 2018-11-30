package edu.calvin.cs262.lab09;

/**
 * This class implements a Question Data-Access Object (QAO) class for the Question relation.
 * This provides an object-oriented way to represent and manipulate question "objects" from
 * the traditional (non-object-oriented) Question database.
 *
 */
public class Question {

    private Timestamp time;
    private int id, downloads;
    private String contents;


    public Question() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }
    public Question(int id, String contents, Timestamp time, int downloads) {
        this.id = id;
        this.contents = contents;
        this.time = time;
        this.downloads = downloads;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDownloads() {
        return this.downloads;
    }

    public void setDownloads(int id) {
        this.downloads = downloads;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
       this.time = time;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String content) {
        this.contents = contents;
    }
}
