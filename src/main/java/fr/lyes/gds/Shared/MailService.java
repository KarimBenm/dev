package fr.lyes.gds.Shared;

import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    /*
     * The Spring Framework provides an easy abstraction for sending email by using
     * the JavaMailSender interface, and Spring Boot provides auto-configuration for
     * it as well as a starter module.
     */
    private JavaMailSender javaMailSender;

    /**
     *
     * @param javaMailSender
     */
    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * This function is used to send mail without attachment.
     * @param user
     * @throws org.springframework.mail.MailException
     */

    public void sendEmail(User user) throws MailException {

        /*
         * This JavaMailSender Interface is used to send Mail in Spring Boot. This
         * JavaMailSender extends the MailSender Interface which contains send()
         * function. SimpleMailMessage Object is required because send() function uses
         * object of SimpleMailMessage as a Parameter
         */

        SimpleMailMessage mail = new SimpleMailMessage();
        System.out.println("wtf");
        mail.setTo(user.getEmail());
        mail.setSubject("Testing Mail API");
        mail.setText("Hurray ! You have done that dude...");

        /*
         * This send() contains an Object of SimpleMailMessage as an Parameter
         */
        javaMailSender.send(mail);
    }

    /**
     * This fucntion is used to send mail that contains a attachment.
     *
     * @param user
     * @throws MailException
     * @throws MessagingException
     */
    public void sendEmailWithAttachment(User user ,boolean fromAdmin) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(user.getEmail());
        helper.setSubject("SignUp for GDS APP ");
        String nL = System.getProperty("line.separator");
       StringBuilder stringBuilder = new StringBuilder();
        if(fromAdmin){
            stringBuilder.append("Dear ");
            stringBuilder.append(user.getUsername()+",");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("your GDS Account has been created ,you can access it from the following Link :");
            stringBuilder.append(nL);
            stringBuilder.append("http://localhost:4200/auth");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("please note that your password Is : "+user.getConfirmPassword() +"   you can change it once you are loggedIn");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("you have been granted the following permissions :");
            stringBuilder.append(nL);
            user.getAppGroupeList().forEach(role->{
                    switch (role.getCode()) {
                        case "Admin":
                            stringBuilder.append("             -  Complete access to the Administration of the App .");
                            stringBuilder.append(nL);
                            break;
                        case "Facturation":
                            stringBuilder.append("             -  Display/Creation for the  billing Module .");
                            stringBuilder.append(nL);
                            break;
                        case "Stock":
                            stringBuilder.append("             -  Managing the Supplying Process .");
                            stringBuilder.append(nL);
                            break;
                        case "Produit":
                            stringBuilder.append("             -  Creation, Updating and Deletion of the Products dealt By the Store .");
                            stringBuilder.append(nL);
                            break;
                        case "DashBoard":
                            stringBuilder.append("             -  Usage of the APP Dashboards .");
                            stringBuilder.append(nL);
                            break;
                    }
                });
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("For further Information feel free to contact the Administrator Via the following Email : ");
            stringBuilder.append(nL);
            stringBuilder.append("karim.l.bnm@gmail.com");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            helper.setText(stringBuilder.toString());
        }else{
            stringBuilder.append("Dear ");
            stringBuilder.append(user.getUsername()+",");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("your GDS Account has been created ,you can access it from the following Link :");
            stringBuilder.append(nL);
            stringBuilder.append("http://localhost:4200/auth");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("the Admins will grant you permissions shortly  and activate your account at the same time ");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("For further Information feel free to contact the Administrator Via the following Email : ");
            stringBuilder.append(nL);
            stringBuilder.append("karim.l.bnm@gmail.com");
            stringBuilder.append(nL);
            stringBuilder.append(nL);
            stringBuilder.append("thanks for your collaboration");
            helper.setText(stringBuilder.toString());
        }
        ClassPathResource classPathResource = new ClassPathResource("hattSoff.gif");
        helper.addAttachment(classPathResource.getFilename(), classPathResource);
        javaMailSender.send(mimeMessage);
    }

}
