import java.awt.*;

public class ChatClient extends Frame{
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
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
		this.setVisible(true);
	}
}
