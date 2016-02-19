package com.wangshiqi.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * 这是坦克大战的主窗口
 * @author wangshiqihaha
 *
 */

public class TankClient extends Frame {

	/**
	 * 整个窗口的宽度和高度
	 */
	public static final int GAME_WIDTH=1000,GAME_HEIGHT=700;
	
	int level = 0;
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	Image img0 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (1).gif"));
	Image img1 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (2).gif"));
	Image img2 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (3).gif"));
	Image img3 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (4).gif"));
	Image img4 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (5).gif"));
	Image img5 = tk.getImage(TankClient.class.getClassLoader().getResource("Images/ba (6).gif"));
	private static Random tcr=new Random();
	
	Tank myTank=new Tank(450,450,true,Direction.Stop,this);
	
	Wall wBa11 = new Wall(200,112,47,460,this);
	Wall wBa12=new Wall(432,320,397,40,this);
	//Wall w1 = wBa11;
	//Wall w2 = wBa12;
	Wall wBa21 = new Wall(372,150,48,460,this);
	Wall wBa22 = new Wall(0,0,0,0,this);
	Wall wBa31=new Wall(194,132,606,40,this);
	Wall wBa32=new Wall(325,500,375,38,this);
	Wall wBa41=new Wall(13,180,480,40,this);
	Wall wBa42=new Wall(586,363,375,38,this);
	Wall wBa51=new Wall(153,320,680,48,this);
	Wall wBa52=new Wall(0,0,0,0,this);
	Wall wBa61=new Wall(238,102,65,463,this);
	Wall wBa62=new Wall(706,162,65,350,this);
	Wall w1 = wBa11;
	Wall w2 = wBa12;
	

	List<Explode> explodes=new ArrayList<Explode>();
	List<Missile> missiles=new ArrayList<Missile>();
	List<Tank> tanks=new ArrayList<Tank>();
	
	Image offScreenImage=null;
	
	Blood b=new Blood();
	SuperMissileBar smb = new SuperMissileBar();
	SpeedUpBar sub = new SpeedUpBar();
	NoAttackedBar nab = new NoAttackedBar();
	IceMissileBar imb =new IceMissileBar();
	DoubleMissileBar dmb = new DoubleMissileBar();
	LighteningMissileBar lmb = new LighteningMissileBar();
	RelifeBar rlb = new RelifeBar();
	
	
	public void paint(Graphics g) {
		
		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		Color c1=g.getColor();
		//中间
		g.setFont(new Font(s,Font.BOLD,70));
		g.setColor(new Color(178,120,60));
		g.drawString("         level   "+(level+1), 200, 100);
		if(level==1) {
			g.drawString("          Boss", 200, 200);
		}
		if(level==4) {
			g.drawString("          King", 200, 200);
		}
		if(level==9) {
			g.drawString("          Queen", 200, 200);
		}
		g.setFont(new Font(s,style,size));
		g.setColor(c1);
		
		g.setFont(new Font(s,style,18));
		
		g.drawString("myTank's blood:", 5, 635);
		g.setFont(new Font(s,Font.BOLD,20));
		
		g.setFont(new Font(s,style,20));
		g.drawString("fire:--/--", 10, 600);
		if(level<5) {
			g.drawString("super fire:"+myTank.getSpaceNumber()+"/"+(1+level)*3, 130,600);
		}
		else if(level<7) {
			g.drawString("super fire:"+myTank.getSpaceNumber()+"/"+(level+1)*5, 130,600);
		}
		else if(level<10) {
			g.drawString("super fire:"+myTank.getSpaceNumber()+"/"+(level+1)*20, 130,600);
		}
		
		g.drawString("super super fire:"+myTank.getEnterNum()+"/"+(30+level*10), 320, 600);
		
		g.drawString("no attaccked left:"+myTank.getHasNoAttackedNum()+"/3", 570, 600);
		
		if(myTank.isNoAttacked()) {
			g.drawString("No Attacked num:"+myTank.getNoAttackedNum()+"/20", 570, 635);
		}
		else {
			g.drawString("No Attacked num:"+myTank.getNoAttackedNum()+"/0", 570, 635);
		}
		
		if(myTank.SuperMissileNum>0) {
			g.drawString("SuperMissileNum:"+myTank.SuperMissileNum+"/"+(5+smb.eatenNum-1), 760, 400);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("Super!", 780, 450);
			g.setColor(c);
			g.setFont(f);
		}
		if(myTank.doubleFireNum>0) {
			g.drawString("DoubleMissileNum:"+myTank.doubleFireNum+"/"+(20+dmb.eatenNum-1), 760, 300);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("Double", 780, 350);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(myTank.IceMissileNum>0) {
			g.drawString("IceMissileNum:"+myTank.IceMissileNum+"/"+(10+imb.eatenNum-1), 760, 400);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("ICE!", 780, 450);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(lmb.lighteningChipNum==1||lmb.lighteningChipNum==2) {
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("Lightening Chip Num:"+lmb.lighteningChipNum+"/3", 760, 550);
			g.setColor(c);
		}
		if(lmb.lighteningChipNum==3&&myTank.lighteningMissileNum>0) {
			g.drawString("LighteningMissileNum:"+myTank.lighteningMissileNum+"/"+(50+2*lmb.eatenNum-1), 740, 400);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow); 
			g.drawString("Lightening!", 780, 450);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(myTank.SpeedUp) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("Speed up!", 780,450);
			g.setColor(c);
		}
		
		if(myTank.isNoAttacked()) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.pink);
			g.drawString("No Attacked!", 760, 500);
			g.setColor(c);
		}

