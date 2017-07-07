package org.enricogiurin.sushibar.controller;

import org.enricogiurin.sushibar.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by enrico on 2/27/17.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "")
    public  @ResponseBody List<User> getAll() {
        return Arrays.asList(new User("enricogiurin", "enricogiurin@gmail.com"),
                new User("mariorossi", "mariorossi@yahoo.it"));
    }

}
