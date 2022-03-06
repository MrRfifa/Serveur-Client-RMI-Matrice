package application;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface fab extends Remote{
	 zed nouv_calcul() throws RemoteException ;
}
