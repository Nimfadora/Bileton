package service.impl;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class MailService {

    private Properties properties = System.getProperties();
    private String from = "leravasilyeva@bk.ru";
    private String username = "leravasilyeva@bk.ru";
    private String password = "photographer00@bk.ru";

    private static volatile MailService instance;

    public static MailService getInstance() {
        MailService localInstance = instance;
        if (localInstance == null) {
            synchronized (MailService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MailService();
                }
            }
        }
        return localInstance;
    }
    private MailService() {
        init();
    }

    private void init() {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");
    }

    public void sendMailTo(String email, String file, Integer messageType) {
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            System.out.println(email);
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Set Subject: header field
            message.setSubject("Bileton: e-ticket");


            if(messageType == 1) {

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                messageBodyPart.setText("Здравствуйте! \nСпасибо, что пользуетесь нашим сервисом!");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                String filename = "C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\ticketsPdf\\" + file;
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);
            }else{
                message.setText("Здравствуйте! \nСпасибо, что пользуетесь нашим сервисом!\n id Вашего заказа:" + file.substring(0, file.length() - 4) +
                        "\nВы имеете возможность оплатить данный заказ до окончания срока бронирования, который истекает за день до представления.\n" +
                        "Для этого Вам нужно сказать id своего заказа кассиру выбранного театра");
            }

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
