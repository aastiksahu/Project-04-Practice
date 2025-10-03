package com.rays.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.rays.exception.ApplicationException;

/**
 * Utility class to send emails using SMTP configuration.
 * <p>
 * Reads configuration from a resource bundle and sends mail using JavaMail API.
 * </p>
 * 
 * Supports sending both HTML and plain text messages.
 * 
 * @author Aastik Sahu
 */
public class EmailUtility {

    /** Loads email configuration from system resource bundle */
    static ResourceBundle rb = ResourceBundle.getBundle("com.rays.bundle.system");

    /** SMTP server host name */
    private static final String SMTP_HOST_NAME = rb.getString("smtp.server");

    /** SMTP server port */
    private static final String SMTP_PORT = rb.getString("smtp.port");

    /** Email address used to send messages */
    private static final String emailFromAddress = rb.getString("email.login");

    /** Password for the email sender address */
    private static final String emailPassword = rb.getString("email.pwd");

    /** JavaMail SMTP configuration properties */
    private static Properties props = new Properties();

    // Static initializer block for setting SMTP properties
    static {
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    /**
     * Sends an email message using the configured SMTP server.
     *
     * @param emailMessageDTO an instance of {@link EmailMessage} containing message details
     * @throws ApplicationException if there is an error while sending the email
     */
    public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException {
        try {
            // Setup mail session with authentication
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailFromAddress, emailPassword);
                }
            });

            // Create and setup the email message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFromAddress));
            msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(emailMessageDTO.getTo()));
            msg.setSubject(emailMessageDTO.getSubject());

            // Set content type (HTML or plain text)
            String contentType = emailMessageDTO.getMessageType() == EmailMessage.HTML_MSG ? "text/html" : "text/plain";
            msg.setContent(emailMessageDTO.getMessage(), contentType);

            // Send the message
            Transport.send(msg);

        } catch (Exception ex) {
            throw new ApplicationException("Email Error: " + ex.getMessage());
        }
    }

    /**
     * Converts a comma-separated list of email addresses into an array of {@link InternetAddress}.
     *
     * @param emails comma-separated email addresses
     * @return array of InternetAddress objects
     * @throws Exception if address parsing fails
     */
    private static InternetAddress[] getInternetAddresses(String emails) throws Exception {
        if (emails == null || emails.isEmpty()) {
            return new InternetAddress[0];
        }
        String[] emailArray = emails.split(",");
        InternetAddress[] addresses = new InternetAddress[emailArray.length];
        for (int i = 0; i < emailArray.length; i++) {
            addresses[i] = new InternetAddress(emailArray[i].trim());
        }
        return addresses;
    }

}
