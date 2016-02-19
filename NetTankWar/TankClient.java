package NetTankWar;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * 坦克的游戏场所
 * @author wangshiqihaha
 *
 */
public class TankClient extends Frame {
	/**
	 * 游戏场所的宽度
	 */
	public static final int GAME_WIDTH = 800;
	
	/**
	 * 游戏场所的高度
	 */
	public static final int GAME_HEIGHT = 600;
	
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	/**
	 * 背景图片
	 */
	public Image img = tk.getImage(TankClient.class.getClassLoader().getResource("Images/background3.gif"));

	Tank myTank = new Tank(50, 50, true, Dir.STOP, this);
	
	/**
	 * 墙的构建
	 */
	Wall w1=new Wall(300,100,20,150,this);
	Wall w2=new Wall(500,400,150,20,this);

	/**
	 * 子弹列表
	 * fire一次add一个子弹
	 * 子弹死亡时，remove
	 */
	List<Missile> missiles = new ArrayList<Missile>();

	/**
	 * 爆炸列表
	 */
	List<Explode> explodes = new ArrayList<Explode>();

	/**
	 * 敌方tank列表
	 * myTank不在其中
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	
	/**
	 * 血块列表
	 */
	List<Blood> bloods = new ArrayList<Blood>();

	/**
	 * 消除绘画滞留的图画变量
	 */
	Image offScreenImage = null;

	NetClient nc = new NetClient(this);

	/**
	 * 修改端口的界面
	 */
	ConnDialog dialog = new ConnDialog();
	
	Blood b=new Blood();
	
	@Override
	/**
	 * 重写父类的重画方法
	 * 绘制界面
	 */
	public void paint(Graphics g) {
		String s = g.getFont().getName();
		int style = g.getFont().getStyle();
		int size = g.getFont().getSize();
		Color c1 = g.getColor();
		
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		//myTank
		g.setFont(new Font(s,style,25));
		g.setColor(new Color(47,120,60));
		g.drawString(""+myTank.name, 150, 50);
		g.drawString("         level:"+myTank.level, 250, 50);
		g.setFont(new Font(s,style,size));
		g.setColor(c1);
		
		g.setFont(new Font(s,style,18));
		g.drawString("myTank:",640,50);
		g.setFont(new Font(s,style,size));
		
		g.setFont(new Font(s,style,18));
		g.drawString("myTank's blood:", 5, 585);
		g.setFont(new Font(s,Font.BOLD,20));

		
		g.setFont(new Font(s,style,17));
		g.drawString("tank speed:"+myTank.Speed,680,90);
		g.setFont(new Font(s,style,size));
		
		g.setFont(new Font(s,style,17));
		g.drawString("missiles speed:"+(10+0.3*myTank.level),650,110);
		g.setFont(new Font(s,style,size));
		
		g.setFont(new Font(s,style,18));
		g.setColor(new Color(200,50,50));
		g.drawString("     reLife:"+myTank.reLife+"/5", 680, 70);
		g.setFont(new Font(s,style,size));
		g.setColor(c1);
		
		g.setFont(new Font(s,style,20));
		g.drawString("super fire:"+myTank.spaceNumber+"/"+(1+myTank.level)*3, 580, 585);
		g.setFont(new Font(s,style,size));
		
		g.setColor(Color.pink);
		g.drawRect(135, 570, 100+myTank.level*10, 20);
		int w=(100+myTank.level*10)*myTank.getLife()/(100+myTank.level*10);
		g.fillRect(135, 570, w, 20);
		g.setColor(c1);
		
		g.setFont(new Font(s,Font.BOLD,20));
		g.setColor(Color.red);
		g.drawString(myTank.getLife()+"/"+(100+myTank.level*20), 250, 585);
		g.setColor(c1);
		g.setFont(new Font(s,style,15));
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			g.drawString(""+t.name+"("+t.id+")'s life:"+t.getLife(),10,110+i*20);
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			g.setFont(new Font(s,style,18));
			g.drawString("Tank:"+t.name, 10+i*150,480);
			g.setFont(new Font(s,style,size));
			g.drawString("Tank"+t.id+"'s level:"+t.level,10+i*150,500);
			g.drawString("Tank"+t.id+"'s life:"+t.getLife(),10+i*150,520);
			g.drawString("Tank"+t.id+"'s relife:"+t.reLife+"/5",10+i*150,540);
		}
		
		
		

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			// m.hitTanks(tanks);
			
