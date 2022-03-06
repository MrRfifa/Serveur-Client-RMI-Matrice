package application;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class implement extends UnicastRemoteObject implements zed {

	private static final long serialVersionUID = 1L;
	protected implement() throws RemoteException {
		super();
	}
	public int[][] produit(int[][]a,int[][]b,int n,int k,int m) throws RemoteException {
		int[][]C=new int[n][m];
		for(int i=0; i<n; i++){
		      for(int j=0; j<m; j++){    
		        for(int p=0; p<k ;p++)    
		        { 
		          C[i][j] += a[i][p]*b[p][j];    
		        }
              }
        }
		return(C);
	}
}
