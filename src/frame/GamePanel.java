package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	final static int FPS = 30;

	final static int WIDTH = 1280;
	final static int HEIGHT = 720;

	final static int TILE_WIDTH = 100;
	final static int TILE_HEIGHT = 10;
	final static int TILE_SPEED = 10;

	final static int BALL_WIDTH = 25;
	final static int BALL_SPEED = 7;

	final static int BOX_WIDTH = 126;
	final static int BOX_HEIGHT = 30;
	final static int BOX_ROWS = 10;

	boolean gameRunning = true;

	int tileX;
	int tileVel;

	int ballX;
	int ballY;
	int ballXVel;
	int ballYVel;

	boolean boxes[][];

	public GamePanel(){
		setSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(getSize());
		setBackground(Color.WHITE);

		GameInit();
		runGameLoop();
	}

	private void runGameLoop(){
		Thread loop = new Thread(){
			public void run(){
				gameLoop();
			}
		};
		loop.start();
	}

	private void gameLoop(){
		int i = 0;
		long lastLoopTime = System.nanoTime();
		final long OPT_TIME = 1000000000 / FPS;
		while (gameRunning){
			long now = System.nanoTime();
			lastLoopTime = now;
			// long updateLength = now - lastLoopTime;
			// double delta = updateLength / ((double) OPT_TIME);

			GameUpdate();
			i++;
			System.out.println(i);
			try{
				Thread.sleep((lastLoopTime - System.nanoTime() + OPT_TIME) / 1000000);
			} catch (InterruptedException e){}
		}

		GameOver();
	}

	private void GameInit(){
		tileX = WIDTH / 2 - TILE_WIDTH / 2;
		tileVel = 0;

		ballX = WIDTH / 2 - BALL_WIDTH / 2;
		ballY = HEIGHT / 2 - BALL_WIDTH / 2;
		ballXVel = (Math.random() > 0.5) ? -1 : 1;
		ballYVel = (((int) (Math.random() * 100) & 1) == 0) ? -1 : 1;

		boxes = new boolean[10][BOX_ROWS];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < BOX_ROWS; j++)
				boxes[i][j] = true;
	}

	private void GameUpdate(){
		tileX += TILE_SPEED * tileVel;

		if (tileX <= TILE_HEIGHT || tileX + TILE_WIDTH >= WIDTH - TILE_HEIGHT)
			tileVel *= -1;

		ballX += BALL_SPEED * ballXVel;
		ballY += BALL_SPEED * ballYVel;

		if (ballX <= TILE_HEIGHT || ballX + BALL_WIDTH >= WIDTH - TILE_HEIGHT)
			ballXVel *= -1;
		if (ballY <= TILE_HEIGHT
				|| (ballY + BALL_WIDTH >= HEIGHT - TILE_HEIGHT && (ballX
						+ BALL_WIDTH / 2 >= tileX && ballX + BALL_WIDTH / 2 <= tileX
						+ TILE_WIDTH))) ballYVel *= -1;
		
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < BOX_ROWS; j++){
				
			}

		if (ballY + BALL_WIDTH >= HEIGHT) gameRunning = false;

		repaint();
	}

	private void GameOver(){
		System.out.println("Game Over");
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, WIDTH, TILE_HEIGHT);
		g2D.fillRect(0, 0, TILE_HEIGHT, HEIGHT);
		g2D.fillRect(WIDTH - TILE_HEIGHT + 1, 0, TILE_HEIGHT - 1, HEIGHT);

		g2D.fillRect(tileX, HEIGHT - TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g2D.fillOval(ballX, ballY, BALL_WIDTH, BALL_WIDTH);

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < BOX_ROWS; j++)
				if (boxes[i][j])
					g2D.fillRect(TILE_HEIGHT + 1 + (BOX_WIDTH) * i, TILE_HEIGHT
							+ 1 + (BOX_HEIGHT) * j, BOX_WIDTH - 1,
							BOX_HEIGHT - 1);
	}

}
