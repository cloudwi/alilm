package com.project.musinsastocknotificationbot.domain.member.service;

import com.project.musinsastocknotificationbot.domain.member.entity.Member;
import com.project.musinsastocknotificationbot.domain.member.error.MemberNotFoundException;
import com.project.musinsastocknotificationbot.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Member findById(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
  }

}
