package cz.tul.controller;

import cz.tul.code.ResourceNotFoundException;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class PicturesController extends BaseController{

    //temp repository, will be removed
    @Autowired
    AuthorRepository authors;

    @RequestMapping({"/", "/pictures",  "/index/"})
    public String index() {

        Author a = new Author("john doe");

        authors.save(a);

        Picture p = new Picture(a);
        Picture p1 = new Picture(a);

        super.Pictures.save(p);
        super.Pictures.save(p1);
        super.Logger.info(String.format("stored picture (%s)", p.getId()));
        super.Logger.info(String.format("stored picture (%s)", p1.getId()));

        super.Logger.info(String.format("Redirect to picture (%s)", p1.getId()));

        return "redirect:/" + p1.getId();
    }

    @RequestMapping({"/{id}",  "/pictures/{id}", "/index/{id}"})
    public String index(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }

        super.Logger.info(String.format("Showing picture (%s)", picture.getId()));

        return "pictures";
    }

    @RequestMapping("/pictures/{id}/like")
    public String like(@PathVariable("id") UUID id) {
        Picture picture = super.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementLike();
        super.Pictures.save(picture);

        super.Logger.info(String.format("Like picture (%s)", id));

        return "pictures";
    }

    @RequestMapping("/pictures/{id}/dislike")
    public String dislike(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }
        picture.incrementDislikes();
        super.Pictures.save(picture);

        super.Logger.info(String.format("Dislike picture (%s)", id));

        return "pictures";
    }
}
