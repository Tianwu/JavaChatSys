import java.net.*;//TCPextends Frame 
import java.io.DataInputStream;
import java.io.IOException;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
System.out.println("a client connectes!");
			DataInputStream dis = 
					new DataInputStream(s.getInputStream());
			String str = dis.readUTF();
			System.out.println(str);
			dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
