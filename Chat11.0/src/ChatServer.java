import java.net.*;//TCPextends Frame 
import java.util.ArrayList;
import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	boolean bConnected = false;
	
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch(BindException be) {
			System.out.println("�������Ѵ򿪣������ظ���");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("��Ǹ�������������");
			System.exit(0);
		}

		try {		
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
				clients.add(c);
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
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(this.s.getInputStream());
				dos = new DataOutputStream(this.s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
System.out.println(str);
				for(int i=0 ; i<clients.size() ; ++i) {
					Client c = clients.get(i);
					c.send(str);
				}
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
						if(dos != null)dos.close();
						if(s != null)s.close();
					} catch (IOException  e1) {
						e1.printStackTrace();
					}
				}
		}
		
	}
}
