package frame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	static MainFrame fr;

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				fr = new MainFrame("Bubbleshooting");
				fr.setSize(new Dimension(1296, 759));
				fr.setPreferredSize(fr.getSize());
				fr.setDefaultCloseOperation(EXIT_ON_CLOSE);
				fr.setVisible(true);
			}
		});
	}

	GamePanel gp;

	public MainFrame(String title){
		super(title);
		gp = new GamePanel();
		add(gp);
		gp.setVisible(true);
		
		addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			
			public void keyReleased(KeyEvent e){
			}
			
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT){
					gp.tileVel = -1;
				}else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					gp.tileVel = 1;
				}
			}
		});
	}

}
