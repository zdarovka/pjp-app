package cz.tul.repositories;

import cz.tul.data.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by zdars on 6/10/2016.
 */
public class AuthorRepositoryImpl implements AuthorCustomRepository{

    @PersistenceContext
    private EntityManager em;

    public Author random(){
        Query countQuery = em.createNativeQuery("select count(*) from Author");

        BigInteger count = (BigInteger)countQuery.getSingleResult();
        Random random = new Random();
        int number = random.nextInt(count.intValue());
        TypedQuery<Author> selectQuery = em.createQuery("select q from Author q", Author.class);

        selectQuery.setFirstResult(number); selectQuery.setMaxResults(1);

        return selectQuery.getSingleResult();
    }
}
