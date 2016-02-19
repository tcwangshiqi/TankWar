package Boss;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import com.wangshiqi.TankWar.Explode;

import NetTankWar.Dir;

public class Tank {
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	private int enterNum=0;
	
	public boolean ptTrack = false;
	
	public boolean king = false;
	
	public boolean queen = false;
	
	public double Speed=XSPEED;
	
	public int getEnterNum() {
		return enterNum;
	}

	public void setEnterNum(int enterNum) {
		this.enterNum = enterNum;
	}
	
	public int SuperMissileNum = 0;
	
	public int IceMissileNum = 0;
	public int icedDirectNum = 0;
	
	public int lighteningMissileNum = 0;
	
	public int doubleFireNum = 0;
	
	public Boolean SpeedUp=false;
	
	private int spaceNumber=0;
	
	private BloodBar bb=new BloodBar();
	
	private static Random r=new Random();
	
	public int x,y;
	
	private int reLife=0;
	
	private int level=1;
	//按shift键的无敌的次数
	private int hasNoAttackedNum=0;
	
	public int getHasNoAttackedNum() {
		return hasNoAttackedNum;
	}

	public int getReLife() {
		return reLife;
	}

	public void setReLife(int reLife) {
		this.reLife = reLife;
	}

	private int oldX,oldY;
	
	private boolean live=true;
	
	private TankClient tc=null;
	
	private boolean good;
	
	private boolean bL=false,bU=false,bR=false,bD=false;
	
	private int step=r.nextInt(12)+3;
	
	private Direction dir=Direction.Stop;
	private Direction ptDir=Direction.D;
	
	private int life=600;
	
	private boolean noAttacked=false;
	
	private int noAttackedNum=0;
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	
	private static Image[] imgs={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/tankU.gif")),
	};
	
	private static Image[] imgs2={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/SU.gif")),
	};
	
	private static Image[] imgs3={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/ptTrackU.gif")),
	};
	private static Image[] imgs4={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/kingU.gif")),
	};
	private static Image[] imgs5={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/queenU.gif")),
	};
	private static Image[] imgs6={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mL.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mLD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mLU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mR.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mRD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mRU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/mU.gif")),
	};
	private static Image[] imgs7={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2D.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2L.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2LD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2LU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2R.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2RD.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2RU.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/Queen2U.gif")),
	};
	
	public int getNoAttackedNum() {
		return noAttackedNum;
	}

	public void setNoAttackedNum(int noAttackedNum) {
		this.noAttackedNum = noAttackedNum;
	}

	public boolean isNoAttacked() {
		return noAttacked;
	}

	public void setNoAttacked(boolean noAttacked) {
		this.noAttacked = noAttacked;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc) {
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
		this.level=tc.level;
		if(!this.good) {
			this.life = 100 + level*100;
		}
		
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc,Boolean ptTrack) {
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
		this.level=tc.level;
		if(!this.good) {
			if(ptTrack) {
				this.life = 150 + level*100;
			}
			else {
				this.life = 100 + level*100;
			}
			
		}
		this.ptTrack = ptTrack;
		
	}

	public Tank(int x,int y,boolean good,Direction dir,TankClient tc,Boolean ptTrack,Boolean king) {
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
		this.level=tc.level;
		this.ptTrack = ptTrack;
		this.king = king;
		if(!this.good) {
			if(ptTrack) {
				this.life = 120 + 4*100;
			}
			else if(king) {
				this.life = 150 + 5*100;
			}
			else {
				this.life = 100 + level*100;
			}
			
		}
		
		
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc,Boolean ptTrack,Boolean king,boolean queen) {
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
		this.level=tc.level;
		this.ptTrack = ptTrack;
		this.king = king;
		this.queen = queen;
		if(!this.good) {
			if(ptTrack) {
				this.life = 120 + level*100;
			}
			else if(king) {
				this.life = 150 + level*100;
			}
			else if(queen) {
				this.life=5000;
			}
			else {
				this.life = 100 + level*100;
			}
			
			
		}
		
		
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(!good) tc.tanks.remove(this);
			
			return;
		}
		
		
		Color c=g.getColor();
		
		
		if(noAttacked&&good&&noAttackedNum<=20) {
			g.setColor(Color.pink);
			g.fillOval(x-7, y-7, WIDTH+14, HEIGHT+14);
			g.setColor(c);
		}
		if(noAttackedNum>20) {
			setNoAttackedNum(0);
			setNoAttacked(false);
		}
		if(IceMissileNum>0&&good) {
			g.setColor(Color.blue);
			g.fillOval(x-5, y-5, WIDTH+10, HEIGHT+10);
			g.setColor(c);
		}
		if(icedDirectNum>0) {
			g.setColor(new Color(90,255,180));
			g.fillOval(x-5, y-5, WIDTH+10, HEIGHT+10);
			g.setColor(c);
		}
		if(SuperMissileNum>0&&good) {
			g.setColor(Color.gray);
			g.fillOval(x-5, y-5, WIDTH+10, HEIGHT+10);
			g.setColor(c);
		}
		if(lighteningMissileNum>0&&good) {
			g.setColor(Color.yellow);
			g.fillOval(x-5, y-5, WIDTH+10, HEIGHT+10);
			g.setColor(c);
			switch(ptDir) {
			case L:
				g.setColor(Color.yellow);
				g.fillOval(x-11, y-10, 6,50);
				g.setColor(Color.RED);
				g.fillOval(x-14, y+10, 6, 10);
				g.setColor(c);
				break;
			case R:
				g.setColor(Color.yellow);
				g.fillOval(x+35, y-10, 6,50);
				g.setColor(Color.RED);
				g.fillOval(x+37, y+10, 6, 10);
				g.setColor(c);
				break;
			case D:
				g.setColor(Color.yellow);
				g.fillOval(x-10, y+35, 50,6);
				g.setColor(Color.RED);
				g.fillOval(x+10, y+37, 10,6);
				g.setColor(c);
				break;
			case U:
				g.setColor(Color.yellow);
				g.fillOval(x-10, y-11, 50,6);
				g.setColor(Color.RED);
				g.fillOval(x+10, y-14, 10,6);
				g.setColor(c);
				break;
			}
			
		}
		if(doubleFireNum>0&&good) {
			g.setColor(new Color(50,50,50));
			g.fillOval(x-2, y-2, WIDTH+4, HEIGHT+4);
			g.setColor(c);
		}
		
