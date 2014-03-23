import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;


public class DistMutEx {
	static HashMap<String,nodein> nodes = new HashMap<String,nodein>();
	static int my_id;
	
	public static void main(String [] args){
		int num_nodes = 0;
		
		my_id = Integer.parseInt(args[0]);
		// Reading Config file
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
			
		//Starting Application thread
		Application T1 = new Application();
		T1.start();
		//Starting MutEx thread to accept requests
		int p = Integer.parseInt(nodes.get(Integer.toString(my_id)).port);
		MutExept T2 = new MutExept(p);
		T2.start();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
		
}

/* Application Module */

class Application extends Thread{
	 
	 MutEx algo = new MutEx();
	 
	 /* Thread that generates CS request after a random wait
	  * If token is with this node and the Q is empty no request for CS is generated
	  * else Request for CS is generated
	  */
	 public void run(){
		 int counter = 0;
		 while (true){
			 counter++;
			 try{
				 Random ran = new Random();
				 Thread.sleep(ran.nextInt(10000));
				 if(algo.hasToken() && MutEx.Que.isEmpty()) {
					 System.out.println("Entering critical section (" +counter +")");
				 }
				 
				 else{
					 //Generate CS request
					 MutEx.request[0]++;
					 algo.csRequest();
					 System.out.println("Entering critical section (" +counter +")");
					 System.out.println("In critical section " +counter);
					 System.out.println("Leaving critical section");
					 algo.csLeave();
				 }
			 }
			 catch(InterruptedException e){
				 e.printStackTrace();
			 }
			 
			 
		 }
	 }
	 
	
}




/* Module to implement Mutual Execution */



class MutEx {
	static int[] request = new int[2];
	int my_id;
	static final int REQ_PORT = 9999;
	// RN, LN and Queue as required for the algo
	int[] RN = new int[DistMutEx.nodes.size()];
	int[] LN = new int[DistMutEx.nodes.size()];
	static Queue<Integer> Que = new LinkedList<Integer>();
	boolean token;
	
	MutEx(){
		request[0] = DistMutEx.my_id;
		request[1] = 0;
		this.my_id = DistMutEx.my_id;
		for(int i=0;i<DistMutEx.nodes.size();i++){
			 RN[i] = 0;
		 }
		 if(my_id == 0){
			 token = true;
		 for(int i=0;i<DistMutEx.nodes.size();i++){
			 LN[i] = 0;
		 }
		 
		 }
		 else{
			 token = false;
			 this.LN = null;
			 Que = null;
		 }
	}
	
	public void csRequest(){
		//Broadcast request message to all nodes
		for (String nodes : DistMutEx.nodes.keySet()){
			try{
				if (DistMutEx.nodes.get(nodes).id != Integer.toString(my_id));
						String hostname = DistMutEx.nodes.get(nodes).hostname;
						int port = Integer.parseInt(DistMutEx.nodes.get(nodes).port);
						 
						Socket client = new Socket(hostname,port);
						
	         			DataOutputStream out = new DataOutputStream(client.getOutputStream());
						out.writeInt(request[0]);
						out.writeInt(request[1]);
						
						System.out.println("\nConnected and message sent successfully");
						client.close();
						
					}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		
		try{
			ServerSocket LocServersock = new ServerSocket(REQ_PORT);
			Socket server = LocServersock.accept();
			DataInputStream in = new DataInputStream(server.getInputStream());
		
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return;	
	}
	
	public void csLeave(){
		if (!Que.isEmpty()){
			int newid = Que.poll();
			try{
				Socket client = new Socket(DistMutEx.nodes.get(Integer.toString(newid)).hostname,MutEx.REQ_PORT);
				
     			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			}
			
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
	}
	
	
	 public boolean hasToken(){
			return token;
		}
	 
	 public void setToken(boolean tok){
		 this.token = tok;
	 }
	
	
	
}

//Accepts connections for requests from different nodes

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

		
		