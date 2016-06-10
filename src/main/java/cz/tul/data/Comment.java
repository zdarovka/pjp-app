package cz.tul.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="comment")
public class Comment {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author")
    private Author author;

    @Column
    private String text;

    @Column
    private int likes;

    @Column
    private int dislikes;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

    public Comment(Author author, String text)
    {
        this.author = author;
        this.text = text;
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public void incrementLike(){
        this.likes++;
    }

    public void incrementDislikes(){
        this.dislikes++;
    }
}