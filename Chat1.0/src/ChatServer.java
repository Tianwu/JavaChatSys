import java.net.*;//TCPextends Frame 
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	DataInputStream dis = null;
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch(BindException be) {
			System.out.println("服务器已打开，请勿重复打开");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("抱歉，服务器抽风了");
			System.exit(0);
		}

		try {		
			
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
System.out.println("a client connected!");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ss != null)ss.close();
			} catch (IOException  e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class Client implements Runnable {

		private Socket s;
		private DataInputStream dis;
		boolean bConnected = false;
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(this.s.getInputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			
				try {
					while(bConnected) {
						String str = dis.readUTF();
						System.out.println(str);
					}
				} catch (EOFException e){
					System.out.println("a client closed!");
				}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(dis != null)dis.close();
						if(s != null)s.close();
					} catch (IOException  e1) {
						e1.printStackTrace();
					}
				}
			
		}
		
	}
}
