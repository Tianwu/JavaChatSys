import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame{
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Socket s = null;
	public static void main(String[] args) {
		new ChatClient().launchFrame();;

	}
	
	public void launchFrame() {
		this.setTitle("Java在线聊天系统");
		this.setLocation(400, 300);
		this.setSize(300, 300);
		this.add(tfTxt, BorderLayout.SOUTH);
		this.add(taContent, BorderLayout.NORTH);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);;
			}
		});
		tfTxt.addActionListener(new TFListener());
		taContent.setEditable(false);
		this.setVisible(true);
		
		connect();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
System.out.println("Connected!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//函数是接口
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			taContent.setText(str);
			tfTxt.setText("");
			try {
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		
	}
}
