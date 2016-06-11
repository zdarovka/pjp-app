package cz.tul.api;

import cz.tul.DemoApplication;
import org.slf4j.LoggerFactory;

/**
 * Created by zdars on 30.05.2016.
 */
public abstract class ApiBaseController {


    protected org.slf4j.Logger Logger = LoggerFactory.getLogger(DemoApplication.class);

    protected void LogAuthors(String msg)
    {
        this.Logger.info("[API] - Authors - " + msg);
    }

    protected void LogPictures(String msg)
    {
        this.Logger.info("[API] - Pictures - " + msg);
    }

    protected void LogTags(String msg)
    {
        this.Logger.info("[API] - Tags - " + msg);
    }

    protected void LogComments(String msg)
    {
        this.Logger.info("[API] - Comments - " + msg);
    }
}
