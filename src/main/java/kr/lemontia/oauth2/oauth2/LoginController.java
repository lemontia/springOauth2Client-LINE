package kr.lemontia.oauth2.oauth2;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/loginSuccess")
    public Object getLoginInfo(OAuth2AuthenticationToken authentication) {
        System.out.println("authentication = " + authentication);
        return authentication.getPrincipal().getAttributes();
    }
}
