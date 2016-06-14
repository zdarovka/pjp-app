package cz.tul.provisioner;


import cz.tul.code.DataHelper;
import cz.tul.data.Author;
import cz.tul.data.Comment;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.CommentRepository;
import cz.tul.repositories.PictureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Created by Petr on 10. 6. 2016.
 */
@Transactional
@Component
@Profile({"prod_mysql", "prod_mongo"})
public class DBProvisioner implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(DBProvisioner.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        provisionAuthorCollection();
        provisionPictureCollection();
        provisionCommentCollection();
        provisionAuthorForPicture();
        provisionCommentsForPicture();
    }

    private boolean provisionAuthorCollection() throws IOException {
        boolean isEmpty = authorRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/authors.txt")))) {

                String line;
                while ((line = read.readLine()) != null) {
                    String[] values = line.split("\\s");

                    UUID id = UUID.fromString(values[0]);
                    String t = values[1];

                    Author newC = new Author(id, t);
                    authorRepository.save(newC);
                }
            }
        }
        return isEmpty;
    }

    private boolean provisionPictureCollection() throws IOException {
        boolean isEmpty = pictureRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/pictures.txt")))) {
                String line;
                while ((line = read.readLine()) != null) {
                    String[] values = line.split("\\s");

                    UUID id = UUID.fromString(values[0]);
                    String name = values[1];
                    String url = values[2];
                    Integer like = Integer.parseInt(values[3]);
                    Integer dislikes = Integer.parseInt(values[4]);
                    String tag1 = values[5];
                    String tag2 = values[6];

                    Picture p = new Picture(id, name, url, DataHelper.randomDate(), like, dislikes);
                    p.addTag(tag1);
                    p.addTag(tag2);

                    pictureRepository.save(p);
                }
            }
        }
        return isEmpty;
    }


    private boolean provisionCommentCollection() throws IOException {
        boolean isEmpty = commentRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/comments.txt")))) {

                String line;
                while ((line = read.readLine()) != null) {
                    String[] values = line.split("\\s");

                    UUID id = UUID.fromString(values[0]);
                    Author a = authorRepository.findOne(UUID.fromString(values[1]));
                    String t = values[2];
                    Picture p = pictureRepository.findOne(UUID.fromString(values[3]));

                    Comment newC = new Comment(id, a, t, p);
                    commentRepository.save(newC);
                }
            }
        }
        return isEmpty;
    }


    private boolean provisionCommentsForPicture() throws IOException {

        Iterable<Picture> pictures = pictureRepository.findAll();
        for (Picture p : pictures) {
            p.setComments(commentRepository.findByPictureId(p.getId()));
            pictureRepository.save(p);
        }

        return true;
    }

    private boolean provisionAuthorForPicture() throws IOException {

        int max = (int)authorRepository.count();
        int counter = 0;
        Iterable<Picture> pictures = pictureRepository.findAll();
        for (Picture p : pictures) {
            p.setAuthor(authorRepository.findAll().get(counter));
            pictureRepository.save(p);

            if(counter != max - 1){
                counter++;
            }
        }

        return true;
    }
}