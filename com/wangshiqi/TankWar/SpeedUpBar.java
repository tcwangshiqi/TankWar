package com.wangshiqi.TankWar;

import java.awt.*;
import java.util.Random;

public class SpeedUpBar {
	private int x,y,w,h;
	
	private static Random tcr=new Random(45);
	
	TankClient tc;
	
	public int eatenNum = 0;
	
	private int step=0;
	
	private boolean live=true;
	
	public boolean rlbRelife1 = true;
	public boolean rlbRelife2 = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public SpeedUpBar() {
		x=tcr.nextInt(880)+50;
		y=tcr.nextInt(550)+80;
		w=h=20;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			x=tcr.nextInt(900);
			y=tcr.nextInt(700);
			return;
		}
		Color c=g.getColor();
		g.setColor(new Color(90,255,180));
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.black);
		g.fillOval(x-3, y, 7, 7);
		g.fillOval(x+19, y, 7, 7);
		g.fillOval(x+8, y+19, 7, 7);

		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,Font.BOLD,15));
		g.drawString(""+eatenNum, x+8, y+2);
		g.setFont(new Font(s,Font.BOLD,15));
		g.drawString("Q", x+6, y+17);
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
