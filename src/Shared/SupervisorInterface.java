package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Worker.Worker;


public interface SupervisorInterface extends Remote 
{
	boolean replaceWorker(Worker worker) throws RemoteException;
}
