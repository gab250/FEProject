
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
	public static enum State {INIT,WATCH_PROCESSES,RESTART_PROCESS};
	public static State currentState;
	
	public static void main(String[] args)
	{   
		currentState = State.INIT;
		
		for(;;)
		{
			switch(currentState)
			{
				case INIT:
					
				case WATCH_PROCESSES:
					
				case RESTART_PROCESS:
					
				default:
				break;
			}
		}
	}
	
	private void  Init()
	{
		//Create Data Structure
		
		
		//Create processes and fill data structures
		
		//Send email with specs
		
		//Exit state to Watch
	}
	
	
	
}




