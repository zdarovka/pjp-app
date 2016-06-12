package cz.tul.api;

import cz.tul.client.ServerApi;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.PictureRepository;
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
public class PicturesApiController extends ApiBaseController {

    @Autowired
    private PictureRepository Pictures;

    @Autowired
    private AuthorRepository Authors;

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
    public ResponseEntity<Picture> addPicture(@RequestBody Picture picture, @RequestParam(value = "author", required = true) UUID author) {

        Author a = this.Authors.findOne(author);

        if(a == null)
        {
            super.Logger.warn("API - Pictures - Author of picture not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Picture newPic = new Picture(UUID.randomUUID(), picture.getName(), picture.getUrl(), picture.getDateCreated(), picture.getLikes(), picture.getDislikes());
        newPic.setAuthor(a);

        this.Pictures.save(newPic);
        super.LogPictures("Created picture: " + newPic.getId());
        return new ResponseEntity<>(newPic,HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Picture> updatePicture(@RequestBody Picture picture, @PathVariable(value = "id") UUID id) {
        if (!this.Pictures.exists(id)) {
            super.Logger.warn("API - Pictures - Picture for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            super.LogPictures("Updated picture: " + picture.getId());

            Picture pic = this.Pictures.findOne(id);
            pic.setUrl(picture.getUrl());
            pic.setDislikes(picture.getDislikes());
            pic.setLikes(picture.getLikes());
            pic.setName(picture.getName());
            pic.setDateUpdated(new Date());

            this.Pictures.save(pic);
            return new ResponseEntity<>(pic,HttpStatus.OK);
        }
    }
}
