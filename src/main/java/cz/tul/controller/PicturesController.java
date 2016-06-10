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

        Author a = new Author("john doe");

        authors.save(a);

        Picture p = new Picture(a);
        p.setDateCreated(DataHelper.randomDate());
        p.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-EjO7WIBpny2Q9beiqP45EfiOGLXYxTDLtaDWhf4LQ43Y36Xtv0iMEkN4");
        Picture p1 = new Picture(a);
        p1.setDateCreated(DataHelper.randomDate());
        p1.setUrl("http://az616578.vo.msecnd.net/files/2016/05/08/635982646968672800-1766933105_fresh_nature-1280x720.jpg");
        Picture p2 = new Picture(a);
        p2.setDateCreated(DataHelper.randomDate());
        p2.setUrl("http://az616578.vo.msecnd.net/files/2016/04/23/6359702716756729221307410466_Free-Wallpaper-Nature-Scenes.jpg");

        super.Pictures.save(p);
        super.Pictures.save(p1);
        super.Pictures.save(p2);

        Picture picture = super.Pictures.first();
        super.Logger.info(String.format("Redirect to picture (%s)", picture.getId()));

        return "redirect:/" + p1.getId();
    }

    @RequestMapping({"/{id}",  "/pictures/{id}", "/index/{id}"})
    public ModelAndView index(@PathVariable("id") UUID id) {

        Picture picture = super.Pictures.findOne(id);

        Picture prev = super.Pictures.previous(picture.getDateCreated());
        Picture next = super.Pictures.next(picture.getDateCreated());

        super.Logger.info(String.format("prev picture (%s)", prev.getId()));

        super.Logger.info(String.format("next picture (%s)", next.getId()));

        if(picture == null)
        {
            throw new ResourceNotFoundException();
        }

        super.Logger.info(String.format("Showing picture (%s)", picture.getId()));

        ModelAndView mav = new ModelAndView("pictures");
        mav.addObject("picture", picture );
        mav.addObject("prev", prev);
        mav.addObject("next", next);

        //todo: comments

        return mav;
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
