package src;

import java.applet.AudioClip;

public class Bullet extends Sprite {
	AudioClip clip = Utils.getAudio("res/explode.wav");
	boolean isPlayer = true;

	public Bullet(int x, int y, boolean isPlayer, MainFrame context) {
		super(x, y, isPlayer,Utils.getImage("res/bullet.png"), context);
		this.width = Config.BULLET_WIDTH;
		this.height = Config.BULLET_WIDTH;
		this.isPlayer = isPlayer;
	}

	public void move() {
		if (isPlayer) {
			y += 5;
		} else {
			y -= 5;
		}
	}

	public void hitAll() {
		if(context.player.isAlive){
			if (this.isCollide(context.player)) {
				if(isPlayer != context.player.isPlayer){
					context.player.isAlive = false;
					this.isAlive = false;
					context.isOver = true;
					clip.play();
				}
			}
		}
		
		for (int i = 0; i < context.allEnemies.size(); i++) {
			Sprite sprite = context.allEnemies.get(i);
			if (this.isCollide(sprite)) {
				if(isPlayer&&sprite.isAlive){
					sprite.isAlive = false;
					context.score += Config.SCORE;
					this.isAlive = false;
					clip.play();
				}
			}
		}
	}

}
