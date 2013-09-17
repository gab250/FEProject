import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class Server 
{
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) 
	{
		Socket socket;
		Process process;
		EmailSender sender = new EmailSender();

		try {
			
			//Create Process
			ProcessBuilder procesBuilder = new ProcessBuilder("java","-jar","dist/Client.jar","49152");
			process = procesBuilder.start();
			
			Thread.sleep(5*1000);
			
		    while(true)
			{
				socket = new Socket("127.0.0.1", 49152);
				
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		        String answer = input.readLine();
		        
		        System.out.println(answer);
		        
		        if(!Boolean.valueOf(answer))
		        {
		        	System.out.println("Killing Process");
		        			        	
		        	socket.close();
		        	
		        	process.destroy();
		        	
		        	System.out.println("Restarting Process");
		        	
		        	process = procesBuilder.start();
		        	
		        	sender.Send("gabriel.laprise", "GAla25!!", "gabriel.laprise@outlook.com","Process Died", "Process restarted ");
		        }
		        else
		        {
		        	socket.close();
		        }

		        
		        Thread.sleep(5*1000);
			}
			
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (AddressException e) {
			
			e.printStackTrace();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

	}
}