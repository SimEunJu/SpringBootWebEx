package com.example.demo.security.handler;

import com.example.demo.security.dto.AuthMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        boolean fromSocial = authMemberDto.isFromSocial();
        boolean isSamePw = passwordEncoder.matches("1234", authMemberDto.getPassword());
        if(fromSocial && isSamePw){
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }
}
