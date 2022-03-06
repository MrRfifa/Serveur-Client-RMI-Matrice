package application;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class scene0Controller 
{	
	@FXML
	private  TextField txt1,txt2;
	@FXML
	public TextField lig1,col1,col2,lig2;
	@FXML
	private Button btn,conf1,conf2,reset;
	@FXML
	private Label lab;
	@FXML
	private Label label_res;
	@FXML
	private TextArea mat_txt1,mat_txt2;
	@FXML
	private  TextArea mat_res;

	
	static int [][] A;
	static int [][] B;
	
	public void calcul(ActionEvent event)
	{
		try {
			System.setProperty("java.security.policy", "/java.policy");
			Registry registry = LocateRegistry.getRegistry("localhost");
			fab serviceFactory =(fab)registry.lookup("fab");
			int n=Integer.parseInt(lig1.getText());
			int k=Integer.parseInt(col1.getText());
			int m=Integer.parseInt(col2.getText());
			int[][]c=new int[n][m];
			zed objet1 = serviceFactory.nouv_calcul();
			zed objet2 = serviceFactory.nouv_calcul();
			zed objet3 = serviceFactory.nouv_calcul();
			zed objet4 = serviceFactory.nouv_calcul();
			
			// generation de la matrice A
			 int q=0;
			 String[] mat1_s = txt1.getText().split(" ");
			 int [][] A = new int [n][k];
				for(int i=0;i<n;i++)
				{
					for(int j=0;j<k;j++)
					{
						A[i][j]=Integer.parseInt(mat1_s[q]);
						q++;
					}
				}
			 //generation de la matrice B
			 int l=0;
				String[] mat2_s = txt2.getText().split(" ");
				int [][] B = new int [k][m];
				
				for(int i=0;i<k;i++)
				{
					for(int j=0;j<m;j++)
					{
						B[i][j]=Integer.parseInt(mat2_s[l]);
						l++;
					}
				}
				c=menu(objet1,objet2,objet3,objet4,n,k,m,A,B);
				for (int i=0;i<c.length;i++) {
					System.out.println();
					for (int j=0;j<c[0].length;j++) {
						mat_res.appendText(""+c[i][j]+"\t");
					}
					mat_res.appendText("\n");
				}
				 afficher(c);
				 mat_res.setOpacity(1);
				 label_res.setOpacity(1);
		}
			catch (Exception e) {
			e.printStackTrace();
		} 
}
	public void reset (ActionEvent e)
	{
		lig1.setText(null);
		lig2.setText(null);
		col1.setText(null);
		col2.setText(null);
		txt1.setText(null);
		txt2.setText(null);
		mat_txt1.setText(null);
		mat_txt2.setText(null);
		mat_res.setText(null);
		mat_res.setOpacity(0);
		label_res.setOpacity(0);;
	}
	public static int[][] menu(zed stub1,zed stub2,zed stub3,zed stub4,int n,int k,int m,int[][] A,int[][] B) throws RemoteException {
	   	 ArrayList<int[][]> M1=new ArrayList<int[][]>();
	   	 ArrayList<int[][]> M2=new ArrayList<int[][]>();
	   	 ArrayList<int[][]> M3=new ArrayList<int[][]>();
	   	 ArrayList<int[][]> M4=new ArrayList<int[][]>();
	   	 int[][]c=new int[n][m];
 
		 M1=deviser(A,n,k);
		 M2=deviser(B,k,m);
		 M3.add(stub1.produit(M1.get(0),M2.get(0),n/2,k/2,m/2));//a*a//1
		 M3.add(stub1.produit(M1.get(0),M2.get(1),n/2,k/2,m-m/2));//a*b//3
		 M3.add(stub2.produit(M1.get(1),M2.get(2),n/2,k-k/2,m/2));//b*c//2
		 M3.add(stub2.produit(M1.get(1),M2.get(3),n/2,k-k/2,m-m/2));//b*d//4
		 M3.add(stub3.produit(M1.get(2),M2.get(1),n-k/2,k/2,m-m/2));//c*b//7
		 M3.add(stub3.produit(M1.get(2),M2.get(0),n-k/2,k/2,m/2));//c*a//5
		 M3.add(stub4.produit(M1.get(3),M2.get(3),n-n/2,k-k/2,m-m/2));//d*d//8
		 M3.add(stub4.produit(M1.get(3),M2.get(2),n-n/2,k-k/2,m/2));//d*c//6	 
		 M4.add(add(M3.get(0),M3.get(2))); //a
		 M4.add(add(M3.get(1),M3.get(3))); //b
		 M4.add(add(M3.get(5),M3.get(7))); //c
		 M4.add(add(M3.get(4),M3.get(6))); //d
		 c=rassembler(M4,n,m);
		 return c;
   }
	public static ArrayList<int[][]> deviser(int[][]A,int x,int y) throws RemoteException {
		ArrayList<int[][]> arl=new ArrayList<int[][]>();
		int[][]a =new int[x/2][y/2];
	    int[][]b =new int[x/2][y-(y/2)];
		int[][]c =new int[x-(x/2)][y/2];
	    int[][]d =new int[x-(x/2)][y-(y/2)];
	    int i,j;
	    for (i=0;i<x;i++) {
	    	for(j=0;j<y;j++) {
	    		if ( (i<x/2) && (j<y/2)) {
	    			a[i][j]=A[i][j];
	    			}
	    		else if ((i>=x/2) && (j<y/2)) {
	    			c[i-(x/2)][j]=A[i][j];
	    		}
	    		else if ((i<x/2) && (j>=y/2)) {
	    			b[i][j-(y/2)]=A[i][j];
	    		}
	    		else if((i>=x/2) && (j>=y/2)) {
	    			d[i-(x/2)][j-(y/2)]=A[i][j];
	    		}
	    	}
	    }
	   arl.add(a);
	   arl.add(b);
	   arl.add(c);
	   arl.add(d);
	return arl;    
	}
	public static int[][] add(int[][] a,int[][] b) {
		int[][] c=new int[a.length][a[0].length];
		for (int i=0;i<a.length;i++) {
			for (int j=0;j<a[0].length;j++) {
                 c[i][j]=a[i][j]+b[i][j];
			}
		}	
		return c;
	}
	public static int[] []rassembler(ArrayList<int[][]> M,int n,int m) {
		int[][] c= new int[n][m] ;
		for (int i=0;i<n;i++) {
	    	for(int j=0;j<m;j++) {
	    		if ( (i<M.get(0).length) && (j<M.get(0)[0].length)) {
	    			c[i][j]=M.get(0)[i][j];
	    			}
	    		else if ((i>=M.get(0).length) && (j<M.get(0)[0].length)) {
	    			c[i][j]=M.get(2)[i-(M.get(0).length)][j];
	    		}
	    		else if ((i<M.get(0).length) && (j>=M.get(0)[0].length)) {
	    			c[i][j]=M.get(1)[i][j-(M.get(0)[0].length)];
	    		}
	    		else if((i>=M.get(0).length) && (j>=M.get(0)[0].length)) {
	    			c[i][j]=M.get(3)[i-(M.get(0).length)][j-(M.get(0)[0].length)];
	    		}
	    	}	
	    }
		//afficher(c);	
		return c;
	}
	public static void afficher(int[][] c)  {
		for (int i=0;i<c.length;i++) {
			System.out.println();
			for (int j=0;j<c[0].length;j++) {
				System.out.print("|"+c[i][j]+"|");
			}
		}
		System.out.println();
	}
	public void conf_mat_1(ActionEvent e)
	{
		int k=0;
		String[] mat1_s = txt1.getText().split(" ");
		int n_m1=Integer.parseInt(lig1.getText());
		int m_m1=Integer.parseInt(col1.getText());
		lig2.setEditable(false);
		lig2.setText(""+m_m1);
		int [][] m1 = new int [n_m1][m_m1];
		for(int i=0;i<n_m1;i++)
		{
			for(int j=0;j<m_m1;j++)
			{
				m1[i][j]=Integer.parseInt(mat1_s[k]);
				k++;
			}
		}
		
		 for (int i = 0; i < n_m1; i++) {
		      for (int j = 0; j < m_m1; j++) {
		    	mat_txt1.appendText(""+m1[i][j]+"\t");
		        System.out.print(m1[i][j] + "\t");
		      }
		      System.out.println();
		      mat_txt1.appendText("\n"); 
		    } 
	}
	public void conf_mat_2(ActionEvent e)
	{
		int l=0;
		String[] mat2_s = txt2.getText().split(" ");
		int n_m2=Integer.parseInt(col1.getText());
		int m_m2=Integer.parseInt(col2.getText());
		int [][] m2 = new int [n_m2][m_m2];
		
		for(int i=0;i<n_m2;i++)
		{
			for(int j=0;j<m_m2;j++)
			{
				m2[i][j]=Integer.parseInt(mat2_s[l]);
				l++;
			}
		}
		 for (int i = 0; i < n_m2; i++) 
		 {
		      for (int j = 0; j < m_m2; j++) {
		        System.out.print(m2[i][j] + "\t");
		        mat_txt2.appendText(""+m2[i][j]+"\t");
		      }
		      System.out.println();
		      mat_txt2.appendText("\n");
		  }
	}
}
