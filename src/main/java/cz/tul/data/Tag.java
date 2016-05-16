package cz.tul.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zdars on 5/16/2016.
 */
@Entity
@Table(name="Tag")
public class Tag {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(String name)
    {
        this.name = name;
    }
}
