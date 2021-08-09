package com.chiliexe.myyahoo.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.chiliexe.myyahoo.config.EmailProperties;
import com.chiliexe.myyahoo.models.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private EmailProperties properties;


    public void send(Question question)
    {
        // data
        String user = properties.getUsername();
        String password = properties.getPassword();
        String to = question.getEmail();
        String linkEditar = "http://localhost:8080/detalhe/editar";
        String link = "http://localhost:8080/detalhe/" + question.getSlug();
        

        String html = "<h4>Todas as informações da sua pergunda</h4>" +
        "<p>Dúvida: %s</p>" +
        "<p>Para editar esta pergunta basta seguir teste link: %s </p>" +
        "<p>Para acompanhar esta pergunta, guarde o link: %s </p>" +
        "<p>Código de acesso: <strong>%s</strong></p>" +
        "<br><br>" +
        "<span>Favor não responder este email <br>Atenciosamente MyYahoo</span>";

        String fhtml = String.format(
            html, question.getTitle(), linkEditar, link, question.getAccessKey() 
        );

        // Setup email server
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // auth
        Session session = Session.getInstance(prop, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });
        try {
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("(MyYahoo) Informações da sua pergunta ");
            message.setContent(fhtml, "text/html");

            Transport.send(message);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

