package com.zx.shark.service;

import javax.mail.MessagingException;

public interface MailService {
    /**
     * 发送简单文本
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to,String subject,String content);

    /**
     * 发送html
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to,String subject,String content) throws MessagingException;
}
