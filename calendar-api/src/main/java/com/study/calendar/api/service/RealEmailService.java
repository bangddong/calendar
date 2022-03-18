package com.study.calendar.api.service;

import com.study.calendar.api.dto.EngagementEmailStuff;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Locale;

@EnableAsync
@RequiredArgsConstructor
@Service
public class RealEmailService implements EmailService{

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @EventListener
    @Async
    public void sendEngagement(EngagementEmailStuff stuff) {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(stuff.getToEmail());
            helper.setSubject(stuff.getSubject());
            helper.setText(
                    templateEngine.process("engagement-email",
                        new Context(Locale.KOREAN, stuff.getProps())), true
            );
        };
        emailSender.send(preparator);
    }

}
