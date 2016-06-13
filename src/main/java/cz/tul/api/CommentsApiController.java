package cz.tul.api;

import cz.tul.client.ServerApi;
import cz.tul.data.Author;
import cz.tul.data.Comment;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.CommentRepository;
import cz.tul.repositories.PictureRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@RestController
public class CommentsApiController {

    private org.slf4j.Logger Logger = LoggerFactory.getLogger(CommentsApiController.class);

    @Autowired
    private PictureRepository Pictures;

    @Autowired
    private AuthorRepository Authors;

    @Autowired
    private CommentRepository Comments;

    @RequestMapping(value = ServerApi.COMMENTS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getComments() {

        this.Logger.info("Fetching all comments");
        return new ResponseEntity<>(this.Comments.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable("id") UUID id) {

        this.Logger.info("Get Comment by Id: " + id);

        Comment comment = this.Comments.findOne(id);
        if(comment == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("id") UUID id) {

        if (this.Comments.exists(id)) {
            this.Logger.info("Deleting comment: " + id);

            this.Comments.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            this.Logger.warn("Comment for delete not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.COMMENTS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @RequestParam(value = "author", required = true) UUID author,@RequestParam(value = "picture", required = true) UUID picture) {

        Author a = this.Authors.findOne(author);

        if(a == null)
        {
            this.Logger.warn("Author of comment not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Picture p = this.Pictures.findOne(picture);

        if(p == null)
        {
            this.Logger.warn("Picture not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Comment newCom = new Comment(UUID.randomUUID(), a, comment.getText(), p, new Date());
        newCom.setLikes(comment.getLikes());
        newCom.setDislikes(comment.getDislikes());


        this.Comments.save(newCom);
        this.Logger.info("Created comment: " + newCom.getId());
        return new ResponseEntity<>(newCom,HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable(value = "id") UUID id) {
        if (!this.Comments.exists(id)) {
            this.Logger.warn("Comment for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Logger.info("Updated comment: " + id);

            Comment com = this.Comments.findOne(id);
            com.setDislikes(comment.getDislikes());
            com.setLikes(comment.getLikes());
            com.setText(comment.getText());
            com.setDateUpdated(new Date());

            this.Comments.save(com);
            return new ResponseEntity<>(com,HttpStatus.OK);
        }
    }
}
