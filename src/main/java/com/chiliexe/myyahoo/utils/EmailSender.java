package com.chiliexe.myyahoo.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.chiliexe.myyahoo.models.Question;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    
    public void send(Question question)
    {
        // data
        String to = question.getEmail();
        String from = "chiliexebr@gmail.com";
        String host = "smtp.gmail.com";
        Properties props = System.getProperties();

        String html = "<h4>Todas as informações da sua pergunda</h4>" +
        "<p>Dúvida: %s</p>" +
        "<p>Para editar esta pergunta basta seguir teste link: <a href='%s'></a></p>" +
        "<p>Para acompanhar esta pergunta, guarde o link: <a href='%s'></a></p>" +
        "<p>Código de acesso: <strong>%s</strong></p>" +
        "<br><br>" +
        "<span>Favor não responder este email <br>Atenciosamente MyYahoo</span>";

        // Setup email server
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        // auth
        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "Anode2019");
            }
        });
        session.setDebug(true);

        try {
            
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("(MyYahoo) Informações da sua pergunta ");
            message.setContent(
                String.format(
                    html, question.getTitle(),
                    "localhost:8080/detalhe/editar",
                    "localhost:8080/detalhe/" + question.getSlug(),
                    question.getAccessKey() 
                ),
                "text/html");

            Transport.send(message);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
