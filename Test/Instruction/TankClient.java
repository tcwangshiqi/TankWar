package Test.Instruction;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	int level=0;
	
	public int testStep = 0;
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	Image img = tk.getImage(TankClient.class.getClassLoader().getResource("Images/background3.gif"));
	
	private static Random tcr=new Random();
	
	Tank myTank=new Tank(450,450,true,Direction.Stop,this);
	
	Wall w1=new Wall(300,100,20,150,this);
	Wall w2=new Wall(500,400,150,20,this);
	
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
	
	
	public void paint(Graphics g) {
		
		String s=g.getFont().getName();
		int style=g.getFont().getStyle();
		int size=g.getFont().getSize();
		Color c1=g.getColor();
		//中间
		g.setFont(new Font(s,Font.BOLD,70));
		g.setColor(new Color(178,20,60));
		g.drawString("         Instruction   ", 200, 100);
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
			g.drawString("SuperMissileNum:"+myTank.SuperMissileNum+"/"+(10+smb.eatenNum-1), 760, 400);
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 30));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("SLOW!", 780, 450);
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
			for(int i = myTank.getReLife();i<5;i++) {
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
		if(level>9) {
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
		//提示显示
		if(testStep == 0) {
			Font f=g.getFont();
			Color c=g.getColor();
			g.setFont(new Font("宋体", Font.BOLD, 25));
			g.setColor(Color.black);
			g.drawString("游戏前的注意事项：", 20, 100);
			g.drawString("(1)最好不要使用", 20, 150);
			g.drawString("搜狗拼音输入法", 20, 200);
			g.drawString("而使用标准", 20, 250);
			g.drawString("英语输入或者", 20, 300);
			g.drawString("总是锁定英语大写 ", 20, 350);
			g.drawString("(2)F2是复活，如果不能用，",20,400);
			g.drawString("请按着function再按F2",20,450);
			g.drawString("(3)请注意不要按到alt键",20,500);
			g.drawString("(4)请保持主界面为第一界面",20,550);
			g.setColor(new Color(10,80,10));
			g.setFont(new Font("Tahoma", Font.BOLD, 54));
			g.drawString("   OK!", 720, 160);
			g.drawString("Let's Try!", 720, 260);
			g.setColor(Color.orange);
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			g.drawString("+J", 370, 350);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(testStep==1) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.orange);
			g.drawString("+SPACE", 370, 350);
			g.drawString("-----", 120, 635);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(testStep==2) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.orange);
			g.drawString("+Enter:", 370, 300);
			g.drawString("don't release!", 270, 400);
			g.drawString("-----", 320, 635);
			g.setColor(c);
			g.setFont(f);
		}
		
		if(testStep==3) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.pink);
			g.drawString("+M:", 370, 200);
			g.drawString("Wasn't Attacked!", 220, 300);
			g.drawString("Limit 3 times!", 320, 400);
			g.drawString("-----", 570, 635);
			g.drawString("-----", 570, 670);
			g.setColor(c);
			g.setFont(f);

		}
		
		if(testStep==4) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(new Color(50,50,50));
			g.drawString("Eat Double Missile Bar(D):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.orange);
			g.drawString("Num was limited", 220, 380);
			g.drawString("Eat And Num Will Add ", 220, 500);
			g.setColor(Color.pink);
			g.drawString("---------", 760, 530);
			g.setColor(c);
			g.setFont(f);

		}
		
		if(testStep==5) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 46));
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.drawString("+K", 370, 300);
			g.setColor(new Color(50,50,50));
			g.drawString("Num was limited", 220, 380);
			g.drawString("Eat And Num Will Add ", 220, 500);
			g.setColor(new Color(50,50,50));
			g.drawString("--------", 760, 335);
			g.drawString("------", 760, 385);
			g.setColor(c);
			g.setFont(f);

		}
		
		if(testStep==6) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 46));
			Color c=g.getColor();
			g.setColor(new Color(90,255,180));
			g.drawString("Eat SpeedUp MISSILE BAR(Q):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.white);
			g.drawString("move quicker", 220, 380);
			g.drawString("Eat And Num Will Add ", 220, 500);
			g.setColor(new Color(50,50,50));
			g.drawString(" --------", 760, 335);
			g.drawString(" ------", 760, 385);
			g.setColor(c);
			g.setFont(f);

		}
		
		if(testStep==7) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 46));
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawString("Eat Blood Bar(B):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.pink);
			g.drawString("get Blood", 220, 380);
			g.drawString("exist every level", 220, 500);
			g.setColor(Color.red);
			g.drawString("---------------", 10, 670);
			g.drawString("---------------", 10, 680);
			g.setColor(new Color(90,255,180));
			g.drawString("--------", 780, 475);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==8) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.gray);
			g.drawString("Eat Super Missile BAR(S):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.black);
			g.drawString("Num was limited", 220, 380);
			g.drawString("Eat and Num will add ", 220, 500);
			g.setColor(Color.red);
			g.drawString("---------------", 10, 670);
			g.drawString("---------------", 10, 680);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==9) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.gray);
			g.drawString("+K:", 350, 200);
			g.setColor(Color.black);
			g.drawString("Great Strength", 220, 380);
			g.drawString("But Be A Bit Slow", 220, 500);
			g.setColor(Color.gray);
			g.drawString("---------------", 760, 435);
			g.drawString("---------------", 760, 480);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==10) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.blue);
			g.drawString("Eat Iced Missile BAR(I):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.white);
			g.drawString("Enemy Will Be Iced", 220, 380);
			g.drawString("Eat And Num will add ", 220, 500);
			g.setColor(Color.GRAY);
			g.drawString("---------------", 760, 435);
			g.drawString("---------------", 760, 480);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==11) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.blue);
			g.drawString("+K:", 350, 200);
			g.setColor(Color.white);
			g.drawString("Num was limited", 220, 380);
			g.drawString("eat and Num will add ", 220, 500);
			g.setColor(Color.BLUE);
			g.drawString("---------------", 760, 435);
			g.drawString("---------------", 760, 480);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==12) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawString("Eat The First Lightening Missile BAR(L):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.yellow);
			g.drawString("three times:", 220, 380);
			g.setFont(new Font("Tahoma", Font.BOLD, 54));
			g.drawString("First!!!", 250, 480);
			g.setColor(Color.red);
			g.drawString("First!!!", 255, 480);
			g.setColor(Color.BLUE);
			g.drawString("---------------", 760, 435);
			g.drawString("---------------", 760, 480);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==13) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawString("Eat The Second Lightening Missile BAR(L):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.yellow);
			g.setFont(new Font("Tahoma", Font.BOLD, 54));
			g.drawString("the Ultimate Strength!!", 220, 380);
			g.drawString("Second!!!", 250, 480);
			g.setColor(Color.red);
			g.drawString("Second!!", 255, 480);
			g.drawString("the Ultimate Strength!!", 224, 380);
			g.drawString("---------------", 760, 575);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==14) {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 48));
			Color c=g.getColor();
			g.setColor(Color.red);
			g.drawString("Eat The Third Lightening Missile BAR(L):", 0, 200);
			g.drawString("(same color)", 320, 260);
			g.setColor(Color.yellow);
			g.setFont(new Font("Tahoma", Font.BOLD, 54));
			g.drawString("Use It Carefully!!", 220, 380);
			g.drawString("Third!!!", 250, 480);
			g.setColor(Color.red);
			g.drawString("Third!!", 255, 480);
			g.drawString("Use It Carefully!!", 224, 380);
			g.drawString("---------------", 760, 575);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==15) {
			Font f=g.getFont();
			Color c=g.getColor();
			g.setColor(Color.yellow);
			g.setFont(new Font("Tahoma", Font.BOLD, 58));
			g.drawString("+L:", 350, 200);
			g.drawString("don't realease!!!", 220, 380);
			g.setColor(Color.red);
			g.drawString("+w:", 355, 200);
			g.drawString("don't realease!!!", 224, 380);
			g.drawString("---------------", 760, 435);
			g.drawString("---------------", 760, 480);
			g.setColor(c);
			g.setFont(f);
		}
		if(testStep==16) {
			Font f=g.getFont();
			Color c=g.getColor();
			g.setColor(new Color(10,70,10));
			g.setFont(new Font("Tahoma", Font.BOLD, 54));
			g.drawString("   OK!", 620, 160);
			g.drawString("The Whole Level", 500, 260);
			g.drawString("Is Ten!", 650, 360);
			g.drawString("Good Luck!", 570, 460);
			g.drawString("Please Restart!", 540, 560);
			g.setColor(c);
			g.setFont(f);
		}
		
		
		if(tanks.size()<=0&&level<=9) {
			myTank.setSpaceNumber(0);
			myTank.setNoAttacked(true);
			myTank.setNoAttackedNum(0);
			level++;
			b.setLive(true);
			smb.setLive(false);//防止smb一直消失或看不见
			sub.setLive(false);
			imb.setLive(false);
			dmb.setLive(false);
			lmb.setLive(false);
			if(level%2==0||level ==9) nab.setLive(true);
			myTank.setLife(myTank.getLife()+20);
			
			//升级时tank的初始化
			for(int i=0;i<5;i++) {
				boolean ptTrack = false;
				boolean king = false;
				if(level == 1) {
					if(i==1) {
						ptTrack=true;
					}
				}
				if(level==2) {
					if(i==1||i==2) {
						ptTrack=true;
					}
				}
				if(level==3) {
					if(i==1||i==2||i==3) {
						ptTrack=true;
					}
				}
				if(level==4) {
					if(i==1||i==2||i==3||i==4) {
						ptTrack=true;
					}
				}
				int x=tcr.nextInt(140)+160*i;
				int y=tcr.nextInt(250)+50;
				while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
					x=tcr.nextInt(140)+160*i;
					y=tcr.nextInt(250)+50;
				}
				tanks.add(new Tank(x,y,false,Direction.D,this,ptTrack));
			}
			if(level>=5) {
				for(int i=5;i<level+1;i++) {
					boolean ptTrack = false;
					boolean king = false;
					if(level ==5) {
						if(i==9) {
							ptTrack=true;
						}
					}
					if(level == 6) {
						if(i==9||i==7) {
							ptTrack=true;
						}
					}
					if(level==7) {
						if(i==9||i==7||i==6) {
							ptTrack=true;
						}
					}
					if(level==8) {
						if(i==9||i==7||i==6||i==8) {
							ptTrack=true;
						}
					}
					if(level==9) {
						if(i==9||i==7||i==6||i==8||i==5) {
							ptTrack=true;
						}
					}
					int x=tcr.nextInt(140)+160*(i-5);
					int y=tcr.nextInt(250)+300;
					while(x>=280&&x<=340&&y>=80&&y<=270||x>=480&&x<=670&&y>=380&&y<=440) {
						x=tcr.nextInt(140)+160*(i-5);
						y=tcr.nextInt(250)+300;
					}
					tanks.add(new Tank(x,y,false,Direction.D,this,ptTrack));
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
		if(testStep==7) {
			myTank.eat(b);
		}
		if(testStep==8) {
			myTank.eat(smb);
		}
		
		if(testStep==6) {
			myTank.eat(sub);
		}
		myTank.eat(nab);
		if(testStep==10) {
			myTank.eat(imb);
		}
		if(testStep==4) {
			myTank.eat(dmb);
		}
		if(testStep==12||testStep==13||testStep==14) {
			myTank.eat(lmb);
		}
		
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
	}

	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		
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
		
		tanks.add(new Tank(100,100,false,Direction.D,this));
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
