package com.example.demo.controller;

import com.example.demo.security.dto.AuthMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public void allowAll(){

    }

    @PreAuthorize("#authMemberDto != null && #authMemberDto.username eq \"test@gmail.com\"")
    @GetMapping("/member")
    public void allowMember(@AuthenticationPrincipal AuthMemberDto authMemberDto){


    }

    @PreAuthorize("permitAll()")
    @GetMapping("/admin")
    public void allowAdmin(){

    }
}
