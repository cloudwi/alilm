package com.project.musinsastocknotificationbot.infrastructure.message.email.service;

import com.project.musinsastocknotificationbot.infrastructure.message.service.MessageService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.io.UnsupportedEncodingException;
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
    // TODO document why this method is empty
  }

  //메일 양식 작성
  public MimeMessage createEmailForm(String email) {

    String setFrom = "cloudwiiiii@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
    String toEmail = email; //받는 사람
    String title = "every-time-clone-web";
    String content = "내용";

    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      message.addRecipients(RecipientType.TO, toEmail); //보낼 이메일 설정
      message.setSubject(title);
      message.setText(content);//제목 설정
      message.setFrom(setFrom); //보내는 이메일
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

    return message;
  }

  //실제 메일 전송
  public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
    MimeMessage emailForm = createEmailForm(toEmail);
    javaMailSender.send(emailForm);

    return null; //인증 코드 반환
  }
}
