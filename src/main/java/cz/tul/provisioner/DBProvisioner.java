package cz.tul.provisioner;


import cz.tul.code.DataHelper;
import cz.tul.data.Author;
import cz.tul.data.Comment;
import cz.tul.data.Tag;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.CommentRepository;
import cz.tul.repositories.PictureRepository;
import cz.tul.repositories.TagRepository;

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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Petr on 10. 6. 2016.
 */
@Transactional
@Component
@Profile({"prod_mysql", "prod_mysql"})
public class DBProvisioner implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(DBProvisioner.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        provisionAuthorCollection();
        provisionPictureCollection();
        provisionTagCollection();
        provisionCommentCollection();
        provisionTagsForPicture();
        provisionAuthorForPicture();
        provisionCommentsForPicture();
    }

    private boolean provisionAuthorCollection() throws IOException {
        boolean isEmpty = authorRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/Author.txt")))) {
                List<Author> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new Author(UUID.fromString(a[0]), a[1], DataHelper.randomDate())).collect(Collectors.toList());
                authorRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionPictureCollection() throws IOException {
        boolean isEmpty = pictureRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/pictures.txt")))) {
                List<Picture> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new Picture(UUID.fromString(a[0]), a[1], a[2], DataHelper.randomDate(), Integer.parseInt(a[3]), Integer.parseInt(a[4]))).collect(Collectors.toList());
                pictureRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionTagCollection() throws IOException {
        boolean isEmpty = tagRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/picturetags.txt")))) {
                List<Tag> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new Tag(UUID.fromString(a[0]), a[1])).collect(Collectors.toList());
                tagRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionCommentCollection() throws IOException {
        boolean isEmpty = commentRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/comments.txt")))) {
                List<Comment> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new Comment(UUID.fromString(a[0]), authorRepository.findOne(UUID.fromString(a[1])),a[2], DataHelper.randomDate())).collect(Collectors.toList());
                commentRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionTagsForPicture() throws IOException {

        int max = (int)tagRepository.count();
        Iterable<Picture> pictures = pictureRepository.findAll();
        for (Picture p : pictures) {
            int lowI = DataHelper.randomNumber(0,max);
            p.setTags(tagRepository.findAll().subList(lowI,DataHelper.randomNumber(lowI,max)));
            pictureRepository.save(p);
        }

        return true;
    }

    private boolean provisionCommentsForPicture() throws IOException {

        int max = (int)commentRepository.count();
        Iterable<Picture> pictures = pictureRepository.findAll();
        for (Picture p : pictures) {
            int lowI = DataHelper.randomNumber(0,max);
            p.setComments(commentRepository.findAll().subList(lowI,DataHelper.randomNumber(lowI,max)));
            pictureRepository.save(p);
        }

        return true;
    }

    private boolean provisionAuthorForPicture() throws IOException {

        int max = (int)authorRepository.count();
        Iterable<Picture> pictures = pictureRepository.findAll();
        for (Picture p : pictures) {
            p.setAuthor(authorRepository.random());
            pictureRepository.save(p);
        }

        return true;
    }
}