package cz.tul.client;


import cz.tul.data.Author;
import cz.tul.data.Picture;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit.http.*;

import java.util.List;
import java.util.UUID;

public interface ServerApi {

    public static final String PICTURES_PATH = "/api/pictures";
    public static final String PICTURE_PATH = PICTURES_PATH + "/{id}";

    public static final String AUTHORS_PATH = "/api/authors";
    public static final String AUTHOR_PATH = AUTHORS_PATH + "/{id}";

    //-----------------------------------------------------------------------
    // Picture API

    @GET(PICTURES_PATH)
    public List<Picture> getPictures();

    @GET(PICTURE_PATH)
    public Picture getPicture(@Path("id") UUID id);

    @DELETE(PICTURES_PATH)
    public Picture deletePicture(@Path("id") UUID id);

    @POST(PICTURES_PATH)
    public Picture addPicture(@RequestBody Picture picture, @Param("author") UUID author);

    @PUT(PICTURE_PATH)
    public Picture updatePicture(@RequestBody Picture picture, @Path("id") UUID id);

    //-----------------------------------------------------------------------
    // Authors API

    @GET(PICTURES_PATH)
    public List<Author> getAuthors();

    @GET(PICTURE_PATH)
    public Author getAuthor(@Path("id") UUID id);

    //--------------------------------------------------------------------------
}
