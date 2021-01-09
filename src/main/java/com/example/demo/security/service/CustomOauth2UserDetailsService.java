package com.example.demo.security.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.dto.AuthMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOauth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = null;
        if("Google".equals(clientName)){
            email = oauth2User.getAttribute("email");
        }

        Member member = saveSocialMember(email);

        AuthMemberDto authMemberDto = new AuthMemberDto(
                member.getEmail(),
                member.getPw(),
                true,
                member.getRoleSet().stream().map(role ->
                    new SimpleGrantedAuthority("ROLE_"+role.name())
                ).collect(Collectors.toList()),
                oauth2User.getAttributes()
        );
        authMemberDto.setName(member.getName());
        return authMemberDto;
    }
    private Member saveSocialMember(String email){
        Optional<Member> result = memberRepository.findByEmail(email, true);
        if(result.isPresent()){
            return result.get();
        }
        Member member = Member.builder()
                .email(email)
                .name(email)
                .pw(passwordEncoder.encode("1234"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);
        return member;
    }
}
