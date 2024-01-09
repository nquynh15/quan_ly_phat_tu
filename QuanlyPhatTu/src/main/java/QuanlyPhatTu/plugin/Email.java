package QuanlyPhatTu.plugin;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author PC
 */
public class Email {

    private String myEmail;
    private String emailPass;
    private int count = 1;

    public Email(String myEmail_, String emailPass_) {
        myEmail = myEmail_;
        emailPass = emailPass_;
//        myEmail = "nguyenquynh170820@gmail.com";
//        String emailPass = "taoypvnczzcrnmok";

    }

    public boolean sendContentToVer2(String toEmail, String headerEmail, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, emailPass);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(toEmail, "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse(toEmail, false));
            msg.setSubject(headerEmail, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (MessagingException ex) {
            System.out.println("Error.");
            return false;
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Yêu cầu mật khẩu 16 ký tự từ xác minh 2 bước Google.");
            return false;
        }
        return true;
    }

    /**
     * Gửi email sử dụng SMTP
     *
     * @param toEmail     gửi đến.
     * @param headerEmail tiêu đề email.
     * @param body        thân email.
     * @param dataSource  link file.
     * @return Trả về true nếu gửi thành công và false nếu thất bại.
     */
//    public boolean sendContentAndMultipartToVer2(String toEmail, String headerEmail, String body, List<String> dataSource) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//        props.put("mail.smtp.port", "587"); //TLS Port
//        props.put("mail.smtp.auth", "true"); //enable authentication
//        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(myEmail, emailPass);
//            }
//        });
//        MimeMessage message = new MimeMessage(session);
//        try {
//            message.setFrom(new InternetAddress(myEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//            message.setSubject(headerEmail);
//            BodyPart headerEmailPart = new MimeBodyPart();
//            headerEmailPart.setText(body);
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(headerEmailPart);
//            for (String source : dataSource) {
//                MimeBodyPart bodyPart = new MimeBodyPart();
//                DataSource dataSourceDetail = new FileDataSource(source);
//                bodyPart.setDataHandler(new DataHandler(dataSourceDetail));
//                bodyPart.setFileName("file" + count);
//                multipart.addBodyPart(bodyPart);
//                count++;
//            }
//            message.setContent(multipart);
//            Transport.send(message);
//        } catch (AddressException ex) {
//            System.out.println("Yêu cầu mật khẩu 16 ký tự từ xác minh 2 bước Google.");
//            return false;
//        } catch (MessagingException ex) {
//            System.out.println("Yêu cầu mật khẩu 16 ký tự từ xác minh 2 bước Google.");
//            return false;
//        }
//        return true;
//    }

}
