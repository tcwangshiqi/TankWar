package NetTankWar;

import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
/**
 * 代表坦克诞生的消息类
 * @author wangshiqihaha
 */
public class TankNewMsg implements Msg {
	int msgType = Msg.TANK_NEW_MSG;

	Tank tank;

	TankClient tc;
	
	
	
	/**
	 * 根据tank的信息构建消息
	 * @param tank
	 */
	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	/**
	 * 根据消息产生的场所构建新的消息
	 * @param tc
	 */
	public TankNewMsg(TankClient tc) {
		this.tc = tc;
	}
	/*myTank不在tanks里，而其余坦克都加入了tanks里
	 * 如果是新加入的tank，首先会发一个TankNewMsg
	 * send：告知了服务器自己的id...的信息
	 * parse：是指接收到服务器转发的客户端（已经存在的旧客户端）进行解析packet，从而根据其信息自行绘制
	 * 最后，旧客户将自己的信息发送给服务器，服务器转发给新客户，以提供旧有信息
	 */
	
	/**
	 * 发送相关的消息
	 * @param ds 通过该socket发送数据
	 * @param IP 数据的目标IP
	 * @param udpPort 数据的目标端口
	 * myTank不在tanks里，而其余坦克都加入了tanks里
	 * 如果是新加入的tank，首先会发一个TankNewMsg
	 * send：告知了服务器自己的id...的信息
	 */
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.getLife());
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.good);
			dos.writeBoolean(tank.reLifeMsg);
			dos.writeUTF(tank.name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(IP, udpPort));
			ds.send(dp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 分析接收到的消息数据
	 * @param dis 接收到的消息数据的输入流
	 * parse：是指接收到服务器转发的客户端（已经存在的旧客户端）进行解析packet，从而根据其信息自行绘制
	 * 最后，旧客户将自己的信息发送给服务器，服务器转发给新客户，以提供旧有信息
	 */
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			if (tc.myTank.id == id) {
				return;
			}
			
			

			int x = dis.readInt();
			int y = dis.readInt();
			int life = dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];
			boolean good = dis.readBoolean();
			boolean reLifeMsg = dis.readBoolean();
			String name = dis.readUTF();
			// System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" +
			// dir + "-good:" + good);
			boolean exist = false;
			
			for (int i = 0; i < tc.tanks.size(); i++) {
				Tank t = tc.tanks.get(i);
				if (t.id == id) {
					exist = true;
					break;
				}
				
			}

			if (!exist) {
				TankNewMsg tnMsg = new TankNewMsg(tc.myTank);
				tc.nc.send(tnMsg);

				Tank t = new Tank(x, y, good, dir, tc);
				t.id = id;
				t.setLife(life);
				t.name = name;
				tc.tanks.add(t);
				
				
				//产生一辆坦克，产生一个血块
				BloodNewMsg bnmsg = new BloodNewMsg(tc.b);
				tc.nc.send(bnmsg);
			}
			
			if(reLifeMsg) {
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
