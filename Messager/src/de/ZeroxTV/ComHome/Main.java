package de.ZeroxTV.ComHome;

import java.io.IOException;
import java.net.InetAddress;
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

import org.json.JSONObject;

import com.sun.mail.smtp.SMTPTransport;

import comhome.telegram.TelegramBot;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Main {
	
	final static int sensorAmt = 2;
	public static HashMap<Integer, String> last = new HashMap<Integer, String>();
	public static HashMap<Integer, String> cName = new HashMap<Integer, String>();
	public static String email;
	public static String userIP;
	public static String com;
	public static TelegramBot bot = new TelegramBot("216511865:AAEdsDpQF6xiDrRZfvp9z5Ln9jMZ2HnWdug");
	public static String chatID;
	public static InetAddress IP;
	
	public static void main(String[] args) {
		Connection con = connect();
		for (int i = 1; i <= sensorAmt; i++) {
			last.put(i, "0");
			cName.put(i, SQLSelect(con, "SELECT * FROM Sensors WHERE ID = '" + i + "'", 2));
			com = SQLSelect(con, "SELECT * FROM Config WHERE `Key` = 'Communication'", 2);
			chatID = SQLSelect(con, "SELECT * FROM Config WHERE `Key` = 'chatID'", 2);
		}
		if (com.equalsIgnoreCase("telegram") && chatID.equals("0")) {
			bot.registerHandler(new Function1<JSONObject, Unit>() {
				@Override
				public Unit invoke(JSONObject json) {
					if(json.getString("text").equalsIgnoreCase("comHome register")) {
						int chatInt = json.getJSONObject("from").getInt("id");
						chatID = chatInt + "";
						SQLUpdate(con, "UPDATE Config SET `Value` = '" + chatID + "' WHERE `Key` = 'chatID'");
						bot.sendMessage(chatID, "You successfully registered your Telegram Account at your ComHome System");
						}
					return null;
				}
			});
		}
		
		
		
		
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("ran");
				for (int i = 1; i <= sensorAmt; i++) {
					System.out.println(i);
					String current = getState(con, i);
					userIP = SQLSelect(con, "SELECT * FROM Config WHERE `Key` = 'userIP'", 2);
					if (last.get(i).equals(current)) continue;
					if (!current.equals("1")) continue;
					try {
						IP = InetAddress.getByName(userIP);
						if (IP.isReachable(2000)) {
							System.out.println("unreachable");
							continue;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("returns passed");
					email = SQLSelect(con, "SELECT * FROM Config WHERE `Key` = 'Email'", 2);
					if(com.equalsIgnoreCase("mail")) {
						sendMail(i, new Date() + "\nSomebody toggled your Sensor \"" + cName.get(i) + "\" without your Device being in the Network");
					} else if (com.equalsIgnoreCase("telegram")) {
					bot.sendMessage(chatID, new Date() + "\nSomebody toggled your Sensor \"" + cName.get(i) + "\" without your Device being in the Network");
					}
					last.put(i, current);
				}
			}
		}, 200, 200, TimeUnit.MILLISECONDS);
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
	
	public static void SQLUpdate(Connection con, String command) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(command);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean sendMail(int sensor, String text) {
	    String to = email;
	    String from = "ComHome@zeroxtv.de";
	    Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.host", "smtp.strato.de");
	    properties.setProperty("mail.smtp.socketFactory.port", "465");
	    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    properties.setProperty("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(properties, null);

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
        	String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
        	con = DriverManager.getConnection(url, user, password);
        	System.out.println("Connected with MySQL-Server"); 
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
	    try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    System.out.println("Disconnected");
	}
}