			if (m.hitTank(myTank)) {
				
				if(myTank.isLive() == false) {
					TankLevelUpMsg tlumsg = new TankLevelUpMsg(m.tankId);
					nc.send(tlumsg);
System.out.println("TankId:"+m.tankId);

				}
				
					TankDeadMsg msg = new TankDeadMsg(myTank.id, myTank.getLife(),m.tankId);
					nc.send(msg);
				
				MissileDeadMsg mdmMsg = new MissileDeadMsg(m.tankId, m.id);
				nc.send(mdmMsg);
			}
			
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
			if(m.hitMissiles(missiles)) {
				MissileDeadMsg mdmMsg = new MissileDeadMsg(m.tankId, m.id);
				nc.send(mdmMsg);
			}
			
			
			
		}

		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			//t.collidesWithTanks(tanks);
			
			t.eat(bloods);
			t.draw(g);
			Color c = g.getColor();
			if (t.good)
				g.setColor(Color.RED);
			else
				g.setColor(Color.white);
			g.drawString(t.name, t.x-5, t.y - 10);
			g.setColor(c);
		}
		
		for(int i=0;i<bloods.size();i++) {
			Blood b = bloods.get(i);
			b.draw(g);
		}
		
		if(myTank.reLife<6) {
			myTank.draw(g);
			Color c = g.getColor();
			if (myTank.good)
				g.setColor(Color.RED);
			else
				g.setColor(Color.white);
			g.drawString("myTank", myTank.x-10, myTank.y - 10);
			g.setColor(c);
		}
		else {
			Font f=g.getFont();
			g.setFont(new Font("Tahoma", Font.BOLD, 80));
			Color c=g.getColor();
			g.setColor(Color.white);
			g.drawString("Game over", 270, 350);
			g.setColor(c);
			g.setFont(f);
		}
		myTank.eat(bloods);
		
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		
		w1.draw(g);
		w2.draw(g);

	}

	@Override
	/**
	 * 重写父类的update方法用于实现双缓冲，消除画面滞留
	 */
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(800, 600);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		if (height != -1 && height > getHeight())
            height = getHeight();
		if (width != -1 && width > getWidth())
            width = getWidth();
 
         int bx = (int) (((double) (getWidth() - width)) / 2.0);
         int by = (int) (((double) (getHeight() - height)) / 2.0);
         gOffScreen.drawImage(img, bx, by, width, height, this);
		/*
         Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		*/
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	/**
	 * 显示窗口
	 *
	 */
	public void launchFrame() {

		this.setLocation(200, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);

		this.addKeyListener(new KeyMonitor());

		this.setVisible(true);

		new Thread(new PaintThread()).start();

		// nc.connect("127.0.0.1", TankServer.TCP_PORT);
	}

	/**
	 * main方法
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
 
	/**
	 * 线程启动
	 *
	 */
	class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 键盘监听器
	 */
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_C) {
				dialog.setVisible(true);
			} else {
				myTank.keyPressed(e);
			}
		}

	}

	/**
	 * 对话窗口绘制
	 */
	class ConnDialog extends Dialog {
		Button b = new Button("确定");
		
		TextField tfName = new TextField("tiancai", 6);

		TextField tfIP = new TextField("192.168.1.105", 12);

		TextField tfPort = new TextField("" + TankServer.TCP_PORT, 4);

		TextField tfMyUDPPort = new TextField("2223", 4);

		public ConnDialog() {
			super(TankClient.this, true);

			this.setLayout(new FlowLayout());
			this.add(new Label("Name:"));
			this.add(tfName);
			this.add(new Label("IP:"));
			this.add(tfIP);
			this.add(new Label("Port:"));
			this.add(tfPort);
			this.add(new Label("My UDP Port:"));
			this.add(tfMyUDPPort);
			this.add(b);
			this.setLocation(300, 300);
			/*this.add(new Label("Are you ready?"));
			this.add(new Label("let's go!"));*/
			this.pack();
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			});
			b.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String Name = tfName.getText().trim();
					String IP = tfIP.getText().trim();
					int port = Integer.parseInt(tfPort.getText().trim());
					int myUDPPort = Integer.parseInt(tfMyUDPPort.getText().trim());
					myTank.name = Name;
					nc.setUdpPort(myUDPPort);
					nc.connect(IP, port);
					setVisible(false);
				}

			});
		}

	}

}
