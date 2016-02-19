package Boss;

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
		x=tcr.nextInt(900)+50;
		y=tcr.nextInt(570)+80;
		w=h=20;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			x=tcr.nextInt(900)+50;
			y=tcr.nextInt(570)+80;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.pink);
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.red);
		g.fillOval(x-3, y, 7, 7);
		g.fillOval(x+19, y, 7, 7);
		g.fillOval(x+8, y+19, 7, 7);
		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,Font.BOLD,15));
		g.drawString("N", x+7, y+17);
		g.setFont(new Font(s,style,size));
		g.setColor(c);
	}
	

	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
	
	public boolean hitWall(Wall w) {
		if(live&&this.getRech().intersects(w.getRech())) {
			live=false;
			return true;
		}
		return false;
	}
}
