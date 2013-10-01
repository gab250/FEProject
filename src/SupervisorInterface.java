import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SupervisorInterface extends Remote 
{
	boolean replaceWorker(Worker worker) throws RemoteException;
}
