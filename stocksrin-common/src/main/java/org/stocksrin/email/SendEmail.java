package org.stocksrin.email;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.stocksrin.common.utils.DateUtils;

public class SendEmail {
	private static boolean sendEmail = true;
	private static String toMail = "stocksrin2@gmail.com";
	static String hostName;

	static {
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void sentMailimp(String subject, String body, String msName) {
		toMail = "rahul6789sharma@gmail.com";
		sentMail(subject, body, msName);

	}

	public static void sentMail(String subject, String body, String msName) {
		if (hostName.contains("BLRDL-PC0W67KX")) {
			// dont send events
			return;
		}
		try {
			String username = "rahul6789sharma@gmail.com";
			String password = "8971323434@123";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("rahul6789sharma@gmail.com", "8971323434@123");
				}
			});
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("rahul6789sharma@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
			message.setSubject("Ms : " + subject);
			String str = DateUtils.dateToString(new Date(), "dd-MMM hh:mm:ss");
			String finalBody = body + "\n \n*******************\n\n" + hostName + "\n********** \n" + str;
			finalBody = finalBody + "\n MicroSerice : " + msName;
			message.setText(finalBody);
			if (sendEmail) {
				Transport.send(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sent email to :" + toMail);
	}
}
