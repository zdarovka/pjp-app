package cz.tul.data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cascade;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Document(collection = "author")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="id")
public class Author {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private Date dateCreated;

    public Author(UUID id, String name){
        this.name = name;
        this.dateCreated = new Date();
        this.id = id;
    }

    public Author(UUID id, String name, Date date){
        this.name = name;
        this.dateCreated = date;
        this.id = id;
    }

    public Author(String name) {
        this.name = name;
        this.dateCreated = new Date();
        this.id = UUID.randomUUID();
    }

    public Author(){
        this.name = "Unnamed Author";
        this.dateCreated = new Date();
        this.id = UUID.randomUUID();
    }

    @OneToMany(mappedBy="author",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @DBRef
    private List<Comment> comments;

    @OneToMany(mappedBy="author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @DBRef
    private List<Picture> pictures;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }
}