		//正常版
		
		if(good) g.setColor(Color.pink);
		else {
			
			if(tc.level<=1) g.setColor(Color.white);
			else if(tc.level>1&&tc.level<=2) g.setColor(Color.yellow);
			else if(tc.level>2&&tc.level<=4) g.setColor(Color.orange);
			else if(tc.level>4&&tc.level<=6) g.setColor(Color.MAGENTA);
			else if(tc.level>6&&tc.level<=8) g.setColor(Color.BLUE);
			else if(tc.level==9) g.setColor(Color.blue);
			
		}
		/*
		if(queen) {
			g.fillOval(x, y, WIDTH+30, HEIGHT+30);
		}
		*/
		if(!queen)  {
			g.fillOval(x, y, WIDTH, HEIGHT);
		}
		g.setColor(c);
		
		if(ptTrack) {
				g.setColor(Color.pink);
				g.fillOval(x+5, y+5, 20, 20);
				g.setColor(c);
			}
		if(king) {
			g.setColor(Color.gray);
			g.fillOval(x+5, y+5, 20, 20);
			g.setColor(c);
		}
		/*
		if(queen) {
			g.setColor(Color.black);
			g.fillOval(x+10, y+10, 40, 40);
			g.setColor(c);
		}
		
		if(queen) {
			switch(ptDir) {
			case L:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+20, x-10, y+Tank.HEIGHT/2+20);
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x-12, y+Tank.HEIGHT/2+15);
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+10, x-10, y+Tank.HEIGHT/2+10);
				break;
			case LU:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+2, y+2);
				g.drawLine(x+Tank.WIDTH/2+15-3, y+Tank.HEIGHT/2+18, x-1, y+5);
				g.drawLine(x+Tank.WIDTH/2+18, y+Tank.HEIGHT/2+12, x+5, y-1);
				break;
			case U:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+15, y-10);
				g.drawLine(x+Tank.WIDTH/2+10, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+10, y-10);
				g.drawLine(x+Tank.WIDTH/2+20, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+20, y-10);
				break;
			case RU:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+58, y+2);
				g.drawLine(x+Tank.WIDTH/2+15-3, y+Tank.HEIGHT/2+12, x+55, y-1);
				g.drawLine(x+Tank.WIDTH/2+18, y+Tank.HEIGHT/2+18, x+61, y+5);
				break;
			case R:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+20, x+Tank.WIDTH+12+30, y+Tank.HEIGHT/2+20);
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+Tank.WIDTH+12+30, y+Tank.HEIGHT/2+15);
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+10, x+Tank.WIDTH+12+30, y+Tank.HEIGHT/2+10);
				break;
			case RD:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+58, y+58);
				g.drawLine(x+Tank.WIDTH/2+15-3, y+Tank.HEIGHT/2+18, x+55, y+61);
				g.drawLine(x+Tank.WIDTH/2+18, y+Tank.HEIGHT/2+12, x+61, y+55);
				break;
			case D:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+15, y+70);
				g.drawLine(x+Tank.WIDTH/2+10, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+10, y+70);
				g.drawLine(x+Tank.WIDTH/2+20, y+Tank.HEIGHT/2+15, x+Tank.WIDTH/2+20, y+70);
				break;
			case LD:
				g.drawLine(x+Tank.WIDTH/2+15, y+Tank.HEIGHT/2+15, x+2, y+58);
				g.drawLine(x+Tank.WIDTH/2+15-3, y+Tank.HEIGHT/2+12, x-1, y+55);
				g.drawLine(x+Tank.WIDTH/2+18, y+Tank.HEIGHT/2+18, x+5, y+61);
				break;
			}
		}
		*/
		if(!good&&!queen) {
			switch(ptDir) {
			case L:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-1, y+Tank.HEIGHT/2);
				break;
			case LU:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
				break;
			case U:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y-1);
				break;
			case RU:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
				break;
			case R:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+1, y+Tank.HEIGHT/2);
				break;
			case RD:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
				break;
			case D:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT+1);
				break;
			case LD:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
				break;
			}
		}
		
		
	//图片版	
	  {
		if(good) {
			int x = this.x ;
			int y = this.y;			
			switch(ptDir) {
			case L:
				g.drawImage(imgs6[1], x-2, y-2, 35,35,null);
				break;
			case LU:
				g.drawImage(imgs6[3], x-4, y-4,39,39, null);
				break;
			case U:
				g.drawImage(imgs6[7], x-2, y-2,35,35, null);
				break;
			case RU:
				g.drawImage(imgs6[6], x-4, y-4,39,39, null);
				break;
			case R:
				g.drawImage(imgs6[4], x-2, y-2,35,35, null);
				break;
			case RD:
				g.drawImage(imgs6[5], x-4, y-4,39,39, null);
				break;
			case D:
				g.drawImage(imgs6[0], x-2, y-2,35,35, null);
				break;
			case LD:
				g.drawImage(imgs6[2], x-4, y-4,39,39, null);
				break;
			}
			
		}
		else if(!ptTrack&&!king&&!queen) {
			int x = this.x ;
			int y = this.y;			
			switch(ptDir) {
			case L:
				g.drawImage(imgs2[1], x-2, y-2, 35,35,null);
				break;
			case LU:
				g.drawImage(imgs2[3], x-2, y-2,35,35, null);
				break;
			case U:
				g.drawImage(imgs2[7], x-2, y-2,35,35, null);
				break;
			case RU:
				g.drawImage(imgs2[6], x-2, y-2,35,35, null);
				break;
			case R:
				g.drawImage(imgs2[4], x-2, y-2,35,35, null);
				break;
			case RD:
				g.drawImage(imgs2[5], x-2, y-2,35,35, null);
				break;
			case D:
				g.drawImage(imgs2[0], x-2, y-2,35,35, null);
				break;
			case LD:
				g.drawImage(imgs2[2], x-2, y-2,35,35, null);
				break;
			}
		}
		else if(ptTrack) {
			int x = this.x ;
			int y = this.y;			
			switch(ptDir) {
			case L:
				g.drawImage(imgs3[1], x-2, y-2, 35,35,null);
				break;
			case LU:
				g.drawImage(imgs3[3], x-2, y-2,35,35, null);
				break;
			case U:
				g.drawImage(imgs3[7], x-2, y-2,35,35, null);
				break;
			case RU:
				g.drawImage(imgs3[6], x-2, y-2,35,35, null);
				break;
			case R:
				g.drawImage(imgs3[4], x-2, y-2,35,35, null);
				break;
			case RD:
				g.drawImage(imgs3[5], x-2, y-2,35,35, null);
				break;
			case D:
				g.drawImage(imgs3[0], x-2, y-2,35,35, null);
				break;
			case LD:
				g.drawImage(imgs3[2], x-2, y-2,35,35, null);
				break;
			}
		}
		else if(king) {
			int x = this.x ;
			int y = this.y;			
			switch(ptDir) {
			case L:
				g.drawImage(imgs4[1], x-2, y-2, 35,35,null);
				break;
			case LU:
				g.drawImage(imgs4[3], x-4, y-4,39,39, null);
				break;
			case U:
				g.drawImage(imgs4[7], x-2, y-2, 35,35, null);
				break;
			case RU:
				g.drawImage(imgs4[6], x-4, y-4,39,39, null);
				break;
			case R:
				g.drawImage(imgs4[4],x-2, y-2, 35,35, null);
				break;
			case RD:
				g.drawImage(imgs4[5], x-4, y-4,39,39, null);
				break;
			case D:
				g.drawImage(imgs4[0], x-2, y-2, 35,35, null);
				break;
			case LD:
				g.drawImage(imgs4[2], x-4, y-4,39,39, null);
				break;
			}
		}
		
		else if(queen) {
			int x = this.x ;
			int y = this.y;			
			if(life>3000) {
				switch(ptDir) {
				case L:
					g.drawImage(imgs5[1], x-7, y-7, 74,74,null);
					break;
				case LU:
					g.drawImage(imgs5[3], x-10, y-10, 80,80, null);
					break;
				case U:
					g.drawImage(imgs5[7], x-10, y-10,  74,74, null);
					break;
				case RU:
					g.drawImage(imgs5[6], x-10, y-10, 80,80, null);
					break;
				case R:
					g.drawImage(imgs5[4],x-10, y-10,  74,74, null);
					break;
				case RD:
					g.drawImage(imgs5[5], x-10, y-10, 80,80, null);
					break;
				case D:
					g.drawImage(imgs5[0], x-7, y-7,  74,74 ,null);
					break;
				case LD:
					g.drawImage(imgs5[2], x-10, y-10, 80,80, null);
					break;
				}
			}
			else {
				switch(ptDir) {
				case L:
					g.drawImage(imgs7[1], x-7, y-7, 74,74,null);
					break;
				case LU:
					g.drawImage(imgs7[3], x-10, y-10, 80,80, null);
					break;
				case U:
					g.drawImage(imgs7[7], x-10, y-10,  74,74, null);
					break;
				case RU:
					g.drawImage(imgs7[6], x-10, y-10, 80,80, null);
					break;
				case R:
					g.drawImage(imgs7[4],x-10, y-10,  74,74, null);
					break;
				case RD:
					g.drawImage(imgs7[5], x-10, y-10, 80,80, null);
					break;
				case D:
					g.drawImage(imgs7[0], x-7, y-7,  74,74 ,null);
					break;
				case LD:
					g.drawImage(imgs7[2], x-10, y-10, 80,80, null);
					break;
				}
			}
		}
		/*
		else {
			if(tc.level<=5) g.setColor(Color.white);
			else if(tc.level>5&&tc.level<=10) g.setColor(Color.yellow);
			else if(tc.level>10&&tc.level<=15) g.setColor(Color.orange);
			else if(tc.level>15&&tc.level<=20) g.setColor(Color.pink);
			else if(tc.level>20&&tc.level<=25) g.setColor(Color.BLUE);
			else if(tc.level>25&&tc.level<=29) g.setColor(Color.cyan);
			else if(tc.level==30) g.setColor(Color.darkGray);
			g.fillOval(x, y, WIDTH, HEIGHT);
			g.setColor(c);
			switch(ptDir) {
			case L:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-1, y+Tank.HEIGHT/2);
				break;
			case LU:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
				break;
			case U:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y-1);
				break;
			case RU:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
				break;
			case R:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+1, y+Tank.HEIGHT/2);
				break;
			case RD:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
				break;
			case D:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT+1);
				break;
			case LD:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
				break;
			}
		}
	  }
	  */
		bb.draw(g);
		
		move();
	  }
	}
	
	public void stay() {
		x=this.oldX;
		y=this.oldY;
	}
	//碰到tank时换方向
	public void changeDirect(Tank t) {
		if(!good) {
			if(x<t.x-15&&y<t.y-15) {
				dir = Direction.LU;
			}
			else if(x<t.x-15&&y>t.y-15&&y<t.y+15) {
				dir = Direction.L;
			}
			else if(x<t.x-15&&y>t.y+15) {
				dir = Direction.LD;
			}
			else if(x>t.x-15&&x<t.x+15&&y<t.y-15) {
				dir = Direction.U;
			}
			else if(x>t.x-15&&x<t.x+15&&y>t.y+15) {
				dir = Direction.D;
			}
			else if(x>t.x+15&&y<t.y-15) {
				dir = Direction.RU;
			}
			else if(x>t.x+15&&y>t.y-15&&y<t.y+15) {
				dir = Direction.R;
			}
			else if(x>t.x+15&&y>t.y+15) {
				dir = Direction.RD;
			}
		}
		Direction[] dirs=Direction.values();
		if(step==0) {
			step=r.nextInt(12)+3;

			int rn=r.nextInt(dirs.length);
			dir=dirs[rn];
		}
	}
	
	public void changeDirect(Wall m) {
		if(x<m.x-15&&y<m.y-15) {
			dir = Direction.LU;
		}
		else if(x<m.x-15&&y>m.y-15&&y<m.y+m.h-15) {
			dir = Direction.L;
		}
		else if(x<m.x-15&&y>m.y+m.h-15) {
			dir = Direction.LD;
		}
		else if(x>m.x-15&&x<m.x+m.w-15&&y<m.y-15) {
			dir = Direction.U;
		}
		else if(x>m.x-15&&x<m.x+m.w-15&&y>m.y+m.h-15) {
			dir = Direction.D;
		}
		else if(x>m.x+m.w-15&&y<m.y-15) {
			dir = Direction.RU;
		}
		else if(x>m.x+m.w-15&&y>m.y-15&&y<m.y+m.h-15) {
			dir = Direction.R;
		}
		else if(x>m.x+m.w-15&&y>m.y+m.h-15) {
			dir = Direction.RD;
		}
		Direction[] dirs=Direction.values();
		if(step==0) {
			step=r.nextInt(12)+3;

			int rn=r.nextInt(dirs.length);
			dir=dirs[rn];
		}
	}
	
	public void changePtDirect(Tank t) {
		if(!good) {
			if(x<t.x-15&&y<t.y-15) {
				ptDir = Direction.RD;
			}
			else if(x<t.x-15&&y>t.y-15&&y<t.y+15) {
				ptDir = Direction.R;
			}
			else if(x<t.x-15&&y>t.y+15) {
				ptDir = Direction.RU;
			}
			else if(x>t.x-15&&x<t.x+15&&y<t.y-15) {
				ptDir = Direction.D;
			}
			else if(x>t.x-15&&x<t.x+15&&y>t.y+15) {
				ptDir = Direction.U;
			}
			else if(x>t.x+15&&y<t.y-15) {
				ptDir = Direction.LD;
			}
			else if(x>t.x+15&&y>t.y-15&&y<t.y+15) {
				ptDir = Direction.L;
			}
			else if(x>t.x+15&&y>t.y+15) {
				ptDir = Direction.LU;
			}
			Direction[] dirs=Direction.values();
			if(step==0) {
				step=r.nextInt(12)+3;

				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
		}
		
	}
	
	public void move() {
		this.oldX=x;
		this.oldY=y;
		if(SpeedUp) {
			this.Speed=XSPEED+3+tc.sub.eatenNum*0.4; 
		}
		else Speed = XSPEED;
		if(SuperMissileNum>0) {
			this.Speed=XSPEED-1.5-0.1*tc.smb.eatenNum;
		}
		if(icedDirectNum>0) {
			this.dir = Direction.Stop;
			icedDirectNum--;
		}
		
		double XSpeed =XSPEED;
		double YSpeed =XSPEED;
		if(!good) {
			if(ptTrack) {
				XSpeed=Speed+1+0.5*tc.level;
				YSpeed=Speed+1+0.5*tc.level;
			}
			else if(king) {
				XSpeed=Speed+1.5+0.5*tc.level;
				YSpeed=Speed+1.5+0.5*tc.level;
			}
			else if(queen) {
				XSpeed=12; 
				YSpeed=12;
			}
			else {
				XSpeed=Speed+0.5*tc.level;
				YSpeed=Speed+0.5*tc.level;
			}
		}
		else
		{
			XSpeed=Speed+0.45*tc.level;
			YSpeed=Speed+0.45*tc.level;
		}
		
		Speed=XSpeed;
		
		switch(dir) {
		case L:
			x-=XSpeed;
			break;
		case LU:
			x-=0.7*XSpeed;
			y-=0.7*YSpeed;
			break;
		case U:
			y-=YSpeed;
			break;
		case RU:
			x+=0.7*XSpeed;
			y-=0.7*YSpeed;
			break;
		case R:
			x+=YSpeed;
			break;
		case RD:
			x+=0.7*XSpeed;
			y+=0.7*YSpeed;
			break;
		case D:
			y+=YSpeed;
			break;
		case LD:
			x-=0.7*XSpeed;
			y+=0.7*YSpeed;
			break;
		case Stop:
			break;
		}
		
		if(this.dir!=Direction.Stop&&!this.collidesWithTanks(tc.tanks)) {
			ptDir=dir;
		}
		if(good&&this.dir!=Direction.Stop) {
			ptDir=dir;
		}
		//边界
		if(x<0) {
			x=0;
		}
		if(queen) {
			if(y<30) y=30;
			if(x+Tank.WIDTH+30>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH-30;
			if(y+Tank.HEIGHT+30>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT-30;
		}
		else {
			if(y<30) y=30;
			if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
			if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		}
		
		//小兵
		if(!good&&!queen&&!king&&!ptTrack) {
			Direction[] dirs=Direction.values();
			if(step==0) {
				step=r.nextInt(12)+3;

				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			
			step--;
			/*
			if(collidesWithTanks(tc.tanks)) {
				step=20;
			}
			*/
			if(r.nextInt(40)>(38-0.4*tc.level)) this.fire();
			
		}
		//king
		if(king) {
			Direction[] dirs=Direction.values();
			int rn=r.nextInt(dirs.length);
			if(step==0) {
				step=r.nextInt(12)+3;

				rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			/*
			//避开子弹
			for(int i=0;i<tc.missiles.size();i++) {
				Missile m = null;
				m =	tc.missiles.get(i);
				//避开右边子弹
				if(m.isGood()&&m.x<x+100&&m.y<=m.y+45&&y>=m.y-45&&m.dir == Direction.L) {
					dir = Direction.LU;
					step=10;
				}
				//避开左边子弹
				if(m.isGood()&&m.x>x-100&&m.y<=m.y+45&&y>=m.y-45&&m.dir == Direction.R) {
					dir = Direction.LU;
					step=10;
				}
			}
			*/
			
			
			step--;
			
			if(x<tc.myTank.x-50&&y<tc.myTank.y-50) {
				ptDir = Direction.RD;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.R;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y+50) {
				ptDir = Direction.RU;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.U;
			}
			else if(x>tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.LD;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.L;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.LU;
			}
			
			if(r.nextInt(400)>(380-3*tc.level)) this.fire();
			if(r.nextInt(4000)>(3920-9*tc.level)) this.deathFire();
			if(r.nextInt(4000)>(3985-4*tc.level)) this.death2Fire();
		}
		//小boss
		if(!good&&ptTrack) {
			//随机方向
			Direction[] dirs=Direction.values();
			if(step==0) {
				step=r.nextInt(12)+3;

				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			
			step--;
			
			if(x<tc.myTank.x-50&&y<tc.myTank.y-50) {
				ptDir = Direction.RD;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.R;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y+50) {
				ptDir = Direction.RU;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.U;
			}
			else if(x>tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.LD;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.L;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.LU;
			}
			
			if(r.nextInt(400)>(374-3.5*tc.level)) this.fire();
			
		}
		if(queen) {
			//随机方向
			Direction[] dirs=Direction.values();
			if(step==0) {
				step=r.nextInt(12)+3;

				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			
			step--;
			
			if(x<tc.myTank.x-50&&y<tc.myTank.y-50) {
				ptDir = Direction.RD;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.R;
			}
			else if(x<tc.myTank.x-50&&y>tc.myTank.y+50) {
				ptDir = Direction.RU;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.D;
			}
			else if(x>tc.myTank.x-50&&x<tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.U;
			}
			else if(x>tc.myTank.x+50&&y<tc.myTank.y-50) {
				ptDir = Direction.LD;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y-50&&y<tc.myTank.y+50) {
				ptDir = Direction.L;
			}
			else if(x>tc.myTank.x+50&&y>tc.myTank.y+50) {
				ptDir = Direction.LU;
			}
			if(r.nextInt(20000)>19998) {
				this.lighteningMissileNum=100;
			}
			if(r.nextInt(10000)>(9875)) {
				this.lighteningMissileNum=50;
			}
			/*
			if(r.nextInt(100)>(97)) {
				this.lighteningMissileNum=15;
			}
			*/
			
			if(r.nextInt(40)>(35.0)) {
				this.fire();
				this.doubleFire();
			}
			if(lighteningMissileNum>0) {
				dir = Direction.Stop;
				this.lighteningFire();
				if(r.nextInt(400)>(388)) {
					this.doubleDeathFire();
				}
				if(life>3000) {
					if(r.nextInt(400)>(397)) {
						this.doubleDeath2Fire();
					}
				}
				else {
					if(r.nextInt(400)>(393)) {
						this.doubleDeath2Fire();
					}
				}
				lighteningMissileNum --;
			}
			else {
				if(r.nextInt(40)>(36.5)) {
					this.doubleFire();
				}
				if(r.nextInt(40)>(34.0)) {
					this.fire();
				}
				
				/*
				if(r.nextInt(40)>(35.0)) {
					this.fire();
					this.doubleFire();
					this.fire();
					this.doubleFire();
				}
				*/
				if(this.life>3000) {
					if(r.nextInt(400)>(365)) {
						this.doubleDeathFire();
					}
					if(r.nextInt(400)>(390)) {
						this.doubleDeath2Fire();
					}
				}
				else {
					if(r.nextInt(400)>(375)) {
						this.doubleDeathFire();
					}
					if(r.nextInt(400)>(383)) {
						this.doubleDeath2Fire();
					}
				}
				
			}
			if(r.nextInt(40)>(38)) {
				this.superDoubleFire();
				this.superDoubleFire();
			}
			if(r.nextInt(40)>(37.0)) {
				this.superFire();
				this.superFire();
			}
		}
	
	}
	
	public void KeyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
			
		case KeyEvent.VK_A:
			bL=true;
			break;
		case KeyEvent.VK_W:
			bU=true;
			break;
		case KeyEvent.VK_D:
			bR=true;
			break;
		case KeyEvent.VK_S:
			bD=true;
			break;
		case KeyEvent.VK_L:
			if(lighteningMissileNum>0) {
				lighteningFire();
				lighteningMissileNum--;
				this.noAttacked=true;
			}
			if(lighteningMissileNum==0) {
				this.noAttacked = false;
			}
			
			break;
		case KeyEvent.VK_ENTER:
			if(enterNum<30+tc.level*10) {
				superFire();
				enterNum++;
			}
			break;
		}
		
		locateDirection();
	}
	
	public void locateDirection(){
		if(bL&&!bU&&!bR&&!bD) dir=Direction.L;
		else if(bL&&bU&&!bR&&!bD) dir=Direction.LU;
		else if(!bL&&bU&&!bR&&!bD) dir=Direction.U;
		else if(!bL&&bU&&bR&&!bD) dir=Direction.RU;
		else if(!bL&&!bU&&bR&&!bD) dir=Direction.R;
		else if(!bL&&!bU&&bR&&bD) dir=Direction.RD;
		else if(!bL&&!bU&&!bR&&bD) dir=Direction.D;
		else if(bL&&!bU&&!bR&&bD) dir=Direction.LD;
		else if(!bL&&!bU&&!bR&&!bD) dir=Direction.Stop;
	}

	public void KeyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F2:
			if(!this.live&&reLife<=3) {
				live=true;
				reLife++;
				this.life=100+(tc.level*20);
				if(tc.level == 9) {
					this.life = 600;
				}
				dir=Direction.Stop;	
				noAttacked=true;
			}
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_J:
			if(doubleFireNum>0) {
				doubleFire();
				doubleFireNum--;
			}
			else fire();
			break;
			
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
			
		case KeyEvent.VK_A:
			bL=false;
			break;
		case KeyEvent.VK_W:
			bU=false;
			break;
		case KeyEvent.VK_D:
			bR=false;
			break;
		case KeyEvent.VK_S:
			bD=false;
			break;
		case KeyEvent.VK_M:
			//无敌次数只有3次
			if(hasNoAttackedNum<3) {
				setNoAttacked(true);
				hasNoAttackedNum++;
			}
			break;
		case KeyEvent.VK_Z:
			if(SuperMissileNum>0) {
				sFire();
				SuperMissileNum--;
			}
			break;
		case KeyEvent.VK_K:
			if(IceMissileNum>0) {
				IceFire();
				IceMissileNum--;
			}
			else if(SuperMissileNum>0) {
				sFire();
				SuperMissileNum--;
			}
			break;
		case KeyEvent.VK_L:
			lighteningMissileNum = 0;
			this.noAttacked=false;
			break;
		case KeyEvent.VK_SPACE:
			if(tc.level<5) {
				if(spaceNumber<(tc.level+1)*3) {
					spaceNumber++;
					superFire();
				}
				else fire();
			}
			else if(tc.level>=5&&tc.level<=7){
				if(spaceNumber<(tc.level+1)*5) {
					spaceNumber++;
					superFire();
				}
				else fire();
			}
			else {
				if(spaceNumber<(level+1)*20) {
					spaceNumber++;
					superFire();
				}
				else fire();
			}
			break;
		}
		locateDirection();
	}
	
	public int getSpaceNumber() {
		return spaceNumber;
	}

	public void setSpaceNumber(int spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public Missile fire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new Missile(x0,y0,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	
	public Missile deathFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new DeathMissile(x0,y0,good,ptDir,this.tc,tc.level);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile death2Fire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new DeathMissile2(x0,y0,good,ptDir,this.tc,tc.level);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile doubleDeathFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new DeathMissile(x0,y0,good,ptDir,this.tc,tc.level);
		Missile m1=new DeathMissile(x0,y0,good,ptDir,this.tc,tc.level);
		tc.missiles.add(m);
		tc.missiles.add(m1);
		return m;
	}
	public Missile doubleDeath2Fire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new DeathMissile2(x0,y0,good,ptDir,this.tc,tc.level);
		Missile m1=new DeathMissile2(x0,y0,good,ptDir,this.tc,tc.level);
		tc.missiles.add(m);
		tc.missiles.add(m1);
		return m;
	}
	public Missile lighteningFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new LighteningMissile(x0-3,y0-3,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile doubleFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x1=x,y1=y,x2=x,y2=y;
		if(good) {
			switch(ptDir) {
			case L:
				x1=x;
				y1=y-5;
				x2=x;
				y2=y+5;
				break;
			case LU:
				x1=x+3;
				y1=y-3;
				x2=x-3;
				y2=y+3;
				break;
			case U:
				x1=x+5;
				y1=y;
				x2=x-5;
				y2=y;
				break;
			case RU:
				x1=x+3;
				y1=y+3;
				x2=x-3;
				y2=y-3;
				break;
			case R:
				x1=x;
				y1=y-5;
				x2=x;
				y2=y+5;
				break;
			case RD:
				x1=x+3;
				y1=y-3;
				x2=x-3;
				y2=y+3;
				break;
			case D:
				x1=x+5;
				y1=y;
				x2=x-5;
				y2=y;
				break;
			case LD:
				x1=x+3;
				y1=y+3;
				x2=x-3;
				y2=y-3;
				break;
			}
		}
		if(queen) {
			switch(ptDir) {
			case L:
				x1=x;
				y1=y+20;
				x2=x;
				y2=y+10;
				break;
			case LU:
				x1=x+18;
				y1=y+12;
				x2=x+12;
				y2=y+18;
				break;
			case U:
				x1=x+20;
				y1=y;
				x2=x+10;
				y2=y;
				break;
			case RU:
				x1=x+18;
				y1=y+18;
				x2=x+12;
				y2=y+12;
				break;
			case R:
				x1=x;
				y1=y+10;
				x2=x;
				y2=y+20;
				break;
			case RD:
				x1=x+18;
				y1=y+12;
				x2=x+12;
				y2=y+18;
				break;
			case D:
				x1=x+20;
				y1=y;
				x2=x+10;
				y2=y;
				break;
			case LD:
				x1=x+18;
				y1=y+18;
				x2=x+12;
				y2=y+12;
				break;
			}
		}
		
		
		Missile m1=new Missile(x1,y1,good,ptDir,this.tc,tc.level);
		Missile m2=new Missile(x2,y2,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum+=2;
		}
		tc.missiles.add(m1);
		tc.missiles.add(m2);
		return m1;
	}
	
	public Missile sFire() {
		if(!live) return null;
		
		int x=this.x+Tank.WIDTH/2-SuperMissile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-SuperMissile.HEIGHT/2;
		
		Missile m=new SuperMissile(x,y,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum+=3;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile IceFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-IceMissile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-IceMissile.HEIGHT/2;
		Missile m=new IceMissile(x,y,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new Missile(x,y,good,dir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile deathFire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x0 = x;
		int y0 = y;
		if(queen) {
			x0 = x+15;
			y0 = y+15;
		}
		Missile m=new DeathMissile(x0,y0,good,dir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile doubleFire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x1=x,y1=y,x2=x,y2=y;
		
		if(queen) {
			switch(dir) {
			case L:
				x1=x;
				y1=y+20;
				x2=x;
				y2=y+10;
				break;
			case LU:
				x1=x+18;
				y1=y+12;
				x2=x+12;
				y2=y+18;
				break;
			case U:
				x1=x+20;
				y1=y;
				x2=x+10;
				y2=y;
				break;
			case RU:
				x1=x+18;
				y1=y+18;
				x2=x+12;
				y2=y+12;
				break;
			case R:
				x1=x;
				y1=y+10;
				x2=x;
				y2=y+20;
				break;
			case RD:
				x1=x+18;
				y1=y+12;
				x2=x+12;
				y2=y+18;
				break;
			case D:
				x1=x+20;
				y1=y;
				x2=x+10;
				y2=y;
				break;
			case LD:
				x1=x+18;
				y1=y+18;
				x2=x+12;
				y2=y+12;
				break;
			}
		}
		
		Missile m1=new Missile(x1,y1,good,dir,this.tc,tc.level);
		Missile m2=new Missile(x2,y2,good,dir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum+=2;
		}
		tc.missiles.add(m1);
		tc.missiles.add(m2);
		return m1;
	}
	public Missile sFire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new SuperMissile(x,y,good,dir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum+=3;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile IceFire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new IceMissile(x,y,good,dir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	
	
	public Rectangle getRech() {
		int Width = WIDTH;
		int Height = HEIGHT;
		if(queen) {
			Width = WIDTH+30;
			Height = HEIGHT+30;
		}
		return new Rectangle(x,y,Width,Height);
	}
	public Rectangle getRech1() {
		return new Rectangle(x-1,y-1,WIDTH+2,HEIGHT+2);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}
	
	/**
	 * 判断是否撞上了墙
	 * @param w 墙
	 * @return 撞上了返回true，否则返回falsed
	 */
	
	public boolean collidesWithWall(Wall w) {
		if(live&&this.getRech().intersects(w.getRech())) {
			this.stay();
			changeDirect(w);
			return true;
		}
		return false;
	}
	
	
	public boolean collidesWithTanks(List<Tank> tanks) {
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			if(this!=t) {
				if(live&&t.isLive()&&this.getRech().intersects(t.getRech())) {
					this.stay();
					t.stay();
					//t.changeDirect(this);
					t.changePtDirect(this);
				
					
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void superFire() {
		Direction[] dirs=Direction.values();
		for(int i=0;i<8;i++) {
			fire(dirs[i]);
		}
	}
	public void superDeathFire() {
		Direction[] dirs=Direction.values();
		for(int i=0;i<8;i++) {
			deathFire(dirs[i]);
		}
	}
	
	public void superDoubleFire() {
		Direction[] dirs=Direction.values();
		for(int i=0;i<8;i++) {
			doubleFire(dirs[i]);
		}
	}
	
	private class BloodBar {
		public void draw(Graphics g) {
			if(good) {
				String s=g.getFont().getName();
				int style=g.getFont().getStyle();
				int size=g.getFont().getSize();
				Color c=g.getColor();
				
				
				g.setColor(Color.pink);
				if(tc.level == 9) {
					g.drawRect(135, 620, 600, 20);
				}
				else {
					g.drawRect(135, 620, 100+tc.level*20, 20);
				}
				//int w1=(100+tc.level*20)*life/(100+tc.level*20);
				g.fillRect(135, 620, life, 20);
				g.setColor(c);
				g.setColor(Color.red);
				g.drawRect(x, y-10, WIDTH, 8);
				//g.drawRect(x-tc.level/2*(WIDTH/100), y-10, WIDTH+tc.level*(WIDTH/100), 8);
				//int w=(WIDTH+tc.level*(WIDTH/100))*life/(100+tc.level*10);
				//g.fillRect(x-tc.level/2*(WIDTH/100), y-10, w, 8);
				if(tc.level == 9) {
					g.fillRect(x, y-10, life*WIDTH/(600), 8);
				}
				else {
					g.fillRect(x, y-10, life*WIDTH/(100+tc.level*20), 8);
				}
				g.setColor(c);
				
				g.setFont(new Font(s,Font.BOLD,20));
				g.setColor(Color.red);
				if(tc.level == 9) {
					g.drawString(getLife()+"/"+(600), 250, 635);
				}
				else{
					g.drawString(getLife()+"/"+(100+tc.level*20), 250, 635);
				}
				g.setColor(c);
				g.setFont(new Font(s,style,20));
			}
			else {
				Color c=g.getColor();
				int w=WIDTH*(life%101)/100;
				
				
				if(queen) {
					String s=g.getFont().getName();
					int style=g.getFont().getStyle();
					int size=g.getFont().getSize();
					
					g.setColor(Color.black);
					g.drawRect(270, 35, 700, 35);
					//int w1=(100+tc.level*20)*life/(100+tc.level*20);
					g.setColor(Color.gray);
					g.fillRect(270, 37,700*life/5000, 33);
					g.setColor(Color.red);
					g.drawRect(x, y-10, 60, 8);
					//g.drawRect(x-tc.level/2*(WIDTH/100), y-10, WIDTH+tc.level*(WIDTH/100), 8);
					//int w=(WIDTH+tc.level*(WIDTH/100))*life/(100+tc.level*10);
					//g.fillRect(x-tc.level/2*(WIDTH/100), y-10, w, 8);
					g.fillRect(x, y-10, life*60/5000, 8);
					g.setColor(c);
					
					g.setFont(new Font(s,Font.BOLD,20));
					g.setColor(Color.blue);
					g.drawString(getLife()+"/"+5000, 500, 70);
					g.setColor(c);
					g.setFont(new Font(s,style,20));
				}
				
				if(!queen) {
					g.drawRect(x, y-10, WIDTH, 8);
				}
				//敌人血条
				if(!queen) {
					switch(level) {
					case 9:
						if(life>900) {
							g.setColor(new Color(0,0,0));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(255,255,255));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 8:
						if(life>900&&life<=1000) {
							g.setColor(new Color(0,0,0));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(255,255,255));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>800&&life<=900) {
							g.setColor(new Color(255,255,255));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(180,10,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 7:
						if(life>800&&life<=900) {
							g.setColor(new Color(255,255,255));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(180,10,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>700&&life<=800) {
							g.setColor(new Color(50,50,50));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(180,10,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 6:
						if(life>700&&life<=800) {
							g.setColor(new Color(50,50,50));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(180,10,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>600&&life<=700) {
							g.setColor(new Color(180,10,10));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(10,230,240));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 5:
						if(life>600&&life<=700) {
							g.setColor(new Color(180,10,10));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(10,230,240));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>500&&life<=600) {
							g.setColor(new Color(10,230,240));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,80,40));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 4:
						if(life>500&&life<=600) {
							g.setColor(new Color(10,230,240));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,80,40));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>400&&life<=500) {
							g.setColor(new Color(0,80,40));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(255,100,100));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 3:
						if(life>400&&life<=500) {
							g.setColor(new Color(0,80,40));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(255,100,100));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>300&&life<=400) {
							g.setColor(new Color(255,100,100));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,0,100));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 2:
						if(life>300&&life<=400) {
							g.setColor(new Color(255,100,100));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,0,100));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>200&&life<=300) {
							g.setColor(new Color(0,0,100));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,255,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 1:
						if(life>200&&life<=300) {
							g.setColor(new Color(0,0,100));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(0,255,10));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life>100&&life<=200) {
							g.setColor(new Color(0,255,10));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(100,0,0));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
					case 0:
						if(life>100&&life<=200) {
							g.setColor(new Color(0,255,10));
							g.fillRect(x, y-10, w, 8);
							g.setColor(new Color(100,0,0));
							g.fillRect(x+w, y-10, WIDTH-w, 8);
						}
						if(life<=100) {
							g.setColor(new Color(100,0,0));
							g.fillRect(x, y-10, w, 8);
							break;
						}
										
					}
					
				}
				
				g.setColor(c);
			}
			
		}
	}
	
	public boolean eat(Blood b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			if(life+20*tc.level+20<=100+tc.level*20) {
				if(level!=9) {
					this.life+=tc.level*20+20;
				}
				else {
					this.life = 600;
				}
			}
			else {
				if(tc.level == 9) {
					this.life = 600;
				}
				else {
					this.life = 100+tc.level*20;
				}
				
			}
			b.setLive(false);
			return true;
		}
		return false;
	}
	public boolean eat(RelifeBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.reLife --;
			b.setLive(false);
			return true;
		}
		return false;
	}
	public boolean eat(SuperMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.SuperMissileNum = 5+tc.smb.eatenNum;
			this.IceMissileNum = 0;
			b.setLive(false);
			SpeedUp = false;
			if(tc.smb.eatenNum<15) {
				tc.smb.eatenNum++;
			}
			if(tc.smb.eatenNum==20&&tc.smb.rlbRelife1) {
				tc.smb.rlbRelife1 = false;
				tc.rlb.setLive(true);
			}
			if(tc.smb.eatenNum==25&&tc.smb.rlbRelife2) {
				tc.smb.rlbRelife2 = false;//限制命块产生次数
				tc.rlb.setLive(true);
			}
			return true;
		}
		return false;
	}
	public boolean eat(LighteningMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			b.lighteningChipNum++;
			if(b.lighteningChipNum == 3) {
				this.lighteningMissileNum = 50+2*tc.lmb.eatenNum;
				this.SuperMissileNum = 0;
				this.IceMissileNum = 0;
				SpeedUp = false;
				tc.lmb.eatenNum ++;
				/*
				if(tc.lmb.eatenNum==5&&tc.lmb.rlbRelife1) {
					tc.lmb.rlbRelife1 = false;
					tc.rlb.setLive(true);
				}
				if(tc.lmb.eatenNum==10&&tc.lmb.rlbRelife2) {
					tc.lmb.rlbRelife2 = false;
					tc.rlb.setLive(true);
				}
				*/
				if(tc.lmb.eatenNum==15&&tc.lmb.rlbRelife3) {
					tc.lmb.rlbRelife3 = false;
					tc.rlb.setLive(true);
				}
			}
			if(b.lighteningChipNum == 4) {
				b.lighteningChipNum = 1;
			}
			b.setLive(false);
			
			
			return true;
		}
		return false;
	}
	public boolean eat(SpeedUpBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			SpeedUp=true;
			SuperMissileNum = 0;
			this.IceMissileNum = 0;
			b.setLive(false);
			if(tc.sub.eatenNum<30) {
				tc.sub.eatenNum ++;
			}
			if(tc.sub.eatenNum==35&&tc.sub.rlbRelife1) {
				tc.sub.rlbRelife1 = false;
				tc.rlb.setLive(true);
			}
			/*
			if(tc.sub.eatenNum==30&&tc.sub.rlbRelife2) {
				tc.sub.rlbRelife2 = false;//限制命块产生次数
				tc.rlb.setLive(true);
			}
			*/
			return true;
		}
		return false;
	}
	public boolean eat(NoAttackedBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			noAttacked=true;
			SuperMissileNum-=5;
			this.IceMissileNum-=5;
			SpeedUp=false;
			b.setLive(false);
			return true;
		}
		return false;
	}
	public boolean eat(IceMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.IceMissileNum=10+tc.imb.eatenNum;
			this.SuperMissileNum=0;
			SpeedUp=false;
			b.setLive(false);
			SpeedUp=false;
			if(tc.imb.eatenNum<30) {
				tc.imb.eatenNum ++;
			}
			if(tc.imb.eatenNum==35&&tc.imb.rlbRelife1) {
				tc.imb.rlbRelife1 = false;
				tc.rlb.setLive(true);
			}
			/*
			if(tc.imb.eatenNum==30&&tc.imb.rlbRelife2) {
				tc.imb.rlbRelife2 = false;//限制命块产生次数
				tc.rlb.setLive(true);
			}
			*/
			return true;
		}
		return false;
	}
	public boolean eat(DoubleMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.doubleFireNum = 20+tc.dmb.eatenNum;
			this.IceMissileNum -= 5;
			this.SuperMissileNum -= 5;
			b.setLive(false);
			if(tc.dmb.eatenNum<30) {
				tc.dmb.eatenNum ++;
			}
			if(tc.dmb.eatenNum==35&&tc.dmb.rlbRelife1) {
				tc.dmb.rlbRelife1 = false;
				tc.rlb.setLive(true);
			}
			/*
			if(tc.dmb.eatenNum==30&&tc.dmb.rlbRelife2) {
				tc.imb.rlbRelife2 = false;//限制命块产生次数
				tc.rlb.setLive(true);
			}
			*/
			return true;
		}
		return false;
	}

	public int getLevel() {
		return level;
	}

}
