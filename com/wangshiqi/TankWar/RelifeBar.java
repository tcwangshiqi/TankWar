package com.wangshiqi.TankWar;

import java.awt.*;
import java.util.Random;

public class RelifeBar {
	private int x,y,w,h;
	
	private static Random tcr=new Random(50);
	
	public int eatenNum = 0;
	
	TankClient tc;
	
	private int step=0;
	
	private boolean live=false;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public RelifeBar() {
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
		g.setColor(Color.red);
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.white);
		g.fillOval(x-3, y-1, 7, 7);
		g.fillOval(x-3, y+17, 7, 7);
		g.fillOval(x+18, y-1, 7, 7);
		g.fillOval(x+18, y+17, 7, 7);

		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,Font.BOLD,15));
		g.drawString("R", x+6, y+17);
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
