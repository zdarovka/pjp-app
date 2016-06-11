package cz.tul.api;

import cz.tul.client.ServerApi;
import cz.tul.data.Picture;
import cz.tul.repositories.PictureRepository;
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
public class PicturesApiController extends ApiBaseController {

    @Autowired
    private PictureRepository Pictures;

    @RequestMapping(value = ServerApi.PICTURES_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Picture>> getPictures() {

        super.LogPictures("Fetching all pictures");
        return new ResponseEntity<>(this.Pictures.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.GET)
    public ResponseEntity<Picture> getPicture(@PathVariable("id") UUID id) {

        super.LogPictures("Get picture by Id: " + id);

        Picture picture = this.Pictures.findOne(id);
        if(picture == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }
}
