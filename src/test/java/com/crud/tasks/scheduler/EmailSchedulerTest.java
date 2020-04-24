package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmailSchedulerTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendmailWithFewTask() {
        //Given
        when(adminConfig.getAdminMail()).thenReturn("tmp@tmp.tmp");
        Mail mail = emailScheduler.prepareMail(2);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
        assertTrue(mail.getMessage().contains("tasks"));
    }

    @Test
    public void shouldSendmailWithOneTask() {
        //Given
        when(adminConfig.getAdminMail()).thenReturn("tmp@tmp.tmp");
        Mail mail = emailScheduler.prepareMail(1);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
        assertTrue(mail.getMessage().contains("task"));
    }
}
