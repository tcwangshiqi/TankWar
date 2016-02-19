package NetTankWar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;


/**
 * 代表坦克的类
 * @author wangshiqihaha
 */
public class Tank {
	/**
	 * 每辆坦克有一个独一无二的id号
	 */
	int id;
	
	/**
	 * tank等级，杀死一辆升一级
	 */
	int level;

	/**
	 * Tank的初始横向速度
	 */
	public static final int XSPEED = 5;
	
	/**
	 *  Tank的初始纵向速度
	 */
	public static final int YSPEED = 5;

	/**
	 * Tank的宽度
	 
	 */
	public static final int WIDTH = 30;
	
	/**
	 * Tank的高度
	 */
	public static final int HEIGHT = 30;
	
	/**
	 * myTank的name
	 */
	public String name = null;

	/**
	 * Tank的好坏
	 */
	boolean good;

	/**
	 * x,y的坐标位置
	 */
	int x, y;
	
	private int oldX,oldY;

	private static Random r = new Random();

	private boolean live = true;

	private int step = r.nextInt(12) + 3;
	
	private BloodBar bb = new BloodBar();

	TankClient tc;

	boolean bL, bU, bR, bD;

	Dir dir = Dir.STOP;

	Dir ptDir = Dir.D;
	
	private int life=100;
	
	/**
	 * 复活的次数
	 */
	int reLife = 0;
	
	/**
	 * 大招的限制次数
	 */
	int spaceNumber=0;
	
	/**
	 * 是否发出复活信息
	 */
	public boolean reLifeMsg=false;
	
