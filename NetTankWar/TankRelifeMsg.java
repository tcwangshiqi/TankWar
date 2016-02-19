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
 * tank�������Ϣ
 * @author wangshiqihaha
 */
public class TankRelifeMsg implements Msg {
	
	int msgType = Msg.TANK_RELIFE_MSG;
	
	TankClient tc;
	
	Tank tank;
	
	/**
	 * �������븴���tank�����µ���Ϣ��
	 * @param m ������Ϣ���ӵ�
	 */
	public TankRelifeMsg(Tank tank) {
		this.tank = tank;
	}
	
	/**
	 * ������Ϣ�����ĳ��������µ���Ϣ
	 * @param tc
	 */
	public TankRelifeMsg(TankClient tc) {
		this.tc=tc;
	}
	
	@Override
	/**
	 * ������ص���Ϣ
	 * @param ds ͨ����socket��������
	 * @param IP ���ݵ�Ŀ��IP
	 * @param udpPort ���ݵ�Ŀ��˿�
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
	 * �������յ�����Ϣ����
	 * @param dis ���յ�����Ϣ���ݵ�������
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
				
				//����һ��̹�ˣ�����һ��Ѫ��
				BloodNewMsg bnmsg = new BloodNewMsg(tc.b);
				tc.nc.send(bnmsg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
