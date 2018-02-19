package src;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 193790338128033289L;
	long timeStamp = 0;
	int score = 0;
	int direction = 0;// 0-stop 1-left 2-right
	boolean[] LVS = { false, false, false, false };
	boolean isOver = false;
	List<Hero> newHeros = null;
	public boolean done = false;

	Image offScreen = null;
	Graphics offGraphics = null;

	Player player = new Player(10, 113, this);
	List<Enemy> allEnemies = new ArrayList<Enemy>();
	List<Bullet> allBullets = new ArrayList<Bullet>();
	Enemy enemy1 = new Enemy(50, 296, true, this);
	Enemy enemy2 = new Enemy(350, 366, false, this);
	Enemy enemy3 = new Enemy(500, 438, true, this);
	Enemy enemy4 = new Enemy(200, 508, false, this);

	public MainFrame() {
		this.setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		this.setTitle("Sea War");
		this.setResizable(false);
		this.setLocation((Config.SCREEN_WIDTH - Config.FRAME_WIDTH) / 2,
				(Config.SCREEN_HEIGHT - Config.FRAME_HEIGHT) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.addKeyListener(new MyKeyAdaper());

		this.setVisible(true);
		allEnemies.add(enemy1);
		allEnemies.add(enemy2);
		allEnemies.add(enemy3);
		allEnemies.add(enemy4);

		offScreen = createImage(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		offGraphics = offScreen.getGraphics();
		new Thread(new PaintThread()).start();

	}

	public void paint(Graphics g) {
		if (offGraphics != null) {
			offGraphics.drawString(allBullets.size() + "", Config.FRAME_WIDTH - 100, 50);
			offGraphics.drawImage((BufferedImage) Utils.getImage("res/bg.png"), 0, 20, this);

			// »­Íæ¼Ò
			player.draw(offGraphics);

			// »­Ç±Í§
			for (int i = 0; i < allEnemies.size(); i++) {
				Enemy enemy = allEnemies.get(i);
				enemy.draw(offGraphics);
			}
			// »­×Óµ¯
			for (int i = 0; i < allBullets.size(); i++) {
				Bullet bullet = allBullets.get(i);
				bullet.draw(offGraphics);
			}
			// »­ÎÄ×Ö
			offGraphics.setFont(new Font("ËÎÌå", Font.PLAIN, 15));
			offGraphics.drawString("SCORE£º" + score, Config.FRAME_WIDTH - 100, 60);

			if (isOver) {
				Hero me = new Hero(score, Utils.getTimeStamp());
				if(!done)
					newHeros = Utils.checkRank(me);
				if (newHeros != null||done) {
					if(!done){
						Utils.updateHeros(newHeros);
						done = true;
					}
					Utils.drawHeros(offGraphics, newHeros);
				} else {
					offGraphics.setFont(new Font("ºÚÌå", Font.PLAIN, 120));
					offGraphics.drawString("GAME OVER", Config.FRAME_WIDTH / 2 - 280, Config.FRAME_HEIGHT / 2);
				}
			}
			g.drawImage(offScreen, 0, 0, this);
		}
	}

	class MyKeyAdaper extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				direction = 1;
				break;
			case KeyEvent.VK_RIGHT:
				direction = 2;
				break;
			case KeyEvent.VK_SPACE:
				if (timeStamp == 0) {
					player.fire();
					timeStamp = System.currentTimeMillis();
				} else {
					if (System.currentTimeMillis() - timeStamp > Config.FIRE_INTERVAL) {
						timeStamp = System.currentTimeMillis();
						player.fire();
					}
				}
				break;
			default:
				break;
			}

		}

		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
				direction = 0;
				break;
			default:
				break;
			}
		}
	}

	class PaintThread implements Runnable {
		@Override
		public void run() {
			while (!isOver) {
				// ×Óµ¯ÔË¶¯Âß¼­
				for (int i = 0; i < allBullets.size(); i++) {
					Bullet bullet = allBullets.get(i);
					bullet.move();
					bullet.hitAll();
					if (bullet.y < 173 || bullet.y > Config.FRAME_HEIGHT || !bullet.isAlive)
						allBullets.remove(bullet);
				}

				// Ç±Í§ÔË¶¯Âß¼­
				for (int i = 0; i < allEnemies.size(); i++) {
					Enemy enemy = allEnemies.get(i);
					enemy.fire();
					if (enemy.toRight)
						enemy.x += 5;
					else
						enemy.x -= 5;
					if (enemy.x > Config.FRAME_WIDTH || enemy.x < -Config.ENEMY_WIDTH || !enemy.isAlive) {
						int y = enemy.y;
						allEnemies.remove(enemy);
						int temp = Utils.getRand(2);
						allEnemies.add(new Enemy(temp * Config.FRAME_WIDTH - Config.ENEMY_WIDTH, y,
								temp == 0 ? true : false, MainFrame.this));
					}
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.move(direction);
				repaint();
			}
		}

	}

}
