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
    public static final String PICTURE_LIKE_PATH = PICTURE_PATH + "/like";
    public static final String PICTURE_DISLIKE_PATH = PICTURE_PATH + "/dislike";

    public static final String PICTURES_BY_NAME_PATH = PICTURES_PATH + "/name/{name}";
    public static final String PICTURES_BY_AUTHOR_PATH = PICTURES_PATH + "/author/{id}";
    public static final String PICTURES_BY_TAG_PATH = PICTURES_PATH + "/tag/{name}";
}
