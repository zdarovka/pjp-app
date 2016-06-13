package cz.tul.data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zdars on 5/16/2016.
 */
@Entity
@Table(name="tag")
@Document(collection = "tag")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@ref")
public class Tag {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @org.springframework.data.annotation.Id
    private UUID id;

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(UUID id, String name){
        this.id = id;
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture")
    @DBRef
    private Picture picture;

    public Tag(String name)
    {
        this.name = name;
    }

    public Tag(){
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return id != null ? id.equals(tag.id) : tag.id == null;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture pictures) {
        this.picture = pictures;
    }
}