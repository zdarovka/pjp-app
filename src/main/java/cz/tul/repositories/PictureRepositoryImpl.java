package cz.tul.repositories;

import cz.tul.DemoApplication;
import cz.tul.data.Picture;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by zdars on 6/9/2016.
 */
public class PictureRepositoryImpl implements CustomPictureRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Picture first() {

        List<Picture> result = em.createQuery("from Picture")
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();

        if(result.isEmpty())
        {
            return null;
        }

        Picture pic = result.get(0);

        LoggerFactory.getLogger(DemoApplication.class).info("Fetched picture: " + pic.getId() + ")");

        return pic;
    }
}
