package cz.tul.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commentId")
    private int commentId;

    @ManyToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "authorName")
    private Author author;

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
}
