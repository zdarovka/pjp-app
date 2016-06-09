package cz.tul.repositories;

import cz.tul.data.Picture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by zdars on 6/9/2016.
 */
public class PictureRepositoryImpl implements CustomPictureRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Picture first() {
        return null;
    }
}
