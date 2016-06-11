package cz.tul.client;


import com.sun.org.apache.bcel.internal.generic.ATHROW;
import cz.tul.data.Author;
import cz.tul.data.Picture;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;
import java.util.UUID;

public interface ServerApi {

    public static final String PICTURES_PATH = "/api/pictures";
    public static final String PICTURE_PATH = PICTURES_PATH + "/{id}";

    public static final String AUTHORS_PATH = "/api/authors";
    public static final String AUTHOR_PATH = AUTHORS_PATH + "/{id}";

    @GET(PICTURES_PATH)
    public List<Picture> getPictures();

    @GET(PICTURE_PATH)
    public Picture getPicture(@Path("id") UUID id);

    @GET(PICTURES_PATH)
    public List<Author> getAuthors();

    @GET(PICTURE_PATH)
    public Author getAuthor(@Path("id") UUID id);


}
