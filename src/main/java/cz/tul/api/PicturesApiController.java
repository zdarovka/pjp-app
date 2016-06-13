package cz.tul.api;

import cz.tul.client.ServerApi;
import cz.tul.code.FileManager;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.PictureRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@RestController
public class PicturesApiController{


    @Autowired
    private FileManager imageDataMgr;

    private org.slf4j.Logger Logger = LoggerFactory.getLogger(PicturesApiController.class);

    @Autowired
    private PictureRepository Pictures;

    @Autowired
    private AuthorRepository Authors;

    @RequestMapping(value = ServerApi.PICTURES_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Picture>> getPictures() {

        this.Logger.info("Fetching all pictures");
        return new ResponseEntity<>(this.Pictures.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.GET)
    public ResponseEntity<Picture> getPicture(@PathVariable("id") UUID id) {

        this.Logger.info("Get picture by Id: " + id);

        Picture picture = this.Pictures.findOne(id);
        if(picture == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURES_BY_NAME_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Picture>> getPicturesByName(@PathVariable("name") String name) {

        this.Logger.info("Get pictures by name: " + name);

        List<Picture> pictures = this.Pictures.findByNameIgnoreCaseContaining(name);
        if(pictures == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURES_BY_AUTHOR_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Picture>> getPicturesByAuthor(@PathVariable("id") UUID id) {

        this.Logger.info("Get pictures by author: " + id);

        List<Picture> pictures = this.Pictures.findByAuthorId(id);
        if(pictures == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURES_BY_TAG_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Picture>> getPicturesByTag(@PathVariable("name") String name) {

        this.Logger.info("Get pictures by tag name: " + name);

        List<Picture> pictures = this.Pictures.findByTagsName(name);
        if(pictures == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deletePicture(@PathVariable("id") UUID id) {

        if (this.Pictures.exists(id)) {
            this.Logger.info("Deleting picture: " + id);

            this.Pictures.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            this.Logger.warn("Picture for delete not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.PICTURES_PATH, method = RequestMethod.POST)
    public ResponseEntity<Picture> addPicture(@RequestBody Picture picture, @RequestParam(value = "author", required = true) UUID author) {

        Author a = this.Authors.findOne(author);

        if(a == null)
        {
            this.Logger.warn("Author of picture not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Picture newPic = new Picture(UUID.randomUUID(), picture.getName(), picture.getUrl(), picture.getDateCreated(), picture.getLikes(), picture.getDislikes());
        newPic.setAuthor(a);

        this.Pictures.save(newPic);
        this.Logger.info("Created picture: " + newPic.getId());
        return new ResponseEntity<>(newPic,HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Picture> updatePicture(@RequestBody Picture picture, @PathVariable(value = "id") UUID id) {
        if (!this.Pictures.exists(id)) {
            this.Logger.warn("Picture for update not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Logger.info("Updated picture: " + id);

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


    @RequestMapping(value = ServerApi.PICTURE_LIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Picture> likePicture(@PathVariable(value = "id") UUID id) {
        if (!this.Pictures.exists(id)) {
            this.Logger.warn("Picture for like not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Logger.info("Liked picture: " + id);

            Picture pic = this.Pictures.findOne(id);
            pic.incrementLike();

            this.Pictures.save(pic);
            return new ResponseEntity<>(pic,HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.PICTURE_DISLIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Picture> dislikePicture(@PathVariable(value = "id") UUID id) {
        if (!this.Pictures.exists(id)) {
            this.Logger.warn("Picture for dislike not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.Logger.info("Disliked picture: " + id);

            Picture pic = this.Pictures.findOne(id);
            pic.incrementDislikes();

            this.Pictures.save(pic);
            return new ResponseEntity<>(pic, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.UPLOAD_PATH, method = RequestMethod.POST)
    public ResponseEntity<Picture> uploadImage(
            @PathVariable("name") String name,
            @PathVariable("author") UUID author,
            @RequestParam("data") MultipartFile imageData)
    {
        if(!this.Authors.exists(author))
        {
            this.Logger.warn("Author not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {

            String path = imageDataMgr.saveImageData(name, imageData.getInputStream());

            Author a = this.Authors.findOne(author);
            path = "file:///" + path.replace("\\", "/");
            Picture p = new Picture(UUID.randomUUID(), name,  path, new Date());
            p.setAuthor(a);

            this.Pictures.save(p);
            this.Logger.info("Picture uploaded: " + p.getId());
            return new ResponseEntity<>(p, HttpStatus.OK);

        } catch (IOException e) {
            this.Logger.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
