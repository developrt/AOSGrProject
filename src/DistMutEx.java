import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;


public class DistMutEx {
	static HashMap<String,nodein> nodes = new HashMap<String,nodein>();
	static int my_id;
	
	
	
	public static void main(String [] args){
		int num_nodes = 0;
		
		
		my_id = Integer.parseInt(args[0]);
		try{
			
			Scanner rfile = new Scanner(new File("Config.txt"));
			String rline;
			int valcn = 0;
			while(rfile.hasNextLine()){
				
					rline = rfile.nextLine();
					if(rline.charAt(0) != '#'){
						
						if (valcn == 0){
							num_nodes = Character.getNumericValue(rline.charAt(0));
							System.out.println("Number of nodes = " +num_nodes);
							valcn = 1;
							rline = rfile.nextLine();
							}
						String[] params = rline.split(" ");
						System.out.println(" " +params[0] +" " +params[1] +" " +params[2]);
						nodein temp = new nodein(params[0],params[1],params[2]);
						nodes.put(params[0],temp);
					
						}
				}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}

class application extends Thread{
	
	
	
	
}



/* Module to implement Mutual Execution */



class MutEx extends Thread{
	static int[] RN = new int[2];
	int my_id;
	MutEx(){
		RN[0] = DistMutEx.my_id;
		RN[1] = 0;
		this.my_id = DistMutEx.my_id;
	}
	
	public void csRequest(){
		for (String nodes : DistMutEx.nodes.keySet()){
			try{
				if (DistMutEx.nodes.get(nodes).id != Integer.toString(my_id));
						String hostname = DistMutEx.nodes.get(nodes).hostname;
						int port = Integer.parseInt(DistMutEx.nodes.get(nodes).port);
						 
						Socket client = new Socket(hostname,port);
						
					
	         			DataOutputStream out = new DataOutputStream(client.getOutputStream());
						out.writeInt(RN[0]);
						out.writeInt(RN[1]);
						
						System.out.println("\nConnected and message sent successfully");
						client.close();
					}
			catch (IOException e){
				e.printStackTrace();
			}
		}	
	}		
	
	
	static boolean hasToken(){
		boolean x;
		if(true){
			x = true;
		}
		return x;
	}
}


class MutExept extends Thread{
	private ServerSocket LocServersock;
	
	public MutExept(int port) throws IOException{
		LocServersock = new ServerSocket(port);
	}
	public void run(){
		while(true){
			try{
				Socket server = LocServersock.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
			
		}
	}
}


class nodein{
	String id;
	String hostname;
	String port;
	
	public nodein(String i, String h, String p){
		this.id = i;
		this.hostname = h;
		this.port = p;
		
	}
	
	public void print(){
		System.out.println("");
		System.out.print(" " +this.id + " " +this.hostname + " " +this.port);
	}
}

		
		