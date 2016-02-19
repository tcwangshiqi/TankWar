package Boss;

import java.awt.*;
import java.awt.Toolkit;

public class Explode {

	int x,y;
	private boolean live=true;
	
	private boolean init=false;
	
	private int explosionLevel=0;
	
	int[] diameter={4,7,12,18,26,32,49,30,14,6};
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	
	private static Image[] imgs={
		tk.getImage(Explode.class.getClassLoader().getResource("Images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Images/10.gif")),
	};
	
	int step=0;
	
	private TankClient tc;
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.explosionLevel=tc.level;
	}

	
	public void draw(Graphics g) {
		if(!init) {
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init=true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(explosionLevel>15) {
			if(step==imgs.length) {
			live=false;
			step=0;
			return;
			}
		}
		else {
			if(step==diameter.length) {
				live=false;
				step=0;
				return;
				}
		}
		
		
		if(explosionLevel<=15) {
			Color c=g.getColor();
			g.setColor(Color.orange);
			g.fillOval(x, y, diameter[step], diameter[step]);
			g.setColor(c);
		}
		else {
			g.drawImage(imgs[step], x, y, null);
		}
		
		step++;
		
	}
}
