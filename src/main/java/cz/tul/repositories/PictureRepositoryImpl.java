package cz.tul.repositories;

import cz.tul.DemoApplication;
import cz.tul.data.Picture;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zdars on 6/9/2016.
 * For some reason it does not return the expected results ?
 */
public class PictureRepositoryImpl implements CustomPictureRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Picture first() {

        Picture picture = em.createQuery("select p from Picture p order by p.dateCreated asc", Picture.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getSingleResult();

        if(picture == null)
        {
            return null;
        }

        LoggerFactory.getLogger(DemoApplication.class).info("Fetched first picture: " + picture.getId() + ")");

        return picture;
    }

    @Override
    public Picture previous(Date date) {
        TypedQuery<Picture> q = em.createQuery("select p from Picture p where p.dateCreated < :date order by p.dateCreated desc", Picture.class);
        q.setParameter("date", date, TemporalType.DATE);

        Picture picture = q.setFirstResult(0)
                .setMaxResults(1)
                .getSingleResult();


        if(picture == null)
        {
            return null;
        }

        LoggerFactory.getLogger(DemoApplication.class).info("Fetched previous picture: " + picture.getId() + ")");

        return picture;
    }

    @Override
    public Picture next(Date date) {
        TypedQuery<Picture> q = em.createQuery("select p from Picture p where p.dateCreated > :date order by p.dateCreated asc", Picture.class);
        q.setParameter("date",date, TemporalType.DATE);
        Picture picture = q.setFirstResult(0)
                .setMaxResults(1)
                .getSingleResult();


        if(picture == null)
        {
            return null;
        }

        LoggerFactory.getLogger(DemoApplication.class).info("Fetched previous picture: " + picture.getId() + ")");

        return picture;
    }
}
