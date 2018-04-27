package server;

import java.net.Socket;
import java.net.SocketException;

import server.communications_handler.CommunicationsHandler;
import server.communications_handler.CommunicationsHandlerFactory;
import server.communications_handler.MainPageHandler;

public class ClientHandler implements Runnable {
	private CommunicationsHandlerFactory myCHFactory;
	private CommunicationsHandler myCommunicationsHandler;
	public ClientHandler(RTSServer server, Socket socket) {
		myCHFactory = new CommunicationsHandlerFactory(server,socket);
		myCommunicationsHandler = myCHFactory.get(MainPageHandler.CLASS_REF);
	}
	@Override
	public void run() {
		try {
		while(true) {
			myCommunicationsHandler.updateClient();
			String newHandler =  myCommunicationsHandler.updateServer();
			if(!myCommunicationsHandler.getClass().getSimpleName().startsWith(newHandler))
				myCommunicationsHandler = myCHFactory.get(newHandler);
		}
		}
		catch(SocketException e) {
			System.out.println("hi");
			Thread.currentThread().interrupt();
		}
	}

}
