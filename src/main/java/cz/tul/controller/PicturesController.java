package cz.tul.controller;

import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Comment;
import cz.tul.data.Picture;
import cz.tul.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class PicturesController extends BaseController{

    @Autowired
    PictureRepository Pictures;

    @RequestMapping({"/", "/pictures",  "/index"})
    public String index() {
        Picture picture = this.Pictures.first();
        super.Logger.info(String.format("Redirect to picture (%s)", picture.getId()));

        return "redirect:/" + picture.getId();
    }

    @RequestMapping({"/{id}",  "/pictures/{id}", "/index/{id}"})
    public ModelAndView index(@PathVariable("id") UUID id) {

        Picture picture = this.Pictures.findOne(id);

        Picture prev = this.Pictures.previous(picture.getDateCreated());
        Picture next = this.Pictures.next(picture.getDateCreated());

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }

        super.Logger.info(String.format("Showing picture (%s)", picture.getId()));

        ModelAndView mav = new ModelAndView("pictures");
        mav.addObject("picture", picture );
        if(prev != null)
        {
            super.Logger.info(String.format("prev picture (%s)", prev.getId()));
            mav.addObject("prev", prev);
        }
        if(next != null)
        {
            super.Logger.info(String.format("next picture (%s)", next.getId()));
            mav.addObject("next", next);
        }

        List<Comment> sorted = picture.getComments();
        sorted.sort((p1, p2) -> p1.getDateCreated().compareTo(p2.getDateCreated()));
        picture.setComments(sorted);

        return mav;
    }

    @ResponseBody
    @RequestMapping("/pictures/{id}/like")
    public int like(@PathVariable("id") UUID id) {

        Picture picture = this.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementLike();
        this.Pictures.save(picture);

        super.Logger.info(String.format("Like picture (%s)", id));

        return picture.getLikes();
    }

    @ResponseBody
    @RequestMapping("/pictures/{id}/dislike")
    public int dislike(@PathVariable("id") UUID id) {

        Picture picture = this.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementDislikes();
        this.Pictures.save(picture);

        super.Logger.info(String.format("Dislike picture (%s)", id));

        return picture.getDislikes();
    }
}
