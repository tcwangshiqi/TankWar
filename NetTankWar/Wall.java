package NetTankWar;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * ǽ��
 * @author wangshiqihaha
 *
 */
public class Wall {

	int x,y,w,h;
	TankClient tc;
	
	/**
	 * ǽ�Ĺ��췽��
	 * @param x ������
	 * @param y ������
	 * @param w ���
	 * @param h �߶�
	 * @param tc ��ܼҵ�����
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * �滭
	 * @param g TankClient�Ļ���
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * ����Լ��Ĵ�С
	 * @return �����Լ����ڵ�λ�úʹ�С
	 */
	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
