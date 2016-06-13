package cz.tul.client;


public interface ServerApi {

    public static final String UPLOAD_PATH = "/api/upload/{name}";

    public static final String PICTURES_PATH = "/api/pictures";
    public static final String PICTURE_PATH = PICTURES_PATH + "/{id}";

    public static final String AUTHORS_PATH = "/api/authors";
    public static final String AUTHOR_PATH = AUTHORS_PATH + "/{id}";

    public static final String COMMENTS_PATH = "/api/comments";
    public static final String COMMENT_PATH = COMMENTS_PATH + "/{id}";

}
