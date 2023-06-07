package com.project.musinsastocknotificationbot.domain.member.entity;

import com.project.musinsastocknotificationbot.common.entity.BaseTimeEntity;
import com.project.musinsastocknotificationbot.domain.member.entity.vo.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseTimeEntity {

  @Id
  private Long id;

  @Column
  private String email;

  @Column
  private String provider;

  @Enumerated(EnumType.STRING)
  Role role;

  protected Member() {}

  public Member(String email, String provider, Role role) {
    this.email = email;
    this.provider = provider;
    this.role = role;
  }

  public Member update(String provider) {
    this.provider = provider;
    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }
}
