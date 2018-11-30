package edu.calvin.cs262.lab09;

/**
 * This class implements a Question Data-Access Object (QAO) class for the Question relation.
 * This provides an object-oriented way to represent and manipulate question "objects" from
 * the traditional (non-object-oriented) Question database.
 *
 */
public class Question {

    private int id, downloads;
    private String contents;


    public Question() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }
    public Question(int id, int downloads, String contents) {
        this.id = id;
        this.contents = contents;
        this.name = name;
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

    public String getContents() {
        return this.contents;
    }
    public void setContents(String content) {
        this.contents = contents;
    }
}
