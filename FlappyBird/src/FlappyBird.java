import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

	int boardWidth = 360;
	int boardHeight = 640;

	Image backgroundImage;
	Image birdImage;
	Image topPipeImage;
	Image bottomPipeImage;

	int birdX = boardWidth / 8;
	int birdY = boardHeight / 2;
	int birdWidth = 34;
	int birdHeight = 24;

	class Bird {
		int x = birdX;
		int y = birdY;
		int width = birdWidth;
		int height = birdHeight;
		Image img;

		Bird(Image img) {
			this.img = img;
		}
	}

	int pipeX = boardWidth;
	int pipeY = 0;
	int pipWidth = 64;
	int pipeHeight = 512;

	class Pipe {
		int x = pipeX;
		int y = pipeY;
		int width = pipWidth;
		int height = pipeHeight;
		Image img;
		boolean passed = false;

		Pipe(Image img) {
			this.img = img;

		}
	}

	Bird bird;
	int speedX = -4;
	int speedY = 0;
	int gravity = 1;

	ArrayList<Pipe> pipes;
	Random random = new Random();

	Timer loop;
	Timer placePipesTimer;
	boolean over = false;
	double score = 0;

	FlappyBird() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		setFocusable(true);

		addKeyListener(this);

		backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
		birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
		topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
		bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

		bird = new Bird(birdImage);
		pipes = new ArrayList<Pipe>();

		placePipesTimer = new Timer(1500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				placePipes();
			}
		});
		placePipesTimer.start();

		loop = new Timer(1000 / 60, this);
		loop.start();
	}

	public void placePipes() {
		int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random() * (pipeHeight/2));
		int openingSpace = boardHeight/4;
		
		Pipe topPipe = new Pipe(topPipeImage);
		topPipe.y = randomPipeY;
		pipes.add(topPipe);
		
		Pipe bottomPipe = new Pipe(bottomPipeImage);
		bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
		pipes.add(bottomPipe);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

		g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
		}

		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,28));
		if(over) {
			g.drawString("Oyun Bitti..."+ String.valueOf((int)score),10,35);
		}
		else {
			g.drawString(String.valueOf((int) score),10,35);
		}
		
	}

	public void move() {
		speedY += gravity;
		bird.y += speedY;
		bird.y = Math.max(bird.y, 0);

		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			pipe.x += speedX;
			
			if(!pipe.passed && bird.x > pipe.x + pipe.width) {
				pipe.passed = true;
				score += 0.5;
			}
			
			if(collision(bird, pipe)) {
				over = true;
			}
		}
		
		if(bird.y > boardHeight) {
			over = true;
		}
	}
	
	public boolean collision(Bird a, Pipe b) {
		return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(over) {
			placePipesTimer.stop();
			loop.stop();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			speedY = -9;
			if(over) {
				bird.y = birdY;
				speedY = 0;
				pipes.clear();
				score = 0;
				over = false;
				loop.start();
				placePipesTimer.start();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
