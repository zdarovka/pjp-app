package cz.tul.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "author")
public class Author {

    @Id
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

    public String getAuthorName() {
        return name;
    }

    public void setAuthorName(String authorName) {
        this.name = authorName;
    }

    public UUID getId() {
        return id;
    }
}
