package cz.tul.api;

import cz.tul.client.ServerApi;
import cz.tul.data.Picture;
import cz.tul.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deletePicture(@PathVariable("id") UUID id) {

        if (this.Pictures.exists(id)) {
            super.LogPictures("Deleting picture: " + id);

            this.Pictures.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            super.Logger.warn("API - Pictures - Picture for delete not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.PICTURES_PATH, method = RequestMethod.POST)
    public ResponseEntity<Picture> addPicture(@RequestBody Picture picture) {

        if (this.Pictures.exists(picture.getId())) {
            super.Logger.warn("API - Pictures - Picture can not be created as is already existing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Pictures.save(picture);
            super.LogPictures("Created picture: " + picture.getId());
            return new ResponseEntity<>(picture,HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.PICTURES_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Picture> updatePicture(@RequestBody Picture picture) {
        if (!this.Pictures.exists(picture.getId())) {
            super.Logger.warn("API - Pictures - Picture for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            super.LogPictures("Updated picture: " + picture.getId());
            this.Pictures.save(picture);
            return new ResponseEntity<>(picture,HttpStatus.OK);
        }
    }
}
