package cz.tul.data;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="picture")
@Document(collection = "picture")
public class Picture {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String url;

    @Column
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author")
    private Author author;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

    @Column
    private int likes;

    @Column
    private int dislikes;

    @ManyToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "tag")
    private List<Tag> Tags;

    public Picture() {
    }

    public Picture(UUID id,String name, String url, Date d){
        this.name = name;
        this.url = url;
        this.id = id;
        this.dateCreated = d;
        this.dateUpdated = d;
    }

    public Picture(UUID id,String name, String url, Date d, int likes, int dislikes){
        this.name = name;
        this.url = url;
        this.id = id;
        this.dateCreated = d;
        this.dateUpdated = d;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Picture(Author author) {
        this.author = author;
        this.id = UUID.randomUUID();
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
        this.name = "Unnamed picture";
    }

    public List<cz.tul.data.Tag> getTags() {
        return this.Tags;
    }

    public void setTags(List<Tag> tags) {
        this.Tags = tags;
    }

    public void addTag(Tag tag) {
        this.Tags.add(tag);
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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

    public void incrementLike(){
        this.likes++;
    }

    public void incrementDislikes(){
        this.dislikes++;
    }

    public UUID getId() {
        return id;
    }
}