package com.org.fashtag.notify;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;

import com.org.fashtag.util.CommonUtils;

public class MailSender {

	private static String mailHost = "localhost";
	private static String user;
	private static String pass;
	private static MailSender fMailSender = null;
	private MailSender() {
	}

	/**
	 * default mail host will be localhost
	 * 
	 * @return
	 */
	public static MailSender getSoleInstance() {

		if (fMailSender == null) {
			fMailSender = new MailSender();
		}
		return fMailSender;
	}

	/**
	 * @param aHost
	 * @return
	 */
	public static MailSender getSoleInstance(String aHost) {
		if (fMailSender == null) {
			fMailSender = new MailSender();
			if (CommonUtils.isEmpty(aHost)) {
				mailHost = "localhost";
			} else {
				mailHost = aHost;
			}
		}
		return fMailSender;
	}

	/**
	 * @param aHost
	 * @return
	 */
	public static MailSender getSoleInstance(String aHost, String userName,
			String password) {
		if (fMailSender == null) {
			fMailSender = new MailSender();
			if (CommonUtils.isEmpty(aHost)) {
				mailHost = "localhost";
			} else {
				mailHost = aHost;
			}
		}

		user = userName;
		pass = password;

		return fMailSender;
	}

	public boolean sendMessage(String aFromId, String aToId, String aSubject,
			String aMessage) {
		Properties tProps = new Properties();
		tProps.put("mail.smtp.host", mailHost);
		Session tSession = Session.getDefaultInstance(tProps, null);
		tSession.setDebug(false);
		try {
			MimeMessage tMsg = new MimeMessage(tSession);
			// parses the comma separated email addreses to an array
			InternetAddress[] tRecipientAddress = InternetAddress.parse(aToId);
			tMsg.setFrom(new InternetAddress(aFromId));
			tMsg.setRecipients(Message.RecipientType.TO, tRecipientAddress);
			tMsg.setSubject(aSubject);
			tMsg.setSentDate(new Date());
			// create & fill the first message part...
			MimeBodyPart tMBPart = new MimeBodyPart();
			tMBPart.setText(aMessage);
			// create the multipart and its parts to it...
			Multipart tMulPart = new MimeMultipart();
			tMulPart.addBodyPart(tMBPart);
			// add the body part to the message...
			tMsg.setContent(tMulPart);
			// send the message....
			Transport.send(tMsg);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
	}

	public boolean sendMessage(String aFromId, String aToId, String aCCIds,

	String aSubject, String aMessage) {
		Properties tProps = new Properties();
		tProps.put("mail.smtp.host", mailHost);
		Session tSession = Session.getDefaultInstance(tProps, null);
		tSession.setDebug(false);
		try {
			MimeMessage tMsg = new MimeMessage(tSession);
			// parses the comma separated email addreses to an array
			InternetAddress[] tRecipientAddress = InternetAddress.parse(aToId);
			// parses the comma separated email addreses to an array
			InternetAddress[] tCCAddress = InternetAddress.parse(aCCIds);
			tMsg.setFrom(new InternetAddress(aFromId));
			tMsg.setRecipients(Message.RecipientType.TO, tRecipientAddress);
			tMsg.setRecipients(Message.RecipientType.CC, tCCAddress);
			tMsg.setSubject(aSubject);
			tMsg.setSentDate(new Date());
			// create & fill the first message part...
			MimeBodyPart tMBPart = new MimeBodyPart();
			tMBPart.setText(aMessage);
			// create the multipart and its parts to it...
			Multipart tMulPart = new MimeMultipart();
			tMulPart.addBodyPart(tMBPart);
			// add the body part to the message...
			tMsg.setContent(tMulPart);
			// send the message....
			Transport.send(tMsg);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
	} // end of sendMessage()

	public boolean sendMessage(String aFromId, String aToId, String aCCIds,
			String aBCCIds, String aSubject, String aMessage) {
		Properties tProps = new Properties();
		tProps.put("mail.smtp.host", mailHost);
		Session tSession = Session.getDefaultInstance(tProps, null);
		tSession.setDebug(true);
		try {
			MimeMessage tMsg = new MimeMessage(tSession);
			// parses the comma separated email addreses to an array
			InternetAddress[] tRecipientAddress = InternetAddress.parse(aToId);
			// parses the comma separated email addreses to an array
			InternetAddress[] tCCAddress = InternetAddress.parse(aCCIds);
			// parses the comma separated email addreses to an array
			InternetAddress[] tBCCAddress = InternetAddress.parse(aBCCIds);
			tMsg.setFrom(new InternetAddress(aFromId));
			tMsg.setRecipients(Message.RecipientType.TO, tRecipientAddress);
			tMsg.setRecipients(Message.RecipientType.CC, tCCAddress);
			tMsg.setRecipients(Message.RecipientType.BCC, tBCCAddress);
			tMsg.setSubject(aSubject);
			tMsg.setSentDate(new Date());
			// create & fill the first message part...
			MimeBodyPart tMBPart = new MimeBodyPart();
			tMBPart.setText(aMessage);
			// create the multipart and its parts to it...
			Multipart tMulPart = new MimeMultipart();
			tMulPart.addBodyPart(tMBPart);
			// add the body part to the message...
			tMsg.setContent(tMulPart);
			// send the message....
			Transport.send(tMsg);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
	}

	public boolean sendEmailAttachment(String aFromId, String aToId,
			String aSubject, String aMessage, String aAttachFileName) {
		Properties tProps = new Properties();
		tProps.put("mail.smtp.host", mailHost);
		Session tSession = Session.getDefaultInstance(tProps, null);
		tSession.setDebug(true);
		try {
			MimeMessage tMsg = new MimeMessage(tSession);
			// parses the comma separated email addreses to an array
			InternetAddress[] tRecipientAddress = InternetAddress.parse(aToId);
			tMsg.setFrom(new InternetAddress(aFromId));
			tMsg.setRecipients(Message.RecipientType.TO, tRecipientAddress);
			tMsg.setSubject(aSubject);
			tMsg.setSentDate(new Date());
			// create the multipart and its parts to it...
			Multipart tMulPart = new MimeMultipart();
			// create & fill the first message part...
			BodyPart tMBPart = new MimeBodyPart();
			tMBPart.setText(aMessage);
			tMulPart.addBodyPart(tMBPart);
			// Attachment body
			tMBPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(aAttachFileName);
			tMBPart.setDataHandler(new DataHandler(fds));
			// set the attached file name
			tMBPart.setFileName(aAttachFileName.substring(
					aAttachFileName.lastIndexOf(File.separator) + 1,
					aAttachFileName.length()));
			tMulPart.addBodyPart(tMBPart);
			// add the body part to the message...
			tMsg.setContent(tMulPart);
			// send the message....
			Transport.send(tMsg);
			return true;

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}

	}

	public boolean sendFormattedMessage(String aFromId, String aToId,
			String aCCIds, String aBCCIds, String aSubject,
			String formattedMessage, String embeddedImagePath) {

		Properties tProps = new Properties();
		tProps.put("mail.smtp.host", mailHost);
		tProps.setProperty("mail.transport.protocol", "smtp");
		Session tSession = Session.getDefaultInstance(tProps, null);
		tSession.setDebug(true);
		try {
			Session mailSession = Session.getDefaultInstance(tProps, null);
			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(aSubject);
			// parses the comma separated email addreses to an array
			InternetAddress[] tRecipientAddress = null;
			if (!CommonUtils.isEmpty(aToId)) {
				tRecipientAddress = InternetAddress.parse(aToId);
			}

			// parses the comma separated email addreses to an array
			InternetAddress[] tCCAddress = null;
			if (!CommonUtils.isEmpty(aCCIds)) {
				tCCAddress = InternetAddress.parse(aCCIds);
			}

			// parses the comma separated email addreses to an array
			InternetAddress[] tBCCAddress = null;
			if (!CommonUtils.isEmpty(aBCCIds)) {
				tBCCAddress = InternetAddress.parse(aBCCIds);
			}

			message.setFrom(new InternetAddress(aFromId));
			message.setRecipients(Message.RecipientType.TO, tRecipientAddress);
			message.setRecipients(Message.RecipientType.CC, tCCAddress);
			message.setRecipients(Message.RecipientType.BCC, tBCCAddress);
			message.setSubject(aSubject);
			message.setSentDate(new Date());

			//
			// This HTML mail have to 2 part, the BODY and the embedded image
			//
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			if (!formattedMessage.contains("cid:image")) {
				formattedMessage += "<img src=\"cid:image\">";
			}
			messageBodyPart.setContent(formattedMessage, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// check whether any embedded image is found
			if (!CommonUtils.isEmpty(embeddedImagePath)) {
				try {
					MimeBodyPart embeddedImage = new MimeBodyPart();
					DataSource ds = new FileDataSource(embeddedImagePath);
					// DataSource ds = new URLDataSource(new URL("file:"
					// + embeddedImagePath));
					embeddedImage.setDataHandler(new DataHandler(ds));
					embeddedImage.setHeader("Content-ID", "<image>");
					multipart.addBodyPart(embeddedImage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// put everything together
			message.setContent(multipart);
			transport.connect();
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
	}
}
