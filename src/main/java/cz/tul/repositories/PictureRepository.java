package cz.tul.repositories;

import cz.tul.data.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepository extends CrudRepository<Picture, UUID>, CustomPictureRepository {

    List<Picture> findAll();
    List<Picture> findByName(String name);
    List<Picture> findByAuthorId(UUID id);
    List<Picture> findByTagsName(String tag);
}
