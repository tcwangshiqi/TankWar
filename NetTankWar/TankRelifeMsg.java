package NetTankWar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * tank复活的消息
 * @author wangshiqihaha
 */
public class TankRelifeMsg implements Msg {
	
	int msgType = Msg.TANK_RELIFE_MSG;
	
	TankClient tc;
	
	Tank tank;
	
	/**
	 * 根据申请复活的tank构建新的消息类
	 * @param m 产生消息的子弹
	 */
	public TankRelifeMsg(Tank tank) {
		this.tank = tank;
	}
	
	/**
	 * 根据消息产生的场所构建新的消息
	 * @param tc
	 */
	public TankRelifeMsg(TankClient tc) {
		this.tc=tc;
	}
	
	@Override
	/**
	 * 发送相关的消息
	 * @param ds 通过该socket发送数据
	 * @param IP 数据的目标IP
	 * @param udpPort 数据的目标端口
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
			dos.writeInt(tank.reLife);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.good);
			dos.writeBoolean(tank.reLifeMsg);
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

	@Override
	/**
	 * 分析接收到的消息数据
	 * @param dis 接收到的消息数据的输入流
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
			int reLife = dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];
			boolean good = dis.readBoolean();
			boolean reLifeMsg = dis.readBoolean();
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
				//TankNewMsg tnMsg = new TankNewMsg(tc.myTank);
				//tc.nc.send(tnMsg);

				Tank t = new Tank(x, y, good, dir, tc);
				t.id = id;
				t.reLife = reLife;
				
				tc.tanks.add(t);
				
				//产生一辆坦克，产生一个血块
				BloodNewMsg bnmsg = new BloodNewMsg(tc.b);
				tc.nc.send(bnmsg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