		g.setFont(new Font(s,style,size));
		//life
		g.setColor(Color.yellow);
		g.setFont(new Font(s,Font.BOLD,25));
		g.drawString("Life:", 15, 70);
		g.setFont(new Font(s,style,25));
		g.setColor(Color.red);
		g.drawString("Life:", 15, 70);
		g.setColor(Color.red);
		if(myTank.getReLife()<=4&&myTank.isLive()) {
			for(int i = 0;i<5-myTank.getReLife();i++) {
				g.fillOval(70+40*i, 50, 20, 20);
				g.setColor(Color.yellow);
				g.fillOval(70+40*i, 50, 5, 5);
				g.fillOval(70+40*i+15, 50, 5, 5);
				g.setColor(Color.red);
			}
		}
		
		
		g.setColor(c1);
		g.setFont(new Font(s,style,size));
		
		//win
		if(level==10) {
			tanks.removeAll(tanks);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawString("you win,the end!", 300, 350);
			g.setColor(c);
			g.setFont(f);
		}
		//Game Over
		if(myTank.getReLife()==4 && myTank.isLive()==false) {//总共五条命
			myTank.setLive(false);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.white);
			g.drawString("Game over", 270, 350);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(tanks.size()<=0&&level<=9) {
			myTank.x = 450;
			myTank.y = 450;
			myTank.setSpaceNumber(0);
			myTank.setNoAttacked(true);
			myTank.setNoAttackedNum(0);
			
			level++;
			if(level == 0||level == 7) {
				w1 = wBa11;
				w2 = wBa12;
			}
			if(level == 1||level == 4) {
				w1 = wBa21;
				w2 = wBa22;
			}
			if(level == 2||level == 3) {
				w1 = wBa31;
				w2 = wBa32;
			}
			if(level == 8) {
				w1 = wBa41;
				w2 = wBa42;
			}
			if(level == 5||level == 6) {
				w1 = wBa61;
				w2 = wBa62;
			}
			if(level == 9) {
				w1 = wBa51;
				w2 = wBa52;
			}
			b.setLive(true);
			smb.setLive(false);//防止smb一直消失或看不见
			sub.setLive(false);
			imb.setLive(false);
			dmb.setLive(false);
			lmb.setLive(false);
			if(level>7) {
				rlb.setLive(true);
			}
			if(level%2==0||level ==9) nab.setLive(true);
			myTank.setLife(myTank.getLife()+20);
			if(level == 9) {
				myTank.setLife(600);
			}
			
			//升级时tank的初始化
			if(level==9) {
				boolean queen = true;
				tanks.add(new Tank(100,100,false,Direction.D,this,false,false,queen));
			}
			if(level==4) {
				boolean ptTrack = false;
				boolean king = true;
				tanks.add(new Tank(100,100,false,Direction.D,this,ptTrack,king));
				tanks.add(new Tank(900,700,false,Direction.D,this,true,false));
			}
			if(level==1) {
				boolean ptTrack = true;
				boolean king = false;
				tanks.add(new Tank(100,100,false,Direction.D,this,ptTrack,king));
				tanks.add(new Tank(900,700,false,Direction.D,this,false,false));
				tanks.add(new Tank(100,700,false,Direction.D,this,false,false));
			}
			if(level!=4&&level!=1&&level<8) {
				for(int i=0;i<5;i++) {
					int x=tcr.nextInt(140)+160*i;
					int y=tcr.nextInt(250)+50;
					while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
						x=tcr.nextInt(140)+160*i;
						y=tcr.nextInt(250)+50;
					}
					boolean ptTrack = false;
					boolean king = false;
					if(level==2) {
						if(i==1||i==2) {
							x = 100+130*i;
							y = 300;
							ptTrack=true;
						}
						else if(i==3||i==0) {
							x = 50;
							y = 100*i;
						}
						else if(i == 4) {
							x = 900;
							y = 500;
						}
					}
					
					if(level==3) {
						if(i==4) {
							ptTrack=true;
							x=900;
							y=100;
						}
						if(i==1) {
							ptTrack=true;
							x=100;
							y=100;
						}
						else if(i==2) {
							ptTrack=true;
							x=900;
							y=650;
						}
						else if(i==3) {
							ptTrack=true;
							x=100;
							y=650;
						}
					}
					if(level == 5) {
						if(i == 0) {
							x = 30;
							y = 100;
						}
						else if(i == 1) {
							x = 30;
							y = 200;
						}
						else if(i == 2) {
							x = 900;
							y = 150;
						}
						else if(i == 3) {
							x = 900;
							y = 250;
						}
						else if(i == 4) {
							x = 900;
							y = 350;
						}
					}
					if(level == 6) {
						if(i == 0) {
							x = 30;
							y = 100;
						}
						else if(i == 1) {
							x = 30;
							y = 200;
						}
						else if(i == 2) {
							x = 30;
							y = 350;
						}
						else if(i == 3) {
							x = 500;
							y = 550;
						}
						else if(i == 4) {
							x = 500;
							y = 450;
						}
					}
					if(level == 7) {
						if(i == 0) {
							x = 30;
							y = 100;
						}
						else if(i == 1) {
							x = 300;
							y = 200;
						}
						else if(i == 2) {
							x = 500;
							y = 150;
						}
						else if(i == 3) {
							x = 500;
							y = 600;
						}
						else if(i == 4) {
							x = 700;
							y = 150;
						}
					}
					tanks.add(new Tank(x,y,false,Direction.D,this,ptTrack));
				}
			}
			if(level>4&&level<9) {
				for(int i=5;i<level+2;i++) {
					int x=tcr.nextInt(140)+160*(i-5);
					int y=tcr.nextInt(250)+300;
					while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
						x=tcr.nextInt(140)+160*(i-5);
						y=tcr.nextInt(250)+300;
					}
					boolean ptTrack = false;
					boolean king = false;
					if(level == 5) {
						if(i==5) {
							x = 100;
							y = 650;
							ptTrack=true;
						}
						else if(i == 6) {
							x = 900;
							y = 650;
							ptTrack=true;
						}
					}
					if(level==6) {
						if(i==5) {
							ptTrack = true;
							x = 100;
							y = 650;
						}
						if(i==6) {
							king = true;
							x = 600;
							y = 650;
						}
						if(i == 7) {
							ptTrack = true;
							x = 900;
							y = 100;
						}
					}
					if(level==7) {
						if(i==7) {
							ptTrack=true;
							x = 100;
							y = 650;
						}
						if(i==9) {
							ptTrack=true;
							x = 900;
							y = 550; 
						}
						if(i==6) {
							king=true;
							x = 900;
							y = 100;
						}
						if(i==8) {
							king=true;
							x = 900;
							y = 200;
						}
						
					}
					if(level==8) {
						if(i==5||i==6||i==7) {
							king=true;
							x = 100*(i-3);
							y = 600;
						}
						if(i == 8||i == 9) {
							king=true;
							x = 100*(i-3);
							y = 100;
						}
					}
					
					tanks.add(new Tank(x,y,false,Direction.D,this,ptTrack,king));
				}
				
			}
			
			
		}
		
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
			m.hitMissilses( missiles);
			
			//if(!m.isLive()) missiles.remove(m);
			//else m.draw(g);
		}
		
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		myTank.collidesWithTanks(tanks);
		
		myTank.draw(g);
		myTank.eat(b);
		myTank.eat(smb);
		myTank.eat(sub);
		myTank.eat(nab);
		myTank.eat(imb);
		myTank.eat(dmb);
		myTank.eat(lmb);
		myTank.eat(rlb);
		//画墙
		w1.draw(g);
		w2.draw(g);
		//画属性块
		b.draw(g);
		smb.draw(g);
		sub.draw(g);
		nab.draw(g);
		imb.draw(g);
		dmb.draw(g);
		lmb.draw(g);
		rlb.draw(g);
		
		smb.hitWall(w1);
		smb.hitWall(w2);
		sub.hitWall(w1);
		sub.hitWall(w2);
		nab.hitWall(w1);
		nab.hitWall(w2);
		imb.hitWall(w1);
		imb.hitWall(w2);
		dmb.hitWall(w1);
		dmb.hitWall(w2);
		lmb.hitWall(w1);
		lmb.hitWall(w2);
		if(rlb.isLive()) {
			if(rlb.hitWall(w1)||rlb.hitWall(w2)) {
				rlb.setLive(true);
			}
		}
		
		//被吃后换个地方显示 
