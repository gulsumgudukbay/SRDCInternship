import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {
	
		int portNo = 1024;
		ServerSocket serverSock = null;

		public void handle(){
			try{
				serverSock = new ServerSocket(portNo);
			}catch(IOException ex){
				System.out.println(ex.getMessage());
			}
			
			
			while(true){
				try{
					Socket clientSock = serverSock.accept();
					new Thread(new ServerRunnable(clientSock)).start();
				}catch (IOException ex){
					System.out.println(ex.getMessage());
				}
			}
		}

}
