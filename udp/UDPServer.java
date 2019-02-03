/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import common.MessageInfo;

public class UDPServer {
	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	//private boolean[] receivedMessages;
	private int[] checklist;
	private boolean close=false;
	private int count=0;
	private int count_process = 0;
	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;
		byte[] buff = new byte[2222];
		while(!close){
			try{
				pac = new DatagramPacket(buff,2222);
				recvSoc.receive(pac);
				pacData = pac.getData();
				pacSize =	pac.getLength();
				// System.out.println(pacSize);
				String pacdata_string = new String(pacData,0,pacSize,StandardCharsets.UTF_8);
				//System.out.println("xxx");
				processMessage(pacdata_string);

			}catch(Exception e){
				System.out.println("Exception"+e.getMessage());
				count++;
			}
		}
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
	}

	public void processMessage(String data) {
		MessageInfo msg = null;
		try{
			msg = new MessageInfo(data);
			if(totalMessages==-1){
				totalMessages = msg.totalMessages;
				checklist = new int[totalMessages];

				//receivedMessages = new boolean[totalMessages];
				count_process = totalMessages;
			}
			System.out.printf("%d",msg.messageNum);
			System.out.printf("\n");
			//receivedMessages[msg.messageNum]=true;
			checklist[msg.messageNum]=1; //set to 1 if transmitted, for checking later

			count_process--;
			if (count_process==0) {
				close = true;
			}
		}catch(Exception e){
			System.err.println("ignore "+e.getMessage());
		}
		}
		// TO-DO: Use the data to construct a new MessageInfo object
		// TO-DO: On receipt of first message, initialise the receive buffer
		// TO-DO: Log receipt of the message
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages


	public UDPServer(int rp) {
		try{
				recvSoc = new DatagramSocket(rp);
				recvSoc.setSoTimeout(30000);
		}catch(SocketException e){System.out.println("Socket:"+e.getMessage());
	}
	System.out.println("UDPServer ready");
}
	public static void main(String args[]) {
		int	recvPort;
		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		// TO-DO: On receipt of first message, initialise the receive buffer
		// TO-DO: Log receipt of the message
		// TO-DO: If this is the last expected message, then identify
		recvPort = Integer.parseInt(args[0]);
		UDPServer server = new UDPServer(recvPort);
		server.run();

		// TO-DO: Construct Server object and start it by calling run().
	}


}
