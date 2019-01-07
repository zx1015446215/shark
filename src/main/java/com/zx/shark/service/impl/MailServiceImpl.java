package com.zx.shark.service.impl;

import com.zx.shark.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;  //模板引擎

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);
        helper.setFrom(from);
        mailSender.send(message);
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setText(content);
        helper.setTo(to);
        helper.setSubject(subject);
        FileSystemResource file = new FileSystemResource(filePath);
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        mailSender.send(message);
    }

    /**
     * content 实例
     *  "<html><body>这是有图片的邮件:<img src=\'cid:"+rscId+
     *  "\'>"+
     *  "</body><html/>"
     * @param to
     * @param subject
     * @param content
     * @param rscPath  路径
     * @param rscId   图片的id
     * @throws MessagingException
     */
    @Override
    public void sendInlinResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setText(content);
        helper.setTo(to);
        helper.setSubject(subject);
        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,res);
        mailSender.send(message);
    }


    public void TestTemplate(){
        Context context = new Context();
        context.setVariable("id","006");
        templateEngine.process("email/emailTemplate",context);

    }
}
