package Worker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Shared.SupervisorInterface;


public class Worker 
{
	public static void main(String[] args) throws RemoteException 
	{
		//Check arguments
		if(args.length<1)
		{
			System.err.println("Wrong argument count for Supervisor");
			System.exit(-1);
		}
		
		ID_ = Integer.valueOf(args[0]);
		
		Worker worker = new Worker();
		
		try 
		{
			for(;;)
			{
				Thread.sleep(2*1000);
				
				if(!worker.callForReplacement())
				{
					writeToFile("Worker : " + Integer.valueOf(ID_) + " couldn't be replaced.");
				}
				else
				{
					writeToFile("Worker : " + Integer.valueOf(ID_) + " replaced.");
				}
			}
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static int ID_;
	private static SupervisorInterface supervisorStub_;
		
	public Worker()
	{

		super();

		if (System.getSecurityManager() == null) 
		{
			System.setSecurityManager(new SecurityManager());
		}
		
		supervisorStub_ = loadSupervisorStub("127.0.0.1");
	
	}
	
	public int getID()
	{
		return ID_;
	}
	
	private SupervisorInterface loadSupervisorStub(String hostname) 
	{
		SupervisorInterface supervisorStub = null;
		
		try 
		{
			Registry registry = LocateRegistry.getRegistry(hostname);
			supervisorStub = (SupervisorInterface) registry.lookup("Supervisor");
		} 
		catch (NotBoundException e) 
		{
			System.out.println("Erreur: Le nom '" + e.getMessage() + "' n'est pas defini dans le registre.");
		} 
		catch (AccessException e) 
		{
			System.out.println("Erreur: " + e.getMessage());
		} 
		catch (RemoteException e) 
		{
			System.out.println("Erreur: " + e.getMessage());
		}
		
		return supervisorStub;
	}
	
	private boolean callForReplacement()
	{
		boolean result = false;
		
		try 
		{
			result = supervisorStub_.replaceWorker(this);
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return result;
	}
	
	private static void writeToFile(String text)
	{
		try {
			 
			File file = new File("log.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
