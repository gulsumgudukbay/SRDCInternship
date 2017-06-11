import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Server {
	static Logger Logger = LoggerFactory.getLogger(Server.class);
	public static void main(String[] args)  {
		ClientHandler ch = new ClientHandler();
		Logger.info("HELLO");

		ch.handle();
	}
}
