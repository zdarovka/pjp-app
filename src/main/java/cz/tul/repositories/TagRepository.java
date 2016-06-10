package cz.tul.repositories;

import cz.tul.data.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends CrudRepository<Tag, UUID> {

}
