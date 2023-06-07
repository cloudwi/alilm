package com.project.musinsastocknotificationbot.message.infrastructure.email.service;

import com.project.musinsastocknotificationbot.message.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Profile("email")
public class EmailService implements MessageService {

  private final JavaMailSender javaMailSender;
  private final String from;

  public EmailService(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String from) {
    this.javaMailSender = javaMailSender;
    this.from = from;
  }

  @Override
  public void sendMessage(String message) {

  }
}
