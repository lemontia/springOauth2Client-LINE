package kr.lemontia.oauth2.config.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 소셜로 로그인 한 후 처리
 */
@Component
public class CustomOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        OidcUserInfo userInfo = null;
        Set<GrantedAuthority> authorities = setGrantedAuthorities(userRequest, userInfo);

        OidcUser user;

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        if (StringUtils.hasText(userNameAttributeName)) {
            user = new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo, userNameAttributeName);
        } else {
            user = new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);
        }

        return user;
    }

    private Set<GrantedAuthority> setGrantedAuthorities(OidcUserRequest userRequest, OidcUserInfo userInfo) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OidcUserAuthority(userRequest.getIdToken(), userInfo));
        // SCOPE_{}는 필요없으므로 추가하지 않는다
//        OAuth2AccessToken token = userRequest.getAccessToken();
//        for (String authority : token.getScopes()) {
//            authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
//        }

        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        return authorities;
    }
}
