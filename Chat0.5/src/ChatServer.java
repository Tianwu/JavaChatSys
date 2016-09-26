import java.net.*;//TCPextends Frame 
import java.awt.*;
import java.io.IOException;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
System.out.println("a client connectes!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
