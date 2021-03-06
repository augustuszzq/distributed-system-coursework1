/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean condition;
	private int count;
	private static final int registry_port=1099; // denote which name server to use
	private static final String serverURL = new String("Dominic");

	public RMIServer() throws RemoteException {
		super();
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {
		if (totalMessages==-1){
			totalMessages = msg.totalMessages;

			count = totalMessages;
		}
		System.out.printf("%d",msg.messageNum);
		count--;

		// TO-DO: On receipt of first message, initialise the receive buffer

		// TO-DO: Log receipt of the message

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public static void main(String[] args) {

		RMIServer rmis = null;
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}
		try{
			rmis = new RMIServer();
			rebindServer(serverURL,rmis);
			System.out.println("Server ready");

		}catch(Exception e){
			System.err.println("Exception:"+e);
		}
		// TO-DO: Initialise Security Manager

		// TO-DO: Instantiate the server class

		// TO-DO: Bind to RMI registry

	}

	protected static void rebindServer(String serverURL, RMIServer server) throws Exception {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		Registry registry = LocateRegistry.createRegistry(registry_port);
		registry.bind(serverURL,server);
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
