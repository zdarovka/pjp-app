package cz.tul.api;

/**
 * Created by zdars on 30.05.2016.
 */


import cz.tul.client.ServerApi;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@RestController
public class AuthorsApiController extends ApiBaseController {

    @Autowired
    private AuthorRepository Authors;

    @RequestMapping(value = ServerApi.AUTHORS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Author>> getAuthors() {

        super.LogAuthors("Fetching all authors");
        return new ResponseEntity<>(this.Authors.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.AUTHOR_PATH, method = RequestMethod.GET)
    public ResponseEntity<Author> getAuthor(@PathVariable("id") UUID id) {

        Author author = this.Authors.findOne(id);
        if(author == null)
        {
            super.Logger.warn("API - Authors - Author not found: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        super.LogAuthors("Get author by Id: " + id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.AUTHOR_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteAuthor(@PathVariable("id") UUID id) {

        if (this.Authors.exists(id)) {
            super.LogAuthors("Deleting picture: " + id);

            this.Authors.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            super.Logger.warn("API - Authors - Author for delete not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.AUTHORS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {

        Author newAuthor = new Author(author.getName());

        this.Authors.save(newAuthor);
        super.LogAuthors("Created author: " + newAuthor.getId());

        return new ResponseEntity<>(newAuthor,HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.AUTHOR_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable(value = "id") UUID id) {

        if (!this.Authors.exists(id)) {
            super.Logger.warn("API - Authors - Author for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            super.LogAuthors("Updated author: " + id);

            Author a = this.Authors.findOne(id);
            a.setName(author.getName());

            this.Authors.save(a);
            return new ResponseEntity<>(a,HttpStatus.OK);
        }
    }
}