package cz.tul.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="Author")
public class Author {

    @Id
    @Column(name="name")
    private String name;

    @Column(name="dateCreated")
    private Date dateCreated;

    public Author(String name, Date dateCreated) {
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
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
}
