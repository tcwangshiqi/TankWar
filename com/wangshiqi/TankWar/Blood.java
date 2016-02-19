package com.wangshiqi.TankWar;

import java.awt.*;

public class Blood{
	private int x,y,w,h;
	
	TankClient tc;
	
	private int step=0;
	
	private boolean live=true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	
	//指明血块的运动轨迹，由各个点构成
	private int[][] pos={
			{300,350},{310,345},{330,340},{350,325},{360,320},{370,300},
			{370,320},{345,350},{325,380},{300,400},{275,410},{250,400},
			{230,420},{200,390},{180,370},{200,355},{250,340},{280,356},
			{310,370}
	};
	
	
	
	public Blood() {
		x=pos[0][0];
		y=pos[0][1];
		w=h=20;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c=g.getColor();
		g.setColor(Color.red);
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.pink);
		g.fillOval(x-3, y, 7, 7);
		g.fillOval(x+19, y, 7, 7);
		g.fillOval(x+8, y+19, 7, 7);

		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,Font.BOLD,15));
		g.drawString("B", x+7, y+17);
		g.setFont(new Font(s,style,size));
		
		g.setColor(c);
		
		move();
	}
	
	public void move() {
		step++;
		if(step==pos.length) {
			step=0;
		}
		
		x=pos[step][0];
		y=pos[step][1];
	}
	
	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
