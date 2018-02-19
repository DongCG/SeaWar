package src;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
	public int x,y,width,height;
	public Image img;
	protected MainFrame context;
	public boolean isAlive = true;
	boolean isPlayer = true;
	
	public Sprite(int x, int y, boolean isPlayer,Image img,MainFrame context){
		this.x = x;
		this.y = y;
		this.img = img;
		this.isPlayer = isPlayer;
		this.context = context;
	}
	public Rectangle getRect(){
		return new Rectangle(x,y,width,height);
	}
	
	public boolean isCollide(Sprite sprite){
		return this.getRect().intersects(sprite.getRect());
	}
	
	public void draw(Graphics g){
		if(isAlive)
			g.drawImage(img, x, y, null);
	}

}
