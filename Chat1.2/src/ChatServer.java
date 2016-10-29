import java.net.*;//TCPextends Frame 
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ChatServer {
	
	boolean started = false;
	ServerSocket ss = null;
	//private DataInputStream dis = null;
	private DataOutputStream dos = null;
	boolean bConnected = false;
	//INFO WARN ERROR FATAL <low to high>
	private Logger serverLogger = Logger.getLogger(ChatServer.class.getName());
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args) {
		PropertyConfigurator.configure ( "src/ServerWithLog4j.properties" ) ;
		new ChatServer().start();
	}

	public void start() {
		
		try {
			ss = new ServerSocket(8888);
serverLogger.info( "服务端打开，客服端还没连接上," + ss ) ;
			started = true;
		} catch(BindException be) {
serverLogger.error("服务器已打开，请勿重复打开," + be);
			System.exit(0);
		} catch (IOException e1) {
serverLogger.error("抱歉，服务器抽风了," + e1);
			System.exit(0);
		}
		Socket s = null;
		try {		
			while(started) {
				 s = ss.accept();
				Client c = new Client(s);
				clients.add(c);
serverLogger.info ( "服务端连接上一个客户端" ) ;
				new Thread(c).start();
			}
		} catch (IOException e) {
serverLogger.info ( e ) ;
		}finally {
			try {
				if(ss != null)ss.close();
				if(s != null)s.close();
			} catch (IOException  e1) {
serverLogger.warn("Socket或ServerSocket关闭失败" + e1);
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
serverLogger.info(e);
				//e.printStackTrace();
			}
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (SocketException e){
				clients.remove(this);
				serverLogger.info("a client quited!");
				//System.out.println("a client quited!");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		public void run() {
			
				try {
					while(bConnected) {
						String str = dis.readUTF();
						for(int i=0 ; i<clients.size() ; ++i) {
//System.out.println(i);
							Client c = clients.get(i);
							c.send(str);
						}
//System.out.println(str);
					}
				} catch (EOFException e){
serverLogger.info("一个客户端离开");
					//System.out.println("a client closed!");
				}catch (IOException e) {
serverLogger.info(e);				
					//e.printStackTrace();
				} finally {
					try {
						if(dis != null)dis.close();
						if(dos != null)dos.close();
						if(s != null)s.close();
					} catch (IOException  e1) {
serverLogger.warn("输入输出流或Socket关闭失败" + e1);
					}
				}
		}
	}
}
