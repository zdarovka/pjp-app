package cz.tul.data;

import javax.persistence.*;

/**
 * Created by zdars on 5/16/2016.
 */
@Entity
@Table(name="Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tagId")
    private int tagId;

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
