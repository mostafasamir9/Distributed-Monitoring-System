import java.io.*;
import java.net.*;

/*
 * Sensors Nodes IOT Device interface
 */

public class Sensor {

	public static void main(String[] args) throws IOException {

	/* Connect To Computer and send to it data */
	Socket Node = new Socket ("localhost",1212);

		
	DataOutputStream dos_node = new DataOutputStream (Node.getOutputStream());

		dos_node.writeUTF("street 2 is jammed");		

	
		dos_node.close();
		Node.close();
	
	}

}
