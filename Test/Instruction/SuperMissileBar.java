package Test.Instruction;

import java.awt.*;
import java.util.Random;

public class SuperMissileBar {
	private int x,y,w,h;
	
	private static Random tcr=new Random(47);
	
	public int eatenNum = 0;
	
	TankClient tc;
	
	private int step=0;
	
	private boolean live=true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public SuperMissileBar() {
		x=tcr.nextInt(850)+50;
		y=tcr.nextInt(650)+50;
		w=h=15;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			x=tcr.nextInt(850)+50;
			y=tcr.nextInt(650)+50;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.gray);
		
		g.fillOval(x, y, w+2, h+2);
		g.setColor(Color.black);
		g.fillOval(x-2, y, 5, 5);
		g.fillOval(x+14, y, 5, 5);
		g.fillOval(x+6, y+15, 5, 5);

		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		g.setFont(new Font(s,style,12));
		g.drawString(""+eatenNum, x+6, y+2);
		g.setFont(new Font(s,Font.BOLD,12));
		g.drawString("S", x+6, y+13);
		g.setFont(new Font(s,style,size));
		
		g.setColor(c);
	}
	

	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
