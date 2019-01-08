package data;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ArticleData {

    private String title;
    private String author;
    private String text;
    private String image;
    private String id;


    public ArticleData() {

    }

    public String toString () {
        StringBuilder str = new StringBuilder();
        str.append("Title: " + title + "\n");
        str.append("Author: " + author+"\n");
        str.append("Image: " + image +"\n");
        str.append("Text: " + text+"\n");
        str.append("...");
        return str.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author == null) {
            this.author = null;
            return;
        }
        String[] tmp = author.split(":");
        if(tmp.length == 2)
            this.author = tmp[1];
        else {
            this.author = tmp[0];
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id.toString();
    }

}
