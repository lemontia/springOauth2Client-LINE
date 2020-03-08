package kr.lemontia.oauth2.oauth2;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