	/**
	 * 实际速度
	 */
	double Speed;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * 根据位置和好坏构建坦克
	 * @param x
	 * @param y
	 * @param good
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	/**
	 * 根据相关属性构建坦克
	 * @param x
	 * @param y
	 * @param good
	 * @param dir
	 * @param tc 游戏的场所
	 */
	public Tank(int x, int y, boolean good, Dir dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	/**
	 * 画出坦克
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		if (!live) {
			
				tc.tanks.remove(this);
			
			return;
		}

		Color c = g.getColor();
		if (good)
			g.setColor(Color.RED);
		else
			g.setColor(Color.white);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		bb.draw(g);

		switch (ptDir) {
		case L:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x-5, y+Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y-5);
			break;
		case RU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH+5, y+Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT+5);
			break;
		case LD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
			break;
		}

		move();
	}
	
	/**
	 * 滞留
	 * 如碰见墙或tank
	 */
	public void stay() {
		x=this.oldX;
		y=this.oldY;
	}
	
	private void move() {
		this.oldX=x;
		this.oldY=y;
		
		double XSpeed=XSPEED+0.25*level;
		double YSpeed=YSPEED+0.25*level;
		
		Speed = XSpeed;
		
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
		case STOP:
			break;
		}

		if (dir != Dir.STOP) {
			ptDir = dir;
		}

		if (x < 0) x = 0;
		if (y < 30) y = 30;
		if (x + WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - WIDTH;
		if (y + HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - HEIGHT;

		/*
		 * if(!good) { if(step == 0) { step = r.nextInt(12) + 3; Dir[] dirs =
		 * Dir.values(); dir = dirs[r.nextInt(dirs.length)]; } step --;
		 * if(r.nextInt(40) > 38) this.fire(); }
		 */

	}
	
	/**
	 * 键按下的消息处理
	 * @param e 按键事件
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	private void locateDirection() {
		Dir oldDir = this.dir;

		if (bL && !bU && !bR && !bD)
			dir = Dir.L;
		else if (bL && bU && !bR && !bD)
			dir = Dir.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Dir.U;
		else if (!bL && bU && bR && !bD)
			dir = Dir.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Dir.R;
		else if (!bL && !bU && bR && bD)
			dir = Dir.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Dir.D;
		else if (bL && !bU && !bR && bD)
			dir = Dir.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Dir.STOP;

		if (dir != oldDir) {
			TankMoveMsg msg = new TankMoveMsg(id, x, y, dir, ptDir);
			tc.nc.send(msg);
		}
	}
	
	/**
	 * 键抬起的消息处理
	 * @param e 抬键消息
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:
			if(!this.live) {
				reLifeMsg=true;
				reLife++;
				spaceNumber = 0;
				TankRelifeMsg msg=new TankRelifeMsg(this);
				tc.nc.send(msg);
				live=true;
				this.life=100;
				reLifeMsg=false;
				dir=Dir.STOP;
			}
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_Z:
			sRightFire();
			break;
		case KeyEvent.VK_X:
			sLeftFire();
			break;
		case KeyEvent.VK_Q:
			sRightFire();
			sLeftFire();
			break;
		case KeyEvent.VK_SPACE:
			if(spaceNumber<(level+1)*3) {
				spaceNumber++;
				superFire();
			}
			else fire();
			break;
		}
		locateDirection();
	}

	private Missile fire() {
		if (!live)
			return null;

		int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(id, x, y, this.good, this.ptDir, this.tc);
		tc.missiles.add(m);

		MissileNewMsg msg = new MissileNewMsg(m);
		tc.nc.send(msg);

		return m;
	}
	
	private Missile sRightFire() {
		if (!live)
			return null;

		int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new SuperMissile(id, x, y, this.good, this.ptDir, this.tc);
		tc.missiles.add(m);

		MissileNewMsg msg = new MissileNewMsg(m);
		tc.nc.send(msg);

		return m;
	}
	
	private Missile sLeftFire() {
		if (!live)
			return null;

		int x = this.x - WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y - HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new SuperMissile(id, x, y, this.good, this.ptDir, this.tc);
		tc.missiles.add(m);
		
		MissileNewMsg msg = new MissileNewMsg(m);
		tc.nc.send(msg);

		return m;
	}
	
	/**
	 * 开火
	 * @param dir 根据自身方向开火
	 * @return 产生的子弹
	 */
	public Missile fire(Dir dir) {
		if(!live) return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m=new Missile(id, x, y, this.good, dir, this.tc);
		tc.missiles.add(m);
		MissileNewMsg msg = new MissileNewMsg(m);
		tc.nc.send(msg);
		return m;
	}
	
	/**
	 * 取得坦克的外切方形
	 * @return 坦克的外切Rectangle
	 */
	public Rectangle getRech() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 检测坦克是否还活着
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	
	/**
	 * 设定坦克的生死状态
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	
	/**
	 * 撞墙
	 * @param w 被撞的墙
	 * @return 是否撞到墙
	 */
	public boolean collidesWithWall(Wall w) {
		if(live&&this.getRech().intersects(w.getRech())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * 撞tank
	 * @param tanks 被撞的所有tank
	 * @return 是否被撞
	 */
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
	
	/**
	 * space大招
	 */
	public void superFire() {
		Dir[] dirs=Dir.values();
		for(int i=0;i<8;i++) {
			fire(dirs[i]);
		}
	}

	/**
	 * @return 自身的好坏
	 */
	public boolean isGood() {
		return good;
	}
	
	private class BloodBar {
		public void draw(Graphics g) {
			String s=g.getFont().getName();
			int style=g.getFont().getStyle();
			int size=g.getFont().getSize();
			Color c=g.getColor();
			
			
			
			g.setColor(Color.red);
			g.drawRect(x-level/2*(WIDTH/100), y-10, WIDTH+level*(WIDTH/100), 8);
			int w=(WIDTH+level*(WIDTH/100))*life/(100+level*10);
			g.fillRect(x-level/2*(WIDTH/100), y-10, w, 8);
			g.setColor(c);
			
			
		}
	}
	
	/**
	 * 吃血块
	 * @param b 血块
	 * @return 是否吃到
	 */
	public boolean eat(Blood b) {
		if(live&&b.isLive()&&this.getRech().intersects(b.getRech())) {
			this.life=100;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 吃血块
	 * @param bloods 血块的List
	 * @return 是否吃到
	 */
	public boolean eat(List<Blood> bloods) {
		for(int i=0;i<bloods.size();i++) {
			Blood b = bloods.get(i);
			if(eat(b)) {
				bloods.remove(b);
				return true;
			}	
		}
		return false;
	}
 
}
