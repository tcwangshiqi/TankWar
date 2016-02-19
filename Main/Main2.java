package Main;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wangshiqi.TankWar.Explode;


public class Main2 extends JFrame {
	private static Toolkit  tk=Toolkit.getDefaultToolkit();
	Image img = tk.getImage(Explode.class.getClassLoader().getResource("Images/БъЬт2.gif"));
			
	public Main2() {
		this.setVisible(true);
		this.setBounds(400, 300, 400, 400);
		
		JPanel jpanl = new JPanel();
		
		this.add(jpanl);
		jpanl.setBackground(Color.white);
		JButton button = new JButton("start game");
		button.setFont(new Font("Arial",Font.BOLD,17));
		button.setBounds(10, 320, 180, 40);
		JButton button2 = new JButton("online game"); 
		button2.setFont(new Font("Arial",Font.BOLD,17));
		button2.setBounds(200, 320, 180, 40);
		JButton button3 = new JButton("Instruction");
		button3.setFont(new Font("Arial",Font.BOLD,17));
		button3.setBounds(200, 280, 180, 30);
		JButton button4 = new JButton("Challenge Boss");
		button4.setFont(new Font("Arial",Font.BOLD,17));
		button4.setBounds(10, 280, 180, 30);
		JLabel label = new JLabel("add");
		label.setText("Instruction");
		label.setVisible(true);
		label.setBounds(0, 200, 150, 150);
		jpanl.setLayout(null);
		jpanl.add(button);
 		jpanl.add(button);
		jpanl.add(button2);
		jpanl.add(button3);
		jpanl.add(button4);
		jpanl.add(label);
		
		this.setVisible(true);
		
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				com.wangshiqi.TankWar.TankClient tc = new com.wangshiqi.TankWar.TankClient();
				tc.launchFrame();
				setBounds(20, 20, 100, 20);
				setVisible(false);
				
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				NetTankWar.TankClient tc = new NetTankWar.TankClient();
				tc.launchFrame();
				setVisible(false);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		
		button3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Test.Instruction.TankClient tc = new Test.Instruction.TankClient();
				tc.launchFrame();
				setVisible(false);
			}
		});
		button4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Boss.TankClient tc = new Boss.TankClient();
				tc.launchFrame();
				setVisible(false);
			}
		});
	}
	
	public void paint(Graphics g) {
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		if (height != -1 && height > getHeight())
            height = getHeight();
 
         if (width != -1 && width > getWidth())
            width = getWidth();
 
         int x = (int) (((double) (getWidth() - width)) / 2.0);
         int y = (int) (((double) (getHeight() - height)) / 2.0);
         g.drawImage(img, x, y, width, height, this);
         
	}
	
	public static void main(String args[]) {
		Main2 m = new Main2();
	}

}
