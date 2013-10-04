package Util;
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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;



public class Client 
{
	
	private static Queue<Integer> status = new LinkedList<Integer>();
	public static int port;
	private static ReadWriteLock rwlock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) 
	{
		port = Integer.valueOf(args[0]);
		
		System.out.println("Creating client with port: " + Integer.toString(port));
		
		NetworkListner listener = new NetworkListner();
		listener.start();
		
		System.out.println("Started Thread");
		
		try 
		{
			//Main Porcess
			for(int i=0; i<2*2*60; ++i)
			{
				Thread.sleep(500);
				writeQueue(0);
			}
			
			EmailSender sender = new EmailSender();
			sender.Send("gabriel.laprise", "GAla25!!", "gabriel.laprise@outlook.com","Process Dying", "Process entering deadlock ");
			
			for(;;)
			{
				
			}
		
		} 
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
    }
	
	public static int getQueueSize()
	{
		int queueSize;
		
		rwlock.readLock().lock();
		
		queueSize = status.size();
	
	    rwlock.readLock().unlock();
	    return queueSize;
	}
	
	public static void clearQueue()
	{
		rwlock.writeLock().lock();
		
	    try 
	    {
	    	status.clear();
	    } 
	    finally 
	    {
	          rwlock.writeLock().unlock();
	    }
	}
	
	public static void writeQueue(int statusMsg)
	{
		rwlock.writeLock().lock();
		
	    try 
	    {
	    	status.add(statusMsg);
	    } 
	    finally 
	    {
	          rwlock.writeLock().unlock();
	    }
	}
	
	private static class NetworkListner extends Thread
	{
		public void run()
		{
			System.out.println("Starting Listener");
			
    		ServerSocket listener = null;
			
			try 
	        {
			   
		       listener = new ServerSocket(port);
	        	
	            while (true) 
	            {
	            	System.out.println("Listening..");
	            	
	                Socket socket = listener.accept();
	                
	                int currentSize = getQueueSize();
	                boolean status = false;
	                
	                for(int i=0; i<15; ++i)
	                {
	                	Thread.sleep(1000);
	                	
	                	if((getQueueSize() - currentSize) > 0)
	                	{
	                		status = true;
	                		break;
	                	}
	                }
	                
	                if(status)
	                {
	                	clearQueue();
	                }
         
                	try 
  	                {
  	                    PrintWriter out =	new PrintWriter(socket.getOutputStream(), true);
  	                    out.println(Boolean.valueOf(status));
  	                } 
  	                finally 
  	                {
  	                    socket.close();
  	                }
                	
                	if(true)
                	{
                		
                	}
	              
	                
	           }
	                 
	        }
	        catch (IOException e) 
	        {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try 
				{
					listener.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
	      
	      }
	}
		
 }
   