//smb的限制
		if(smb.eatenNum<3&&level==0) {
			if(!smb.isLive()) {
				smb.setLive(true);
		    }
		}
		else if(smb.eatenNum<10&&level==1) {
			if(!smb.isLive()) {
				smb.setLive(true);
		    }
		}
		else if(smb.eatenNum<20&&level==2) {
			if(!smb.isLive()) {
				smb.setLive(true);
		    }
		}
		else if(level>=3) {
			if(!smb.isLive()) {
				smb.setLive(true);
		    }
		}
//sub的限制
		if(sub.eatenNum<3&&level==0) {
			if(!sub.isLive()) {
				sub.setLive(true);
		    }
		}
		else if(smb.eatenNum<10&&level==1) {
			if(!sub.isLive()) {
				sub.setLive(true);
		    }
		}
		else if(sub.eatenNum<20&&level==2) {
			if(!sub.isLive()) {
				sub.setLive(true);
		    }
		}
		else if(level>=3) {
			if(!sub.isLive()) {
				sub.setLive(true);
		    }
		}
	//imb的限制	
		if(imb.eatenNum<3&&level==0) {
			if(!imb.isLive()) {
				imb.setLive(true);
		    }
		}
		else if(imb.eatenNum<10&&level==1) {
			if(!imb.isLive()) {
				imb.setLive(true);
		    }
		}
		else if(imb.eatenNum<20&&level==2) {
			if(!imb.isLive()) {
				imb.setLive(true);
		    }
		}
		else if(level>=3) {
			if(!imb.isLive()) {
				imb.setLive(true);
		    }
		}
		
