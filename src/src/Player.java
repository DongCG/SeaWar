package src;

public class Player extends Sprite {

	int speed = 5;
	public Player(int x, int y, MainFrame context) {
		super(x, y,true,Utils.getImage("res/player.png"), context);
		this.width = Config.PLAYER_WIDTH;
		this.height = Config.PLAYER_HEIGHT;
	}

	public void move(int dir) {
		if (dir == 1) {
			x -= speed;
		} else if (dir == 2)
			x += speed;

		if (x < 0)
			x = 0;
		if (x > Config.FRAME_WIDTH - Config.PLAYER_WIDTH)
			x = Config.FRAME_WIDTH - Config.PLAYER_WIDTH;
	}

	public void fire() {
		Bullet bullet = new Bullet(x+Config.PLAYER_WIDTH/2,y+Config.PLAYER_HEIGHT,true,this.context);
		context.allBullets.add(bullet);
	}

}
