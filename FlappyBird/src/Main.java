import javax.swing.*;


public class Main {

	public static void main(String[] args) throws Exception{

		int boardWidth = 360;
		int boardHeight = 640;
		
		JFrame frame = new JFrame("Flappy Bird");
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FlappyBird fp = new FlappyBird();
		frame.add(fp);
		frame.pack();
		fp.requestFocus();
		frame.setVisible(true);
		
	}

}
