package NetTankWar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;



/**
 * �����ӵ�����
 * @author wangshiiqihaha
 *
 */
public class Missile {
	/**
	 * �ӵ�x������ٶ�
	 */
	public static final int XSPEED = 10;
	/**
	 * �ӵ�y������ٶ�
	 */
	public static final int YSPEED = 10;
	/**
	 * �ӵ��Ŀ��
	 */
	public static final int WIDTH = 10;
	/**
	 * �ӵ��ĸ߶�
	 */
	public static final int HEIGHT = 10;

	private static int ID = 1;

	TankClient tc;

	int tankId;

	int id;

	int x, y;

	Dir dir = Dir.R;

	boolean live = true;

	boolean good;
	
	/**
	 * ����λ�õ����Թ����ӵ�
	 * @param tankId ����̹�˵�id��(���������)
	 * @param x �ӵ�������x����
	 * @param y �ӵ�������y����
	 * @param good �ӵ��������Ǻû��ǻ�
	 * @param dir �ӵ��ķ���
	 * @see Dir
	 */
	
	public Missile(int tankId, int x, int y, boolean good, Dir dir) {
		this.tankId = tankId;
		this.x = x;
		this.y = y;
		this.good = good;
		this.dir = dir;
		this.id = ID++;
	}
	
	/**
	 * ����λ�ú�TankClient�����ӵ�
	 * @param tankId
	 * @param x
	 * @param y
	 * @param good
	 * @param dir
	 * @param tc �ӵ������ĳ���
	 * @see TankClient
	 */
	public Missile(int tankId, int x, int y, boolean good, Dir dir,
			TankClient tc) {
		this(tankId, x, y, good, dir);
		this.tc = tc;
	}
	
	/**
	 * �����ӵ�
	 * @param g ����
	 */
	public void draw(Graphics g) {
		if (!live) {
			tc.missiles.remove(this);
			return;
		}

		Color c = g.getColor();
		if(good) {
			g.setColor(Color.GRAY);
		}
		else g.setColor(Color.black);
		
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		move();
	}

	private void move() {
		double XSpeed=XSPEED+0.3*tc.myTank.level;
		double YSpeed=YSPEED+0.3*tc.myTank.level;
		
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

		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH
				|| y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	
	/**
	 * ȡ���ӵ������з���
	 * @return �ӵ�������Rectangle
	 */
	public Rectangle getRech() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * ����ӵ��Ƿ�ײ��̹��
	 * @param t ������̹��
	 * @return ���ײ������true,���򷵻�false
	 */
	public boolean hitTank(Tank t) {
		if(this.live&&this.getRech().intersects(t.getRech())&&t.isLive()&&this.good!=t.isGood()) {
			/*if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0) {
					t.setLive(false);
				}
			}
			else {
				t.setLive(false);
			}*/
			t.setLife(t.getLife()-20);
			if(t.getLife()<=0) {
				t.setLive(false);
			}
			this.live=false;
			tc.explodes.add(new Explode(x, y, tc));
			return true;
		}
		return false;
	}
	
	/**
	 * ����Ƿ�ײ��һϵ��̹���е�һ��
	 * @param tanks ������̹������
	 * @return ���ײ������һ��,����true,���򷵻�false
	 */
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (this.hitTank(tanks.get(i))) {
				return true;
			}
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
	
	public boolean hitMissile(Missile m) {
		if(m.good!=this.good&&m.live&&this.live&&this.getRech().intersects(m.getRech())) {
			live=false;
			m.live=false;
			return true;
		}
		return false;
	}
	
	public boolean hitMissiles(List<Missile> missiles) {
		if(good) {
			for(int j=0;j<missiles.size();j++) {
				if(this!=missiles.get(j)) {
					if(this.hitMissile(missiles.get(j))) {
						return true;
					}
				}
			}
		}
			
		return false;
		
	}
}
