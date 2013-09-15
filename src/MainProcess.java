
import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainProcess 
{
	public static void main(String[] args)
	{   
		try 
		{
			EmailSender emailSender = new EmailSender();
			
			for(;;)
			{
				emailSender.Send("gabriel.laprise", "GAla25!!", "gabriel.laprise@outlook.com", "MainProcess Message", "Sending from main process @ " + Calendar.getInstance().getTime().toString());
				Thread.sleep(15*60*1000);
			}
		   
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	


	
}