//dmb的限制
		if(dmb.eatenNum<3&&level==0) {
			if(!dmb.isLive()) {
				dmb.setLive(true);
		    }
		}
		else if(dmb.eatenNum<10&&level==1) {
			if(!dmb.isLive()) {
				dmb.setLive(true);
		    }
		}
		else if(dmb.eatenNum<20&&level==2) {
			if(!dmb.isLive()) {
				dmb.setLive(true);
		    }
		}
		else if(level>=3) {
			if(!dmb.isLive()) {
				dmb.setLive(true);
		    }
		}
//lmb的限制
		if(lmb.eatenNum<3&&level==0) {
			if(!lmb.isLive()) {
				lmb.setLive(true);
		    }
		}
		else if(lmb.eatenNum<10&&level==1) {
			if(!lmb.isLive()) {
				lmb.setLive(true);
		    }
		}
		else if(lmb.eatenNum<20&&level==2) {
			if(!lmb.isLive()) {
				lmb.setLive(true);
		    }
		}
		else if(level>=3) {
			if(!lmb.isLive()) {
				lmb.setLive(true);
		    }
		}
//rlb的限制 在eat里
	}

	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Image img=null;
		switch(level) {
		case 0:
			img = img0;
			break;
		case 1:
			img = img1;
			break;
		case 2:
			img = img2;
			break;
		case 3:
			img = img2;
			break;
		case 4:
			img = img1;
			break;
		case 5:
			img = img5;
			break;
		case 6:
			img = img5;
			break;
		case 7:
			img = img0;
			break;
		case 8:
			img = img3;
			break;
		case 9:
			img = img4;
			break;
		case 10:
			img = img1;
			break;
			
		}
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
        height = getHeight();
        width = getWidth();
 
	     int bx = (int) (((double) (getWidth() - width)) / 2.0);
	     int by = (int) (((double) (getHeight() - height)) / 2.0);
	     gOffScreen.drawImage(img, bx, by, width, height, this);
		/*Color c=gOffScreen.getColor();
		gOffScreen.setColor(new Color(0,220,0));
		gOffScreen.fillRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		*/
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 这个方法显示主窗口
	 */
	
	public void launchFrame() {
		//将屏幕分成5*2，在每个区域随机生成1个坦克
		//tanks.add(new Tank(100,100,false,Direction.D,this,false,false));
		/*
		for(int i=0;i<5;i++) {
			int x=tcr.nextInt(140)+160*i;
			int y=tcr.nextInt(250)+50;
			boolean ptTrack = false;
			while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
				x=tcr.nextInt(140)+160*i;
				y=tcr.nextInt(250)+50;
			}
			tanks.add(new Tank(x,y,false,Direction.D,this,ptTrack,false));
		}
		*/

		tanks.add(new Tank(30,100,false,Direction.D,this,false,false));
		tanks.add(new Tank(300,200,false,Direction.D,this,false,false));
		tanks.add(new Tank(900,150,false,Direction.D,this,false,false));
		tanks.add(new Tank(150,700,false,Direction.D,this,false,false));
		tanks.add(new Tank(900,650,false,Direction.D,this,false,false));

		/*
		for(int i=5;i<10;i++) {
			int x=tcr.nextInt(140)+160*(i-5);
			int y=tcr.nextInt(250)+300;
			while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
				x=tcr.nextInt(140)+160*(i-5);
				y=tcr.nextInt(250)+300;
			}
			tanks.add(new Tank(x,y,false,Direction.D,this));
		}
		*/
		this.setLocation(200,0);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		//匿名类 关闭窗口
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setTitle("TankWar");
		this.setResizable(false);
		this.setBackground(new Color(0,220,0));
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}
		
	}
	
}
