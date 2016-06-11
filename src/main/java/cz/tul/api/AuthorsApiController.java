package cz.tul.api;

/**
 * Created by zdars on 30.05.2016.
 */


import cz.tul.client.ServerApi;
import cz.tul.data.Author;
import cz.tul.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        super.LogAuthors("Get author by Id: " + id);
        return new ResponseEntity<>(this.Authors.findOne(id), HttpStatus.OK);
    }
}