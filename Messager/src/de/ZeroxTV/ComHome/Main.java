package de.ZeroxTV.ComHome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class Main {
	
	final static int sensorAmt = 2;
	public static HashMap<Integer, String> last = new HashMap<Integer, String>();
	public static String email;
	public static void main(String[] args) {
		Connection con = connect();
		for (int i = 1; i <= sensorAmt; i++) {
			last.put(i, getState(con, i));
		}
		
		email = SQLSelect(con, "SELECT * FROM Config WHERE Key = Email", 2);
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 1; i <= sensorAmt; i++) {
					String current = getState(con, i);
					//System.out.println(i + "> " + current);
					if (!(last.get(i).equals(current)) && current.equals("0")) {
						//System.out.println("unequal");
						sendMail(i, new Date() + "\nSomebody toggled your Magnet Sensor " + i + ", without your SmartPhone being in the Network");
						last.put(i, current);
					} //else System.out.println("equal");
				}
				
				//System.out.println("--------------------");
			}
		}, 1, 1, TimeUnit.SECONDS);
	}
	
	public static String getState(Connection con, int sensor) {
		return SQLSelect(con, "SELECT * FROM Data WHERE SensorID = '" + sensor + "' ORDER BY Zeit DESC LIMIT 1", 2);
	}
	
	public static String SQLSelect(Connection con, String command, int column) {
		PreparedStatement ps;
		String result = null;
		try {
			ps = con.prepareStatement(command);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getString(column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean sendMail(int sensor, String text) {
		//System.out.println(new Date());
	    String to = email;
	    String from = "ComHome@zeroxtv.de";
	    Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.host", "smtp.strato.de");
	    properties.setProperty("mail.smtp.socketFactory.port", "465");
	    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    properties.setProperty("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(properties, null);
	    //System.out.println("Connected to MailServer");

	    try {
	    	Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(from));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        message.setSubject("ComHome Warning");
	        message.setText(text);
	        message.setSentDate(new Date());
	        
	        Transport t = (SMTPTransport) session.getTransport("smtp");
	        t.connect("smtp.strato.de", "ComHome@zeroxtv.de", "Admin123456");
	        t.sendMessage(message, message.getAllRecipients());
	        System.out.println(((SMTPTransport) t).getLastServerResponse());
	        t.close();
	        return true;
	         
	    } catch (MessagingException mex) {
	        mex.printStackTrace();
	        return false;
	    }
	}
	
	public static Connection connect() {
		final String hostname = "192.168.2.102"; 
        final String port = "3306"; 
        final String dbname = "comHome"; 
        final String user = "email"; 
        final String password = "admin123"; 
        
        Connection con = null; 
        
        try { 
        	
        	System.out.println("Connecting..."); 
        	String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
        	con = DriverManager.getConnection(url, user, password);
        } 
        catch (SQLException sqle) { 
            System.out.println("SQLException: " + sqle.getMessage()); 
            System.out.println("SQLState: " + sqle.getSQLState()); 
            System.out.println("VendorError: " + sqle.getErrorCode()); 
            sqle.printStackTrace(); 
        } 
        
        return con;
	}
	
	public static void disconnect(Connection con) {
		System.out.println("Disconnecting..."); 
	    try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    System.out.println("Disconnected");
	}
}