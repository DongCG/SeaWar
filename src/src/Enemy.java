package src;

public class Enemy extends Sprite {
	boolean toRight = true;
	int countdown = Utils.getRand(200);

	public Enemy(int x, int y, boolean toRight, MainFrame context) {
		super(x, y, false, Utils.getImage("res/enemy.png"), context);
		this.width = Config.ENEMY_WIDTH;
		this.height = Config.ENEMY_WIDTH;
		this.toRight = toRight;
	}

	public void fire() {
		countdown--;
		if (countdown == 0) {
			context.allBullets.add(new Bullet(x+Config.ENEMY_WIDTH / 2, y - 10, false, context));
			countdown = Utils.getRand(130);
		}

	}

}
