package application;

import java.rmi.*;

public interface zed extends Remote {
  public int[][] produit(int[][]a,int[][]b,int n,int k,int m) throws RemoteException;
}

