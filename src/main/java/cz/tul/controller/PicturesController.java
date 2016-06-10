package cz.tul.controller;

import cz.tul.code.DataHelper;
import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class PicturesController extends BaseController{

    //temp repository, will be removed
    @Autowired
    AuthorRepository authors;



    @RequestMapping({"/", "/pictures",  "/index"})
    public String index() {
        Picture picture = super.Pictures.first();
        super.Logger.info(String.format("Redirect to picture (%s)", picture.getId()));

        return "redirect:/" + picture.getId();
    }

    @RequestMapping({"/{id}",  "/pictures/{id}", "/index/{id}"})
    public ModelAndView index(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        Picture prev = super.Pictures.previous(picture.getDateCreated());
        Picture next = super.Pictures.next(picture.getDateCreated());

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

        //todo: comments

        return mav;
    }

    @ResponseBody
    @RequestMapping("/pictures/{id}/like")
    public int like(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementLike();
        super.Pictures.save(picture);

        super.Logger.info(String.format("Like picture (%s)", id));

        return picture.getLikes();
    }

    @ResponseBody
    @RequestMapping("/pictures/{id}/dislike")
    public int dislike(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementDislikes();
        super.Pictures.save(picture);

        super.Logger.info(String.format("Dislike picture (%s)", id));

        return picture.getDislikes();
    }
}
