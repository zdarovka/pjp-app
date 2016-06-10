package cz.tul.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by zdars on 5/16/2016.
 */
@Entity
@Table(name="tag")
@Document(collection = "tag")
public class Tag {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(UUID id,String name)
    {
        this.id = id;
        this.name = name;
    }

    public Tag(){

    }

}