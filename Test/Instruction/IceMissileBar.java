package Test.Instruction;

import java.awt.*;
import java.util.Random;

public class IceMissileBar {
	private int x,y,w,h;
	
	private static Random tcr=new Random(43);
	
	TankClient tc;
	
	public int eatenNum = 0;
	
	private int step=0;
	
	private boolean live=true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public IceMissileBar() {
		x=tcr.nextInt(850)+25;
		y=tcr.nextInt(700)+50;
		w=h=15;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			x=tcr.nextInt(850)+25;
			y=tcr.nextInt(700)+50;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.blue);
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.white);
		g.fillOval(x-2, y, 5, 5);
		g.fillOval(x+14, y, 5, 5);
		g.fillOval(x+6, y+15, 5, 5);

		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,style,12));
		g.drawString(""+eatenNum, x+6, y+2);
		g.setFont(new Font(s,Font.BOLD,12));
		g.drawString("I", x+8, y+13);
		g.setFont(new Font(s,style,size));
		
		g.setColor(c);
	}
	

	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
