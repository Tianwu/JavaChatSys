import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	public static void main(String[] args) {
		new ChatClient().launchFrame();;

	}
	public void launchFrame() {
		this.setTitle("Java��������ϵͳ");
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
		this.setVisible(true);
	}
	
}
