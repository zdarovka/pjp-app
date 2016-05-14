package cz.tul.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="Comment")
public class Comment {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="author")
    private String author;

    @Column(name="text")
    private String text;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name="dateCreated")
    private Date dateCreated;

    @Column(name="datepdated")
    private Date dateUpdated;

    public Comment(String author, String text)
    {
        this.author = author;
        this.text = text;
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
