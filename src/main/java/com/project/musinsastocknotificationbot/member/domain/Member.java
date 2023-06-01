package com.project.musinsastocknotificationbot.member.domain;

import com.project.musinsastocknotificationbot.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseTimeEntity {

  @Id
  private Long id;

  @Column
  private String password;

  protected Member() {}

  public Member(long id, String password) {
    this.id = id;
    this.password = password;
  }
}
