package application;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class fabimpl extends UnicastRemoteObject  implements fab  {
	
	private static final long serialVersionUID = 1L;
	public fabimpl() throws RemoteException {
		super();
	    System.setProperty("java.rmi.server.codebase",fab.class.getProtectionDomain().getCodeSource().getLocation().toString());
	    System.setProperty("java.security.policy", "/java.policy"); 
	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost");
	        registry.rebind("EchoService", this);
	        System.out.println("Echo service factory registered.");
	    } catch (Exception e) {
	        System.err.println("Error registering echo service factory: "
	                + e.getMessage());
	    }		
	}
	public zed nouv_calcul() throws RemoteException {
		return (new implement() );
	}
	
	/*public static void main(String[] args) throws RemoteException {
	    try {
	        new fabimpl();
	    } catch (RemoteException e) {
	        System.err.println("Error creating echo service factory: "
	                + e.getMessage());
	    }
	}*/
	
	
}
