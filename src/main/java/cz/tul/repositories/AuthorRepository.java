package cz.tul.repositories;

import cz.tul.data.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {
    List<Author> findAll();
}