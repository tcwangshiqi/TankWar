package Test.Instruction;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import NetTankWar.Dir;

public class Tank {
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	private int enterNum=0;
	
	public boolean ptTrack = false;
	
	public boolean king = false;
	
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
	
	private int x,y;
	
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
	
	private int life=80;
	
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
		if(!this.good) {
			if(ptTrack) {
				this.life = 150 + level*100;
			}
			else {
				this.life = 100 + level*100;
			}
			
		}
		this.ptTrack = ptTrack;
		this.king = king;
		
	}
	public void draw(Graphics g) {
		if(!live) {
			if(!good) tc.tanks.remove(this);
			
			return;
		}
		
		
		Color c=g.getColor();
		
		
		if(noAttacked&&good&&noAttackedNum<=20) {
			g.setColor(Color.pink);
			g.fillOval(x-4, y-4, WIDTH+8, HEIGHT+8);
			g.setColor(c);
		}
		if(noAttackedNum>20) {
			setNoAttackedNum(0);
			setNoAttacked(false);
		}
		if(IceMissileNum>0&&good) {
			g.setColor(Color.blue);
			g.fillOval(x-2, y-2, WIDTH+4, HEIGHT+4);
			g.setColor(c);
		}
		if(icedDirectNum>0) {
			g.setColor(new Color(90,255,180));
			g.fillOval(x-3, y-3, WIDTH+6, HEIGHT+6);
			g.setColor(c);
		}
		if(SuperMissileNum>0&&good) {
			g.setColor(Color.gray);
			g.fillOval(x-3, y-3, WIDTH+6, HEIGHT+6);
			g.setColor(c);
		}
		if(lighteningMissileNum>0&&good) {
			g.setColor(Color.yellow);
			g.fillOval(x-2, y-2, WIDTH+4, HEIGHT+4);
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
		
		if(good) g.setColor(Color.red);
		else {
			
			if(tc.level<=5) g.setColor(Color.white);
			else if(tc.level>1&&tc.level<=2) g.setColor(Color.yellow);
			else if(tc.level>2&&tc.level<=4) g.setColor(Color.orange);
			else if(tc.level>4&&tc.level<=6) g.setColor(Color.MAGENTA);
			else if(tc.level>6&&tc.level<=8) g.setColor(Color.BLUE);
			else if(tc.level==9) g.setColor(Color.darkGray);
			
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
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
		/*
	//图片版	
	  {
		if(good) {
			int x = this.x ;
			int y = this.y;			
			switch(ptDir) {
			case L:
				g.drawImage(imgs[1], x, y, null);
				break;
			case LU:
				g.drawImage(imgs[3], x, y, null);
				break;
			case U:
				g.drawImage(imgs[7], x, y, null);
				break;
			case RU:
				g.drawImage(imgs[6], x, y, null);
				break;
			case R:
				g.drawImage(imgs[4], x, y, null);
				break;
			case RD:
				g.drawImage(imgs[5], x, y, null);
				break;
			case D:
				g.drawImage(imgs[0], x, y, null);
				break;
			case LD:
				g.drawImage(imgs[2], x, y, null);
				break;
			}
		}
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
	
	public void stay() {
		x=this.oldX;
		y=this.oldY;
	}
	
	public void move() {
		this.oldX=x;
		this.oldY=y;
		if(SpeedUp) {
			this.Speed=XSPEED+2.5+tc.sub.eatenNum*0.2;
		}
		if(SuperMissileNum>0) {
			this.Speed=XSPEED-1.5-0.1*tc.smb.eatenNum;
		}
		if(icedDirectNum>0) {
			this.dir = Direction.Stop;
			icedDirectNum--;
		}
		else Speed = XSPEED;
		double XSpeed =XSPEED;
		double YSpeed =XSPEED;
		if(!good) {
			XSpeed=Speed+0.5*tc.level;
			YSpeed=Speed+0.5*tc.level;
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
			x+=YSpeed;
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
		case Stop:
			break;
		}
		
		if(this.dir!=Direction.Stop) {
			ptDir=dir;
		}
		
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		//小兵
		if(!good&&!ptTrack) {
			Direction[] dirs=Direction.values();
			if(step==0) {
				step=r.nextInt(12)+3;

				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			
			step--;
			if(r.nextInt(40)>(38-0.5*tc.level)) this.fire();
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
			
			if(r.nextInt(40)>(37.5-0.6*tc.level)) this.fire();
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
			
			if(r.nextInt(40)>(37.5-0.6*tc.level)) this.fire();
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
				if(tc.testStep==15) {
					tc.testStep++;
				}
			}
			
			break;
		case KeyEvent.VK_ENTER:
			if(enterNum<30+tc.level*10) {
				superFire();
				enterNum++;
				if(tc.testStep==2) {
					tc.testStep++;
				}
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
				if(tc.testStep==5) {
					tc.testStep++;
				}
			}
			else {
				fire();
				if(tc.testStep==0) {
					tc.testStep++;
				}
			}
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
				if(tc.testStep==3) {
					tc.testStep++;
				}
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
				if(tc.testStep==11) {
					tc.testStep++;
				}
			}
			else if(SuperMissileNum>0) {
				sFire();
				SuperMissileNum--;
				if(tc.testStep==9) {
					tc.testStep++;
				}
			}
			break;
		case KeyEvent.VK_L:
			lighteningMissileNum = 0;
			break;
		case KeyEvent.VK_SPACE:
			if(tc.level<5) {
				if(spaceNumber<(tc.level+1)*3) {
					spaceNumber++;
					superFire();
					if(tc.testStep==1) {
						tc.testStep++;
					}
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
		Missile m=new Missile(x,y,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum++;
		}
		tc.missiles.add(m);
		return m;
	}
	public Missile lighteningFire() {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new LighteningMissile(x-3,y-3,good,ptDir,this.tc,tc.level);
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
	public Missile doubleFire(Direction dir) {
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		int x1=x,y1=y,x2=x,y2=y;
		
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
		
		Missile m1=new Missile(x1,y1,good,ptDir,this.tc,tc.level);
		Missile m2=new Missile(x2,y2,good,ptDir,this.tc,tc.level);
		if(good&&noAttacked) {
			noAttackedNum+=2;
		}
		tc.missiles.add(m1);
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
		return new Rectangle(x,y,WIDTH,HEIGHT);
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
	
	private class BloodBar {
		public void draw(Graphics g) {
			if(good) {
				String s=g.getFont().getName();
				int style=g.getFont().getStyle();
				int size=g.getFont().getSize();
				Color c=g.getColor();
				
				
				g.setColor(Color.pink);
				g.drawRect(135, 620, 100+tc.level*20, 20);
				//int w1=(100+tc.level*20)*life/(100+tc.level*20);
				g.fillRect(135, 620, life, 20);
				g.setColor(c);
				g.setColor(Color.red);
				g.drawRect(x, y-10, WIDTH, 8);
				//g.drawRect(x-tc.level/2*(WIDTH/100), y-10, WIDTH+tc.level*(WIDTH/100), 8);
				//int w=(WIDTH+tc.level*(WIDTH/100))*life/(100+tc.level*10);
				//g.fillRect(x-tc.level/2*(WIDTH/100), y-10, w, 8);
				g.fillRect(x, y-10, life*WIDTH/(100+tc.level*20), 8);
				g.setColor(c);
				
				g.setFont(new Font(s,Font.BOLD,20));
				g.setColor(Color.red);
				g.drawString(getLife()+"/"+(100+tc.level*20), 250, 635);
				g.setColor(c);
				g.setFont(new Font(s,style,20));
			}
			else {
				Color c=g.getColor();
				int w=WIDTH*(life%101)/100;
				g.drawRect(x, y-10, WIDTH, 8);
				//敌人血条
				switch(level) {
				case 9:
					if(life>900&&life<=1000) {
						g.setColor(new Color(0,0,0));
						g.fillRect(x, y-10, w, 8);
						g.setColor(new Color(255,255,255));
						g.fillRect(x+w, y-10, WIDTH-w, 8);
					}
				case 8:
					if(life>800&&life<=900) {
						g.setColor(new Color(255,255,255));
						g.fillRect(x, y-10, w, 8);
						g.setColor(new Color(180,10,10));
						g.fillRect(x+w, y-10, WIDTH-w, 8);
					}
				case 7:
					if(life>700&&life<=800) {
						g.setColor(new Color(50,50,50));
						g.fillRect(x, y-10, w, 8);
						g.setColor(new Color(180,10,10));
						g.fillRect(x+w, y-10, WIDTH-w, 8);
					}
				case 6:
					if(life>600&&life<=700) {
						g.setColor(new Color(180,10,10));
						g.fillRect(x, y-10, w, 8);
						g.setColor(new Color(10,230,240));
						g.fillRect(x+w, y-10, WIDTH-w, 8);
					}
				case 5:
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
				
				g.setColor(c);
			}
			
		}
	}
	
	public boolean eat(Blood b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			if(life+20*tc.level+20<=100+tc.level*20) {
				this.life+=tc.level*20+20;
				if(tc.testStep==7) {
					tc.testStep++;
				}
			}
			else {
				this.life = 100+tc.level*20;
				if(tc.testStep==7) {
					tc.testStep++;
				}
			}
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	public boolean eat(SuperMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.SuperMissileNum = 10+tc.smb.eatenNum;
			this.IceMissileNum = 0;
			b.setLive(false);
			SpeedUp = false;
			tc.smb.eatenNum++;
			if(tc.testStep==8) {
				tc.testStep++;
			}
			return true;
		}
		return false;
	}
	public boolean eat(LighteningMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			b.lighteningChipNum++;
			if(tc.testStep==12||tc.testStep==13||tc.testStep==14) {
				tc.testStep++;
			}
			if(b.lighteningChipNum == 3) {
				this.lighteningMissileNum = 50+2*tc.lmb.eatenNum;
				this.SuperMissileNum = 0;
				this.IceMissileNum = 0;
				SpeedUp = false;
				tc.lmb.eatenNum ++;
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
			tc.sub.eatenNum ++;
			if(tc.testStep==6) {
				tc.testStep++;
			}
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
			tc.imb.eatenNum ++;
			if(tc.testStep==10) {
				tc.testStep++;
			}
			return true;
		}
		return false;
	}
	public boolean eat(DoubleMissileBar b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.doubleFireNum = 20+tc.dmb.eatenNum;
			this.IceMissileNum = 0;
			this.SuperMissileNum = 0;
			SpeedUp=false;
			b.setLive(false);
			SpeedUp=false;
			tc.dmb.eatenNum ++;
			if(tc.testStep==4) {
				tc.testStep++;
			}
			return true;
		}
		return false;
	}

	public int getLevel() {
		return level;
	}

}
