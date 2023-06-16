package com.project.musinsastocknotificationbot.common.config.security.oauth2;

import com.project.musinsastocknotificationbot.common.config.security.jwt.JwtTokenProvider;
import com.project.musinsastocknotificationbot.domain.member.entity.Member;
import com.project.musinsastocknotificationbot.domain.member.entity.vo.Role;
import com.project.musinsastocknotificationbot.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public OAuth2AuthenticationSuccessHandler(MemberRepository memberRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.memberRepository = memberRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    var principal = authentication.getPrincipal();

    if (principal instanceof OAuth2User oauth2User) {

      Member member = saveOrUpdate(oauth2User.getAttributes());
      String targetUrl = determineTargetUrl(member);
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
  }

  private Member saveOrUpdate(Map<String, Object> attributes) {
    String email = (String) attributes.get("email");
    Provider provider = (Provider) attributes.get("provider");

    Member member = memberRepository.findByEmail(email)
        .map(entity -> entity.update(provider))
        .orElse(toEntity(attributes));

    return memberRepository.save(member);
  }

  private Member toEntity(Map<String, Object> attributes) {
    String email = (String) attributes.get("email");
    Provider provider = (Provider) attributes.get("provider");
    Role role = Role.USER;

    return Member.from(email, provider, role);
  }

  protected String determineTargetUrl(Member member) {

    String targetUrl = "http://localhost:3000/";

    return UriComponentsBuilder.fromOriginHeader(targetUrl)
        .queryParam("Authorization", jwtTokenProvider.createToken(member.getId()))
        .build()
        .toUriString();
  }
}
