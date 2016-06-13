package cz.tul.controller;

import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Comment;
import cz.tul.repositories.CommentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class CommentsController {


    protected org.slf4j.Logger Logger = LoggerFactory.getLogger(CommentsController.class);

    @Autowired
    private CommentRepository Comments;


    @ResponseBody
    @RequestMapping("/comments/{id}/like")
    public int like(@PathVariable("id") UUID id) {
        Comment comment = this.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementLike();
        this.Comments.save(comment);

        this.Logger.info(String.format("Like comment (%s)", id));


        return comment.getLikes();
    }

    @ResponseBody
    @RequestMapping("/comments/{id}/dislike")
    public int dislike(@PathVariable("id") UUID id) {

        Comment comment = this.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementDislikes();
        this.Comments.save(comment);

        this.Logger.info(String.format("Dislike comment (%s)", id));

        return comment.getDislikes();
    }
}
