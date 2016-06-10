package cz.tul.controller;

import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class CommentsController extends BaseController{

    @ResponseBody
    @RequestMapping("/comments/{id}/like")
    public int like(@PathVariable("id") UUID id) {
        Comment comment = super.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementLike();
        super.Comments.save(comment);

        super.Logger.info(String.format("Like comment (%s)", id));

        return comment.getDislikes();
    }

    @ResponseBody
    @RequestMapping("/comments/{id}/dislike")
    public int dislike(@PathVariable("id") UUID id) {

        Comment comment = super.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementDislikes();
        super.Comments.save(comment);

        super.Logger.info(String.format("Dislike comment (%s)", id));

        return comment.getDislikes();
    }
}
