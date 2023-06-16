package com.project.musinsastocknotificationbot.common.config.security.jwt;

import com.project.musinsastocknotificationbot.domain.member.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberService memberService;

  public CustomUserDetailsService(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    long id = Long.parseLong(memberId);
    return memberService.findById(id);
  }
}
