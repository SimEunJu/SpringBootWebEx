package com.example.demo.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDto extends User implements OAuth2User {

    private String email;
    private String name;
    private String pw;
    private boolean fromSocial;
    private Map<String, Object> attr;

    public AuthMemberDto(String username, String password, boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.pw = password;
        this.fromSocial = fromSocial;
    }

    public AuthMemberDto(String username, String password, boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        super(username, password, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
