package NetTankWar;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 墙类
 * @author wangshiqihaha
 *
 */
public class Wall {

	int x,y,w,h;
	TankClient tc;
	
	/**
	 * 墙的构造方法
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @param w 宽度
	 * @param h 高度
	 * @param tc 大管家的引用
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * 绘画
	 * @param g TankClient的画笔
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * 获得自己的大小
	 * @return 返回自己所在的位置和大小
	 */
	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
