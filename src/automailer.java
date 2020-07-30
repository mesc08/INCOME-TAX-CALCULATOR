import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;


class automailer{

    public String from = "mike.tax.calculator@gmail.com";
    public String password = "taxCALC465";
    public String to;


    public automailer(String to2) {
        to = to2;

    }
    public void send_user(String sub, String msg, boolean flag, String FILE){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(this.to));
            message.setSubject(sub);


            if (flag) {
                Multipart emailcontent = new MimeMultipart();
                MimeBodyPart pdfattachment = new MimeBodyPart();
                MimeBodyPart textbodypart = new MimeBodyPart();
                textbodypart.setText(msg);
                pdfattachment.attachFile(FILE);
                emailcontent.addBodyPart(textbodypart);
                emailcontent.addBodyPart(pdfattachment);
                message.setContent(emailcontent);
            }
            else{
                message.setText(msg);
            }

            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        }
        catch (MessagingException e) {
            JOptionPane.showMessageDialog(null,"Unable to sent mail, check your connection or your e-mail id","Alert",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send_admins(String sub, String msg){
        String to2 = "p.somnath2599@gmail.com";



        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to2));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        }
        catch (MessagingException e) {
            JOptionPane.showMessageDialog(null,"Unable to sent mail, check your connection or your e-mail id","Alert",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);

        }
    }

}
