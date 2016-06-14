package cz.tul.repositories;

import cz.tul.data.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepository extends CrudRepository<Picture, UUID> {

    List<Picture> findAll();
    List<Picture> findByName(String name);
    List<Picture> findByAuthorId(UUID id);
    List<Picture> findByTags(String tag);

    List<Picture> findByNameIgnoreCaseContaining(String name);

    Picture findOne(UUID id);
    Picture findFirstByIdLessThanOrderByIdDesc(UUID id);
    Picture findFirstByIdGreaterThan(UUID id);
}
