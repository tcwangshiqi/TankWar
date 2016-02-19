package com.wangshiqi.TankWar;

import java.awt.*;
import java.util.List;


public class Missile2 extends Missile{

	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	int x,y;
	Direction dir;
	
	protected boolean live=true;

	private TankClient tc;
	
	protected boolean good;
	
	public boolean isGood() {
		return good;
	}

	private int level=1;
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	
	private static Image[] imgs1={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileU.gif")),
	};
	
	private static Image[] imgs2={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileD1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileL1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLD1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLU1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileR1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRD1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRU1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/missileU1.gif")),
	};
	
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public Missile2(int x, int y, Direction dir) {
		super(x,y,dir);
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile2(int x,int y,boolean good,Direction dir,TankClient tc,int level) {
		this(x,y,dir);
		this.good=good;
		this.tc=tc;
		this.level=level;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		/*
		Color c=g.getColor();
		if(good) {
			g.setColor(new Color(50+6*level,50+6*level,50+6*level));
		}
		else g.setColor(Color.black);
		if(good) {
			int Width=WIDTH+(int)(level*0.5);
			int Height=HEIGHT+(int)(level*0.5);
			g.fillOval(x, y, Width, Height);
		}
		else {
			g.fillOval(x, y, WIDTH, HEIGHT);
		}
		g.setColor(c);
		*/
		
		int x = this.x;
		int y = this.y;
		if(!good) {
			switch(dir) {

			case L:
				g.drawImage(imgs1[1], x, y, null);
				break;
			case LU:
				g.drawImage(imgs1[3], x, y, null);
				break;
			case U:
				g.drawImage(imgs1[0], x, y, null);
				break;
			case RU:
				g.drawImage(imgs1[6], x, y, null);
				break;
			case R:
				g.drawImage(imgs1[4], x, y, null);
				break;
			case RD:
				g.drawImage(imgs1[5], x, y, null);
				break;
			case D:
				g.drawImage(imgs1[7], x, y, null);
				break;
			case LD:
				g.drawImage(imgs1[2], x, y, null);
				break;
			}
		}
		else {
			switch(dir) {

			case L:
				g.drawImage(imgs2[1], x, y, null);
				break;
			case LU:
				g.drawImage(imgs2[3], x, y, null);
				break;
			case U:
				g.drawImage(imgs2[0], x, y, null);
				break;
			case RU:
				g.drawImage(imgs2[6], x, y, null);
				break;
			case R:
				g.drawImage(imgs2[4], x, y, null);
				break;
			case RD:
				g.drawImage(imgs2[5], x, y, null);
				break;
			case D:
				g.drawImage(imgs2[7], x, y, null);
				break;
			case LD:
				g.drawImage(imgs2[2], x, y, null);
				break;
			}
		}
		move();
	}

	private void move() {
		double XSpeed=XSPEED+0.5*tc.level;
		double YSpeed=YSPEED+0.5*tc.level;
		
		if(x<tc.myTank.x&&y<tc.myTank.y) {
			dir = Direction.RD;
		}
		else if(x<tc.myTank.x&&y>tc.myTank.y&&y<tc.myTank.y+30) {
			dir = Direction.R;
		}
		else if(x<tc.myTank.x&&y>tc.myTank.y+30) {
			dir = Direction.RU;
		}
		else if(x>tc.myTank.x&&x<tc.myTank.x+30&&y<tc.myTank.y) {
			dir = Direction.D;
		}
		else if(x>tc.myTank.x&&x<tc.myTank.x+30&&y>tc.myTank.y+30) {
			dir = Direction.U;
		}
		else if(x>tc.myTank.x+30&&y<tc.myTank.y) {
			dir = Direction.LD;
		}
		else if(x>tc.myTank.x+30&&y>tc.myTank.y&&y<tc.myTank.y+30) {
			dir = Direction.L;
		}
		else if(x>tc.myTank.x+30&&y>tc.myTank.y+30) {
			dir = Direction.LU;
		}
		
		switch(dir) {
		case L:
			x-=XSpeed;
			break;
		case LU:
			x-=XSpeed;
			y-=YSpeed;
			break;
		case U:
			y-=YSpeed;
			break;
		case RU:
			x+=XSpeed;
			y-=YSpeed;
			break;
		case R:
			x+=XSpeed;
			break;
		case RD:
			x+=XSpeed;
			y+=YSpeed;
			break;
		case D:
			y+=YSpeed;
			break;
		case LD:
			x-=XSpeed;
			y+=YSpeed;
			break;	
		}
		
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) {
			live=false;
			//tc.missiles.remove(this);
		}
	}
	
	public Rectangle getRech() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live&&this.getRech().intersects(t.getRech())&&t.isLive()&&this.good!=t.isGood()) {
			if(t.isGood()) {
				if(!t.isNoAttacked()) {
						t.setLife(t.getLife()-20);
					if(t.getLife()<=0) {
						t.setLive(false);
					}
				}
			}
			/*
			else {
				if(tc.level<=5) {
					t.setLive(false);
				}
				else if(tc.level>5&&tc.level<=20) {
					t.setLife(t.getLife()-55+2*tc.level);
					if(t.getLife()<=0) {
						t.setLive(false);
					}
				}
				else if(tc.level<30){
					t.setLife(t.getLife()-35+tc.level);
					if(t.getLife()<=0) {
						t.setLive(false);
					}
				}
				else {
					t.setLife(t.getLife()-1);
					if(t.getLife()<=0) {
						t.setLive(false);
					}
				}
			}
			*/
			else {
				t.setLife(t.getLife()-10);
				if(t.getLife()<=0) {
					t.setLive(false);
				}
			}
			
			this.live=false;
			Explode e=new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		else return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0;i<tanks.size();i++) {
			if(hitTank(tanks.get(i))) return true;
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(live&&this.getRech().intersects(w.getRech())) {
			live=false;
			return true;
		}
		return false;
	}
	
	public boolean hitMissile(Missile2 m) {
		if(m.good!=this.good&&m.live&&this.live&&this.getRech().intersects(m.getRech())) {
			live=false;
			m.live=false;
			return true;
		}
		return false;
	}
	
	public void hitMissilses(List<Missile> missiles) {
		if(this.isGood()) {
			for(int j=0;j<missiles.size();j++) {
				if(this!=missiles.get(j)) this.hitMissile(missiles.get(j));
			}
		}
	}
	
}
