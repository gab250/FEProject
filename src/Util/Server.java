package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
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
		
		process = createProcess();
		
		while(true)
		{
			
			try {
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
			        	
			        	process = createProcess();
			        	
			        	sender.Send("gabriel.laprise", "GAla25!!", "gabriel.laprise@outlook.com","Process Died", "Process restarted ");
			        }
			        else
			        {
			        	socket.close();
			        }
	
			        
			        Thread.sleep(5*1000);
				}
				
			} 
			catch (ConnectException e) 
			{
				System.out.println("Catched ConnectionException");
			
			}
		    catch (UnknownHostException e) 
			{
				e.printStackTrace();
			} 
		     catch (InterruptedException e) {
				
				e.printStackTrace();
			} catch (AddressException e) {
				
				e.printStackTrace();
			} catch (MessagingException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static Process createProcess()
	{
		try
		{
			Process process;
			
			//Create Process
			ProcessBuilder procesBuilder = new ProcessBuilder("java","-jar","dist/Client.jar","49152");
			process = procesBuilder.start();
			
			return process;
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
}