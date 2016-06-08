package cz.tul.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Author")
public class Author {

    @Id
    @Column(name="authorName")
    private String authorName;

    @Column(name="dateCreated")
    private Date dateCreated;

    public Author(String name, Date dateCreated) {
        this.authorName = name;
        this.dateCreated = dateCreated;
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
}
