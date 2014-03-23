import java.io.File;
import java.io.IOException;
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

class mutEx extends Thread{
	
	public void csEnter(){
		
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

		
		