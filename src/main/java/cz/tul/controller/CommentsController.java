package cz.tul.controller;

import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class CommentsController extends BaseController{

    @RequestMapping("/comments/{id}/like")
    public String like(@PathVariable("id") UUID id) {
        Comment comment = super.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementLike();
        super.Comments.save(comment);

        super.Logger.info(String.format("Like comment (%s)", id));

        return "pictures";
    }

    @RequestMapping("/comments/{id}/dislike")
    public String dislike(@PathVariable("id") UUID id) {

        Comment comment = super.Comments.findOne(id);

        if(comment == null)
        {
            throw new ResourceNotFoundException();
        }
        comment.incrementDislikes();
        super.Comments.save(comment);

        super.Logger.info(String.format("Dislike comment (%s)", id));

        return "pictures";
    }
}
