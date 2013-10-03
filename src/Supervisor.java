import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class Supervisor implements SupervisorInterface
{
	private static Map<Integer,Process> mapWorkers_ = new HashMap<Integer,Process>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		//Check arguments
		if(args.length<1)
		{
			System.err.println("Wrong argument count for Supervisor");
			System.exit(-1);
		}
				
		Supervisor supervisor = new Supervisor();
		supervisor.createWorkers(Integer.valueOf(args[0]));
		supervisor.run();
	}
	
	public Supervisor() 
	{
				
	}
	
	private void run() throws Exception 
	{
		if (System.getSecurityManager() == null) 
		{
			System.setSecurityManager(new SecurityManager());
		}

		try 
		{
			SupervisorInterface stub = (SupervisorInterface) UnicastRemoteObject.exportObject(this, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Supervisor", stub);
			System.out.println("Supervisor ready.");
		} 
		catch (ConnectException e) 
		{
			System.err.println("Impossible to connect to RMI registry");
			System.err.println();
			System.err.println("Erreur: " + e.getMessage());
		} 
		catch (Exception e) 
		{
			System.err.println("Erreur: " + e.getMessage());
		}
	}
	
	private void createWorkers(int in_nbWorkers) throws IOException 
	{
		//Create Workers 
		for(int i=0; i<in_nbWorkers; ++i)
		{
			ProcessBuilder procesBuilder = new ProcessBuilder("java","-jar","dist/Worker.jar",Integer.toString(i));
			mapWorkers_.put(i,procesBuilder.start());
		}
				
	}
	
	private static Process createProcess(int in_ID)
	{
		try
		{
			Process process;
			
			//Create Process
			ProcessBuilder procesBuilder = new ProcessBuilder("java","-jar","dist/Worker.jar",Integer.toString(in_ID));
			process = procesBuilder.start();
			
			return process;
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
		
	@Override
	public boolean replaceWorker(Worker worker) 
	{
		mapWorkers_.get(worker.getID()).destroy();
		mapWorkers_.put(worker.getID(), createProcess(worker.getID()));
		
		if(mapWorkers_.get(worker.getID()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
