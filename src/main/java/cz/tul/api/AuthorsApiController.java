package cz.tul.api;

/**
 * Created by zdars on 30.05.2016.
 */

import cz.tul.data.Author;
import cz.tul.repositories.AuthorRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@RestController
public class AuthorsApiController {


    public static final String AUTHORS_PATH = "/api/authors";
    public static final String AUTHOR_PATH = AUTHORS_PATH + "/{id}";

    private org.slf4j.Logger Logger = LoggerFactory.getLogger(AuthorsApiController.class);

    @Autowired
    private AuthorRepository Authors;

    @RequestMapping(value = AUTHORS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Author>> getAuthors() {

        this.Logger.info("Fetching all authors");
        return new ResponseEntity<>(this.Authors.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = AUTHOR_PATH, method = RequestMethod.GET)
    public ResponseEntity<Author> getAuthor(@PathVariable("id") UUID id) {

        Author author = this.Authors.findOne(id);
        if(author == null)
        {
            this.Logger.warn("Author not found: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.Logger.info("Get author by Id: " + id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(value = AUTHOR_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteAuthor(@PathVariable("id") UUID id) {

        if (this.Authors.exists(id)) {
            this.Logger.info("Deleting picture: " + id);

            this.Authors.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            this.Logger.warn("Author for delete not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = AUTHORS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {

        Author newAuthor = new Author(author.getName());

        this.Authors.save(newAuthor);
        this.Logger.info("Created author: " + newAuthor.getId());

        return new ResponseEntity<>(newAuthor,HttpStatus.OK);
    }

    @RequestMapping(value = AUTHOR_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable(value = "id") UUID id) {

        if (!this.Authors.exists(id)) {
            this.Logger.warn("Author for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Logger.info("Updated author: " + id);

            Author a = this.Authors.findOne(id);
            a.setName(author.getName());

            this.Authors.save(a);
            return new ResponseEntity<>(a,HttpStatus.OK);
        }
    }
}