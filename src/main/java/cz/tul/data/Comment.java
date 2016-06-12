package cz.tul.data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="comment")
@Document(collection = "comments")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="id")
public class Comment {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author")
    @DBRef
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

    @ManyToOne(optional = false)
    private Picture picture;

    public Comment(){

    }

    public Comment(UUID id,Author author, String text, Picture picture)
    {
        this.id = id;
        this.author = author;
        this.text = text;
        this.picture = picture;
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
    }

    public Comment(UUID id,Author author, String text, Picture picture, Date date)
    {
        this.id = id;
        this.author = author;
        this.text = text;
        this.picture = picture;
        this.dateCreated = date;
        this.dateUpdated = date;
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

    public Picture getPicture() {
        return picture;
    }
}