package cz.tul.data;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="picture")
@Document(collection = "picture")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="id")
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
    @JoinColumn(name="author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @DBRef
    private Author author;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

    @Column
    private int likes;

    @Column
    private int dislikes;

    @OneToMany(mappedBy="picture", fetch = FetchType.LAZY)
    @DBRef
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @DBRef
    private List<Tag> tags;

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

    public List<Comment> getComments(){
        return this.comments;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public void addComment(Comment comment)
    {
        this.comments.add(comment);
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
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