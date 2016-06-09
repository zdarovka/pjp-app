package cz.tul.controller;

import cz.tul.DemoApplication;
import cz.tul.repositories.PictureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zdars on 30.05.2016.
 */

public abstract class BaseController {

    protected Logger Logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    PictureRepository Pictures;
}
