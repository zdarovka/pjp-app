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
}
