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
    private String authorName;

    @Column
    private Date dateCreated;

    public Author(String name, Date dateCreated) {
        this.authorName = name;
        this.dateCreated = dateCreated;
    }

    public Author(){

    }

    public String getName() {
        return this.authorName;
    }

    public void setName(String name) {
        this.authorName = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public UUID getId() {
        return id;
    }
}
