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
 * ����̹�˵�������Ϣ��
 * @author wangshiqihaha
 */
public class TankNewMsg implements Msg {
	int msgType = Msg.TANK_NEW_MSG;

	Tank tank;

	TankClient tc;
	
	
	
	/**
	 * ����tank����Ϣ������Ϣ
	 * @param tank
	 */
	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	/**
	 * ������Ϣ�����ĳ��������µ���Ϣ
	 * @param tc
	 */
	public TankNewMsg(TankClient tc) {
		this.tc = tc;
	}
	/*myTank����tanks�������̹�˶�������tanks��
	 * ������¼����tank�����Ȼᷢһ��TankNewMsg
	 * send����֪�˷������Լ���id...����Ϣ
	 * parse����ָ���յ�������ת���Ŀͻ��ˣ��Ѿ����ڵľɿͻ��ˣ����н���packet���Ӷ���������Ϣ���л���
	 * ��󣬾ɿͻ����Լ�����Ϣ���͸���������������ת�����¿ͻ������ṩ������Ϣ
	 */
	
	/**
	 * ������ص���Ϣ
	 * @param ds ͨ����socket��������
	 * @param IP ���ݵ�Ŀ��IP
	 * @param udpPort ���ݵ�Ŀ��˿�
	 * myTank����tanks�������̹�˶�������tanks��
	 * ������¼����tank�����Ȼᷢһ��TankNewMsg
	 * send����֪�˷������Լ���id...����Ϣ
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
	 * �������յ�����Ϣ����
	 * @param dis ���յ�����Ϣ���ݵ�������
	 * parse����ָ���յ�������ת���Ŀͻ��ˣ��Ѿ����ڵľɿͻ��ˣ����н���packet���Ӷ���������Ϣ���л���
	 * ��󣬾ɿͻ����Լ�����Ϣ���͸���������������ת�����¿ͻ������ṩ������Ϣ
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
				
				
				//����һ��̹�ˣ�����һ��Ѫ��
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
