package cz.tul.data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Picture")
public class Picture {

    @Id
    @Column(name="url")
    private String url;

    @Column(name="name")
    private String name;

    @Column(name="author")
    private String author;

    @Column(name="dateCreated")
    private Date dateCreated;

    @Column(name="datepdated")
    private Date dateUpdated;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;
<<<<<<< HEAD
=======

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
>>>>>>> pr/2
}
