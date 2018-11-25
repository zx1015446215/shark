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


    /**
     * 发送带附件的文本
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to,String subject,String content,String filePath) throws MessagingException;
}
