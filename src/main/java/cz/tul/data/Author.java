package cz.tul.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "author")
@Document(collection = "author")
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
        this.id = id;
        this.name = name;
        this.dateCreated = new Date();
    }

    public Author(){

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

    public UUID getId() {
        return id;
    }
}
