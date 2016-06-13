package cz.tul.client;

/**
 * Created by zdars on 6/13/2016.
 */
public class ServerApi {

    public static final String AUTHORS_PATH = "/api/authors";
    public static final String AUTHOR_PATH = AUTHORS_PATH + "/{id}";


    public static final String COMMENTS_PATH = "/api/comments";
    public static final String COMMENT_PATH = COMMENTS_PATH + "/{id}";


    public static final String PICTURES_PATH = "/api/pictures";
    public static final String PICTURE_PATH = PICTURES_PATH + "/{id}";
}
