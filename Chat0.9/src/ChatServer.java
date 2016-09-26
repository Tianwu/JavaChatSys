import java.net.*;//TCPextends Frame 
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class ChatServer {
	
	public static void main(String[] args) {
		boolean started = false;
		ServerSocket ss = null;
		DataInputStream dis = null;
		Socket s = null;

		try {
			ss = new ServerSocket(8888);
		} catch(BindException be) {
			System.out.println("服务器已打开，请勿重复打开");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("抱歉，服务器抽风了");
			System.exit(0);
		}

		try {		
			started = true;
			while(started) {
				boolean bConnected = false;
				s = ss.accept();
System.out.println("a client connected!");
				bConnected = true;
				dis = new DataInputStream(s.getInputStream());
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
				}
				//dis.close();
			}
		} catch (EOFException e){
System.out.println("a client closed!");
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(dis != null)dis.close();
				if(s != null)s.close();
			} catch (IOException  e1) {
				e1.printStackTrace();
			}
		}
	}
}
