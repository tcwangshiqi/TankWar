package Test.Instruction;

import java.awt.*;
import java.util.Random;

public class NoAttackedBar {
	private int x,y,w,h;
	
	private static Random tcr=new Random(44);
	
	TankClient tc;
	
	private boolean live=false;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public NoAttackedBar() {
		x=tcr.nextInt(900);
		y=tcr.nextInt(700);
		w=h=15;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			x=tcr.nextInt(900);
			y=tcr.nextInt(700);
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.pink);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	

	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
