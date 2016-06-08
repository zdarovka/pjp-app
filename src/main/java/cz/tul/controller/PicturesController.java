package cz.tul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Created by zdars on 30.05.2016.
 */
@Controller
public class PicturesController extends BaseController{

    @RequestMapping({"/", "/pictures", "/index"})
    public String index() {

        return "pictures";
    }
}
