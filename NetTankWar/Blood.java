package NetTankWar;

import java.awt.*;

/**
 * 血块类
 * @author wangshiqihaha
 *
 */
public class Blood {
	
	/**
	 * 定义位置、长和宽
	 */
	int x,y,w,h;
	
	TankClient tc;
	
	private int step=0;
	
	private boolean live=true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	/**
	 * 移动位置
	 */
	private int[][] pos={
			{20,20},{20,70},{40,130},{55,175},{67,228},{74,275},{60,300},
			{88,345},{92,390},{100,430},{105,480},{90,500},{100,550},{150,570},
			{180,540},{220,530},{250,560},
			{300,580},{340,530},{390,510},{410,500},{460,475},{500,440},{550,450},
			{600,460},{645,440},{675,420},{700,400},{735,370},{770,380},{760,335},
			{750,300},{750,267},{770,215},
			{760,180},{750,130},{740,100},{700,50},{710,70},{680,35},{635,40},{600,60},
			{540,80},{520,75},{480,100},{400,150},
			{350,200},{325,250},{305,280},{290,330},{310,360},
			{300,350},{310,345},{330,340},{350,325},{360,320},{370,300},
			{370,320},{345,350},{325,380},{300,400},{275,410},{250,400},
			{230,420},{200,390},{180,370},{200,355},{250,340},{280,356},
			{310,370},{280,320},{245,300},{200,280},{170,250},{150,220},
			{120,170},{80,150},{50,100},{30,65},{25,30}
	};
	
	public Blood() {
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
	}
	
	/**
	 * 绘画方法
	 * @param g TankClient的画笔
	 */
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c=g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 移动
	 */
	public void move() {
		step++;
		if(step==pos.length) {
			step=0;
		}
		
		x=pos[step][0];
		y=pos[step][1];
	}
	
	/**
	 * 获得大小，以方便被吃
	 * @return 返回自己所在的区域
	 */
	public Rectangle getRech() {
		return new Rectangle(x,y,w,h);
	}
}
